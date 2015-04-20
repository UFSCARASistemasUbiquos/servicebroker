/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.sbi.dlna;

import br.ufsc.sb.RequestToDevices;
import br.ufsc.type.Requisicao;

/**
 *
 * @author elder
 */
public class DLNARequestToDevices extends RequestToDevices
{
    public DLNARequestToDevices(Requisicao requisicao) {
        super(requisicao);
    }
    
    @Override
    public void sendResponse()
    {
    }
}
