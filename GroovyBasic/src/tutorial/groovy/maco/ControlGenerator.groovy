package tutorial.groovy.maco
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
import sap.maco.edifact.factory.EdifactMessageFactory;

import groovy.transform.Field;

@Field int counter = 0;
@Field String interchangeReference;

import java.util.HashMap;

def processData(){
	def messageName = "contrl"
	def contrlMessage = EdifactMessageFactory.loadEdiFactMessageMig(messageName)
	
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
	
	def UNHSegment = this.getUNHSegment(contrlMessage)
	
	if(UNHSegment.getSegmentStructure() != null){
		UNHSegment.getSegmentStructure().getDataElement("0062").setValue("1");
		UNHSegment.getSegmentStructure().getComplexDataElement("S009").getDataElement("0065").setValue("CONTRL");
		UNHSegment.getSegmentStructure().getComplexDataElement("S009").getDataElement("0052").setValue("D");
		UNHSegment.getSegmentStructure().getComplexDataElement("S009").getDataElement("0054").setValue("3");
		UNHSegment.getSegmentStructure().getComplexDataElement("S009").getDataElement("0051").setValue("UN");
		UNHSegment.getSegmentStructure().getComplexDataElement("S009").getDataElement("0057").setValue("2.0");
		
	}
	def UCISegment = this.getUCISegment(contrlMessage) 
	
	if(UCISegment.getSegmentStructure() != null){
		UCISegment.getSegmentStructure().getDataElement("0020").setValue("1");
		UCISegment.getSegmentStructure().getComplexDataElement("S002").getDataElement("0004").setValue("SENDERABC");
		UCISegment.getSegmentStructure().getComplexDataElement("S002").getDataElement("0007").setValue("14");
		UCISegment.getSegmentStructure().getComplexDataElement("S003").getDataElement("0010").setValue("ReceiverXYZ");
		UCISegment.getSegmentStructure().getComplexDataElement("S003").getDataElement("0007").setValue("14");
		UCISegment.getSegmentStructure().getDataElement("0083").setValue("7");
		
	}
	
	def UNTSegment = this.getUNTSegment(contrlMessage)
	if(UNTSegment.getSegmentStructure() != null){
		UNTSegment.getSegmentStructure().getDataElement("0074").setValue("3");
		UNTSegment.getSegmentStructure().getDataElement("0062").setValue("1");
	
	}
	
	
	def UNZSegment = this.getUNZSegment(contrlMessage)
	if(UNZSegment.getSegmentStructure()){
		UNZSegment.getSegmentStructure().getDataElement("0036").setValue("1");
		UNZSegment.getSegmentStructure().getDataElement("0020").setValue("1");
		
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
