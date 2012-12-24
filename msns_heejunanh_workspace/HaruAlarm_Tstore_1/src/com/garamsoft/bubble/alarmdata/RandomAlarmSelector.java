package com.garamsoft.bubble.alarmdata;

import java.util.ArrayList;
import java.util.Calendar;


public class RandomAlarmSelector {
	private static RandomAlarmSelector instance = null;
	
	// ����ȭ.
	public static RandomAlarmSelector getSingleton() {
		if (instance != null) return instance;
		
		synchronized (RandomAlarmSelector.class) {
			if (instance == null) {
				instance = new RandomAlarmSelector();
			}
		}
		
		return instance;
	}

	private ArrayList<MP3Item> mp3List;
	
	// ������.
	private RandomAlarmSelector() {}
	
	// ��� ����.
	public void setMP3InfoList(ArrayList<MP3Item> mp3List) {
		this.mp3List = mp3List;
	}
	
	// ���Ϻ��� �︮�� �ϱ�. �̰� ���� ���⿡�� �ʿ� ������. 
	public MP3Item getTodaySong() {
		if (mp3List == null || mp3List.size() == 0) return null;
		
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK); 
		// If current day is Sunday, day=1
		// Monday, day=2, increase 1
		
		// ���� ��� 7���� ���� �����ؼ� day ���� �°� �������ָ� �˴ϴ�.
		final int size = mp3List.size();
		
		if (size < Calendar.SATURDAY) {
			return mp3List.get(size % day);
		}
		
		return mp3List.get(day);
	}
}