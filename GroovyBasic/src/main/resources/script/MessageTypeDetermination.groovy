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
	//Body
	   def body = message.getBody();
	   message.setBody(body + "Body is modified");
	   //Headers
	   def headers = message.getHeaders();
	   def edifactMessageType = headers.get("EDI_Message_Type")
	   def allowedMessageTypes = ['ORDERS', 'ORDESP', 'INVOIC']
	   
	   //set isMMTAllowned to be false
	   
	   message.setHeader("isMMTAllowed", "false");
	   ArrayList types = Arrays.asList(allowedMessageTypes)
	   
	   def iterater = types.iterator();
	   
	   while(iterater.hasNext()){
		   def messageType = iterater.next();
		   
		   if(messageType == edifactMessageType){
			   message.setHeader("isMMTAllowed", "true");
		   }
	   }
	   return message;
}