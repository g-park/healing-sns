package com.garamsoft.bubble.weather.network.week.day;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.util.Log;

import com.garamsoft.bubble.weather.data.day.Header;
import com.garamsoft.bubble.weather.data.day.WeatherData;
import com.garamsoft.bubble.weather.data.day.Wid;
import com.garamsoft.bubble.weather.network.XMLParser;

public class DayWetherParser extends XMLParser {
	Wid wid = new Wid();
	String buffer = new String();
	String line;
	private InputStream input;
	private Document mDocument;
	private InputSource mInputSource;
	@Override
	public void doParse(InputStream is) {
		try {
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			 while ((line = br.readLine()) != null) {
				 buffer += line;
	            }
			 
			 Log.i("TAG",buffer);
			mInputSource = new InputSource();
			mInputSource.setCharacterStream(new StringReader(buffer));
			mDocument = db.parse(mInputSource);
//			input.close();
			
		} catch (Exception e) {
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
				}
			}
		}
		

		Element docEle = mDocument.getDocumentElement();
		NodeList widNodeList = docEle.getElementsByTagName("wid");
		
		if ((widNodeList != null) && (widNodeList.getLength() > 0)) {
			for (int i = 0; i < widNodeList.getLength(); i++) {
				Element head_body = (Element) widNodeList.item(i);
				NodeList childNodeList = head_body.getChildNodes();
				if ((childNodeList != null)&& (childNodeList.getLength() > 0)) {
					for (int j = 0; j < childNodeList.getLength(); j++) {
						Node childNode = childNodeList.item(j);
						if (!(childNode instanceof Element))continue;
						if(childNode.getNodeName().compareTo("header")==0){wid.header = parserHeader(childNode);}
						else if (childNode.getNodeName().compareTo("body") == 0) {wid.datas = parserBody(childNode);}
					}
				}
			}
		}
	}
	
	private ArrayList<WeatherData> parserBody(Node child) {
		ArrayList<WeatherData> datas = new ArrayList<WeatherData>();
		NodeList childList = child.getChildNodes();
		if(childList!=null && childList.getLength()>0){
			for(int i =0 ; i<childList.getLength(); i++){
				Node childNode = childList.item(i);
				if(childNode instanceof Element){
					if (childNode.getNodeName().compareTo("data") == 0) {
						WeatherData data = getData(childNode);
						if(data !=null)datas.add(data);
					}
				}
			}
			return datas;	
		}
		return null;
	}

	private WeatherData getData(Node childNode2) {
		WeatherData data = new WeatherData();
		NodeList childList = childNode2.getChildNodes();
		if(childList!=null && childList.getLength()>0){
			for(int i =0 ; i<childList.getLength(); i++){
				Node childNode = childList.item(i);
				if(childNode instanceof Element){
					if (childNode.getNodeName().compareTo("day") == 0) {data.day = childNode.getFirstChild().getNodeValue();}
					else if (childNode.getNodeName().compareTo("hour")==0){data.hour = childNode.getFirstChild().getNodeValue();}
					else if (childNode.getNodeName().compareTo("pop")==0){data.pop =childNode.getFirstChild().getNodeValue();}
					else if (childNode.getNodeName().compareTo("pty")==0){data.pty = childNode.getFirstChild().getNodeValue();}
					else if (childNode.getNodeName().compareTo("r12")==0){data.r12 = childNode.getFirstChild().getNodeValue();}
					else if (childNode.getNodeName().compareTo("reh")==0){data.reh = childNode.getFirstChild().getNodeValue();}
					else if (childNode.getNodeName().compareTo("s12")==0){data.s12 = childNode.getFirstChild().getNodeValue();}
					else if (childNode.getNodeName().compareTo("sky")==0){data.sky = childNode.getFirstChild().getNodeValue();}
					else if (childNode.getNodeName().compareTo("temp")==0){data.temp = childNode.getFirstChild().getNodeValue();}
					else if (childNode.getNodeName().compareTo("tmn")==0){data.tmn = childNode.getFirstChild().getNodeValue();}
					else if (childNode.getNodeName().compareTo("tmx")==0){data.tmx = childNode.getFirstChild().getNodeValue();}
					else if (childNode.getNodeName().compareTo("wd")==0){data.wd = childNode.getFirstChild().getNodeValue();}
					else if (childNode.getNodeName().compareTo("wdEn")==0){data.wdEn = childNode.getFirstChild().getNodeValue();}
					else if (childNode.getNodeName().compareTo("wdKor")==0){data.wdKor = childNode.getFirstChild().getNodeValue();}
					else if (childNode.getNodeName().compareTo("wfEn")==0){data.wfEn = childNode.getFirstChild().getNodeValue();}
					else if (childNode.getNodeName().compareTo("wfKor")==0){data.wfKor = childNode.getFirstChild().getNodeValue();}
					else if (childNode.getNodeName().compareTo("ws")==0){data.ws = childNode.getFirstChild().getNodeValue();}
					
				}
			}
		}
		return data;
	}

	private Header parserHeader(Node child) {
		Header header = new Header();
		NodeList childList = child.getChildNodes();
		if(childList!=null && childList.getLength()>0){
			for(int i =0 ; i<childList.getLength(); i++){
				Node childNode = childList.item(i);
				if(childNode instanceof Element){
					if (childNode.getNodeName().compareTo("tm") == 0) {header.tm = childNode.getFirstChild().getNodeValue();}
					else if (childNode.getNodeName().compareTo("ts") == 0) {header.ts = childNode.getFirstChild().getNodeValue();}
					else if (childNode.getNodeName().compareTo("x") == 0) {header.x = childNode.getFirstChild().getNodeValue();}
					else if (childNode.getNodeName().compareTo("y") == 0) {header.y = childNode.getFirstChild().getNodeValue();}
					}
			}
			return header;
		}
		return null;
	}

	@Override
	public Object getResult() {
		// TODO Auto-generated method stub
		return wid;
	}

}
