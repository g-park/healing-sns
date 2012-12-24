package com.garamsoft.bubble.sqlite;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.garamsoft.bubble.alarmdata.SingleAlarmListItemInfo;
import com.garamsoft.bubble.sqlite.request.alarm.AlarmDataDBOpenHelper;
import com.garamsoft.bubble.sqlite.request.alarm.AlarmDataHandleThread;

/**SQLite를 관리하는 DB 매니저*/
public class AlarmDBAdapter {
	
	private SQLiteDatabase AlarmDB;
	private AlarmDataHandleThread dataHandleThread;
	private final Context context;
	
	private final static String DATABASE_NAME = "Alarm.db";
	private final static int DATABASE_VERSION = 1;
	private final static String DATABASE_TABLE = "AlarmTables";
	
	SingleAlarmListItemInfo singleAlarmListItemInfo = new SingleAlarmListItemInfo();
	// Column Names
	public final static String KEY_ID = "_id";
	public final static String KEY_HOUR = "_HOUR";
	public final static String KEY_MINUTE = "_MINUTE";
	public final static String KEY_SNOOZETIME = "_SNOOZETIME";
	public final static String KEY_JINDONG = "_JINDONG";
	public final static String KEY_SOUND_ON_OFF= "_SOUND_ON_OFF";
	public final static String KEY_SONG_PATH = "_SONG_PATH";
	public final static String KEY_SONG_NAME = "_SONG_NAME";
	public final static String KEY_SOUND_VOLUMN = "_SOUND_VOLUMN";
	public final static String KEY_REQUEST_CODE = "_REQUEST_CODE";
	public final static String KEY_LIVE_SELECT = "_LIVE_SELECT";
	public final static String KEY_BANBOK_DAY = "_BANBOK_DAY";
	public final static String KEY_ON_OFF = "_ON_OFF";
	
//	// Column Indexes
//	public final static int HOUR_COLUMN = 1;
//	public final static int MINUTE_COLUMN = 2;
//	public final static int SNOOZETIME_COLUMN = 3;
//	public final static int JINDONG_COLUMN = 4;
//	public final static int SOUND_ON_OFF_COLUMN = 5;
//	public final static int SONG_PATH_COLUMN = 6;
//	public final static int SOUND_VOLUMN_COLUMN = 7;
//	public final static int REQUEST_CODE_COLUMN = 8;
//	public final static int LIVE_SELECT_COLUMN = 9;
//	public final static int BANBOK_DAY_COLUMN= 10;
//	public final static int SONG_NAME_COLUMN = 11;
//	public final static int ON_OFF_COLUMN = 12;


	private AlarmDataDBOpenHelper dbHelper;
	
	public AlarmDBAdapter(Context _context){
		context = _context;
		dbHelper = new AlarmDataDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		dataHandleThread = new AlarmDataHandleThread(dbHelper);
		dataHandleThread.start();
	}
	
	public void close(){
		AlarmDB.close();
	}
	
	public void open(){
		try{
			AlarmDB = dbHelper.getWritableDatabase();
		}catch(SQLException e){
			AlarmDB = dbHelper.getReadableDatabase();
		}
	}
	
	// 삽입.
	public long insertTask(SingleAlarmListItemInfo task){
//		long result;
		ContentValues values = new ContentValues();
		values.put(KEY_HOUR, task.alarmTime.hh);
		values.put(KEY_MINUTE, task.alarmTime.mm);
		values.put(KEY_SNOOZETIME, task.snoozeTime.time);
		values.put(KEY_JINDONG, task.alarmType.vibration);
		values.put(KEY_SOUND_ON_OFF, task.alarmType.Wave);
		values.put(KEY_SONG_PATH, task.song.path);
		values.put(KEY_SOUND_VOLUMN, task.soundvolumn.volumn_size);
		values.put(KEY_REQUEST_CODE, task.request_code.requestCode);
		values.put(KEY_LIVE_SELECT, task.city.live_city);
		values.put(KEY_BANBOK_DAY, task.dayOfTheWeek.getBanbok());
		values.put(KEY_SONG_NAME, task.song.name);
		values.put(KEY_ON_OFF, task.alaramONOFF.onoff);
		
//		AlarmDataRequest req = new AlarmDataRequestInsert(DATABASE_NAME, values);
//		req.setOnProcessedListener(new AlarmDataRequest.OnProcessedListener() {
//			public void onProcessed(AlarmDataRequest data) {
//				Toast.makeText(context, ((AlarmDataRequestInsert)data).result+"?", 0).show();	
//			}
//		});
//		dataHandleThread.sendDataRequest(req);
		return AlarmDB.insert(DATABASE_TABLE, null, values);
	}
	
	// DB 삭제.  ( request_code값이 같으면 삭제. )
	public boolean removeTask(int _request_code){
		return AlarmDB.delete(DATABASE_TABLE, 
			KEY_REQUEST_CODE+"="+_request_code, null)>=0;
	}
	
	// 업데이트. 여기에서 updateTask랑 AlarmActivty의 updateTask랑은 다른거.
//	public boolean updateTask(SingleAlarmListItemInfo tmp, String _Start_end){
//		ContentValues values = new ContentValues();
//		
//		values.put(KEY_START_END, _Start_end);
//		
//		return AlarmDB.update(DATABASE_TABLE, values,
//				KEY_START_END+"="+ tmp.getStart_End(), null)>0;
//	}
	
	public int updateTask(SingleAlarmListItemInfo task){
		
		ContentValues values = new ContentValues();
		values.put(KEY_HOUR, task.alarmTime.hh);
		values.put(KEY_MINUTE, task.alarmTime.mm);
		values.put(KEY_SNOOZETIME, task.snoozeTime.time);
		values.put(KEY_JINDONG, task.alarmType.vibration);
		values.put(KEY_SOUND_ON_OFF, task.alarmType.Wave);
		values.put(KEY_SONG_PATH, task.song.path);
		values.put(KEY_SOUND_VOLUMN, task.soundvolumn.volumn_size);
		values.put(KEY_REQUEST_CODE, task.request_code.requestCode);
		values.put(KEY_LIVE_SELECT, task.city.live_city);
		values.put(KEY_BANBOK_DAY, task.dayOfTheWeek.getBanbok());
		values.put(KEY_SONG_NAME, task.song.name);
		values.put(KEY_ON_OFF, task.alaramONOFF.onoff);
		
		
		
//		int result = AlarmDB.update(DATABASE_TABLE, values, KEY_REQUEST_CODE+"=?", new String[]{ String.valueOf(singleAlarmListItemInfo.request_code.requestCode) });
		int result = AlarmDB.update(DATABASE_TABLE, values, KEY_REQUEST_CODE+"="+task.request_code.requestCode, null);
		return result;
	}
	// 커서 위치.
//	public Cursor getAllTaskCursor(){
//		return AlarmDB.query(DATABASE_TABLE, 
//				new String[]{ KEY_ID, KEY_HOUR, KEY_MINUTE, KEY_SNOOZETIME, KEY_JINDONG, KEY_SOUND_ON_OFF, KEY_SONG_PATH, KEY_SOUND_VOLUMN, KEY_REQUEST_CODE, KEY_LIVE_SELECT, KEY_BANBOK_DAY }, null, null, null, null, null);
//	}
	
	public ArrayList<SingleAlarmListItemInfo> getAllArrayList(){
		ArrayList<SingleAlarmListItemInfo> arrayList = new ArrayList<SingleAlarmListItemInfo>();
		Cursor c = AlarmDB.query(DATABASE_TABLE, null, null, null, null, null, null);

			c.moveToFirst();
			while(!(c.isAfterLast())){

				int key = c.getInt(c.getColumnIndex(KEY_ID));
				int key_hour =c.getInt(c.getColumnIndex(KEY_HOUR));
				int key_minute =c.getInt(c.getColumnIndex(KEY_MINUTE));
				int key_snooze=c.getInt(c.getColumnIndex(KEY_SNOOZETIME));
				int vib=c.getInt(c.getColumnIndex(KEY_JINDONG));
				int sound=c.getInt(c.getColumnIndex(KEY_SOUND_ON_OFF));
				String song_path=c.getString(c.getColumnIndex(KEY_SONG_PATH));
				int song_volumn=c.getInt(c.getColumnIndex(KEY_SOUND_VOLUMN));
				int reqCode=c.getInt(c.getColumnIndex(KEY_REQUEST_CODE));
				String living_place=c.getString(c.getColumnIndex(KEY_LIVE_SELECT));
				String repeating=c.getString(c.getColumnIndex(KEY_BANBOK_DAY));
				String song_name = c.getString(c.getColumnIndex(KEY_SONG_NAME));
				int onoff = c.getInt(c.getColumnIndex(KEY_ON_OFF));
				
//				Log.i("TAG"," key : "+ key +
//						" hh : "+ key_hour +
//						" mm : "+key_minute+
//						" snooz : "+ key_snooze +
//						" vib : "+ vib +
//						" sound : "+ sound +
//						" song_path : "+song_path+
//						" vol : "+song_volumn+
//						" reqCode : "+reqCode+
//						" live : "+living_place+
//						" repeating : "+repeating);
				
				arrayList.add(new SingleAlarmListItemInfo(key, key_hour, key_minute, key_snooze, vib, sound, song_name,song_path, song_volumn, reqCode, living_place,
						repeating,onoff));
				
				c.moveToNext();
			}
			c.close();
			return arrayList;
			
	}
	
	public SingleAlarmListItemInfo getSingleAlarmListItemInfo(int requeCode){
		Cursor c = AlarmDB.query(DATABASE_TABLE, null, null, null, null, null, null);
		
		

		c.moveToFirst();
		while(!(c.isAfterLast())){

			int reqCode=c.getInt(c.getColumnIndex(KEY_REQUEST_CODE));
			if(requeCode == reqCode){
			int key = c.getInt(c.getColumnIndex(KEY_ID));
			int key_hour =c.getInt(c.getColumnIndex(KEY_HOUR));
			int key_minute =c.getInt(c.getColumnIndex(KEY_MINUTE));
			int key_snooze=c.getInt(c.getColumnIndex(KEY_SNOOZETIME));
			int vib=c.getInt(c.getColumnIndex(KEY_JINDONG));
			int sound=c.getInt(c.getColumnIndex(KEY_SOUND_ON_OFF));
			String song_path=c.getString(c.getColumnIndex(KEY_SONG_PATH));
			int song_volumn=c.getInt(c.getColumnIndex(KEY_SOUND_VOLUMN));
			
			String living_place=c.getString(c.getColumnIndex(KEY_LIVE_SELECT));
			String repeating=c.getString(c.getColumnIndex(KEY_BANBOK_DAY));
			String song_name = c.getString(c.getColumnIndex(KEY_SONG_NAME));
			int onoff = c.getInt(c.getColumnIndex(KEY_ON_OFF));
			
			return new SingleAlarmListItemInfo(key, key_hour, key_minute, key_snooze, vib, sound, song_name,song_path, song_volumn, reqCode, living_place, repeating, onoff);

			}
			
			c.moveToNext();
		}
		c.close();
		return null;
	}
	
	// boolean형이 가능한지.
//	private static class AlarmDBOpenHelper extends SQLiteOpenHelper{
//		private final static String DATABASE_CREATE = "create table "
//			+ DATABASE_TABLE 
//			+ " (" + KEY_ID+" integer primary key autoincrement, "
//			+ KEY_HOUR + " INT,"
//			+ KEY_MINUTE + " INT,"
//			+ KEY_SNOOZETIME + " INT,"
//			+ KEY_JINDONG + " BOOLEAN," 
//			+ KEY_SOUND_ON_OFF + " BOOLEAN,"
//			+KEY_SONG_PATH + " TEXT,"
//			+KEY_SONG_NAME + " TEXT,"
//			+ KEY_SOUND_VOLUMN + " INT,"
//			+ KEY_REQUEST_CODE + " INT,"
//			+ KEY_LIVE_SELECT + " TEXT,"
//			+ KEY_BANBOK_DAY + " TEXT," 
//			+ KEY_ON_OFF+" INT);";
//		
//		// 생성자.
//		public AlarmDBOpenHelper(Context context, String name,CursorFactory factory, int version) {
//			super(context, name, factory, version);
//		}
//
//		@Override
//		public void onCreate(SQLiteDatabase db) {
//			// TODO Auto-generated method stub
//			db.execSQL(DATABASE_CREATE);
//		}
//
//		@Override
//		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//			// TODO Auto-generated method stub
//			db.execSQL("DROP TABLE" + DATABASE_TABLE);
//			onCreate(db);
//		}
//	}//AlarmDBOpenHelper
}


