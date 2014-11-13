/**********************************************************************************
 * Copyright (c) 2010 MATERNA Information & Communications and TU Dortmund, Dpt.
 * of Computer Science, Chair 4, Distributed Systems All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 **********************************************************************************/
package br.ufsc.sbi.dpws;

import java.io.IOException;
import java.io.InputStream;

import org.ws4d.java.client.DefaultClient;
import org.ws4d.java.client.SearchParameter;
import org.ws4d.java.communication.TimeoutException;
import org.ws4d.java.eventing.EventSink;
import org.ws4d.java.eventing.EventingException;
import org.ws4d.java.service.InvocationException;
import org.ws4d.java.service.Operation;
import org.ws4d.java.service.Service;
import org.ws4d.java.service.parameter.ParameterValue;
import org.ws4d.java.service.reference.ServiceReference;
import org.ws4d.java.types.QName;
import org.ws4d.java.types.QNameSet;
import org.ws4d.java.types.URI;
import org.ws4d.java.types.URISet;
import org.ws4d.java.util.ParameterUtil;

public class TestClient extends DefaultClient {

	Service	ourService;

	Service	ourEventingService;

	TestClient() {
		// we define the service to search.
		QName serviceType = new QName("SimpleService", StartExample.MY_NAMESPACE);
		SearchParameter params = new SearchParameter();
		params.setServiceTypes(new QNameSet(serviceType));

		// we search for the defined service in the network
		searchService(params);

		// searching another service
		serviceType = new QName("EventingService", StartExample.MY_NAMESPACE);
		params = new SearchParameter();
		params.setServiceTypes(new QNameSet(serviceType));

		searchService(params);
	}

	public void useOneWayOperation() {
		// We need to get the operation from the service.
		// getAnyOperation returns the first Operation that fits the
		// specification in the parameters.
		Operation ourOneWayOperation = ourService.getAnyOperation(new QName("SimpleService", StartExample.MY_NAMESPACE), "OneWay");
		ParameterValue ourExampleValue = ourOneWayOperation.createInputValue();
		ParameterUtil.setString(ourExampleValue, null, String.valueOf(42));

		// now lets invoke our first operation
		try {
			ourOneWayOperation.invoke(ourExampleValue);
		} catch (InvocationException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	public void subscribeForNotification() {
		// load the 1st eventSink-configId
		EventSink sink = generateEventSink(1);
		try {
			sink.open();
		} catch (EventingException e) {
			e.printStackTrace();
		}

		// to subscribe we need the eventAction URIs of the events we want to
		// subscribe to.
		// It may be more than one URI.
		URISet eventActionURIs = new URISet();
		eventActionURIs.add(new URI(StartExample.MY_NAMESPACE, "EventingService/SimpleNotification"));
		try {
			// This is the actual subscription. If we would like to renew the
			// subscription or unsubscribe
			// before the duration exceeds we would have to keep a reference to
			// this ClientSubscription
			// object.
			ourEventingService.subscribe(sink, "exampleId", eventActionURIs, 500000);
		} catch (EventingException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	public void subcribeForSolicitResponse() {
		// load the 2nd eventSink-configId
		EventSink sink = generateEventSink(2);
		try {
			sink.open();
		} catch (EventingException e) {
			e.printStackTrace();
		}
		URISet eventActionURIs = new URISet();
		eventActionURIs.add(new URI(StartExample.MY_NAMESPACE, "EventingService/SolicitResponseSolicit"));
		try {
			ourEventingService.subscribe(sink, "exampleId2", eventActionURIs, 500000);
		} catch (EventingException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	public void subcribeForAttachmentEvent() {
		// load the 3rd eventSink-configId
		EventSink sink = generateEventSink(3);
		try {
			sink.open();
		} catch (EventingException e) {
			e.printStackTrace();
		}
		URISet eventActionURIs = new URISet();
		eventActionURIs.add(new URI(StartExample.MY_NAMESPACE, "EventingService/AttachmentEvent"));
		try {
			ourEventingService.subscribe(sink, "exampleId", eventActionURIs, 500000);
		} catch (EventingException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	public void useTwoWayOperation() {
		double tempDegFahrenheit = 56.2;

		Operation ourTwoWayOperation = ourService.getAnyOperation(new QName("SimpleService", StartExample.MY_NAMESPACE), "TwoWay");
		ParameterValue ourExampleValue = ourTwoWayOperation.createInputValue();
		ParameterUtil.setString(ourExampleValue, null, String.valueOf(tempDegFahrenheit));

		ParameterValue returnMessagePV;
		try {
			// We invoke our TwoWay operation. The answer will be returned by
			// the invoke method.
			returnMessagePV = ourTwoWayOperation.invoke(ourExampleValue);
			
			System.out.println("TwoWayOperation returns: " + tempDegFahrenheit + "Deg Fahrenheit are: " + ParameterUtil.getString(returnMessagePV, null) + "Deg Celsius");
		} catch (InvocationException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	// this method is called if there are search-results. A search will be
	// triggered by the developer ("searchService(new SearchParameter())")
	public void serviceFound(ServiceReference serviceRef, SearchParameter search) {
		if (serviceRef.getServiceId().equals(new URI("SimpleService"))) {
			try {
				ourService = (Service) serviceRef.getService();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}

			// try our operations
			useOneWayOperation();
			useTwoWayOperation();
		} else if (serviceRef.getServiceId().equals(new URI("EventingService"))) {
			if (ourEventingService != null) return;
			try {
				ourEventingService = (Service) serviceRef.getService();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}

			subscribeForNotification();
			subcribeForSolicitResponse();
			subcribeForAttachmentEvent();
		}
	}

	public byte[] getBytesFromStream(InputStream is) {
		byte[] re = null;
		try {
			re = new byte[is.available()];
			is.read(re);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re;
	}
}