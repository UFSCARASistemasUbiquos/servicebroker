package br.ufsc.type;

import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="RequisicaoServiceBroker")
@XmlAccessorType(XmlAccessType.FIELD)
public class Requisicao implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static Requisicao create(String xml) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Requisicao.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			StringReader reader = new StringReader(xml);
			return (Requisicao) unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
		}
		
		return null;
	}
        
        public String toXML() {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(Requisicao.class);
                Marshaller marshaller = jaxbContext.createMarshaller();
                StringWriter sw = new StringWriter();
                marshaller.marshal(this, sw);
                return sw.toString();
            } catch (JAXBException e) {
            }
            
            return null;
        }
	
        
	@XmlElement(name="DeviceSource")
	private String deviceSource;
	
	@XmlElement(name="DeviceName")
	private String deviceName;
	
	@XmlElement(name="ServiceName")
	private String serviceName;
        
        @XmlElement(name="OperationName")
	private String operationName;
	
	@XmlElement(name="Escalonamento")
	private Escalonamento escalonamento = new Escalonamento();
	
	@XmlElementWrapper(name="ArgumentosInput")
	@XmlElement(name="Argumento")
	private List<Argumento> argumentosInput = new ArrayList<Argumento>();
	
	@XmlElementWrapper(name="ArgumentosOutput")
	@XmlElement(name="Argumento")
	private List<Argumento> argumentosOutput = new ArrayList<Argumento>();
	
	public Requisicao() {
		super();
	}
	
	public String getDeviceSource() {
		return deviceSource;
	}
	public void setDeviceSource(String deviceSource) {
		this.deviceSource = deviceSource;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Escalonamento getEscalonamento() {
		return escalonamento;
	}

	public void setEscalonamento(Escalonamento escalonamento) {
		this.escalonamento = escalonamento;
	}

	public List<Argumento> getArgumentosInput() {
		return argumentosInput;
	}

	public void setArgumentosInput(List<Argumento> argumentosInput) {
		this.argumentosInput = argumentosInput;
	}

	public List<Argumento> getArgumentosOutput() {
		return argumentosOutput;
	}

	public void setArgumentosOutput(List<Argumento> argumentosOutput) {
		this.argumentosOutput = argumentosOutput;
	}
        
        public String getOperationName() {
            return operationName;
        }

        public void setOperationName(String operationName) {
            this.operationName = operationName;
        }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Requisicao) {
            Requisicao r = (Requisicao) other;
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
}
