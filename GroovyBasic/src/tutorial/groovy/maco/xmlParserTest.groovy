package tutorial.groovy.maco
import groovy.xml.XmlUtil
def testXML='''<?xml version=\"1.0\" encoding=\"UTF-8\"?><ns0:Interchange xmlns:ns0=\"urn:sap.com:typesystem:b2b:6:un-edifact\">
    <M_ORDERS>
        <S_UNH>
            <D_0062>1</D_0062>
            <C_S009>
                <D_0065>ORDERS</D_0065>
                <D_0052>D</D_0052>
                <D_0054>01B</D_0054>
                <D_0051>UN</D_0051>
            </C_S009>
        </S_UNH>
        <S_BGM>
            <C_C002>
                <D_1001>220</D_1001>
            </C_C002>
            <C_C106>
                <D_1004>PO357893</D_1004>
            </C_C106>
            <D_1225>9</D_1225>
        </S_BGM>
        <S_DTM>
            <C_C507>
                <D_2005>2</D_2005>
                <D_2380>200808131430</D_2380>
                <D_2379>102</D_2379>
            </C_C507>
        </S_DTM>
        <S_DTM>
            <C_C507>
                <D_2005>2</D_2005>
                <D_2380>20151128</D_2380>
                <D_2379>203</D_2379>
            </C_C507>
        </S_DTM>
        <S_FTX>
            <D_4451>DEL</D_4451>
            <D_4453>1</D_4453>
            <C_C108>
                <D_4440>INCLUDE TIME IN DELIVERY DATE</D_4440>
            </C_C108>
        </S_FTX>
        <G_SG1>
            <S_RFF>
                <C_C506>
                    <D_1153>AAN</D_1153>
                    <D_1154>APPTNO123445</D_1154>
                </C_C506>
            </S_RFF>
        </G_SG1>
        <G_SG2>
            <S_NAD>
                <D_3035>AA</D_3035>
                <C_C082>
                    <D_3039>Buyer_Id_12345</D_3039>
                    <D_3055>1</D_3055>
                </C_C082>
            </S_NAD>
            <S_LOC>
                <D_3227>1</D_3227>
                <C_C517>
                    <D_3225>Buyer Place Warehouse 678</D_3225>
                    <D_3055>1</D_3055>
                </C_C517>
            </S_LOC>
            <G_SG5>
                <S_CTA>
                    <D_3139>PD</D_3139>
                    <C_C056>
                        <D_3413>BuyerEmployee1234</D_3413>
                        <D_3412>John Smith</D_3412>
                    </C_C056>
                </S_CTA>
                <S_COM>
                    <C_C076>
                        <D_3148>Buyer_email@BuyerCompABC.com</D_3148>
                        <D_3155>EM</D_3155>
                    </C_C076>
                </S_COM>
            </G_SG5>
        </G_SG2>
        <G_SG2>
            <S_NAD>
                <D_3035>AA</D_3035>
                <C_C082>
                    <D_3039>ShipTo_Id_87654</D_3039>
                    <D_3055>1</D_3055>
                </C_C082>
            </S_NAD>
            <S_LOC>
                <D_3227>1</D_3227>
                <C_C517>
                    <D_3225>ShipTo_Id_87654</D_3225>
                    <D_3055>1</D_3055>
                </C_C517>
            </S_LOC>
            <G_SG5>
                <S_CTA>
                    <D_3139>PD</D_3139>
                    <C_C056>
                        <D_3413>BuyerEmployee1234</D_3413>
                        <D_3412>John Smith</D_3412>
                    </C_C056>
                </S_CTA>
                <S_COM>
                    <C_C076>
                        <D_3148>ShipTo_Id_87654</D_3148>
                        <D_3155>EM</D_3155>
                    </C_C076>
                </S_COM>
            </G_SG5>
        </G_SG2>
        <G_SG28>
            <S_LIN>
                <D_1082>1</D_1082>
                <D_1229>1</D_1229>
                <C_C212>
                    <D_7140>1</D_7140>
                </C_C212>
            </S_LIN>
            <S_PIA>
                <D_4347>5</D_4347>
                <C_C212>
                    <D_7140>ENT-93474</D_7140>
                    <D_7143>BH</D_7143>
                </C_C212>
            </S_PIA>
            <S_IMD>
                <D_7077>F</D_7077>
                <C_C273>
                    <D_7008>Product Description</D_7008>
                </C_C273>
            </S_IMD>
            <S_MEA>
                <D_6311>AAA</D_6311>
                <C_C174>
                    <D_6411>EA</D_6411>
                    <D_6314>1</D_6314>
                </C_C174>
            </S_MEA>
            <S_QTY>
                <C_C186>
                    <D_6063>21</D_6063>
                    <D_6060>3</D_6060>
                    <D_6411>A1B</D_6411>
                </C_C186>
            </S_QTY>
            <G_SG32>
                <S_PRI>
                    <C_C509>
                        <D_5125>INV</D_5125>
                        <D_5118>3455.58</D_5118>
                    </C_C509>
                </S_PRI>
            </G_SG32>
        </G_SG28>
        <G_SG28>
            <S_LIN>
                <D_1082>2</D_1082>
                <D_1229>1</D_1229>
                <C_C212>
                    <D_7140>2</D_7140>
                </C_C212>
            </S_LIN>
            <S_PIA>
                <D_4347>5</D_4347>
                <C_C212>
                    <D_7140>PRO-23872</D_7140>
                    <D_7143>BH</D_7143>
                </C_C212>
            </S_PIA>
            <S_IMD>
                <D_7077>F</D_7077>
                <C_C273>
                    <D_7008>Product Description</D_7008>
                </C_C273>
            </S_IMD>
            <S_MEA>
                <D_6311>AAA</D_6311>
                <C_C174>
                    <D_6411>EA</D_6411>
                    <D_6314>1</D_6314>
                </C_C174>
            </S_MEA>
            <S_QTY>
                <C_C186>
                    <D_6063>21</D_6063>
                    <D_6060>5</D_6060>
                    <D_6411>A1B</D_6411>
                </C_C186>
            </S_QTY>
            <G_SG32>
                <S_PRI>
                    <C_C509>
                        <D_5125>INV</D_5125>
                        <D_5118>950.99</D_5118>
                    </C_C509>
                </S_PRI>
            </G_SG32>
        </G_SG28>
        <S_UNS>
            <D_0081>S</D_0081>
        </S_UNS>
        <S_MOA>
            <C_C516>
                <D_5025>1</D_5025>
                <D_5004>4406.57</D_5004>
            </C_C516>
        </S_MOA>
        <S_CNT>
            <C_C270>
                <D_6069>2</D_6069>
                <D_6066>2</D_6066>
            </C_C270>
        </S_CNT>
        <S_UNT>
            <D_0074>30</D_0074>
            <D_0062>1</D_0062>
        </S_UNT>
    </M_ORDERS>
</ns0:Interchange>'''

def test = new XmlParser().parseText(testXML)
assert test instanceof groovy.util.Node

def orders = test.M_ORDERS

def order= orders.iterator().next()

assert order instanceof groovy.util.Node
// Create output with all default settings.
def xmlOutput = new StringWriter()
def xmlNodePrinter = new XmlNodePrinter(new PrintWriter(xmlOutput))
xmlNodePrinter.print(order)

println xmlOutput

