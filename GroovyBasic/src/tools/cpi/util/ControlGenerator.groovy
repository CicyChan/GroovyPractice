package tools.cpi.util
/*
 The integration developer needs to create the method processData
 This method takes Message object of package com.sap.gateway.ip.core.customdev.util
which includes helper methods useful for the content developer:
The methods available are:
	public java.lang.Object getBody()
	public void setBody(java.lang.Object exchangeBody)
	public java.util.Map<java.lang.String,java.lang.Object> getHeaders()
	public void setHeaders(java.util.Map<java.lang.String,java.lang.Object> exchangeHeaders)
	public void setHeader(java.lang.String name, java.lang.Object value)
	public java.util.Map<java.lang.String,java.lang.Object> getProperties()
	public void setProperties(java.util.Map<java.lang.String,java.lang.Object> exchangeProperties)
	   public void setProperty(java.lang.String name, java.lang.Object value)
 */
import com.sap.gateway.ip.core.customdev.util.Message;

import sap.maco.edifact.segment.UNASegement

import java.util.HashMap;

def processData(){
	
	println UNASegement.print();
}

processData()