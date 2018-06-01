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
import com.sap.it.api.pd.PartnerDirectoryService;
import com.sap.it.api.ITApiFactory;
def Message processData(Message message) {
	//Body
	   def service = getPartnerDirectoryService()
	   def headers = message.getHeaders()
	   
	   def senderPid = header.get("receiverPid");
	   def receiverPid = header.get("senderPid");

	   return message;
}

def getPartnerDirectoryService(){
	def service = ITApiFactory.getApi(PartnerDirectoryService.class, null);
	
	if (service == null){
		throw new IllegalStateException("Partner Directory Service not found");
	}
	return service;
}