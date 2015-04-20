/**
 * ********************************************************************************
 * Copyright (c) 2010 MATERNA Information & Communications and TU Dortmund, Dpt.
 * of Computer Science, Chair 4, Distributed Systems All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *********************************************************************************
 */
package br.ufsc.sbi.dpws;

import br.ufsc.sb.RequestToDevices;
import br.ufsc.type.Requisicao;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.ws4d.java.communication.TimeoutException;
import org.ws4d.java.schema.ComplexType;
import org.ws4d.java.schema.Element;
import org.ws4d.java.schema.SchemaUtil;
import org.ws4d.java.service.DefaultService;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.types.QName;
import org.ws4d.java.util.ParameterUtil;

public class ServiceBrokerService extends DefaultService {

    public ServiceBrokerService(int i) {
        // Give the configID to the super class to be processed.
        super(i);

        //this.addOperation(new SomaOperation());
        this.addOperation(new ServiceBrokerOperation());
    }
}

class ServiceBrokerOperation extends Operation {

    static String INPUT_VALUE = "INPUT";
    static String OUTPUT_VALUE = "OUTPUT";

    ServiceBrokerOperation() {
        super("ServiceBroker", new QName("ServiceBrokerService", DPWSServiceBrokerServer.MY_NAMESPACE));

        Element input = new Element(new QName(INPUT_VALUE, DPWSServiceBrokerServer.MY_NAMESPACE), SchemaUtil.getSchemaType(SchemaUtil.TYPE_STRING));
        this.setInput(input);

        Element output = new Element(new QName(OUTPUT_VALUE, DPWSServiceBrokerServer.MY_NAMESPACE), SchemaUtil.getSchemaType(SchemaUtil.TYPE_STRING));
        this.setOutput(output);
    }

    @Override
    @SuppressWarnings("empty-statement")
    public ParameterValue invoke(ParameterValue parameterValues) throws InvocationException, TimeoutException {
        String entradaXml = ParameterUtil.getString(parameterValues, null);
        Requisicao requisicao = Requisicao.create(entradaXml);
        RequestToDevices rtd = new DPWSRequestToDevices(requisicao);
        rtd.run();
        /*
        while (!rtd.isFinished() && !rtd.isTimeout()) {
        }
        
        if (rtd.isTimeout())
            return null;
        */
        // TODO verificar se achou!
        ParameterValue returnValue = createOutputValue();
        if(rtd.getRetorno() != null){
             ParameterUtil.setString(returnValue, null, rtd.getRetorno());
        }
        else{
            ParameterUtil.setString(returnValue, null, null);
        }
       return returnValue;
    }
}