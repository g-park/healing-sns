package com.garamsoft.bubble.sqlite.request.alarm;

import android.database.sqlite.SQLiteDatabase;


public class AlarmDataRequestUpdate extends AlarmDataRequest {
	
	public AlarmDataRequestUpdate() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(AlarmDataDBOpenHelper dbHelper) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();//데이터 베이스를 받아온다.
		
		//updata 알고리즘.
		
				if (mListener != null)
					mListener.onProcessed(AlarmDataRequestUpdate.this);//해당 데이터리퀘스트를 주고 런을 한다.
		db.close();
	}

}
