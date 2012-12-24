package kr.ac.seoultech.healing;


/* 본 APP의 모든 기록을 모두 저장함.
 * 
 * 
 * 
 */


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class AppUsageContentProvider extends ContentProvider {

	
	private static final String TAG =  "AppUsageContentProvider";
		
	private static final int APPUSAGE = 1;   // 응용 Usage정보 					

	// 내용 확인 필요 
	private static final int APPUSAGEGROUPBY = 2;
	private static final int APPUSAGEGROUPBYCUSTOM = 3;
	private static final int APPUSAGEGROUPBYFORCUSTOMDURATION = 4;
	private static final int APPUSAGEDETAILED = 5;
	private static final int APPUSAGEDETAILEDCUSTOM = 6;
	
	private static final int APPUSAGESETTINGS = 7; // 세팅값 
	
	private static final int APPUSAGESETTINGSCOUNT = 8;
	private static final int APPUSAGEDELETECUSTOM = 9;
	private static final int APPUSAGEDETAILEDCUSTOMALL = 10;
	private static final int APPUSAGEDETAILEDALL = 11;
	
	
	// DB 구성 
	private static final String DATABASE_NAME = "healing_app_activity.db"; // SQL DB name
	private static final int DATABASE_VERSION = 5;
	public static final String APP_USAGE_TABLE_NAME = "AppUsage";
	public static final String SETTINGS_TABLE_NAME = "AppUsageSettings";

	// URI 
	public static final String AUTHORITY = "kr.ac.seoultech.healing";
	private static HashMap<String, String> appUsageProjectionMap;
	private static Context mContext = null;
	private static final UriMatcher sUriMatcher = new UriMatcher(-1);

	private DatabaseHelper dbHelper;
	
	static {
		
		// Populate UriMatcher, (URI string 에서 integer 매핑 테이블)  
	    sUriMatcher.addURI(AUTHORITY, "AppUsage", 1);
	    sUriMatcher.addURI(AUTHORITY, "AppUsage/#", 12);  // 이거 추가해야할 것으로 보임.
	    
	    // 아래 놈들은 왜이렇게 많은 거지?
	    sUriMatcher.addURI(AUTHORITY, "AppUsageGroupBy", 2);
	    sUriMatcher.addURI(AUTHORITY, "AppUsageGroupByCustom", 3);
	    sUriMatcher.addURI(AUTHORITY, "AppUsageGroupByForCustomDuration", 4);
	    sUriMatcher.addURI(AUTHORITY, "AppUsageDetailed", 5);
	    sUriMatcher.addURI(AUTHORITY, "AppUsageDetailedCustom", 6);
	    sUriMatcher.addURI(AUTHORITY, "AppUsageSettings", 7);
	    sUriMatcher.addURI(AUTHORITY, "AppUsageSettingsCount", 8);
	    sUriMatcher.addURI(AUTHORITY, "AppUsageDeleteCustom", 9);
	    sUriMatcher.addURI(AUTHORITY, "AppUsageDetailedCustomAll", 10);
	    sUriMatcher.addURI(AUTHORITY, "AppUsageDetailedAll", 11);
	    
	    //  Content 요소와 DB column 사이 매핑  
	    appUsageProjectionMap = new HashMap();
	    appUsageProjectionMap.put("_id", "_id");
	    appUsageProjectionMap.put("app_pkg", "app_pkg");
	    appUsageProjectionMap.put("app", "app");
	    appUsageProjectionMap.put("date_time", "date_time");
	    appUsageProjectionMap.put("duration", "duration");
	  }
	

	/** 기록 삭제 
	 *  
	 *  해당 URI의 기록을 지우고, 지운 갯수를 반환  
	 * 
	 * */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		
		// 1. URL 확인 
		int uri_idx = sUriMatcher.match(uri);
	    if( uri_idx == -1)
	    	throw new IllegalArgumentException("Unknown URI: " + uri);
	    
	    // 2. DB 확보
		SQLiteDatabase localSQLiteDatabase	= dbHelper.getWritableDatabase();
	    	    
	    // 3. 해당 항목 지움 
	    int count = 0;
	    switch(uri_idx){
	     
	    case 1: // 특정 조건의 Row를 지우려고하는 경우 (일반적인 경우)
	    	count = localSQLiteDatabase.delete(APP_USAGE_TABLE_NAME, selection, selectionArgs);
	    	Log.d("AppUsageTracking",
	    			"Deleting from AppUsage where:" + selection + " whereArgs:" + selectionArgs + " Rows Deleted:" + count);	
	    	break;
	    case 12: // 특정  row를 지칭한 경우 (아직 구현 안됨) 
	    	/* 아래 참고 하여 구현할 것 
	    	 	String rowId = uri.getPathSegments().get(1);
	    	 	count = db.delete(BookTableMetaData.TABLE_NAME
	    	 	, BookTableMetaData._ID + "=" + rowId
	    	 	+ (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : "")
	    	 	, whereArgs);
	    		 */
	    default:
	    	throw new IllegalArgumentException("Unknown URI " + uri);
	    }
	    
	    getContext().getContentResolver().notifyChange(uri, null);
	       
		return count;
	}

	/* Return 하는 데이터의 type 
	 *  
	 * @todo URI 별로 따로 주어야 하는지 ?
	 * 
	 * */
	@Override
	public String getType(Uri uri) {
		
 	
		switch( sUriMatcher.match(uri)){
		case 1:
			return "vnd.android.cursor.dir/vnd.seoultech.appusage";
			
		case 12:	
			return "vnd.android.cursor.item/vnd.seoultech.appusage";
		case 2:	
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		
			return "vnd.android.cursor.dir/vnd.seoultech.appusage";  // 뭐야 이거?
		default:
		case -1: // 해당 URI 없을 때	
		        throw new IllegalArgumentException("Unknown URI " + uri);
		
		}
	      
	}

	
	/* 
	 * 기록 추가
	 * 
	 *  측정 기록과 세팅 정보  (세팅 정보는 Preference에 넣어도 될 것 같은데)
	 * 
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		ContentValues localContentValues;
		SQLiteDatabase db;
			  
     
		if (values != null){
			Log.d(TAG, "Call to Insert: uri =" + uri.toString() + ", values = " + values.toString());
			localContentValues = new ContentValues(values);
			db = dbHelper.getWritableDatabase();
		}else{	       	
			throw new IllegalArgumentException("ContentValue is null....");  // 뭔소리. 딴핑게 되지마!    		
		}
				
		switch(sUriMatcher.match(uri)){
		
		case 1:  // AppUsage
			
			// @Todo 입력 값을 확인 할 필요가 있음. 
			if (values.containsKey("app_pkg") == false || values.containsKey("app") == false ||
					values.containsKey("date_time") == false || values.containsKey("duration") == false ){
				Log.e(TAG, "Call to Insert: uri =" + uri.toString() + ", values = " + values.toString());
				throw new IllegalArgumentException("Missing ContentValues");      	
			}
			
			// 1. DB에 삽입 
			long rowId = db.insert(APP_USAGE_TABLE_NAME, null, localContentValues);
		    if (rowId > 0L){ // 데이터 추가 성공 
		        Uri rowUri = ContentUris.withAppendedId(AppUsage.AppUsageColumns.CONTENT_URI, rowId);
		        getContext().getContentResolver().notifyChange(rowUri, null);
		        Log.d(TAG, "Success to Insert : _id" + rowId + values.toString());
		        return rowUri;
		    }else{
		    	throw new SQLException("Failed to insert row into " + uri);    	
		    }
		      
	/*	 당분간 사용하지 않음     
		case 7: // Setting
			
		      String[] arrayOfString;
		      try {
		        
		    	long l1 = db.insert("AppUsageSettings", null, localContentValues);
		        if (l1 <= 0L){
		        //  continue;
		        }
		        Uri localUri1 = ContentUris.withAppendedId(AppUsage.AppUsageColumns.CONTENT_URI_SETTINGS, l1);
		        getContext().getContentResolver().notifyChange(localUri1, null);
		        return localUri1;
		      }
		      catch (SQLiteConstraintException localSQLiteConstraintException)
		      {
		        arrayOfString = new String[1];
		        arrayOfString[0] = localContentValues.getAsString("action");
		        if (localSQLiteDatabase.update("AppUsageSettings", localContentValues, "action='?'", arrayOfString) > 0L)
		        	getContext().getContentResolver().notifyChange(uri, null);
		      }
	*/	  
		    
	  	default: // 잘못된 URI 
	        throw new IllegalArgumentException("Unknown URI " + uri);
		}
		
	}

	
	/*
	 *  CP가 처음 사용될 때 
	 * 
	 * @see android.content.ContentProvider#onCreate()
	 */
	@Override
	public boolean onCreate() {
	
		dbHelper = new DatabaseHelper(getContext());
		if (dbHelper == null)
		      return false;
		 else
			 return true;		    	  
	}

	
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
				  
        Log.d(TAG, "Call to query");

		
		// 1.  쿼리문 생성 
		 SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		 SQLiteDatabase db = dbHelper.getReadableDatabase();
		 
		 String str1 = null;
		 qb.setTables(APP_USAGE_TABLE_NAME);
		 qb.setProjectionMap(appUsageProjectionMap);
		 Cursor c;
		 switch (sUriMatcher.match(uri)){
		 case 1:
		     c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		    break;
		 default:
		      throw new IllegalArgumentException("Unknown URI: " + uri);
		 }
		 
		 c.setNotificationUri(getContext().getContentResolver(), uri);
		 
	     Log.d(TAG, "Success to Query : count = " + c.getCount() );
			
		 
		 return c;
		 
		 
		 /* 누적 해서 값을 읽어 오는 SQL이 있군! */
		 /* 해당 APP을 날짜 별로 가져오는 방법도 있는 것같고. */
		 /*
		 localCursor = qb.query(db, projection, selection, selectionArgs, "app", null, sortOrder);
		      continue;
		      Log.d("AppUsageTracking", "Executing query: " + "Select _id,app_pkg,app,sum(duration) as duration from AppUsage Group By app Order BY duration DESC");
		      localCursor = db.rawQuery("Select _id,app_pkg,app,sum(duration) as duration from AppUsage Group By app Order BY duration DESC", null);
		      continue;
		      Log.i("AppUsageTracking", "Custom queryFromMillis:" + selectionArgs[0]);
		      long l4;
		      long l5;
		      if ((selectionArgs != null) && (selectionArgs.length > 1))
		      {
		        l4 = Long.parseLong(selectionArgs[0]);
		        l5 = Long.parseLong(selectionArgs[1]);
		      }
		      long l3;
		      for (str1 = "Select _id,app_pkg,app,sum(case(date_time <  " + l4 + ") WHEN 1 THEN (" + "date_time" + "+" + "duration" + "-" + l4 + ") ELSE " + "duration" + " END) as  " + "duration" + ", (date_time + duration) as new_duration from " + "AppUsage" + " Where " + " new_duration between " + l4 + " AND " + l5 + " Group By " + "app" + " Order BY " + "duration" + " DESC"; ; str1 = "Select _id,app_pkg,app,sum(case(date_time <  " + l3 + ") WHEN 1 THEN (" + "date_time" + "+" + "duration" + "-" + l3 + ") ELSE " + "duration" + " END) as " + "duration" + ", (date_time + duration) as new_duration from " + "AppUsage" + " Where " + "  new_duration > " + l3 + " Group By " + "app" + " Order BY " + "duration" + " DESC")
		      {
		        do
		        {
		          Log.d("AppUsageTracking", "Executing query: " + str1);
		          localCursor = db.rawQuery(str1, null);
		          break;
		        }
		        while ((selectionArgs == null) || (selectionArgs.length != 1));
		        l3 = Long.parseLong(selectionArgs[0]);
		      }
		      String str4 = "Select _id,date_time,duration from AppUsage Where app_pkg='" + selection + "' Order BY " + "date_time";
		      Log.d("AppUsageTracking", "Executing query: " + str4);
		      localCursor = db.rawQuery(str4, null);
		      continue;
		      Log.i("AppUsageTracking", "Detailed Custom queryFromMillis:" + selectionArgs[0] + " package:" + "app_pkg");
		      if ((selectionArgs != null) && (selectionArgs.length == 1))
		      {
		        long l2 = Long.parseLong(selectionArgs[0]);
		        str1 = "Select _id,date_time,duration, (date_time + duration) as new_duration from AppUsage Where new_duration > " + l2 + " AND " + "app_pkg" + "='" + selection + "' Order BY " + "date_time";
		      }
		      Log.d("AppUsageTracking", "Executing query: " + str1);
		      localCursor = db.rawQuery(str1, null);
		      continue;
		      Log.d("AppUsageTracking", "Executing query: " + "Select _id,app_pkg,app,date_time,duration from AppUsage Order BY date_time");
		      localCursor = db.rawQuery("Select _id,app_pkg,app,date_time,duration from AppUsage Order BY date_time", null);
		      continue;
		      Log.i("AppUsageTracking", "Detailed Custom queryFromMillis:" + selectionArgs[0]);
		      if ((selectionArgs != null) && (selectionArgs.length == 1))
		      {
		        long l1 = Long.parseLong(selectionArgs[0]);
		        str1 = "Select _id,app_pkg,app,date_time,duration, (date_time + duration) as new_duration from AppUsage Where new_duration > " + l1 + " Order BY " + "date_time";
		      }
		      Log.d("AppUsageTracking", "Executing query: " + str1);
		      localCursor = db.rawQuery(str1, null);
		      continue;
		      Log.i("AppUsageTracking", "");
		      String str3 = "select * from AppUsageSettings where action='" + selectionArgs[0] + "'";
		      Log.d("AppUsageTracking", "Executing query: " + str3);
		      localCursor = db.rawQuery(str3, null);
		      continue;
		      String str2 = "select count(*) as row_count from AppUsageSettings where action='" + selectionArgs[0] + "'";
		      Log.d("AppUsageTracking", "Executing query: " + str2);
		      localCursor = db.rawQuery(str2, null);
		    }
		   */ 
	
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		
		SQLiteDatabase db = this.dbHelper.getWritableDatabase();
	    int count;
	    switch (sUriMatcher.match(uri))
	    {
	    case 1:
	    	count = db.update("AppUsage", values, selection, selectionArgs);
		    break;
/*
	    case 7:
	    	   count = db.update("AppUsageSettings", values, selection, selectionArgs);
		 	   Log.d("AppUsageTracking", "Updating AppUsageSettings using " + values.toString() + " where:" + selection + " whereArgs " + selectionArgs.toString());
 */   
	    default:
	      throw new IllegalArgumentException("Unknown URI: " + uri);
	    }
	 
	   getContext().getContentResolver().notifyChange(uri, null);

	   return count;
   }

	
	/**
	 * DB를 실제 Connect 하는 시기와, upgrade 하는 로직을 관리 
	 *    
	 * @author 안희준
	 */
	
	 private static class DatabaseHelper extends SQLiteOpenHelper
	  {
	    DatabaseHelper(Context ctx){
	      super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	      
	      AppUsageContentProvider.mContext = ctx;  // 이거 필요해?
	    }

	    
	    public void onCreate(SQLiteDatabase db)
	    {
	    	
	      // Create Table 	
	      //db.execSQL("Create table AppUsage(_id INTEGER PRIMARY KEY AUTOINCREMENT, app_pkg TEXT, app TEXT, date_time NUMBER, duration NUMBER);");
		  db.execSQL("Create table " + APP_USAGE_TABLE_NAME + "(" 
				     + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				     + "app_pkg TEXT,"
				     + "app TEXT,"
				     + "date_time NUMBER,"
				     + "duration NUMBER"
				     + ");");
		  
		  
	         Log.d("AppUsageTracking", "Created Table AppUsage using query: " + "Create table AppUsage(_id INTEGER PRIMARY KEY AUTOINCREMENT, app_pkg TEXT, app TEXT, date_time NUMBER, duration NUMBER);");
	         
	         //paramSQLiteDatabase.execSQL("Create table AppUsageSettings(_id INTEGER PRIMARY KEY AUTOINCREMENT, action TEXT, value TEXT);");
	         //Log.d("AppUsageTracking", "Created Table AppUsageSettings using query: " + "Create table AppUsageSettings(_id INTEGER PRIMARY KEY AUTOINCREMENT, action TEXT, value TEXT);");
	    }

	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	    {
	      Log.w("AppUsageTracking", "Upgrading database from version " + oldVersion + " to " + newVersion); 
	      db.execSQL("DROP TABLE IF EXISTS "+ APP_USAGE_TABLE_NAME);
          // db.execSQL("DROP TABLE IF EXISTS AppUsageSettings");
          onCreate(db);
    	      
	      /*  새로 짜야함.	      
	      List localList = 
	    	  MiscUtils.getHomeActivity(AppUsageContentProvider.mContext);
	      
	      
	      if ((newVersion == 2) && (oldVersion == 1))
	      {
	        Log.w("AppUsageTracking", "Executing query: " + "update AppUsage set app='App Usage Tracker' where app='App Usage Tracking'");
	        db.execSQL("update AppUsage set app='App Usage Tracker' where app='App Usage Tracking'");
	      }
	      if ((newVersion == 3) && (oldVersion == 1))
	       {
	          Log.w("AppUsageTracking", "Executing query: " + "update AppUsage set app='App Usage Tracker' where app='App Usage Tracking'");
	          db.execSQL("update AppUsage set app='App Usage Tracker' where app='App Usage Tracking'");
	          Log.w("AppUsageTracking", "Executing query: " + "delete from AppUsage where app_pkg='com.android.launcher'");
	          db.execSQL("delete from AppUsage where app_pkg='com.android.launcher'");
	        }
	        else if ((newVersion == 3) && (oldVersion == 2))
	        {
	          Log.w("AppUsageTracking", "Executing query: " + "delete from AppUsage where app_pkg='com.android.launcher'");
	          db.execSQL("delete from AppUsage where app_pkg='com.android.launcher'");
	        }
	        else if (newVersion == 5)
	        {
	          Log.w("AppUsageTracking", "Executing query: " + "update AppUsage set app='App Usage Tracker' where app='App Usage Tracking'");
	          db.execSQL("update AppUsage set app='App Usage Tracker' where app='App Usage Tracking'");
	          Iterator localIterator = localList.iterator();
	          while (localIterator.hasNext())
	          {
	            String str1 = (String)localIterator.next();
	            String str2 = "delete from AppUsage where app_pkg='" + str1 + "'";
	            Log.w("AppUsageTracking", "Executing query: " + str2);
	            db.execSQL(str2);
	          }
	        }
	        else
	        {
	          Log.w("AppUsageTracking", "Destroying all old data...");
	          db.execSQL("DROP TABLE IF EXISTS AppUsage");
	          db.execSQL("DROP TABLE IF EXISTS AppUsageSettings");
	          onCreate(db);
	        }
	      }
	      */
          
          
	    }
	      
	  }
	  
	
	
}
