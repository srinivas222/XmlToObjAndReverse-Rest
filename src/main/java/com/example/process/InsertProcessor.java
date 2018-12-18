package com.example.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.example.domain.Customer;

public class InsertProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Customer customer = (Customer) exchange.getIn().getBody();
		System.out.println(customer);
		exchange.getIn().setHeader("id", customer.getCid());
		exchange.getIn().setHeader("name", customer.getCname());
		exchange.getIn().setHeader("email", customer.getEmail());
		exchange.getIn().setHeader("location", customer.getLocation());
		
		
		
		

	}

}
