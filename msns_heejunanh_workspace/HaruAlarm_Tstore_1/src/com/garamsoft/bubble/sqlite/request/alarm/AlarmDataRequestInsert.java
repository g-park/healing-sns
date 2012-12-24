package com.garamsoft.bubble.sqlite.request.alarm;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;


public class AlarmDataRequestInsert extends AlarmDataRequest {
	public String mTableName;
	public ContentValues mValues;
	public long result;
	
	public AlarmDataRequestInsert(String tableName,ContentValues values) {
		mTableName = tableName;//���̺� ������ �Ѱܹް�.
		mValues = values;//�װͿ� �־���ϴ� ���� �Ѵ�.
	}
	
	@Override
	public void execute(AlarmDataDBOpenHelper dbHelper) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();//������ ���̽��� �޾ƿ´�.
		
		db.insert	 (mTableName, null, mValues);//�׸��� �ִ´�.
		
				// TODO Auto-generated method stub
				if (mListener != null)
					mListener.onProcessed(AlarmDataRequestInsert.this);//�ش� �����͸�����Ʈ�� �ְ� ���� �Ѵ�.
		db.close();
	}
}
