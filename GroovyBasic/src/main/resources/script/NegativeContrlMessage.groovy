/*
 * The integration developer needs to create the method processData 
 * This method takes Message object of package com.sap.gateway.ip.core.customdev.util
 * which includes helper methods useful for the content developer:
 * 
 * The methods available are:
    public java.lang.Object getBody()
    
    //This method helps User to retrieve message body as specific type ( InputStream , String , byte[] ) - e.g. message.getBody(java.io.InputStream)
    public java.lang.Object getBody(java.lang.String fullyQualifiedClassName)

    public void setBody(java.lang.Object exchangeBody)

    public java.util.Map<java.lang.String,java.lang.Object> getHeaders()

    public void setHeaders(java.util.Map<java.lang.String,java.lang.Object> exchangeHeaders)

    public void setHeader(java.lang.String name, java.lang.Object value)

    public java.util.Map<java.lang.String,java.lang.Object> getProperties()

    public void setProperties(java.util.Map<java.lang.String,java.lang.Object> exchangeProperties) 

	public void setProperty(java.lang.String name, java.lang.Object value)
 * 
 */
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar;
import java.util.Date;
import sap.maco.edifact.message.EdifactMessageStructure;
import sap.maco.edifact.segment.UNASegement;
import sap.maco.edifact.dataelement.basic.ComplexDataElement;
import sap.maco.edifact.dataelement.basic.DataElement;
import sap.maco.edifact.segment.Segment;
import sap.maco.edifact.segment.SegmentGroup;
import sap.maco.edifact.segment.SegmentList
import sap.maco.edifact.util.LoadMigHelper;
import sap.maco.edifact.factory.EdifactMessageFactory;
import com.sap.it.api.ITApiFactory;
import com.sap.it.api.nrc.NumberRangeConfigurationService


def Message processData(Message message) {
	
	//Headers 
	def header = message.getHeaders();

	// create Positive control message 
	def messageName = "contrl"
	EdifactMessageStructure contrlMessage = EdifactMessageFactory.loadEdiFactMessageMig(messageName)
	
	def sendID = header.get("EDI_Sender_ID");
	def receiveID = header.get("EDI_Receiver_ID" )
	def validationLogs = header.get("ValidationLogs")
	def controlReferenceNumber = this.getInterchangeReference()
	
	def date = new Date();
	def dateFormat = new SimpleDateFormat("yyMMdd")
	def timeFormat = new SimpleDateFormat("HHmm")
	
	
	def UNBSegment = this.getSegment(contrlMessage,"UNB")
	def UNHSegment = this.getSegment(contrlMessage, "UNH")
	def UCISegment = this.getSegment(contrlMessage, "UCI")
	def UNTSegment = this.getSegment(contrlMessage, "UNT")
	def UNZSegment = this.getSegment(contrlMessage, "UNZ")
	
	if(UNBSegment.getSegmentStructure() != null){
		UNBSegment.getSegmentStructure().getComplexDataElement("S001").getDataElement("0001").setValue("UNOC")
		UNBSegment.getSegmentStructure().getComplexDataElement("S001").getDataElement("0002").setValue("3")
		UNBSegment.getSegmentStructure().getComplexDataElement("S002").getDataElement("0004").setValue(receiveID)
		UNBSegment.getSegmentStructure().getComplexDataElement("S002").getDataElement("0007").setValue("14")
		UNBSegment.getSegmentStructure().getComplexDataElement("S003").getDataElement("0010").setValue(sendID)
		UNBSegment.getSegmentStructure().getComplexDataElement("S003").getDataElement("0007").setValue("14")
		UNBSegment.getSegmentStructure().getComplexDataElement("S004").getDataElement("0017").setValue(dateFormat.format(date))
		UNBSegment.getSegmentStructure().getComplexDataElement("S004").getDataElement("0019").setValue(timeFormat.format(date))
		UNBSegment.getSegmentStructure().getDataElement("0020").setValue(controlReferenceNumber);
		UNBSegment.getSegmentStructure().getDataElement("0035").setValue("1");
	}
	
	
	if(UNHSegment.getSegmentStructure() != null){
		UNHSegment.getSegmentStructure().getDataElement("0062").setValue("1");
		UNHSegment.getSegmentStructure().getComplexDataElement("S009").getDataElement("0065").setValue("CONTRL");
		UNHSegment.getSegmentStructure().getComplexDataElement("S009").getDataElement("0052").setValue("D");
		UNHSegment.getSegmentStructure().getComplexDataElement("S009").getDataElement("0054").setValue("3");
		UNHSegment.getSegmentStructure().getComplexDataElement("S009").getDataElement("0051").setValue("UN");
		UNHSegment.getSegmentStructure().getComplexDataElement("S009").getDataElement("0057").setValue("2.0");
		
	}
	
	if(validationLogs.size() == 0 && UCISegment.getSegmentStructure() != null){
		UCISegment = createUCISegmentForPositiveContrlMessage(UCISegment, controlReferenceNumber,sendID, receiveID)
	}

	
	if(UNTSegment.getSegmentStructure() != null){
		UNTSegment.getSegmentStructure().getDataElement("0074").setValue("3");
		UNTSegment.getSegmentStructure().getDataElement("0062").setValue("1");
	
	}
	
	if(UNZSegment.getSegmentStructure() != null){
		UNZSegment.getSegmentStructure().getDataElement("0036").setValue("1");
		UNZSegment.getSegmentStructure().getDataElement("0020").setValue("1");
		
	}
	
	message.setBody(contrlMessage.printEdiFactMessage());
	return message;
}

def createUCISegmentForPositiveContrlMessage(Segment UCISegment, String controlReferenceNumber, String sendID, String receiveID){
	UCISegment.getSegmentStructure().getDataElement("0020").setValue(controlReferenceNumber);
	UCISegment.getSegmentStructure().getComplexDataElement("S002").getDataElement("0004").setValue(sendID);
	UCISegment.getSegmentStructure().getComplexDataElement("S002").getDataElement("0007").setValue("14");
	UCISegment.getSegmentStructure().getComplexDataElement("S003").getDataElement("0010").setValue(receiveID);
	UCISegment.getSegmentStructure().getComplexDataElement("S003").getDataElement("0007").setValue("14");
	UCISegment.getSegmentStructure().getDataElement("0083").setValue("7");
	
	return UCISegment;
}

def getInterchangeReference() {
    def service = ITApiFactory.getApi(NumberRangeConfigurationService.class, null);   
    if( service != null)
    {
        interchangeReference = service.getNextValuefromNumberRange("UNB_0020",null);  
    }
}
def Segment getSegment(EdifactMessageStructure message, String segmentName ){
	return message.getSegment(segmentName);
}

def SegmentGroup getSegmentGroup(EdifactMessageStructure message, String segmentName ){
	return message.getSegmentGroup(segmentName);
}
