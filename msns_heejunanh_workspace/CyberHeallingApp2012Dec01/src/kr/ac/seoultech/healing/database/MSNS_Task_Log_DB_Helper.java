package kr.ac.seoultech.healing.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MSNS_Task_Log_DB_Helper extends SQLiteOpenHelper {
	
	public static final String MSNS_DB_NAME = "msnsDB.sqlite";
	static CursorFactory cursorFactory = null;
	static int version = 1;
	
	private static final String CREATE_DB ="" +
			"CREATE table taksInfo (" +
			"" +
			")";

	public MSNS_Task_Log_DB_Helper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
