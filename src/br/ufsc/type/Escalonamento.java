package br.ufsc.type;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Escalonamento")
@XmlAccessorType(XmlAccessType.FIELD)
public class Escalonamento implements Serializable {
	private static final long serialVersionUID = 1L;

	@XmlElement(name="Prioridade")
	private int prioridade;
	
	@XmlElement(name="TipoEscalonamento")
	private TipoEscalonamento tipoEscalonamento;
	
	@XmlElement(name="M")
	private int m;
	
	@XmlElement(name="K")
	private int k;
	
	@XmlElement(name="Deadline")
	private int deadline;
	
	public int getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}
	
	public TipoEscalonamento getTipoEscalonamento() {
		return tipoEscalonamento;
	}
	public void setTipoEscalonamento(TipoEscalonamento tipoEscalonamento) {
		this.tipoEscalonamento = tipoEscalonamento;
	}
	public int getM() {
		return m;
	}
	public void setM(int m) {
		this.m = m;
	}
	public int getK() {
		return k;
	}
	public void setK(int k) {
		this.k = k;
	}
	public int getDeadline() {
		return deadline;
	}
	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}
}
