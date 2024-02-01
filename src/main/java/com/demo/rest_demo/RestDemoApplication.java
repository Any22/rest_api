package com.demo.rest_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**************************************************************************************
 * data flow path: user inputs the data <-> capture in DTO <-> DTO replicates in Entity
 * object <-> transfer to service layer <-> repo layer 
 *
 **************************************************************************************/

@SpringBootApplication
@ComponentScan(basePackages = "com.demo.rest_demo")
public class RestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestDemoApplication.class, args);
	}

}
