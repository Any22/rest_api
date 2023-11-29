package com.demo.rest_demo.util;

public enum RestDemoConstant {
	
	CUSTOMER_NOT_FOUND("customer.not.found"),
	INTERNAL_SERVER_ERROR("internal.server.error"), 
	GENERAL_EXCEPTION("general.excpetion"),
	URI_BAD_REQUEST("bad.request");
    private final String type;
    
    private RestDemoConstant(String type) {
    	this.type = type ;
    }
    
    @Override 
    public String toString() {
    	return this.type;
    }
}
