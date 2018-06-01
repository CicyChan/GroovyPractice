import groovy.json.JsonSlurper
import groovy.json.StringEscapeUtils

import java.text.SimpleDateFormat

import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser

/**
 * 
 * @author spliter
 *
 */

//def from = 'xx.xxx@sap.com'
//def senderArray = Arrays.asList(from.split("@"))
//def Pid = senderArray[1].replaceAll(/\./, "")
//println Pid
//def emailFrom = senderArray[0]
//println emailFrom


/**
 * Calender
 */
Calendar calendar = Calendar.getInstance();
Date date =  calendar.getTime();

def currentMinute= calendar.get(Calendar.MINUTE) + 2

println currentMinute instanceof java.lang.Integer

SimpleDateFormat sdf = new SimpleDateFormat("HHMM");
println calendar.get(Calendar.MINUTE);
println currentMinute


def getIntgerValue(){
	return 1
}

/**
 * test Json
 */

def JSONString = "{\"MappingtoPid\": "+
                     "[{\"MappingType\":\"Email\",\"MappingID\":\"macocloud1@gmail.com\",\"MappingAddress\":\"\",\"Pid\":\"macocloud1\"},"+
					 "{\"MappingType\":\"Email\",\"MappingID\":\"dan.tong@sap.com\",\"MappingAddress\":\"\",\"Pid\":\"SAPCOM\"},"+
					 "{\"MappingType\":\"Email\",\"MappingID\":\"macocloud3@gmail.com\",\"MappingAddress\":\"\",\"Pid\":\"macocloud3\"},"+
					 "{\"MappingType\":\"Email\",\"MappingID\":\"macopartner1@gmail.com\",\"MappingAddress\":\"\",\"Pid\":\"macopartner1\"},"+
					 "{\"MappingType\":\"Email\",\"MappingID\":\"macopartner2@gmail.com\",\"MappingAddress\":\"\",\"Pid\":\"macopartner2\"},"+
					 "{\"MappingType\":\"AS2\",\"MappingID\":\"CustomerAS2ID\",\"MappingAddress\":\"CustomerAS2ID\",\"Pid\":\"macopartner1\"},"+
					 "{\"MappingType\":\"AS2\",\"MappingID\":\"SAP_Maco_MMT_AS2\",\"MappingAddress\":\"SAP_Maco_MMT_AS2\",\"Pid\":\"macocloud2\"}]}"

JSONParser parser = new JSONParser();
Object obj = parser.parse(JSONString);
JSONObject mappingJSON = (JSONObject) obj;
JSONArray emailMappingList = (JSONArray) mappingJSON.get("MappingtoPid");

for (int i = 0; i < emailMappingList.size(); i++) {
	JSONObject mapping = (JSONObject) emailMappingList.get(i);
	if ( mapping.get("MappingID").toString().equalsIgnoreCase("CustomerAS2ID") &&
			mapping.get("MappingType").toString().equalsIgnoreCase("AS2")) {
		
		println mapping.get("Pid").toString()
	}
	if ( mapping.get("MappingID").toString().equalsIgnoreCase("SAP_Maco_MMT_AS2") &&
			mapping.get("MappingType").toString().equalsIgnoreCase("AS2")) {
		
		println mapping.get("Pid").toString()
	}
}

/**
 * Test Array
 */

def allowedMessageTypes = ['ORDERS', 'ORDESP', 'INVOIC'];
ArrayList types = Arrays.asList(allowedMessageTypes)

def iterater = types.iterator();

while(iterater.hasNext()){
	def messageType = iterater.next();
	
	if(messageType == "ORDERS"){
		println messageType + ' is allowned'
	}
}


// GPath Example 

def xmlText = """
              | <root>
              |   <level>
              |      <sublevel id='1'>
              |        <keyVal>
              |          <key>mykey</key>
              |          <value>value 123</value>
              |        </keyVal>
              |      </sublevel>
              |      <sublevel id='2'>
              |        <keyVal>
              |          <key>anotherKey</key>
              |          <value>42</value>
              |        </keyVal>
              |        <keyVal>
              |          <key>mykey</key>
              |          <value>fizzbuzz</value>
              |        </keyVal>
              |      </sublevel>
              |   </level>
              | </root>
              """
def root = new XmlSlurper().parseText(xmlText.stripMargin())

def sublevels = root.'**'.findAll{node-> node.name() == 'sublevel'}*.text()

println sublevels.toString()


/**
 * JSON Slupper test
 */

def pidMappingString = '''
{"MappingtoPid":
	[{"MappingType":"Email","MappingID":"macocloud1@gmail.com","MappingAddress":"","Pid":"macocloud1"},
	{"MappingType":"Email","MappingID":"dan.tong@sap.com","MappingAddress":"","Pid":"SAPCOM"},
{"MappingType":"Email","MappingID":"macocloud3@gmail.com","MappingAddress":"","Pid":"macocloud3"},
{"MappingType":"Email","MappingID":"macopartner1@gmail.com","MappingAddress":"","Pid":"macopartner1"},
{"MappingType":"Email","MappingID":"macopartner2@gmail.com","MappingAddress":"","Pid":"macopartner2"},
{"MappingType":"AS2","MappingID":"CustomerAS2ID","MappingAddress":"CustomerAS2ID","Pid":"macopartner1"},
{"MappingType":"AS2","MappingID":"SAP_Maco_MMT_AS2","MappingAddress":"SAP_Maco_MMT_AS2","Pid":"macocloud2"}]}'''

def jsonSlurper = new JsonSlurper()
def pidMapping = jsonSlurper.parseText(pidMappingString)

//def pidMappingList = pidMapping.MappingtoPid

def pidMappingList = pidMapping.getAt("MappingtoPid")
def iterator = pidMappingList.iterator()
while(iterator.hasNext()){
	def mappingObject = iterator.next();
	
	if(mappingObject.getAt("MappingID") == "macocloud1@gmail.com"){
		def partnerID = mappingObject.Pid
		println partnerID
	}
}


def messageValidationString = '''
{"MessageValidation":[{"TechnicalMessage":"ORDERS","ValidfromDateTime":"1522739651000","MIG_XSD":"","AHB_XSD":"","VB_XSD":"SENDER_XSD"}]}'''

def messageValidationObject = jsonSlurper.parseText(messageValidationString)

def messageValidationList = messageValidationObject.MessageValidation


def iterator1 = messageValidationList.iterator();

while(iterator1.hasNext()){
	def xsdMappingObject = iterator1.next();
	
	println xsdMappingObject 
	if(xsdMappingObject.TechnicalMessage == "ORDERS"){
		println 'is right'
	}
}


//get UNH Arrays 

def edifactString = """
UNB+UNOC:3+RECID:ZZZ:RECID+SENDER ID:ZZZ:SENID+180328:0118+1++++1++1'
UNH+1+CONTRL:D:3:UN'
UCI+1+SENDERABC:14+ReceiverXYZ:14+7'
UNT+3+1'
UNH+2+CONTRL:D:3:UN'
UCI+1+SENDERABC:14+ReceiverXYZ:14+7'
UNT+3+2'
UNZ+2+1'"""

def UNHArray = edifactString.findAll(/UNH(\+\w*){2}/);
def messageType = UNHArray[0].find(/\w*$/);
println(UNHArray);
println(UNHArray.size());
println(messageType);


def testArray = new ArrayList<String>();

print testArray.size();



def test = "testMessage"

if(test.contains("test")){
	println 'test message is right '
}





					 
