package com.garamsoft.bubble.weather.network;

import java.io.InputStream;

abstract public class XMLParser {
	abstract public void doParse(InputStream is); //파싱을 하는 것.
	abstract public Object getResult();//결과를 넘겨주는 것.
}
