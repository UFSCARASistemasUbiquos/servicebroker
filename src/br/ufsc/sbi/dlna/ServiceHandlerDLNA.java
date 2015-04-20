/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.sbi.dlna;

import br.ufsc.sb.IServiceHandler;
import br.ufsc.type.Argumento;
import br.ufsc.type.Requisicao;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.JDOMException;
import org.teleal.cling.UpnpService;
import org.teleal.cling.UpnpServiceImpl;
import org.teleal.cling.controlpoint.*;
import org.teleal.cling.model.action.*;
import org.teleal.cling.model.message.UpnpResponse;
import org.teleal.cling.model.message.header.UDAServiceTypeHeader;
import org.teleal.cling.model.meta.*;
import org.teleal.cling.model.types.*;
import org.teleal.cling.registry.*;

/**
 *
 * @author elder
 */
public class ServiceHandlerDLNA implements IServiceHandler
{

    
    String device, serviceName, action;
    private Requisicao retorno;
    String inputValue;
    String inputName;
    String outputName;
    boolean finished = false;
    Requisicao r = null;

    //public ServiceHandlerDLNA(String device, String service, String action, String mode, String inputValue, String inputName, String outputName)
    public ServiceHandlerDLNA(Requisicao requisicao)
    {
        r = requisicao;

        this.device = r.getDeviceName();   //device;
        this.serviceName = r.getServiceName();  //service;
        this.action = r.getOperationName();//action;

        if (!r.getArgumentosOutput().isEmpty())
            this.outputName = r.getArgumentosOutput().get(0).getNomeArgumento();    //outputName;
    }

    @Override
    public final void findAndExecute()
    {
        /*
        //System.out.println("Buscando o servico " + serviceName);
        UpnpService upnpService = new UpnpServiceImpl();

        // Add a listener for device registration events
        upnpService.getRegistry().addListener(
                createRegistryListener(upnpService));

        UDAServiceType udaType = new UDAServiceType(serviceName);
        upnpService.getControlPoint().search(new UDAServiceTypeHeader(udaType));
        */
    }

    RegistryListener createRegistryListener(final UpnpService upnpService)
    {
        return new DefaultRegistryListener()
        {
            ServiceId serviceId = new UDAServiceId(serviceName);

            @Override
            public void remoteDeviceAdded(Registry registry, RemoteDevice device)
            {
                Service service;

                if ((service = device.findService(serviceId)) != null)
                {
                    System.out.println("Service discovered: " + service);
                    try
                    {
                        executeAction(upnpService, service);
                    } catch (JDOMException ex)
                    {
                        Logger.getLogger(ServiceHandlerDLNA.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex)
                    {
                        Logger.getLogger(ServiceHandlerDLNA.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
    }

    void executeAction(UpnpService upnpService, Service newService) throws JDOMException, IOException
    {
        GenericActionInvocation genericActionInvocation = new GenericActionInvocation(newService, action);

        // Executes asynchronous in the background
        upnpService.getControlPoint().execute(
            new ActionCallback(genericActionInvocation) {

                @Override
                public void success(ActionInvocation invocation) {
                    assert invocation.getOutput().length == 0;
                    System.out.println("Successfully called action!");
                    
                    if (!r.getArgumentosOutput().isEmpty())
                    {
                        r.getArgumentosOutput().get(0).setValorArgumento(invocation.getOutput(outputName).getValue().toString());
                        retorno = r;
                    }
                    finished = true;
                }

                @Override
                public void failure(ActionInvocation invocation,
                                    UpnpResponse operation,
                                    String defaultMsg) {
                    System.err.println(defaultMsg);
                }
            }
        );
    }

    /**
     * @return the retorno
     */
    @Override
    public Requisicao getResponse()
    {
        return retorno;
    }

    class GenericActionInvocation extends ActionInvocation
    {
        GenericActionInvocation(Service service, String action) throws JDOMException, IOException
        {
            super(service.getAction(action));
            try
            {
                if (!r.getArgumentosInput().isEmpty()) {
                    for (Argumento a : r.getArgumentosInput()) {
                        setInput(a.getNomeArgumento(), a.getValorArgumento());
                    }
                }
            } catch (InvalidValueException ex)
            {
                System.err.println(ex.getMessage());
                System.exit(1);
            }
        }
    }

    @Override
    public Boolean isFinished()
    {
        return finished;
    }

    public void setFinished(boolean finished)
    {
        this.finished = finished;
    }

    
}