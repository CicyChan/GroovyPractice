import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import com.sap.it.api.keystore.KeystoreService;
import java.security.Security;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.crypto.Cipher;
import com.sap.it.api.ITApiFactory;
import com.sap.it.api.pd.PartnerDirectoryService;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.Store;
import org.bouncycastle.cms.CMSEnvelopedData;
import org.bouncycastle.cms.CMSAlgorithm;
import org.bouncycastle.cms.CMSEnvelopedDataGenerator;
import org.bouncycastle.cms.CMSEnvelopedDataParser;
import org.bouncycastle.cms.CMSProcessable;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSTypedData;
import org.bouncycastle.cms.jcajce.JceKeyTransRecipientInfoGenerator;
import org.bouncycastle.cms.jcajce.JceCMSContentEncryptorBuilder;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import java.util.Iterator;
import javax.activation.DataHandler;

def Message processData(Message message) {
	
	def inboudType = message.getHeaders().get("InboundType")
	
	switch(inboudType){
		case "Email":
			message = verifyInboundEmailMessage(message)
			break;
		case "AS2":
			break;
	}
	return message;
	
}

def Message verifyInboundEmailMessage(Message message){
    //Body, to be decrypted 
    boolean verifyRet = true; 
    try {
		//Body 
// 		def body = message.getBody((byte[]).class);
		
		//Signed Data
		def headers = message.getHeaders();
        // def properties = message.getProperties();
		
 		def signedData = headers.get("SapCmsSignedData");
        // def signedData = body;
		if (signedData == null){
			throw new IllegalStateException("SapCmsSignedData is null");
		}
		
		CMSSignedData sign = new CMSSignedData(Base64.decode(signedData.getBytes("utf-8")));
// 		CMSSignedData sign = new CMSSignedData(signedData);
		
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
// 		// get signature private key from PartnerDirectoryService
		def service = ITApiFactory.getApi(PartnerDirectoryService.class, null); 
		
// 		def alias = headers.get("keyPairAlias");
		
// 		Store certs = sign.getCertificates();
		
// 		Certificate cert = (Certificate) service.getCertificate(alias);

        def senderPid = headers.get("senderPid");

        Certificate cert = service.getCertificateParameter("Public_Key", senderPid);
		
		SignerInformationStore signers = sign.getSignerInfos();
		
		Collection c = signers.getSigners();
		
		Iterator it = c.iterator();
		
		while (it.hasNext()) {
 			SignerInformation signer = (SignerInformation) it.next();
			if (signer.verify(new JcaSimpleSignerInfoVerifierBuilder()
			 //   .setProvider("BC").build(cert))) {
			    .build(cert))) {
				verifyRet = true;
			} else {
				verifyRet = false;
			}
		}
	} catch(Exception e) {
		e.printStackTrace();
		throw new IllegalStateException("Signature verification failed "+e.toString());
	}
	return message;    
}