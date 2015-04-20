/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.sbi.dpws;

import br.ufsc.sb.IServiceHandler;
import br.ufsc.type.Argumento;
import br.ufsc.type.Requisicao;
import java.util.concurrent.Callable;
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
public class ServiceHandlerDPWS extends DefaultClient implements IServiceHandler {

    private Requisicao requisicao, retorno;
    private Service ourService;
    private QName serviceType;
    private QName deviceType;
    private Boolean finished = false;
    private SearchParameter params;

    public ServiceHandlerDPWS(Requisicao requisicao) {
        this.requisicao = requisicao;

        this.serviceType = new QName(requisicao.getServiceName(), DPWSServiceBrokerServer.MY_NAMESPACE);

        this.params = new SearchParameter();
        params.setServiceTypes(new QNameSet(serviceType));

        
        /*
        
         this.deviceType = new QName(requisicao.getDeviceName(), null);
         SearchParameter params = new SearchParameter();
         params.setDeviceTypes(new QNameSet(deviceType));
        
         searchDevice(params);
        
         */
    }

    public void executeOperation() {
        Operation ourOperation = ourService.getAnyOperation(this.serviceType, this.requisicao.getOperationName());
        
        if (ourOperation == null)
            return;
        
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

            this.retorno = this.requisicao;
        } catch (InvocationException | TimeoutException e) {
            e.printStackTrace();
        }
        
        finished = true;
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
     Logger.getLogger(ServiceHandlerDPWS.class.getName()).log(Level.SEVERE, null, ex);
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

    /**
     * @return the finished
     */
    @Override
    public Boolean isFinished() {
        return finished;
    }

    @Override
    public void findAndExecute()
    {
        searchService(params);
    }

    public Requisicao getResponse()
    {
        return retorno;
    }

}
