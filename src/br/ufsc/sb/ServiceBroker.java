/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.sb;

import br.ufsc.sbi.dlna.DLNAServiceBrokerServer;
import br.ufsc.sbi.dpws.DPWSServiceBrokerServer;

/**
 *
 * @author THIAGO
 */
public class ServiceBroker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       DLNAServiceBrokerServer serverdlna = new DLNAServiceBrokerServer();
       serverdlna.start();
       
       DPWSServiceBrokerServer serverdpws = new DPWSServiceBrokerServer();
       serverdpws.start();
               
    }
    
}
