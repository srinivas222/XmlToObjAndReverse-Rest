package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:common.xml")
public class GetCustomerDataByIdApplication {

	public static void main(String[] args) {
		SpringApplication.run(GetCustomerDataByIdApplication.class, args);
	}
}
