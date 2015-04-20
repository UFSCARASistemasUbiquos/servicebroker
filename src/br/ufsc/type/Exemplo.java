package br.ufsc.type;

public class Exemplo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
            
                Requisicao requisicao = Requisicao.create("<RequisicaoServiceBroker>"+
                        
                                                                "<DeviceTarget>Target Elder</DeviceTarget>"+
                                                                "<DeviceName>Name</DeviceName>"+
                                                                "<ServiceName>Service</ServiceName>"+

                                                                "<ArgumentosInput>"+
                                                                        "<Argumento>"+
                                                                                "<NomeArgumento>Nome</NomeArgumento>"+
                                                                                "<TipoArgumento>String</TipoArgumento>"+
                                                                                "<ValorArgumento>oi</ValorArgumento>"+
                                                                        "</Argumento>"+
                                                                "</ArgumentosInput>"+

                                                                "<Escalonamento>"+
                                                                        "<Prioridade>1</Prioridade>"+
                                                                        "<TipoEscalonamento>MK</TipoEscalonamento>"+
                                                                        "<M>1</M>"+
                                                                        "<K>2</K>"+
                                                                        "<Deadline>7</Deadline>"+
                                                                "</Escalonamento>"+

                                                        "</RequisicaoServiceBroker>");
		
                System.out.println(String.format("Device: %s, Target: %s, Service: %s", requisicao.getDeviceName(), requisicao.getDeviceSource(), requisicao.getServiceName()));
                
                Requisicao r0 = new Requisicao();
                r0.setDeviceName("D0");
                r0.getEscalonamento().setPrioridade(0);
                
                Requisicao r1 = new Requisicao();
                r1.setDeviceName("D1");
                r1.getEscalonamento().setPrioridade(1);
                
                Requisicao r2 = new Requisicao();
                r2.setDeviceName("D2");
                r2.getEscalonamento().setPrioridade(2);
                /*
                Escalonador escalonador = new EscalonadorPrioridadesImpl();
                escalonador.addRequisicao(r1);
                escalonador.addRequisicao(r2);
                escalonador.addRequisicao(r0);
                
                System.out.println(escalonador.getProxima().getDeviceName());
                System.out.println(escalonador.getProxima().getDeviceName());
                System.out.println(escalonador.getProxima().getDeviceName());
                */
                
                System.out.println(requisicao.toXML());
	}

}
