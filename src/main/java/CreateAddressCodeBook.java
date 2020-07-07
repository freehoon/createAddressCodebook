import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class CreateAddressCodeBook {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        String catId = "admi"; //cty:시군구, admi:행정동, zone:법정동
        String code = "11110";
        String url = "http://apis.data.go.kr/B553077/api/open/sdsc/baroApi?resId=dong&catId=" + catId;
        //url += "&ctprvnCd=" + code;    //시군구용 구분자
        url += "&signguCd=" + code;   //행정동,법정동용 구분자
        url += "&ServiceKey=서비스키";

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        //Document document = documentBuilder.parse("/Users/freehoon/Desktop/mega.xml");
        Document document = documentBuilder.parse(url);

        //root tag 확인
        document.getDocumentElement().normalize();
        System.out.println("Root Element : " + document.getDocumentElement().getNodeName());

        //파싱할 tag
        NodeList nodeList = document.getElementsByTagName("item");
        System.out.println("파싱할 리스트 수 : " + nodeList.getLength());

        List<AddressCodeDVO> lists = new ArrayList<AddressCodeDVO>();
        AddressCodeDVO addressCodeDVO = null;


        for(int i = 0 ; i < nodeList.getLength() ; i++) {
            Node node = nodeList.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                addressCodeDVO = new AddressCodeDVO();
                //시도
                //addressCodeDVO.setCode(getTagValue("ctprvnCd", element));
                //addressCodeDVO.setCodeNm(getTagValue("ctprvnNm", element));
                //addressCodeDVO.setPreCode("");
                //addressCodeDVO.setCodeLevel("0");

                //시군구
//                addressCodeDVO.setCode(getTagValue("signguCd", element));
//                addressCodeDVO.setCodeNm(getTagValue("signguNm", element));
//                addressCodeDVO.setPreCode(getTagValue("ctprvnCd", element));
//                addressCodeDVO.setCodeLevel("1");

                //행정동
                addressCodeDVO.setCode(getTagValue("adongCd", element));
                addressCodeDVO.setCodeNm(getTagValue("adongNm", element));
                addressCodeDVO.setPreCode(getTagValue("signguCd", element));
                addressCodeDVO.setCodeLevel("2");

                //법정동


                lists.add(addressCodeDVO);
            //    System.out.println("ctprvnCd : " + getTagValue("ctprvnCd", element));
            //    System.out.println("signguCd : " + getTagValue("ctprvnNm", element));
            }
        }
        //System.out.println(lists);

        //DB insert
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
