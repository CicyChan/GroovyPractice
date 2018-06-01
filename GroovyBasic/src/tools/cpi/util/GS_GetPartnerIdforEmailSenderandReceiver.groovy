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
import com.sap.it.api.ITApiFactory;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.security.cert.Certificate; 
def Message processData(Message message) {
    //get Sender Email Address 
	def inboudType = message.getHeaders().get("InboundType")
	JSONParser parser = new JSONParser();
	def service = getPartnerDirectoryService()
	
	switch(inboudType){
		case "Email":
			message = processEmailInboundMessage(message, inboudType)
			break;
		case "AS2":
			message = processAS2InboundMessage(message, inboudType)
			break;
	}
	

	def senderPid = message.getHeaders().get("senderPid");
	def receiverPid = message.getHeaders().get("receiverPid");
	def privateKeyAlias = "";
	def publicKeyAlias = "";
	 
	if (!receiverPid){
		 throw new IllegalStateException("Pid for receiver not found");
	 } else {
		//get Receiver's private key alias
		def masterData = service.getParameter("PARTNER_MASTER_DATA", receiverPid, String.class);
		Object obj = parser.parse(masterData);
		JSONObject masterDataJSON = (JSONObject) obj;
		privateKeyAlias = (String) masterDataJSON.get("PrivateKeyAlias");
		if (!privateKeyAlias){
			 throw new IllegalStateException("Private key alias for receiver not found");
		 }
		message.setHeader("receiverPrivateKeyAlias", privateKeyAlias);
	 }
	
	return message;
}
	
def processEmailInboundMessage(Message message, String inboundType) {
	//get email address from "From", e.g. get "dan.zhao03@sap.com" from "Zhao, Dan <dan.zhao03@sap.com>"
	def map = message.getHeaders();
	def emailSender = map.get("From");
	if (emailSender.find(/\<.*\>/)) {
	    emailSender = emailSender.find(/\<.*\>/).replace("<", "").replace(">", "");
	}
    
    def emailReceiver = map.get("To");
	if (emailReceiver.find(/\<.*\>/)) {
	    emailReceiver = emailReceiver.find(/\<.*\>/).replace("<", "").replace(">", "");
	}
	
	message = setTransmissionInfoToHeader(message, inboundType, emailSender, emailReceiver)	
    return message;
}

def processAS2InboundMessage(Message message, String inboundType){
	
	def senderAs2ID = message.getHeaders().get("SAP_AS2PartnerID");
	def receiverAs2ID = message.getHeaders().get("SAP_AS2OwnID");
	
	message = setTransmissionInfoToHeader(message, inboundType, senderAs2ID, receiverAs2ID);
	return message;
}

def setTransmissionInfoToHeader(Message message, String inboundType, String sender, String receiver){
	def SAPMACO = "SAPMACO";	
	
	JSONParser parser = new JSONParser();
   
   //get PD service
	def service = getPartnerDirectoryService();
	if (service == null){
		throw new IllegalStateException("Partner Directory Service not found");
	}
	
	//get Pid of sender and receiver
	def pidMappingLong = service.getParameter("MAPPING_TO_PID", SAPMACO, BinaryData.class);
	String pidMappling = new String(pidMappingLong.getData(), "utf-8");
	if (pidMappling) {
		Object obj = parser.parse(pidMappling);
	    JSONObject mappingJSON = (JSONObject) obj;
		JSONArray mappingList = (JSONArray) mappingJSON.get("MappingtoPid");
		
		for (int i = 0; i < mappingList.size(); i++) {
			JSONObject mapping = (JSONObject) mappingList.get(i);
			if ( mapping.get("MappingID").toString().equalsIgnoreCase(sender) &&
			   mapping.get("MappingType").toString().equalsIgnoreCase(inboundType)) {
			   
			   message.setHeader("senderPid", mapping.get("Pid").toString());
			}
			if ( mapping.get("MappingID").toString().equalsIgnoreCase(receiver) &&
			   mapping.get("MappingType").toString().equalsIgnoreCase(inboundType)) {
			   
			   message.setHeader("receiverPid", mapping.get("Pid").toString());
			}
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