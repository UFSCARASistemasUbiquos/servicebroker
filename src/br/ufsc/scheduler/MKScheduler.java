/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.scheduler;

import br.ufsc.type.Requisicao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author THIAGO
 */
public class MKScheduler {
    // Requisicao x Numero de falhas
    private final List<MKSchedulerEntry> entries = new ArrayList<>();
    private final Map<Requisicao, MKSchedulerEntry> requestEntryMap = new HashMap<>();
    
    public void add(Requisicao request) {
        request.getEscalonamento().setTempoChegada(System.currentTimeMillis());
        if (!requestEntryMap.containsKey(request))
            requestEntryMap.put(request,
                    new MKSchedulerEntry(request.getOperationName(), request.getServiceName(), request.getDeviceName(), request.getEscalonamento().getK()));
        requestEntryMap.get(request).enqueue(request);
    }
    
    public MKSchedulerEntry getMaxPriorityEntry() {
        if (entries == null || entries.isEmpty())
            return null;
        
        Integer maxPriority = Integer.MAX_VALUE;
        MKSchedulerEntry maxPriorityEntry = null;
        
        for (MKSchedulerEntry entry : entries) {
            if (entry.getPriority() < maxPriority) {
                maxPriority      = entry.getPriority();
                maxPriorityEntry = entry;
            }
        }
        
        return maxPriorityEntry;
    }
            
    public Requisicao getNext(MKSchedulerEntry entry) {
        Requisicao r;
        
        while (true) {
            r = entry.dequeue();
            
            if (r == null)
                return null;

            if (r.getEscalonamento().isDeadlineMissed()) {
                entry.fail();
            } else {
                entry.meet();
                return r;
            }
        }
    }
}
