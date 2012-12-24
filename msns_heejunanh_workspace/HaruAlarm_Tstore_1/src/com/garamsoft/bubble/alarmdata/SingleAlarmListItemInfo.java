package com.garamsoft.bubble.alarmdata;

import java.util.Calendar;
import java.util.GregorianCalendar;



/**알람 리스트에 적용될 값들을 데이터 클래스(스트럭쳐)*/
public class SingleAlarmListItemInfo {
	
	public AlarmTime alarmTime;
	public DayOfTheWeek dayOfTheWeek;
	public SnoozeTime snoozeTime;
	public AlarmType alarmType;
	public City city;
	public MP3Item song;
	public SoundVolumn soundvolumn; 
	public RequestCode request_code;
	public AlaramONOFF alaramONOFF;
	
	
	public SingleAlarmListItemInfo() {
		alarmTime = new AlarmTime();
		dayOfTheWeek=new DayOfTheWeek(); 
		snoozeTime = new SnoozeTime();
		alarmType = new AlarmType();
		city = new City();
		song = new MP3Item();
		soundvolumn = new SoundVolumn();
		request_code = new RequestCode();
		alaramONOFF = new AlaramONOFF();
	}
	
	public SingleAlarmListItemInfo(int key, int key_hour,int key_minute, int key_snooze, int vib, int sound,String song_name, String song_path,int song_volumn, int reqCode, String living_place, String repeating,int onoff){
		alarmTime = new AlarmTime();
		dayOfTheWeek=new DayOfTheWeek(); 
		snoozeTime = new SnoozeTime();
		alarmType = new AlarmType();
		city = new City();
		song = new MP3Item();
		soundvolumn = new SoundVolumn();
		request_code = new RequestCode();
		alaramONOFF = new AlaramONOFF();
		
		alarmTime.hh = key_hour;
		alarmTime.mm = key_minute;
		snoozeTime.time= key_snooze;
		alarmType.vibration = vib;
		alarmType.Wave= sound;
		song.name = song_name;
		song.path= song_path;
		soundvolumn.volumn_size= song_volumn;
		request_code.requestCode = reqCode;
		city.live_city= living_place;
		dayOfTheWeek = new DayOfTheWeek(repeating);
		alaramONOFF.onoff = onoff;
	}
	
	/**알람이 켜져있는지 꺼져있는지를 나타내는 클래스.*/
	public class AlaramONOFF{
		public static final int ON = 1;
		public static final int OFF = 0;
		public int onoff;
		
		public AlaramONOFF() {
			onoff = ON;
		}
		
		public void turn(int ONOFF){
			onoff = ONOFF;
		}
		
	}
	
	/**알람시간을 저장하는 클래스*/
	public class AlarmTime {
		public int hh;
		public int mm;
		
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		
		public AlarmTime() {
			hh = gregorianCalendar.get(Calendar.HOUR);
			mm = gregorianCalendar.get(Calendar.MINUTE);
		}
	}
	
	/**알람 타입을 결정한다. 알람 타임은 진동과 소리 두개로 나눠진다.*/
	public class AlarmType {
		public int vibration, Wave;

		public AlarmType() {
			vibration = 1;
			Wave = 1;
		}
		public int isVibration() {
			return vibration;
		}

		public void setVibration(int vibration) {
			this.vibration = vibration;
		}

		public int isWave() {
			return Wave;
		}

		public void setWave(int wave) {
			Wave = wave;
		}
		
		@Override
		public String toString() {
			String str = "";
			str +=Wave==1?"소리":"";
			str +=vibration==1?"진동":"";
			
			return str;
		}
		
	}	
}