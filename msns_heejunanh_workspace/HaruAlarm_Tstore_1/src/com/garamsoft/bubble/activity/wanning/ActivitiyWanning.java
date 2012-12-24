package com.garamsoft.bubble.activity.wanning;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.garamsoft.bubble.R;
import com.garamsoft.bubble.manager.AlarmDataManager;
import com.garamsoft.bubble.service.ServiceCheck;
import com.garamsoft.bubble.service.ServiceWarning;
import com.garamsoft.bubble.service.WanningServiceAIDL;

public class ActivitiyWanning extends Activity {
	
	WanningServiceAIDL wanningServiceAIDL;
	AlarmManager alarmManager;
	AlarmDataManager alarmDataManager;
	/**확인을 누르면 true로 바뀌고 기본값은 false. 스누즈하면 쇼노티피케이션 실행. 확인이면 쇼노티피케이션 안실행.*/
	boolean end = false;
	private int requestCode;
	private boolean isbind = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
				| WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
				| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
				| WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wanning_wanning);
		
		
		alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmDataManager = AlarmDataManager.getInstance(getApplicationContext());
		Intent getIntent = getIntent();
		int i = getIntent.getIntExtra("알람종료", -1);
		requestCode = getIntent.getIntExtra("requestCode", -1);
		if(i == 0){
			hideNotification();
			Intent intent = new Intent(getApplicationContext(),ServiceCheck.class);
			intent.putExtra("requestCode", requestCode);
			PendingIntent pi = PendingIntent.getService(getApplicationContext(), 304+requestCode, intent,PendingIntent.FLAG_UPDATE_CURRENT);
			
			alarmManager.cancel(pi);
			Intent intent2 = new Intent(getApplicationContext(),
					ActivityInfo.class);
			startActivity(intent2);
			finish();
		}
		
		Button buttonCheck = (Button) findViewById(R.id.buttonCheck);
		buttonCheck.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				hideNotification();
				Intent intent = new Intent(getApplicationContext(),
						ActivityInfo.class);
				startActivity(intent);
				end = true;
				finish();
			}
		});

		Button buttonAlarmSnooze = (Button) findViewById(R.id.buttonAlarmSnooze);
		
		//만약에 디비 내용이 없다면? 누군가가 알람 리스트를 임의로 삭제한것! 그래서 그냥 피니쉬 해준다.
		if(alarmDataManager.getSingleAlarmListItemInfoAtReqCode(requestCode)!=null)
		{
			//스누즈를 하지 한다면? 
			if(alarmDataManager.getSingleAlarmListItemInfoAtReqCode(requestCode).snoozeTime.time!=0){
				buttonAlarmSnooze.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						/** 3초후에 서비스 호출하는 알람등록 */
						if(!end)
							showNotification();
						Intent intent = new Intent(getApplicationContext(),
								ServiceCheck.class);
						intent.putExtra("requestCode", requestCode);
						PendingIntent pi = PendingIntent.getService(getApplicationContext(), 304+requestCode, intent,PendingIntent.FLAG_UPDATE_CURRENT);
						alarmManager.set(AlarmManager.RTC_WAKEUP,java.lang.System.currentTimeMillis() + alarmDataManager.getSingleAlarmListItemInfoAtReqCode(requestCode).snoozeTime.time*1000*60, pi);
						
						finish();
						
					}
				});
				
				if(requestCode!=-1){
					Intent serivceWanningIntent = new Intent(getApplicationContext(), ServiceWarning.class);
					serivceWanningIntent.putExtra("requestCode", requestCode);
					bindService(serivceWanningIntent,mServiceConnection, Context.BIND_AUTO_CREATE);
					isbind= true;
					}
			}
			else{//스누즈 시간인 이라는건 스누즈를 하지 않겠다는 의미기 때문에 버튼을 안보이게 한다.
				buttonAlarmSnooze.setVisibility(View.INVISIBLE);
			}
		}else {
			hideNotification();
			Intent intent = new Intent(getApplicationContext(),
					ActivityInfo.class);
			startActivity(intent);
			end = true;
			finish();
		}
	}
	
	@Override
	public void onBackPressed() {
		Log.i("HARU_LOG", "onBackPressed");
	}

	
	@Override
	protected void onPause() {
		Log.i("HARU_LOG", "onPause");
		super.onPause();
	};
	
	@Override
	protected void onDestroy() {
		Log.i("HARU_LOG", "onDestroy");
		try {
			if(wanningServiceAIDL!=null)
			wanningServiceAIDL.stopSoundAndVibrate();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(isbind)
		unbindService(mServiceConnection);
		super.onDestroy();
	};
	
	ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			wanningServiceAIDL = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			wanningServiceAIDL = WanningServiceAIDL.Stub.asInterface(service);
			try {
				wanningServiceAIDL.startSoundAndVibrate();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	};
	
	private void showNotification(){
		Intent intent = new Intent(getApplicationContext(), ActivitiyWanning.class);
		intent.putExtra("알람종료", 0);
		intent.putExtra("requestCode", requestCode);
		PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 306+requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		Notification notification = new Notification();
		notification.icon = R.drawable.wanning_notification_icon;
		notification.setLatestEventInfo(getApplicationContext(), "<하루알람>", "미뤄진 시간 : "+
				alarmDataManager.getSingleAlarmListItemInfoAtReqCode(requestCode).snoozeTime.time+"분"+" //   터치하면 알람이 취소됩니다.", pendingIntent);
		notification.flags = Notification.FLAG_ONGOING_EVENT|Notification.FLAG_AUTO_CANCEL;
		notification.when = 0;
		notification.defaults = 0;
		
		NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		notificationManager.notify(300+requestCode,notification);
	}
	
	private void hideNotification(){
		NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		notificationManager.cancel(300+requestCode);
	}
	
}
