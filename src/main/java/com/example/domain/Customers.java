package com.example.domain;

import java.util.ArrayList;
import java.util.List;



public class Customers {
	private List<Customer> customers;
	
	public Customers() {
		customers=new ArrayList<Customer>();
	}
	public void add(Customer c) {
		customers.add(c);
	}
       
}
