package com.garamsoft.bubble.weather.network;

import java.io.InputStream;

abstract public class XMLParser {
	abstract public void doParse(InputStream is); //�Ľ��� �ϴ� ��.
	abstract public Object getResult();//����� �Ѱ��ִ� ��.
}
