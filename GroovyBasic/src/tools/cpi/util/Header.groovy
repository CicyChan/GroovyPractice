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
import java.util.HashMap;
def Message processData(Message message) {
	
	def body = message.getBody();
	message.setBody(body + "Body is modified");
	//Headers
	def map = message.getHeaders();
	def value = map.get("oldHeader");
	message.setHeader("oldHeader", value + "modified");
	message.setHeader("newHeader", "newHeader");
	//Properties
	map = message.getProperties();
	value = map.get("oldProperty");
	message.setProperty("oldProperty", value + "modified");
	message.setProperty("newProperty", "newProperty");
	
		
	def headerMap = message.getHeaders();
	def nextRetry = headerMap.get("nextRetry");
	Calendar calendar = Calendar.getInstance();
	
	if(nextRetry == null){
		message.setHeader("nextRetry", calendar.get(Calendar.MINUTE + 1))
	}
	
	return message
}