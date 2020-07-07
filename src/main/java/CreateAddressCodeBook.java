import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;



public class CreateAddressCodeBook {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse("/Users/freehoon/Desktop/cty_11.xml");

        //root tag 확인
        document.getDocumentElement().normalize();
        System.out.println("Root Element : " + document.getDocumentElement().getNodeName());

        //파싱할 tag
        NodeList nodeList = document.getElementsByTagName("item");
        System.out.println("파싱할 리스트 수 : " + nodeList.getLength());

        List<AddressCodeDVO> lists = null;
        AddressCodeDVO addressCodeDVO = null;

        for(int i = 0 ; i < nodeList.getLength() ; i++) {
            Node node = nodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                System.out.println("##############################");

                addressCodeDVO.setCode(getTagValue("ctprvnCd", element));
                addressCodeDVO.setCodeNm(getTagValue("ctprvnNm", element));
                addressCodeDVO.setPreCode("");
                addressCodeDVO.setCodeLevel("0");
                System.out.println("ctprvnCd : " + getTagValue("ctprvnCd", element));
                System.out.println("signguCd : " + getTagValue("signguCd", element));
                System.out.println("signguNm : " + getTagValue("signguNm", element));

                lists.add(addressCodeDVO);
            }
        }


        //시도
        DBConnection.insertAddressCode(lists, "0");

    }//main



    private static String getTagValue(String tag, Element element){
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node)nodeList.item(0);
        if(node == null){
            return null;
        }
        return node.getNodeValue();
    }//getTagValue
}
