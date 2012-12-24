package com.garamsoft.bubble.sqlite.request.alarm;

import android.database.sqlite.SQLiteDatabase;


public class AlarmDataRequestDelete extends AlarmDataRequest {
	
	int reqCode;
	boolean result;
	
	public AlarmDataRequestDelete(int _request_code) {
		reqCode = _request_code;
	}
	
	@Override
	public void execute(AlarmDataDBOpenHelper dbHelper) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();//데이터 베이스를 받아온다.
		
		result = db.delete(AlarmDataDBOpenHelper.DATABASE_TABLE, AlarmDataDBOpenHelper.KEY_REQUEST_CODE+"="+reqCode, null)>=0;
		
				if (mListener != null)
					mListener.onProcessed(AlarmDataRequestDelete.this);//해당 데이터리퀘스트를 주고 런을 한다.
		db.close();
	

	}

}
