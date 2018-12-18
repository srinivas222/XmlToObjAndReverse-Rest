package com.example.process;

import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.example.domain.Customer;
import com.example.domain.Customers;
import com.thoughtworks.xstream.XStream;

public class XmlFormatProcess implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		XStream xstream = new XStream();
	    xstream.alias("customer", Customer.class);
	    xstream.alias("customers", Customers.class);
	    xstream.addImplicitCollection(Customers.class, "customers");
		Customers list = new Customers();
		Customer cust = null;
		
		
		Map<?, ?> customerDetailsMap = null;

		List<?> customerDetailsList = (List<?>) exchange.getIn().getBody();
		System.out.println(customerDetailsList);

		if (customerDetailsList != null && customerDetailsList.size() > 0) {
			for(int i=0; i<customerDetailsList.size();i++) {
				cust=new Customer();
			customerDetailsMap = (Map<?, ?>) customerDetailsList.get(i);
			cust.setCid((Integer) customerDetailsMap.get("cid"));
			cust.setCname(customerDetailsMap.get("cname").toString());
			cust.setEmail(customerDetailsMap.get("email").toString());
			cust.setLocation(customerDetailsMap.get("location").toString());
			System.out.println((Integer) customerDetailsMap.get("cid"));
			System.out.println(customerDetailsMap.get("cname").toString());
			System.out.println(customerDetailsMap.get("email").toString());
			System.out.println(customerDetailsMap.get("location").toString());
			
			list.add(cust);
			}

		}
		
	    String xml = xstream.toXML(list);
	    System.out.println(xml);
	    exchange.getIn().setBody(xml);
		/*customers.setCustomer(listCustomer);
		JAXBContext context = JAXBContext.newInstance(Customers.class);
		Marshaller m = context.createMarshaller(); // for pretty-print XML in JAXB
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		StringWriter writer = new StringWriter(); // Write to list to a writer
		m.marshal(customers, writer);
		
		exchange.getIn().setBody(m);*/

	}
}
