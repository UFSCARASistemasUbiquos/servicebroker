package br.ufsc.type;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlEnum(String.class)
public enum TipoArgumento implements Serializable {
	@XmlEnumValue("String")
	String,
	@XmlEnumValue("Int")
	Int,
	@XmlEnumValue("Double")
	Double
}
