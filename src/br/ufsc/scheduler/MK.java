/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.scheduler;

import br.ufsc.type.Requisicao;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author THIAGO
 */
public class MK {
    // if fewer than m out of any
    private Integer m;
    // k consecutive customers meet their deadlines
    private final Integer k;
    
    private final Boolean requests[];
    
    private Integer counter;
    
    public MK(Integer k) {
        this.counter = 0;
        this.k = k;
        this.m = k;
        this.requests = new Boolean[k];
        
        for (int i = 0; i < k; i++) {
            this.requests[i] = true;
        }
    }
    
    private void increment(Boolean met) {
        Boolean wasMet = this.requests[this.counter];
        
        if (wasMet && !met) {
            this.m = this.m - 1;
        } else if (!wasMet && met) {
            this.m = this.m + 1;
        }
        
        this.requests[this.counter] = met;
        this.counter = (this.counter + 1) % this.k;
    }
    
    public void meet() {
        increment(true);
    }
    
    public void fail() {
        increment(false);
    }

    /**
     * @return the m
     */
    public Integer getM() {
        return m;
    }

    /**
     * @return the k
     */
    public Integer getK() {
        return k;
    }    
}
