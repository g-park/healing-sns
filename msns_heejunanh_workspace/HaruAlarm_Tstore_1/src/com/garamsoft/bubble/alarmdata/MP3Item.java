package com.garamsoft.bubble.alarmdata;

public class MP3Item {
	public String name;
	public String path;
	
	public MP3Item(String name, String path) {
		this.name = name;
		this.path = path;
	}

	public MP3Item() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "°î : "+name + "À§Ä¡ : "+path;
	}
}