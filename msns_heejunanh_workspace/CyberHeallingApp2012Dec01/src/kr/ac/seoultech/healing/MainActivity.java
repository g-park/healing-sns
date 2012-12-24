package kr.ac.seoultech.healing;

import java.util.Iterator;
import java.util.List;

//import com.example.MyService;

import kr.ac.seoultech.healing.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	static private final String TAG = "MainActivity";
	static String ERROR = "ERROR";
	
	private int numberOfScan = 5;

	private long numberOfAlarmTerm = 3000;
	
	int 
	  IMPORTANCE_BACKGROUND,
	
	 IMPORTANCE_EMPTY,
	
	 IMPORTANCE_FOREGROUND,
	
	 IMPORTANCE_PERCEPTIBLE,
	 
	 IMPORTANCE_SERVICE,
	
	 IMPORTANCE_VISIBLE;
	
	Context mContext;
	List<RunningTaskInfo> info;
	List<RunningAppProcessInfo> info2;
	List<ActivityManager.RecentTaskInfo> info3;
	
	TextView textView;
	
	private ActivityManager activityManager;
	
	private Intent alarmIntent;
	private PendingIntent pendingIntent;;
	
	private TextView textView_NumberOf;

	public static Bitmap bitmap;
	
	private AlarmManager alarmManager;
	
	private Button buttonRecentTaks,
	buttonRunningProccessing,
	buttonRunningTasks,
	buttonStartService,
	buttonStopService,
	buttonNumberOfScan,
	buttonAlarmTerm;
	
	private EditText editTextNumberOfScan,editTextAlarmTerm;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mContext = getApplicationContext();
        
         activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        
        buttonRecentTaks  = (Button) findViewById(R.id.buttonRecentTaks);
     	buttonRunningProccessing= (Button) findViewById(R.id.buttonRunningProccessing);
     	buttonRunningTasks= (Button) findViewById(R.id.buttonRunningTasks);
     	buttonStartService= (Button) findViewById(R.id.buttonStartService);
     	buttonStopService= (Button) findViewById(R.id.buttonStopService);
     	buttonNumberOfScan =(Button)findViewById(R.id.buttonNumberOfScan);
     	buttonAlarmTerm = (Button)findViewById(R.id.buttonAlarmTerm);
     	
     	editTextNumberOfScan = (EditText)findViewById(R.id.editTextNumberOfScan);
     	editTextAlarmTerm = (EditText)findViewById(R.id.editTextAlarmTerm);
     	
     
        textView = (TextView)findViewById(R.id.textView);
        textView_NumberOf = (TextView)findViewById(R.id.textView_NumberOf);
        
        
        buttonRecentTaks.setOnClickListener(onClickListener);
    	buttonRunningProccessing.setOnClickListener(onClickListener);
    	buttonRunningTasks.setOnClickListener(onClickListener);
    	buttonStartService.setOnClickListener(onClickListener);
    	buttonStopService.setOnClickListener(onClickListener);
    	buttonNumberOfScan.setOnClickListener(onClickListener);
    	buttonAlarmTerm.setOnClickListener(onClickListener);
    	
    	alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    	
    
   	
    }
	
	/* 
	 * 언제 리시버 등록 해지를 해야하나?
	 * (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
/*	
	protected void onStart (){
		super.onStart();
		// 3. Register Broadcast Receiver, since we can not make it though Manifest.xml somehow.
    	registerScreenReceiver();
	}
	
	protected void onStop (){
		super.onStop();
		unregisterScreenReceiver(); 		
	}
	*/
	private ScreenReceiver receiver;

	private void registerScreenReceiver() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.SCREEN_OFF");
		filter.addAction("android.intent.action.SCREEN_ON");
		
		receiver = new ScreenReceiver();
	
		registerReceiver(receiver, filter);
	}

	private void unregisterScreenReceiver() {
		// TODO Auto-generated method stub
		unregisterReceiver(receiver);
	}

	private void setTextgetRecentTasks(List<RecentTaskInfo> recentTaskInfos) {
		Iterator<RecentTaskInfo> iterator = recentTaskInfos.iterator();
		
		 while(iterator.hasNext()){
	        	
	        	RecentTaskInfo RecentTaskInfo = iterator.next();
	        	
	        	RecentTaskInfo.describeContents();
	        	textView.append("baseIntent :"+RecentTaskInfo.baseIntent+"\n");
	        	textView.append("description :"+RecentTaskInfo.describeContents()+"\n");
	        	textView.append("id "+RecentTaskInfo.id+"\n");
	        	textView.append("origActivity :"+RecentTaskInfo.origActivity+"\n\n");
	        	//textView.append("persistentId :"+RecentTaskInfo.persistentId+"\n");
	        	//LEVEL 11
	        	//bitmap = runningTaskInfo.thumbnail;
			}
	}

	private void setTextRunningTaskInfo(List<RunningTaskInfo> runningTaskInfos) {
		Iterator<RunningTaskInfo> iterator = runningTaskInfos.iterator();
        while(iterator.hasNext()){
        	
        	RunningTaskInfo runningTaskInfo = iterator.next();
        	
        	runningTaskInfo.describeContents();
        	textView.append("id :"+runningTaskInfo.id+"\n");
        	textView.append(runningTaskInfo.topActivity.getClassName()+"\n");
        	textView.append("baseActivity :"+runningTaskInfo.baseActivity.getPackageName()+"\n");
        	textView.append("description: "+runningTaskInfo.description+"\n");
        	textView.append("numRunning :"+runningTaskInfo.numRunning+"\n");
        	textView.append("numActivities :"+runningTaskInfo.numActivities+"\n");
        	//bitmap = runningTaskInfo.thumbnail;
        	
        	
		}
	}

	private void setTextRunningAppProcessInfo(List<ActivityManager.RunningAppProcessInfo> processInfo){
		Iterator<RunningAppProcessInfo> iterator2 = processInfo.iterator();
        
        while(iterator2.hasNext()){
        	RunningAppProcessInfo appProcessInfo = iterator2.next();
        	textView.append(appProcessInfo.processName+"\n");
        	textView.append(getImportanceReasonCode(appProcessInfo.importanceReasonCode)+"\n");
        	textView.append(getImportanceText(appProcessInfo.importance)+"\n\n");
        }
        
        textView_NumberOf.setText("IMPORTANCE_BACKGROUND : "+IMPORTANCE_BACKGROUND+
	
	 "\nIMPORTANCE_EMPTY : "+IMPORTANCE_EMPTY+
	
	 "\nIMPORTANCE_FOREGROUND : "+IMPORTANCE_FOREGROUND+
	
	 "\nIMPORTANCE_PERCEPTIBLE : "+IMPORTANCE_PERCEPTIBLE+
	 
	 "\nIMPORTANCE_SERVICE : "+IMPORTANCE_SERVICE+
	
	 "\nIMPORTANCE_VISIBLE : "+IMPORTANCE_VISIBLE+
	 "\nTotal : "+totalProcess());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	


	private String getImportanceText(int code){
		
		switch (code) {
		case ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND:
			IMPORTANCE_BACKGROUND++;
			return "IMPORTANCE_BACKGROUND";
		case ActivityManager.RunningAppProcessInfo.IMPORTANCE_EMPTY:
			IMPORTANCE_EMPTY++;
			return "IMPORTANCE_EMPTY";
		case ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND:
			IMPORTANCE_FOREGROUND++;
			return "IMPORTANCE_FOREGROUND";
		//case ActivityManager.RunningAppProcessInfo.IMPORTANCE_PERCEPTIBLE:
		//	IMPORTANCE_PERCEPTIBLE++;
		//	return "IMPORTANCE_PERCEPTIBLE";
		case ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE:
			IMPORTANCE_SERVICE++;
			return "IMPORTANCE_SERVICE";
		case ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE:
			IMPORTANCE_VISIBLE++;
			return "IMPORTANCE_VISIBLE";

		default:
			return ERROR;
		}
		
	}
	
	private String getImportanceReasonCode(int code){
		switch (code) {
		case ActivityManager.RunningAppProcessInfo.REASON_PROVIDER_IN_USE:
			
			return"REASON_PROVIDER_IN_USE";
		case ActivityManager.RunningAppProcessInfo.REASON_SERVICE_IN_USE:
			
			return"REASON_PROVIDER_IN_USE";
		case ActivityManager.RunningAppProcessInfo.REASON_UNKNOWN:
			
			return"REASON_UNKNOWN";

		default:
			
			return ERROR;
		}
	}
	
	private int totalProcess(){
		return  IMPORTANCE_BACKGROUND+
				IMPORTANCE_EMPTY+
				IMPORTANCE_FOREGROUND+
				IMPORTANCE_PERCEPTIBLE+
				IMPORTANCE_SERVICE+
				IMPORTANCE_VISIBLE;
	}

		
	OnClickListener onClickListener = new OnClickListener() {
		 
//       
		@Override
		public void onClick(View view) {
			textView.setText("");
			textView_NumberOf.setText("");
			
			
			textView.append("SCREEN:" + ScreenReceiver.getCount() +"\n");
			if(ScreenReceiver.isOnStatus() == true){
			//	textView.append("Screen On!");
				Log.e("SCREEN","Screen On");
				
			}else{
			//	textView.append("Screen off!");
				Log.e("SCREEN","Screen Off");
			}
				
			switch (view.getId()) {
		
			
			// 잠시 이 버튼을 빌려서 히스토리 보여 주도록  사용하도록 하였음.
			case R.id.buttonRecentTaks:
				
				/*info3 = activityManager.getRecentTasks(numberOfScan, 0);
				setTextgetRecentTasks(info3);
				*/
				Cursor c = getContentResolver().query(AppUsage.AppUsageColumns.CONTENT_URI,
			     									  new String[] { "_id",  "app_pkg", "app", "date_time", "duration" },
			     									  null,
			     									  null,
			     									null);
			    
				if(!c.moveToFirst()){
					  c.close();
					  return;
				}	 
				
			    do{
			      long rowId = c.getLong(0);
			      String appName = c.getString(1);
			      String pkgName = c.getString(2);
			      long date_time = c.getLong(3);
			      long duration = c.getLong(4);
			      textView.append("History: _id=" + rowId + ", app=" + appName + ", pkg=" + pkgName + ", durarion=" + duration + "\n");
			      Log.d(TAG, "History: _id=" + rowId + ", app=" + appName + ", pkg=" + pkgName + ", durarion=" + duration);
			    }while(c.moveToNext());			    
			    c.close();	
				
				break;
			
			case R.id.buttonRunningProccessing:
				info2 = activityManager.getRunningAppProcesses();
				setTextRunningAppProcessInfo(info2);
				break;
					
			case R.id.buttonRunningTasks:
				info = activityManager.getRunningTasks(numberOfScan);
				setTextRunningTaskInfo(info);
				break;
			
			case R.id.buttonStartService:
				
				/*
				alarmIntent = new Intent(mContext, Log_write_Service.class);
				pendingIntent = PendingIntent.getService(mContext, 8109329, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
				alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, numberOfAlarmTerm, pendingIntent);
				*/
				 Log.d(TAG, "onClick: starting srvice");
			     startService(new Intent(MainActivity.this, AppUsageTrackingService.class));
				break;
			
			case R.id.buttonStopService:
				/*
				alarmIntent = new Intent(mContext, Log_write_Service.class);
				pendingIntent = PendingIntent.getService(mContext, 8109329, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
				alarmManager.cancel(pendingIntent);
				*/
				 Log.d(TAG, "onClick: starting srvice");
			     stopService(new Intent(MainActivity.this, AppUsageTrackingService.class));
				break;
			
			case R.id.buttonNumberOfScan:
				numberOfScan = Integer.parseInt(editTextNumberOfScan.getText().toString()); break;
				
			case R.id.buttonAlarmTerm:
				numberOfAlarmTerm = Long.parseLong(editTextAlarmTerm.getText().toString()); break;
				
			}
		}
	};
}

