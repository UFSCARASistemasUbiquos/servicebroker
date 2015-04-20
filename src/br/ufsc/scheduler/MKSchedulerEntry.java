/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.scheduler;

import br.ufsc.type.Requisicao;
import java.util.LinkedList;
import java.util.Objects;

/**
 *
 * @author THIAGO
 */
public class MKSchedulerEntry {
    private final String operationName;
    private final String serviceName;
    private final String deviceName;
    
    private final LinkedList<Requisicao> requests = new LinkedList<>();
    private final MK mk;
    
    public MKSchedulerEntry(String operationName, String serviceName, String deviceName, Integer k) {
        this.operationName = operationName;
        this.serviceName = serviceName;
        this.deviceName = deviceName;
        this.mk = new MK(k);
    }
    
    public void fail() {
        this.mk.fail();
    }
    
    public void meet() {
        this.mk.meet();
    }
    
    public void enqueue(Requisicao request) {
        requests.addLast(request);
    }
    
    public Requisicao dequeue() {
        if (requests.isEmpty())
            return null;
        return requests.removeFirst();
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof MKSchedulerEntry) {
            MKSchedulerEntry r = (MKSchedulerEntry) other;
            return this.getDeviceName().equalsIgnoreCase(r.getDeviceName()) &&
                this.getServiceName().equalsIgnoreCase(r.getServiceName()) &&
                this.getOperationName().equalsIgnoreCase(r.getOperationName());
        }
        return false;
    }    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.operationName);
        hash = 89 * hash + Objects.hashCode(this.serviceName);
        hash = 89 * hash + Objects.hashCode(this.deviceName);
        return hash;
    }

    /**
     * @return the operationName
     */
    public String getOperationName() {
        return operationName;
    }

    /**
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @return the deviceName
     */
    public String getDeviceName() {
        return deviceName;
    }
    
    public Integer getPriority() {
        return this.mk.getK() - this.mk.getM();
    }
}
