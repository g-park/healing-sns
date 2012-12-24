package com.garamsoft.bubble.alarmdata;

import java.util.ArrayList;
import java.util.Calendar;


public class RandomAlarmSelector {
	private static RandomAlarmSelector instance = null;
	
	// 동기화.
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
	
	// 생성자.
	private RandomAlarmSelector() {}
	
	// 목록 세팅.
	public void setMP3InfoList(ArrayList<MP3Item> mp3List) {
		this.mp3List = mp3List;
	}
	
	// 요일별로 울리게 하기. 이거 굳이 여기에선 필요 없을듯. 
	public MP3Item getTodaySong() {
		if (mp3List == null || mp3List.size() == 0) return null;
		
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(Calendar.DAY_OF_WEEK); 
		// If current day is Sunday, day=1
		// Monday, day=2, increase 1
		
		// 예를 들어 7개의 곡을 선택해서 day 값에 맞게 설정해주면 됩니다.
		final int size = mp3List.size();
		
		if (size < Calendar.SATURDAY) {
			return mp3List.get(size % day);
		}
		
		return mp3List.get(day);
	}
}