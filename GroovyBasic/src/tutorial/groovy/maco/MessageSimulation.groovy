package tutorial.groovy.maco
import com.sap.gateway.ip.core.customdev.util.Message;

def byteStream = new Byte[0]
def testByteString = 'Byte'

byteStream = testByteString.bytes

println byteStream.getClass().getName()

println testByteString.bytes
println byteStream

println new String(byteStream)