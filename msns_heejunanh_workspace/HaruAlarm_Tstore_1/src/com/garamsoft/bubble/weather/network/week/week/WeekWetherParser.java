package com.garamsoft.bubble.weather.network.week.week;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.garamsoft.bubble.weather.data.week.Body_Week;
import com.garamsoft.bubble.weather.data.week.Data_Week;
import com.garamsoft.bubble.weather.data.week.HeaderWeek;
import com.garamsoft.bubble.weather.data.week.Location;
import com.garamsoft.bubble.weather.data.week.widWeek;
import com.garamsoft.bubble.weather.network.XMLParser;

public class WeekWetherParser extends XMLParser{
	widWeek widWeek = new widWeek();
	@Override
	public void doParse(InputStream is) {
		try{
		DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document mDocument = db.parse(is);
		Element docEle = mDocument.getDocumentElement();

		NodeList widNodeList = docEle.getElementsByTagName("wid");
		/**도큐멘트 된 것을 넣는다. 그리고 wid로 된 것의 노드 리스트를 꺼낸다.*/
		if ((widNodeList != null) && (widNodeList.getLength() > 0)) {
			for (int i = 0; i < widNodeList.getLength(); i++) {
				Element head_body = (Element) widNodeList.item(i);
				/**노드리스트의 길이만큼 그것의 요소를 끌어들인다.*/
				NodeList childNodeList = head_body.getChildNodes();
				if ((childNodeList != null)&& (childNodeList.getLength() > 0)) {
					for (int j = 0; j < childNodeList.getLength(); j++) {
						Node childNode = childNodeList.item(j);
						if (!(childNode instanceof Element))
							continue;
						if(childNode.getNodeName().compareTo("header")==0){widWeek.headerWeek = parserHeader(childNode);}
						else if (childNode.getNodeName().compareTo("body") == 0) {widWeek.body_Week= parserBody(childNode);}
					}
				}
			}
		}
		}
		catch (Exception e) {
			e.getMessage();
		}
	}

	private Body_Week parserBody(Node child) {
		Body_Week body_Week = new Body_Week();
		NodeList childList = child.getChildNodes();
		if(childList!=null&&childList.getLength()>0){
			for(int i=0;i<childList.getLength();i++){
				Node childNode = childList.item(i);
				if(!(childNode instanceof Element))continue;
					if(childNode.getNodeName().compareTo("location")==0){body_Week.locations.add(parserLocation(childNode));}
			}
		}
		return body_Week;
	}

	private Location parserLocation(Node child) {
		Location location = new Location();
		NodeList chilList = child.getChildNodes();
		if(chilList!=null&&chilList.getLength()>0){
			for(int i = 0 ; i < chilList.getLength(); i++){
				Node childNode = chilList.item(i);
				if(!(childNode instanceof Element))continue;
					if(childNode.getNodeName().compareTo("data")==0){location.datas.add(parserData(childNode));}
					else if(childNode.getNodeName().compareTo("city")==0){location.city_attribute=childNode.getFirstChild().getNodeValue();}
					else if(childNode.getNodeName().compareTo("province")==0){location.province_attribute=childNode.getFirstChild().getNodeValue();}
			}
			return location;
		}
		return null;
	}
	private Data_Week parserData(Node child) {
		NodeList chilList = child.getChildNodes();
		Data_Week data_Week = new Data_Week();
		if(chilList!=null&&chilList.getLength()>0){
			for(int i = 0 ; i < chilList.getLength(); i++){
				Node childNode = chilList.item(i);
				if(!(childNode instanceof Element))
					continue;
					if(childNode.getNodeName().compareTo("numEf")==0){data_Week.numEf=childNode.getFirstChild().getNodeValue();}
					else if(childNode.getNodeName().compareTo("reliability")==0){data_Week.reliability=childNode.getFirstChild().getNodeValue();}
					else if(childNode.getNodeName().compareTo("tmEf")==0){data_Week.tmEf=childNode.getFirstChild().getNodeValue();}
					else if(childNode.getNodeName().compareTo("tmn")==0){data_Week.tmn=childNode.getFirstChild().getNodeValue();}
					else if(childNode.getNodeName().compareTo("tmx")==0){data_Week.tmx=childNode.getFirstChild().getNodeValue();}
					else if(childNode.getNodeName().compareTo("wf")==0){data_Week.wf=childNode.getFirstChild().getNodeValue();}
			}
		}
		return data_Week;
	}

	private HeaderWeek parserHeader(Node child) {
		HeaderWeek headerWeek = new HeaderWeek();
		NodeList childList = child.getChildNodes();
		if(childList!=null && childList.getLength()>0){
			for(int i =0 ; i<childList.getLength(); i++){
				Node childNode = childList.item(i);
				if(!(childNode instanceof Element))continue;
					if (childNode.getNodeName().compareTo("title") == 0) {headerWeek.title = childNode.getFirstChild().getNodeValue();}
					else if (childNode.getNodeName().compareTo("tm") == 0) {headerWeek.tm = childNode.getFirstChild().getNodeValue();}
					else if (childNode.getNodeName().compareTo("wf") == 0) {headerWeek.wf = childNode.getFirstChild().getNodeValue();}
			}
			return headerWeek;
		}
		return null;
	}

	@Override
	public Object getResult() {
		return widWeek;
	}

}
