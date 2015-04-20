package br.ufsc.type;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(String.class)
public enum TipoEscalonamento implements Serializable {
	@XmlEnumValue("Soft")
	Soft,
	@XmlEnumValue("MK")
	MK
}
