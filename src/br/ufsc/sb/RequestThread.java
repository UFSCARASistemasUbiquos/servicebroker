/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.sb;

/**
 *
 * @author THIAGO
 */
public class RequestThread extends Thread {
    IServiceHandler sh;
    
    public RequestThread(IServiceHandler sh) {
        this.sh = sh;
    }
    
    @Override
    public void run() {
        sh.findAndExecute();
    }
}
