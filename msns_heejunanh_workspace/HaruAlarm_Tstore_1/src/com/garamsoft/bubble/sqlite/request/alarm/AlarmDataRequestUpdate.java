package com.garamsoft.bubble.sqlite.request.alarm;

import android.database.sqlite.SQLiteDatabase;


public class AlarmDataRequestUpdate extends AlarmDataRequest {
	
	public AlarmDataRequestUpdate() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(AlarmDataDBOpenHelper dbHelper) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();//������ ���̽��� �޾ƿ´�.
		
		//updata �˰���.
		
				if (mListener != null)
					mListener.onProcessed(AlarmDataRequestUpdate.this);//�ش� �����͸�����Ʈ�� �ְ� ���� �Ѵ�.
		db.close();
	}

}
