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
import com.sap.it.api.pd.BinaryData;
import com.sap.it.api.pd.PartnerDirectoryService;
import groovy.json.JsonSlurper
import com.sap.it.api.ITApiFactory;

def Message processData(Message message) {
	def headers = message.getHeaders();
	def edifactMessageType = headers.get("EDI_Message_Type")
	message.setHeader("isMMTAllowed", "false");
	
	determinMessageType(message, edifactMessageType)
	return message;
	   
}
def determinMessageType(Message message, String edifactMessageType){
	def SAPMACO = "SAPMACO";
   //get PD service
	def service = getPartnerDirectoryService();
	if (service == null){
		throw new IllegalStateException("Partner Directory Service not found");
	}
	
	//get Pid of sender and receiver
	def pidMappingByte = service.getParameter("MAPPING_TO_PID", SAPMACO, BinaryData.class);
	String pidMappingString = new String(pidMappingByte.getData(), "utf-8");
	
	def jsonSlurper = new JsonSlurper()
	def pidMapping = jsonSlurper.parseText(pidMappingString)
	
	def pidMappingList = pidMapping.MappingtoPid
	def iterator = pidMappingList.iterator()
	while(iterator.hasNext()){
		def mappingObject = iterator.next();
		
		if(mappingObject.MappingID == "macocloud1@gmail.com"){
			def partnerID = mappingObject.Pid
			message = setMessageDeterminationStatus(message, partnerID, edifactMessageType)
		}
	}
	
	return message;
}

def getPartnerDirectoryService(){
	def service = ITApiFactory.getApi(PartnerDirectoryService.class, null);
	
	if (service == null){
		throw new IllegalStateException("Partner Directory Service not found");
	}
	return service;
}

def setMessageDeterminationStatus(Message message, String partnerID, String edifactMessageType){
	def messageValidation = 'MESSAGE_VALIDATION';
	def service = getPartnerDirectoryService();
	if (service == null){
		throw new IllegalStateException("Partner Directory Service not found");
	}
	def MessageVlidationInByte = service.getParameter(messageValidation,partnerID, BinaryData.class);
	def messageValidationString = new String(MessageVlidationInByte.getData(), "utf-8");
	
	if(messageValidationString == null){
		message.setHeader("isMMTAllowed", "false");
	}else{
		
		def jsonSlurper = new JsonSlurper()
		def messageValidationObject = jsonSlurper.parseText(messageValidationString)
		
		def messageValidationList = messageValidationObject.MessageValidation
		
		
		def iterator = messageValidationList.iterator();
		
		while(iterator.hasNext()){
			def xsdMappingObject = iterator.next();
			if(xsdMappingObject.TechnicalMessage == edifactMessageType){
				message.setHeader("isMMTAllowed", "true");
			}
		}
		
	}
	return message;
}

