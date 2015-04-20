/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.sb;

import br.ufsc.type.Requisicao;

/**
 *
 * @author elder
 */
public interface IServiceHandler
{
    public void findAndExecute();
    public Boolean isFinished();
    public Requisicao getResponse();
}
