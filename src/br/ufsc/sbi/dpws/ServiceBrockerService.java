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

public class ServiceBrockerService extends DefaultService {

    public ServiceBrockerService(int i) {
        // Give the configID to the super class to be processed.
        super(i);

        this.addOperation(new SomaOperation());
        this.addOperation(new ServiceBrokerOperation());
    }
}

class SomaOperation extends Operation {

    static String A_VALUE = "a";
    static String B_VALUE = "b";
    static String X_VALUE = "x";

    SomaOperation() {
        super("Soma", new QName("SimpleService", DpwsServiceBrockerServer.MY_NAMESPACE));

        Element somaInputWrapperElement = new Element(new QName("SInput", DpwsServiceBrockerServer.MY_NAMESPACE));
        ComplexType somaInput = new ComplexType(new QName("SomaInputType", DpwsServiceBrockerServer.MY_NAMESPACE), ComplexType.CONTAINER_SEQUENCE);
        somaInput.addElement(new Element(new QName(A_VALUE, DpwsServiceBrockerServer.MY_NAMESPACE), SchemaUtil.getSchemaType(SchemaUtil.TYPE_DOUBLE)));
        somaInput.addElement(new Element(new QName(B_VALUE, DpwsServiceBrockerServer.MY_NAMESPACE), SchemaUtil.getSchemaType(SchemaUtil.TYPE_DOUBLE)));
        somaInputWrapperElement.setType(somaInput);
        this.setInput(somaInputWrapperElement);

        Element twoWayOut = new Element(new QName(X_VALUE, DpwsServiceBrockerServer.MY_NAMESPACE), SchemaUtil.getSchemaType(SchemaUtil.TYPE_DOUBLE));
        this.setOutput(twoWayOut);
    }

    @Override
    public ParameterValue invoke(ParameterValue parameterValues) throws InvocationException, TimeoutException {
        double a = Double.parseDouble(ParameterUtil.getString(parameterValues, A_VALUE));
        double b = Double.parseDouble(ParameterUtil.getString(parameterValues, B_VALUE));

        ParameterValue returnValue = createOutputValue();
        ParameterUtil.setString(returnValue, null, String.valueOf(a + b));

        return returnValue;
    }
}

class ServiceBrokerOperation extends Operation {

    static String INPUT_VALUE = "INPUT";
    static String OUTPUT_VALUE = "OUTPUT";

    ServiceBrokerOperation() {
        super("ServiceBroker", new QName("SimpleService", DpwsServiceBrockerServer.MY_NAMESPACE));

        Element input = new Element(new QName(INPUT_VALUE, DpwsServiceBrockerServer.MY_NAMESPACE), SchemaUtil.getSchemaType(SchemaUtil.TYPE_STRING));
        this.setInput(input);

        Element output = new Element(new QName(OUTPUT_VALUE, DpwsServiceBrockerServer.MY_NAMESPACE), SchemaUtil.getSchemaType(SchemaUtil.TYPE_STRING));
        this.setOutput(output);
    }

    @Override
    public ParameterValue invoke(ParameterValue parameterValues) throws InvocationException, TimeoutException {
        String xmlInput = ParameterUtil.getString(parameterValues, null);

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Requisicao.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            StringReader reader = new StringReader(xmlInput);
            Requisicao requisicao = (Requisicao) unmarshaller.unmarshal(reader);

            SbiDpwsClient dpwsClient = new SbiDpwsClient(requisicao);
            if (dpwsClient.getFound()) {
                requisicao.getArgumentosOutput();
                ParameterValue returnValue = createOutputValue();
                ParameterUtil.setString(returnValue, null, requisicao.toXML());
                return returnValue;
            }
            
            /*
            SbiDlnaClient dlnaClient = new SbiDlnaClient(requisicao);
            requisicao.getArgumentosOutput();
            */
            
            return null;
        } catch (JAXBException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}