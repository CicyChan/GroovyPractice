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
		
	def body = message.getBody(java.lang.String) as String;
	def xml = new XmlParser().parseText(body)
	def xmlHeader = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
	def orders = xml.M_ORDERS
	def order  = orders.iterator().next()
	def xmlOutput = new StringWriter()
	def xmlNodePrinter = new XmlNodePrinter(new PrintWriter(xmlOutput))
	xmlNodePrinter.print(order)
	
	message.setBody(xmlHeader + xmlOutput.toString())
	
	return message
}