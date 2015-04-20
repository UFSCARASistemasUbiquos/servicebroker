/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.sbi.dlna;

/**
 *
 * @author elder
 */
import br.ufsc.sb.RequestToDevices;
import br.ufsc.type.Requisicao;
import org.teleal.cling.binding.annotations.*;
//import org.teleal.cling.UpnpService;

@UpnpService(serviceId =
@UpnpServiceId("ServiceBroker"),
serviceType =
@UpnpServiceType(value = "ServiceBroker", version = 1))
public class ServiceBrokerService
{
    @UpnpStateVariable(defaultValue = "idle")
    private String service;
    
    @UpnpAction
    @SuppressWarnings("empty-statement")
    public String getService(@UpnpInputArgument(name = "INPUT") String entradaXml)
    {
        Requisicao requisicao = Requisicao.create(entradaXml);
        RequestToDevices rtd = new DLNARequestToDevices(requisicao); 
        new Thread(rtd).start();
        
        while (!rtd.isFinished());
        
        return rtd.getRetorno();
    }
}
