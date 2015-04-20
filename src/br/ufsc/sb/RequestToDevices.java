/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.sb;

import br.ufsc.sbi.dlna.ServiceHandlerDLNA;
import br.ufsc.sbi.dpws.ServiceHandlerDPWS;
import br.ufsc.type.Requisicao;


/**
 *
 * @author elder
 */
public abstract class RequestToDevices extends Thread
{
    static final long TIMEOUT = 50000; //50s
    boolean timeout = false;
    boolean finished = false;
    
    IServiceHandler shFinished;
    ServiceHandlerDLNA shDlna;
    ServiceHandlerDPWS shDpws;
    
    String clienteCallback;
    String retorno;
    Requisicao requisicao;
    
    public RequestToDevices(Requisicao requisicao)
    {
        this.requisicao = requisicao;
        shDlna = new ServiceHandlerDLNA(requisicao);
        shDpws = new ServiceHandlerDPWS(requisicao);
    }
    
    @Override
    public void run()
    {        
        RequestThread dlnaRequest = new RequestThread(shDlna);
        RequestThread dpwsRequest = new RequestThread(shDpws);
        
        dlnaRequest.start();
        dpwsRequest.start();
        
        long startTime = System.currentTimeMillis();
        while(!shDlna.isFinished() || !shDpws.isFinished()) { 
            if (System.currentTimeMillis() - startTime > TIMEOUT) {
                timeout = true;
                break;
            }
        }
        
        if (timeout) {
            return;
        }
        
        shFinished = shDlna.isFinished() ? shDlna : shDpws;
        this.retorno = shFinished.getResponse().toXML();
        
        this.sendResponse();
        this.finished = true;
    }

    public abstract void sendResponse();

    public Requisicao getRequisicao()
    {
        return requisicao;
    }

    public String getRetorno()
    {
        return retorno;
    }

    public boolean isFinished()
    {
        return finished;
    }
    
    public boolean isTimeout()
    {
        return timeout;
    }
}
