package br.ufsc.sbi.dpws;

import java.io.IOException;

import org.ws4d.java.DPWSFramework;
import org.ws4d.java.service.DefaultDevice;
import org.ws4d.java.service.LocalService;
import org.ws4d.java.util.Log;

public class StartExample {
    static String	MY_NAMESPACE	= "example";

    public StartExample() {
        setUpDevice();
    }

    public void setUpDevice() {
        // DEVICES
        // Obtendo o primeiro device configurado no arquivo de propriedades inicializado.
        DefaultDevice device = new DefaultDevice(1);

        // SERVICES
        // Obtendo o primeiro service configurado no arquivo de propriedades inicializado.
        LocalService service = new TestService(1);

        // Adiciona o service ao device
        device.addService(service);

        try {
            device.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Inicializa o DPWSFramework com o arquivo de propriedades configurado.
        DPWSFramework.start(new String[] { "jmeds.properties" });

        // Desabilita o debug
        Log.setLogLevel(Log.DEBUG_LEVEL_DEBUG);

        new StartExample();

        // Inicializa um cliente do service.
        //new TestClient();
    }
}