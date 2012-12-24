package com.garamsoft.bubble.sqlite.request.alarm;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;


public class AlarmDataRequestInsert extends AlarmDataRequest {
	public String mTableName;
	public ContentValues mValues;
	public long result;
	
	public AlarmDataRequestInsert(String tableName,ContentValues values) {
		mTableName = tableName;//테이블 네임을 넘겨받고.
		mValues = values;//그것에 넣어야하는 것을 한다.
	}
	
	@Override
	public void execute(AlarmDataDBOpenHelper dbHelper) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();//데이터 베이스를 받아온다.
		
		db.insert	 (mTableName, null, mValues);//그리고 넣는다.
		
				// TODO Auto-generated method stub
				if (mListener != null)
					mListener.onProcessed(AlarmDataRequestInsert.this);//해당 데이터리퀘스트를 주고 런을 한다.
		db.close();
	}
}
