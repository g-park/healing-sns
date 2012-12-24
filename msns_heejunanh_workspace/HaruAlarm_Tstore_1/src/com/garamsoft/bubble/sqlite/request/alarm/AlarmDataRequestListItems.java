package com.garamsoft.bubble.sqlite.request.alarm;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.garamsoft.bubble.alarmdata.DayOfTheWeek;
import com.garamsoft.bubble.alarmdata.SingleAlarmListItemInfo;


public class AlarmDataRequestListItems extends AlarmDataRequest {
	
	ArrayList<SingleAlarmListItemInfo> list = new ArrayList<SingleAlarmListItemInfo>();
	
	public AlarmDataRequestListItems() {
		// TODO Auto-generated constructor stub
	}
	
	SingleAlarmListItemInfo singleAlarmListItemInfo;

	@Override
	public void execute( AlarmDataDBOpenHelper dbHelper) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();//데이터 베이스를 받아온다.
		
		Cursor c = db.query(AlarmDataDBOpenHelper.DATABASE_TABLE, null, null, null, null, null, null);
			
			

			c.moveToFirst();
			while(!(c.isAfterLast())){
//				String _id = c.getString(c.getColumnIndex("_id"));
//				String fname = c.getString(c.getColumnIndex("fname"));
//				String lname = c.getString(c.getColumnIndex("lname"));
				
//				private final static String DATABASE_CREATE = "create table "
//				+ DATABASE_TABLE +
//				" (" + KEY_ID + " integer primary key autoincrement, "
//				+ KEY_HOUR + " INT,"
//				+ KEY_MINUTE + " INT,"
//				+ KEY_SNOOZETIME + " INT,"
//				+ KEY_JINDONG + " BOOLEAN,"
//				+ KEY_SOUND_ON_OFF + " BOOLEAN,"
//				+ KEY_SONG_PATH+ " TEXT,"
//				+ KEY_SOUND_VOLUMN + " INT,"
//				+ KEY_REQUEST_CODE + " INT,"
//				+ KEY_LIVE_SELECT + " TEXT,"
//				+ KEY_BANBOK_DAY + " TEXT);";

//				int key = c.getInt(c.getColumnIndex(AlarmDataDBOpenHelper.KEY_ID));
				int key_hour =c.getInt(c.getColumnIndex(AlarmDataDBOpenHelper.KEY_HOUR));;
				int key_minute =c.getInt(c.getColumnIndex(AlarmDataDBOpenHelper.KEY_SNOOZETIME));;
				int key_snooze=c.getInt(c.getColumnIndex(AlarmDataDBOpenHelper.KEY_JINDONG));;
				int vib=c.getInt(c.getColumnIndex(AlarmDataDBOpenHelper.KEY_JINDONG));;
				int sound=c.getInt(c.getColumnIndex(AlarmDataDBOpenHelper.KEY_SOUND_ON_OFF));;
				String song_path=c.getString(c.getColumnIndex(AlarmDataDBOpenHelper.KEY_SONG_PATH));;
				int song_volumn=c.getInt(c.getColumnIndex(AlarmDataDBOpenHelper.KEY_SOUND_VOLUMN));;
				int reqCode=c.getInt(c.getColumnIndex(AlarmDataDBOpenHelper.KEY_REQUEST_CODE));;
				String living_place=c.getString(c.getColumnIndex(AlarmDataDBOpenHelper.KEY_LIVE_SELECT));;
				String repeating=c.getString(c.getColumnIndex(AlarmDataDBOpenHelper.KEY_BANBOK_DAY));;
				
//				list.add(key +":"+ key_hour +":"+ key_snooze +":"+ vib +":"+ sound +":"+song_path+":"+song_volumn+":"+
//				reqCode+":"+living_place+":"+repeating);
				singleAlarmListItemInfo.alarmTime.hh = key_hour;
				singleAlarmListItemInfo.alarmTime.mm = key_minute;
				singleAlarmListItemInfo.snoozeTime.time= key_snooze;
				singleAlarmListItemInfo.alarmType.vibration = vib;
				singleAlarmListItemInfo.alarmType.Wave= sound;
				singleAlarmListItemInfo.song.path= song_path;
				singleAlarmListItemInfo.soundvolumn.volumn_size= song_volumn;
				singleAlarmListItemInfo.request_code.requestCode = reqCode;
				singleAlarmListItemInfo.city.live_city= living_place;
				singleAlarmListItemInfo.dayOfTheWeek = new DayOfTheWeek(repeating);
				
				list.add(singleAlarmListItemInfo);
				
				
				c.moveToNext();
			}
			c.close();
			

				if (mListener != null)
					mListener.onProcessed(AlarmDataRequestListItems.this);//해당 데이터리퀘스트를 주고 런을 한다.
		db.close();
	}

}
