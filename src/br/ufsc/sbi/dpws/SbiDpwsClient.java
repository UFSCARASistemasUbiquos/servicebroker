/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.sbi.dpws;

import br.ufsc.type.Argumento;
import br.ufsc.type.Requisicao;
import org.ws4d.java.client.DefaultClient;
import org.ws4d.java.client.SearchParameter;
import org.ws4d.java.communication.TimeoutException;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.Service;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.reference.ServiceReference;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.QNameSet;
import org.ws4d.java.types.URI;
import org.ws4d.java.util.ParameterUtil;

/**
 *
 * @author Aluno
 */
public class SbiDpwsClient extends DefaultClient {

    private Requisicao requisicao;
    private Service ourService;
    private QName serviceType;
    private QName deviceType;

    public SbiDpwsClient(Requisicao requisicao) {
        this.requisicao = requisicao;

        this.serviceType = new QName(requisicao.getServiceName(), StartExample.MY_NAMESPACE);

        SearchParameter params = new SearchParameter();
        params.setServiceTypes(new QNameSet(serviceType));

        searchService(params);
        /*
        
         this.deviceType = new QName(requisicao.getDeviceName(), null);
         SearchParameter params = new SearchParameter();
         params.setDeviceTypes(new QNameSet(deviceType));
        
         searchDevice(params);
        
         */
    }

    public void executeOperation() {
        Operation ourOperation = ourService.getAnyOperation(this.serviceType, this.requisicao.getOperationName());
        ParameterValue ourValue = ourOperation.createInputValue();

        for (Argumento a : this.requisicao.getArgumentosInput()) {
            ParameterUtil.setString(ourValue, a.getNomeArgumento(), a.getValorArgumento());
            //ParameterUtil.setString(ourExampleValue, null, String.valueOf(tempDegFahrenheit));
        }

        ParameterValue returnMessagePV;
        try {
            // We invoke our TwoWay operation. The answer will be returned by
            // the invoke method.
            returnMessagePV = ourOperation.invoke(ourValue);
            System.out.println("Operation returns: ");

            for (Argumento a : this.requisicao.getArgumentosOutput()) {
                a.setValorArgumento(ParameterUtil.getString(returnMessagePV, a.getNomeArgumento()));
            }

        } catch (InvocationException | TimeoutException e) {
            e.printStackTrace();
        }
    }
    /*
     @Override
     public void deviceFound(DeviceReference deviceRef, SearchParameter search) {
     try {
     Iterator itr = deviceRef.getDevice().getServiceReferences(new QNameSet(serviceType));
     while (itr.hasNext()) {
     ServiceReference serviceRef = (ServiceReference) itr.next();
     if (serviceRef.getServiceId().equals(new URI(requisicao.getServiceName()))) {
     ourService = (Service) serviceRef.getService();
     executeOperation();
     }
     }
     } catch (TimeoutException ex) {
     Logger.getLogger(SbiDpwsClient.class.getName()).log(Level.SEVERE, null, ex);
     }
     }
     */

    @Override
    public void serviceFound(ServiceReference serviceRef, SearchParameter search) {
        if (serviceRef.getServiceId().equals(new URI(requisicao.getServiceName()))) {

            try {
                ourService = (Service) serviceRef.getService();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }

            executeOperation();
        }
    }
}
