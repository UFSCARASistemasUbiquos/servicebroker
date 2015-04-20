/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.sbi.dpws;

import br.ufsc.sb.RequestToDevices;
import br.ufsc.type.Requisicao;

/**
 *
 * @author elder
 */
public class DPWSRequestToDevices extends RequestToDevices
{
    public DPWSRequestToDevices(Requisicao requisicao) {
        super(requisicao);
    }
    
    @Override
    public void sendResponse()
    {
    }
}
