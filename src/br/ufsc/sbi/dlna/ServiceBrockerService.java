/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.sbi.dlna;

/**
 *
 * @author elder
 */
import org.teleal.cling.binding.annotations.*;
//import org.teleal.cling.UpnpService;

@UpnpService(serviceId =
@UpnpServiceId("ServiceBrocker"),
serviceType =
@UpnpServiceType(value = "ServiceBrocker", version = 1))
public class ServiceBrockerService
{

    @UpnpStateVariable(defaultValue = "0")
    private boolean status = false;
    @UpnpStateVariable(defaultValue = "idle")
    private String service;
    

    @UpnpAction(out =
    @UpnpOutputArgument(name = "ResultStatus"))
    public boolean getStatus()
    {
        return status;
    }

    @UpnpAction
    public void getService(@UpnpInputArgument(name = "INPUT") String entradaXml)
    {
        new Thread(new SBT(entradaXml)).start();        
    }
}
