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
import sap.maco.edifact.message.EdifactMessageStructure;
import sap.maco.edifact.segment.UNASegement;
import sap.maco.edifact.dataelement.basic.ComplexDataElement;
import sap.maco.edifact.dataelement.basic.DataElement;
import sap.maco.edifact.segment.Segment;
import sap.maco.edifact.segment.SegmentGroup;
import sap.maco.edifact.util.LoadMigHelper;
import sap.maco.edifact.factory.EdifactMessageFactory;

import java.util.HashMap;

def processData(){
	def messageName = "contrl"
	EdifactMessageStructure contrlMessage = EdifactMessageFactory.loadEdiFactMessageMig(messageName)
	
	def UNBSegment = this.getUNBSegment(contrlMessage);
	
	
	if(UNBSegment.getSegmentStructure() != null){
		UNBSegment.getSegmentStructure().getComplexDataElement("S001").getDataElement("0001").setValue("UNOC")
		UNBSegment.getSegmentStructure().getComplexDataElement("S001").getDataElement("0002").setValue("3")
		UNBSegment.getSegmentStructure().getComplexDataElement("S002").getDataElement("0004").setValue("ReceiverXYZ")
		UNBSegment.getSegmentStructure().getComplexDataElement("S002").getDataElement("0007").setValue("14")
		UNBSegment.getSegmentStructure().getComplexDataElement("S003").getDataElement("0010").setValue("SENDERABC")
		UNBSegment.getSegmentStructure().getComplexDataElement("S003").getDataElement("0007").setValue("14")
		UNBSegment.getSegmentStructure().getComplexDataElement("S004").getDataElement("0017").setValue("180307")
		UNBSegment.getSegmentStructure().getComplexDataElement("S004").getDataElement("0019").setValue("0541")
	}
	
	println(contrlMessage.printEdiFactMessage())
	
}

def Segment getUNBSegment(EdifactMessageStructure message){
	def UNBSegmentName = "UNB"
	
	return message.getSegment("UNB")
}

def Segment getUNHSegment(EdifactMessageStructure message){
	def UNHSegmentName = "UNH"
	
	return message.getSegment(UNHSegmentName)
}

def Segment getUCISegment(EdifactMessageStructure message){
	def UCISegmentName = "UCI"
	
	return message.getSegment(UCISegmentName)
}

def Segment getUNTSegment(EdifactMessageStructure message){
	def UNTSegmentName = "UNT"
	
	return message.getSegment(UNTSegmentName)
}

def Segment getUNZSegment(EdifactMessageStructure message){
	def UNZSegmentName = "UNZ"
	
	return message.getSegment(UNZSegmentName)
}

processData()
