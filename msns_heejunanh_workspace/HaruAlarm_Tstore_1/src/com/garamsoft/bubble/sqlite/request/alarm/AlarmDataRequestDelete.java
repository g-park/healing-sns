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
		SQLiteDatabase db = dbHelper.getWritableDatabase();//������ ���̽��� �޾ƿ´�.
		
		result = db.delete(AlarmDataDBOpenHelper.DATABASE_TABLE, AlarmDataDBOpenHelper.KEY_REQUEST_CODE+"="+reqCode, null)>=0;
		
				if (mListener != null)
					mListener.onProcessed(AlarmDataRequestDelete.this);//�ش� �����͸�����Ʈ�� �ְ� ���� �Ѵ�.
		db.close();
	

	}

}
