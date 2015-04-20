package br.ufsc.type;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Argumento")
@XmlAccessorType(XmlAccessType.FIELD)
public class Argumento implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@XmlElement(name="NomeArgumento")
	private String nomeArgumento;
	@XmlElement(name="TipoArgumento")
	private TipoArgumento tipoArgumento;
	@XmlElement(name="ValorArgumento")
	private String valorArgumento;
	
	public String getNomeArgumento() {
		return nomeArgumento;
	}
	public void setNomeArgumento(String nomeArgumento) {
		this.nomeArgumento = nomeArgumento;
	}
	public TipoArgumento getTipoArgumento() {
		return tipoArgumento;
	}
	public void setTipoArgumento(TipoArgumento tipoArgumento) {
		this.tipoArgumento = tipoArgumento;
	}
	public String getValorArgumento() {
		return valorArgumento;
	}
	public void setValorArgumento(String valorArgumento) {
		this.valorArgumento = valorArgumento;
	}
}
