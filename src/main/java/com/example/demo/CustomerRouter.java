package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.xstream.XStreamDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import com.example.domain.Customer;
import com.example.process.InsertProcessor;
import com.example.process.XmlFormatProcess;

@Component
public class CustomerRouter extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		restConfiguration().component("restlet").host("localhost").port("8080").bindingMode(RestBindingMode.auto);
		 
		// convert xml to java object
		Map<String, String> reference = new HashMap<String, String>();
	        reference.put("customer", Customer.class.getName());
	        XStreamDataFormat xstreamDataFormat = new XStreamDataFormat();
	        xstreamDataFormat.setAliases(reference);
	        xstreamDataFormat.setPermissions(Customer.class.getName());

		

		// ----------------Add the Customer----------------

		rest("/customerPost").post().type(Customer.class).to("direct:customerPost");
		from("direct:customerPost").log("Customer id is ${body}").unmarshal(xstreamDataFormat).process(new InsertProcessor())
				.log("the message in the headers is:${headers.id},${headers.name},${headers.email},${headers.location}")
				.setBody(simple(
						"insert into customer (cid,cname,email,location) values(${headers.id},'${headers.name}','${headers.email}','${headers.location}')"))
				.to("jdbc:datasource")
				.setBody(simple("select * from customer")).to("jdbc:datasource")
				.process(new XmlFormatProcess())
				//.marshal().xstream()
				.to("log:?level=INFO&showBody=true");

		// --------------------Update Customer------------------------

		rest("/customerUpdateById").put("{id}").type(Customer.class).to("direct:putId");
		from("direct:putId").log("Customer id is ${header.id}")
				.setBody(simple(
						"update customer set cname='${header.name}',email='${header.email}'  where cid=${header.id}"))
				.to("jdbc:datasource");

		// ------------------Deleting the customer------------------------

		rest("/customerDelById").delete("{id}").to("direct:deleteId");
		from("direct:deleteId").log("Customer id is ${header.id}")
				.setBody(simple("delete from customer where cid=${header.id}")).to("jdbc:datasource")
				// .log("Customer deleted")
				.to("log:?level=INFO&showBody=true");

		// -----------Get Customer by id----------

		rest("/customer")

		.post().type(Customer.class).to("direct:getId");
		from("direct:getId")
		.unmarshal(xstreamDataFormat).process(new InsertProcessor())
				//.log("Customer id is ${header.name}")

				.setBody(simple("select * from customer where cname='${headers.name}'")).to("jdbc:datasource")
				.process(new XmlFormatProcess())
				//.marshal(xstreamDataFormat)
				.log("count is ${body}")
				.to("log:?level=INFO&showBody=true");

		// ----------- All Customers------------

		rest("/allCustomer").get().to("direct:getAllCustomer");
		from("direct:getAllCustomer").log("Customer id is ${body}").setBody(simple("select * from customer"))
				
		.to("jdbc:datasource")
		.process(new XmlFormatProcess())
		.to("log:?level=INFO&showBody=true");

	}
	
	

}
