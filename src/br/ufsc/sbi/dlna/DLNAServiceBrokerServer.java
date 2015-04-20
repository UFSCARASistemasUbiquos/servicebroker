/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.sbi.dlna;

import java.io.IOException;
import org.teleal.cling.UpnpService;
import org.teleal.cling.UpnpServiceImpl;
import org.teleal.cling.binding.LocalServiceBindingException;
import org.teleal.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.teleal.cling.model.DefaultServiceManager;
import org.teleal.cling.model.ValidationException;
import org.teleal.cling.model.meta.DeviceDetails;
import org.teleal.cling.model.meta.DeviceIdentity;
import org.teleal.cling.model.meta.LocalDevice;
import org.teleal.cling.model.meta.LocalService;
import org.teleal.cling.model.meta.ManufacturerDetails;
import org.teleal.cling.model.meta.ModelDetails;
import org.teleal.cling.model.types.DeviceType;
import org.teleal.cling.model.types.UDADeviceType;
import org.teleal.cling.model.types.UDN;

/**
 *
 * @author THIAGO
 */
public class DLNAServiceBrokerServer extends Thread {

    public DLNAServiceBrokerServer() {
        this.setDaemon(false);
    }

    @Override
    public void run() {
        try {

            final UpnpService upnpService = new UpnpServiceImpl();

            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    upnpService.shutdown();
                }
            });
           
            LocalDevice ld = createDevice();
            //System.out.println("Criado: \n\n " + ld);
            // Add the bound local device to the registry
            upnpService.getRegistry().addDevice(ld);
                    
            

        } catch (Exception ex) {
            System.err.println("Exception occured: " + ex);
            ex.printStackTrace(System.err);
            System.exit(1);
            
        }
    }
    

LocalDevice createDevice()
        throws ValidationException, LocalServiceBindingException, IOException {

    DeviceIdentity identity =
            new DeviceIdentity(
                    UDN.uniqueSystemIdentifier("Version 1 Service Brocker")
            );

    DeviceType type =
            new UDADeviceType("ServiceBroker", 1);

    DeviceDetails details =
            new DeviceDetails(
                    "ServiceBrokerV1",
                    new ManufacturerDetails("UFSC"),
                    new ModelDetails(
                            "ProjSistemasUbiquos",
                            "Servidor que escuta requisições externas e realiza requisições internas de serviços",
                            "v1"
                    )
            );

    LocalService<ServiceBrokerService> serviceBrokerService =
            new AnnotationLocalServiceBinder().read(ServiceBrokerService.class);

    serviceBrokerService.setManager(
            new DefaultServiceManager(serviceBrokerService, ServiceBrokerService.class)
    );

    //return new LocalDevice(identity, type, details, icon, serviceBrockerService);
    return new LocalDevice(identity, type, details, serviceBrokerService);

    /* Several services can be bound to the same device:
    return new LocalDevice(
            identity, type, details, icon,
            new LocalService[] {switchPowerService, myOtherService}
    );
    */
    
}



}


