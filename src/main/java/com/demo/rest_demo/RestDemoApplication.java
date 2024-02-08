package com.demo.rest_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;


/**************************************************************************************
 * data flow path: user inputs the data <-> capture in DTO <-> DTO replicates in Entity
 * object <-> transfer to service layer <-> repo layer 
 *
 **************************************************************************************/

@SpringBootApplication
@ComponentScan(basePackages = "com.demo.rest_demo")
public class RestDemoApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(RestDemoApplication.class, args);
	}
	
	 @Override
	    public void configurePathMatch(PathMatchConfigurer configurer) {
	           UrlPathHelper urlPathHelper = new UrlPathHelper();
	           urlPathHelper.setRemoveSemicolonContent(false);
	        configurer.setUrlPathHelper(urlPathHelper );
	    }

}
