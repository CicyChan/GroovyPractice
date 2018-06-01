import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import com.sap.it.api.pd.PartnerDirectoryService;
import com.sap.it.api.ITApiFactory;
def Message processData(Message message) {
    
    def body = message.getBody();
    def headers = message.getHeaders();
    def properties = message.getProperties();
    
    def service = ITApiFactory.getApi(PartnerDirectoryService.class, null); 
    if (service == null){
        throw new IllegalStateException("Partner Directory Service not found");
    }

    def from = headers.get("From");
    def senderArray = from.split("@");
    def Pid = "SAPCOM"//senderArray[1].replaceAll(".","").toUpperCase();
    def emailFrom = senderArray[0].replaceAll(".", "").toUpperCase();
    def id = "DANTONGSAPCOM";//emailFrom + Pid;
    
    def keyPairAlias = service.getParameter(id, Pid, String.class);
    if (keyPairAlias == null){
        throw new IllegalStateException("SAP parameter not found in the Partner Directory for the partner ID SIGNATUREKEYPAIRALIAS");      
    }
    message.setHeader("keyPairAlias", keyPairAlias);
    message.setHeader("Pid", Pid);
    
    // def algorithm = service.getParameter("SAP", "SIGNATUREALGORITHM" , String.class);
    // if (algorithm == null){
    //     throw new IllegalStateException("SAP parameter not found in the Partner Directory for the partner ID SIGNATUREALGORITHM");      
    // }
    // message.setHeader("algorithm", algorithm);
    return message;
}