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

	def properties = message.getProperties();
	def splitIndex = properties.get("CamelSplitIndex");
	def validtionLogs = properties.get("ValidationLogs");
	def validationlog  = properties.get("ValidationLog");
	
	validationlog = "validationLog: " + splitIndex;
	
	if(splitIndex > 0){
         validtionLogs.add(++splitIndex)
	}
    message.setProperty("ValidationLogs", validtionLogs)
    message.setProperty("ValidationLog",validationlog)
    sleep(5000);
	
	return message
}