/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.sbi.dpws;

import java.io.IOException;
import org.ws4d.java.DPWSFramework;
import org.ws4d.java.service.DefaultDevice;
import org.ws4d.java.service.LocalService;
import org.ws4d.java.util.Log;

/**
 *
 * @author THIAGO
 */
public class DPWSServiceBrokerServer extends Thread {
    static String	MY_NAMESPACE	= "example";

    public void setup() {
        // DEVICES
        // Obtendo o primeiro device configurado no arquivo de propriedades inicializado.
        DefaultDevice device = new DefaultDevice(1);

        // SERVICES
        // Obtendo o primeiro service configurado no arquivo de propriedades inicializado.
        LocalService service = new ServiceBrokerService(1);

        // Adiciona o service ao device
        device.addService(service);

        try {
            device.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // Inicializa o DPWSFramework com o arquivo de propriedades configurado.
        DPWSFramework.start(new String[] { "jmeds.properties" });
        // Desabilita o debug
        Log.setLogLevel(Log.DEBUG_LEVEL_DEBUG);
        
        setup();
    }
}
