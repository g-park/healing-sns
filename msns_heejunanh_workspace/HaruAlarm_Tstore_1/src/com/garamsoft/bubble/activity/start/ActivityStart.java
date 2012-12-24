package com.garamsoft.bubble.activity.start;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.garamsoft.bubble.R;
import com.garamsoft.bubble.activity.tab0.ActivityTab0Main;
import com.garamsoft.bubble.manager.DefaultLiveManager;

// 진짜 첫화면.
public final class ActivityStart extends Activity {
	/**
	 * 이 앱은 앱이 처음 시작할 때 실행 되는 액티비티 이다.
	 * 앱이 시작되면 날씨 정보와 일정 정보를 서버에서 API 형태로 받아온다.
	 * 하지만 이 방식을 서비스로 옵기고 다른 방식을 생각해야한다.
	 * */

//	private static int sApiLevel = 0;
//	
//	private TextView textView;
//	private Uri calendarUri;
//	private Uri eventsUri;
	
//	 public static int getApiLevel() {
//	        if (sApiLevel == 0) {
//	            try {
//	                // SDK_INT only exists since API 4 so let's use the string version.
//	                sApiLevel = Integer.parseInt(Build.VERSION.SDK);
//	            } catch (Exception e) {
//	                // This app doesn't run below 3 anyway
//	                sApiLevel = 3;
//	            }
//	        }
//
//	        return sApiLevel;
//	    }
	
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0) {
				Intent intent = new Intent(ActivityStart.this,ActivityTab0Main.class);
				startActivity(intent);
				finish();
			}
		}
	};

	Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		mContext = getApplicationContext();

		DefaultLiveManager defaultLiveManager = new DefaultLiveManager(mContext);

		String deLive = defaultLiveManager.readLive();

		Toast.makeText(getApplicationContext(), "사는곳이 " + deLive + " 로 설정됨", 0).show();


//        if (getApiLevel() <= 8) {
//        	eventsUri = Uri.parse("content://calendar/events");
//        	calendarUri = Uri.parse("content://calendar/calendars");
//        } else {
//        	eventsUri = Uri.parse("content://com.android.calendar/events");
//        	calendarUri = Uri.parse("content://com.android.calendar/calendars");
//        }
//		
//        textView = (TextView)findViewById(R.id.pa);
//        queryCalendars();
//        queryEvents(-1);
//		
//		
//		textView.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
				mHandler.sendEmptyMessageDelayed(0, 2000);				
//			}
//		});
	}

//	  private void queryCalendars() {
//	    	// Run query
//	    	Cursor cur = null;
//	    	ContentResolver cr = getContentResolver();
//	    	String[] projection = new String[] { "_id", "name" };
//	    	
//	    	// Submit the query and get a Cursor object back. 
//	    	cur = cr.query(calendarUri, projection, null, null, null);
//	    	if (cur == null) return;
//	    	
//	    	StringBuilder sb = new StringBuilder();
//	    	sb.append("\n Calendars-----");
//	    	
//	    	// Use the cursor to step through the returned records
//	    	while (cur.moveToNext()) {
//	    	    long calID = cur.getLong(0);
//	    	    String name = cur.getString(1);
//	    	              
//	    	    sb.append("calendar id : " + calID +", name : " + name + "\n");
//	    	    queryEvents(calID);
//	    	}
//	    	
//	    	textView.setText(sb.toString());
//	    }
	    
//	    private void queryEvents(long calID) {
//	    	// Run query
//	    	Cursor cur = null;
//	    	ContentResolver cr = getContentResolver();
//	    	
//	    	String query = "calendar_id=" + calID;
//	    	if (calID < 0) {
//	    		// all events
//	    		query = null;
//	    	}
//	    	
//	    	String[] projection = new String[] { "_id", "title", "description" };
//	    	// Submit the query and get a Cursor object back. 
//	    	cur = cr.query(eventsUri, projection, query, null, null);
//	    	if (cur == null) return;
//	    	
//	    	StringBuilder sb = new StringBuilder();
//	    	sb.append("\n Events-----[" + calID + "]");
//	    	// Use the cursor to step through the returned records
//	    	while (cur.moveToNext()) {
//	    		long eventId = cur.getLong(0);
//	    	    String title = cur.getString(1);
//	    	    String desc = cur.getString(2);
//	    	    sb.append("\nid : " + eventId + ", title : " + title + ", desc : " + desc);
//	    	}
//	    	
//	    	textView.setText(textView.getText() + sb.toString());
//	    }
}