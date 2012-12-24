package com.garamsoft.bubble.sqlite.request.alarm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AlarmDataDBOpenHelper extends SQLiteOpenHelper {
	
	public final static String DATABASE_NAME = "Alarm.db";
	public final static int DATABASE_VERSION = 1;
	public final static String DATABASE_TABLE = "AlarmTables";
	
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
	
	// Column Indexes
	public final static int HOUR_COLUMN = 1;
	public final static int MINUTE_COLUMN = 2;
	public final static int SNOOZETIME_COLUMN = 3;
	public final static int JINDONG_COLUMN = 4;
	public final static int SOUND_ON_OFF_COLUMN = 5;
	public final static int SONG_PATH_COLUMN = 6;
	public final static int SOUND_VOLUMN_COLUMN = 7;
	public final static int REQUEST_CODE_COLUMN = 8;
	public final static int LIVE_SELECT_COLUMN = 9;
	public final static int BANBOK_DAY_COLUMN= 10;
	public final static int SONG_NAME_COLUMN = 11;
	public final static int ON_OFF_COLUMN = 12;
	
		private final static String DATABASE_CREATE = "create table "
			+ DATABASE_TABLE 
			+ " (" + KEY_ID+" integer primary key autoincrement, "
			+ KEY_HOUR + " INT,"
			+ KEY_MINUTE + " INT,"
			+ KEY_SNOOZETIME + " INT,"
			+ KEY_JINDONG + " BOOLEAN," 
			+ KEY_SOUND_ON_OFF + " BOOLEAN,"
			+KEY_SONG_PATH + " TEXT,"
			+KEY_SONG_NAME + " TEXT,"
			+ KEY_SOUND_VOLUMN + " INT,"
			+ KEY_REQUEST_CODE + " INT,"
			+ KEY_LIVE_SELECT + " TEXT,"
			+ KEY_BANBOK_DAY + " TEXT," 
			+ KEY_ON_OFF+" INT);";
		
		// »ý¼ºÀÚ.
		public AlarmDataDBOpenHelper(Context context, String name,CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE" + DATABASE_TABLE);
			onCreate(db);
		}
	}