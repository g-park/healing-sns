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
	/**Ȯ���� ������ true�� �ٲ�� �⺻���� false. �������ϸ� ���Ƽ�����̼� ����. Ȯ���̸� ���Ƽ�����̼� �Ƚ���.*/
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
		int i = getIntent.getIntExtra("�˶�����", -1);
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
		
		//���࿡ ��� ������ ���ٸ�? �������� �˶� ����Ʈ�� ���Ƿ� �����Ѱ�! �׷��� �׳� �ǴϽ� ���ش�.
		if(alarmDataManager.getSingleAlarmListItemInfoAtReqCode(requestCode)!=null)
		{
			//����� ���� �Ѵٸ�? 
			if(alarmDataManager.getSingleAlarmListItemInfoAtReqCode(requestCode).snoozeTime.time!=0){
				buttonAlarmSnooze.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						/** 3���Ŀ� ���� ȣ���ϴ� �˶���� */
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
			else{//������ �ð��� �̶�°� ����� ���� �ʰڴٴ� �ṉ̀� ������ ��ư�� �Ⱥ��̰� �Ѵ�.
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
		intent.putExtra("�˶�����", 0);
		intent.putExtra("requestCode", requestCode);
		PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 306+requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		Notification notification = new Notification();
		notification.icon = R.drawable.wanning_notification_icon;
		notification.setLatestEventInfo(getApplicationContext(), "<�Ϸ�˶�>", "�̷��� �ð� : "+
				alarmDataManager.getSingleAlarmListItemInfoAtReqCode(requestCode).snoozeTime.time+"��"+" //   ��ġ�ϸ� �˶��� ��ҵ˴ϴ�.", pendingIntent);
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
