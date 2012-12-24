package com.garamsoft.bubble.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.garamsoft.bubble.alarmdata.SingleAlarmListItemInfo;
import com.garamsoft.bubble.service.ServiceCheck;
import com.garamsoft.bubble.sqlite.AlarmDBAdapter;

/**싱글톤 방식을 데이터 매니저 클래스이다
 * 3가지 기능을 한다.
 * 1. 알람저장 DB에 관여한다.
 * 2. 리스트 뷰의 리스트를 관여한다.
 * 3. 알람 매니저(안드로이드 시스템)의 관여한다.
 * 
 * 만약에 알람리스트에 무언가를 했을 경우에 생각해야하는것은
 * 위에 언급한 대로 SQLite DB, 리스트뷰의 리스트(ArrayList), 알람 매니저를 생각하고 코딩해야한다.
 * 그렇지 않으면 오류가 발생할 수 있다.*/
public class AlarmDataManager {
	
	/**이것은 알람매니저에서 구분자로 쓰이는 request code를 저장하는 SharedPreperence에 저장할 때 쓰이는 String Flag 이다.*/
	public static final String REQUEST_CODE_LAST = "REQUEST_CODE_LAST";

	private Context mContext;

	private int reqeustCodeLast;

	private static AlarmDataManager alarmDataManager;

	private AlarmDBAdapter alarmDBAdapter;

	private AlarmManager alarmManager;

	/***/
	private ArrayList<SingleAlarmListItemInfo> alarmListItemInfos;

	private SharedPreferences sharedPref;
	private SharedPreferences.Editor sharedEditor;
	
	private AlarmDataManager(Context context) {
		
		mContext = context;
		
		sharedPref = context.getSharedPreferences(REQUEST_CODE_LAST, Context.MODE_PRIVATE);
		sharedEditor = sharedPref.edit();
		
		reqeustCodeLast = sharedPref.getInt(REQUEST_CODE_LAST, 0);
		
		alarmDBAdapter = new AlarmDBAdapter(context);
		alarmDBAdapter.open();
		
		alarmManager = (AlarmManager)context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//	 	alarmManager.setTimeZone("GMT+09:00");
		
		alarmListItemInfos = alarmDBAdapter.getAllArrayList();
	}
	public static AlarmDataManager getInstance(Context context) {
		if (alarmDataManager != null) {
			return alarmDataManager;
		} else {
			alarmDataManager = new AlarmDataManager(context);
			
			return alarmDataManager;
		}
	}
	public  ArrayList<SingleAlarmListItemInfo> getAlarmListItemInfos() {
		return alarmListItemInfos;
	}
	
	public SingleAlarmListItemInfo getSingleAlarmListItemInfoAtReqCode(int req){
		return alarmDBAdapter.getSingleAlarmListItemInfo(req);
	}
	public void addListItem(SingleAlarmListItemInfo singleAlarmListItemInfo) {
		setRegistAlarm(singleAlarmListItemInfo);
		alarmListItemInfos.add(singleAlarmListItemInfo);
		alarmDBAdapter.insertTask(singleAlarmListItemInfo);		
	}

	/**알람리스트아이템의 정보를 넣으면 해당 정보가 DB, 리스트뷰 아아템, 알람매니저에게 영향을 미친다*/
	public void updateItem(int position,SingleAlarmListItemInfo singleAlarmListItemInfo) {

		setRegistAlarm(singleAlarmListItemInfo);
		
		alarmListItemInfos.set(position, singleAlarmListItemInfo);
		int result = alarmDBAdapter.updateTask(singleAlarmListItemInfo);
		/*1차출시*/
//		Toast.makeText(mContext, "result is "+result, 0).show();
	}
	
	public void deleteItem(int position, SingleAlarmListItemInfo info){
		
		alarmListItemInfos.remove(position);//view;
		alarmDBAdapter.removeTask(info.request_code.requestCode);//db

		PendingIntent pi = createSetAlarmPending(info.request_code.requestCode);
		alarmManager.cancel(pi);//알람 매니저.
		
	}
	
		/**부팅이 시작되면 브로트캐스팅으로 모든 알람을 초기 세팅을 해줘야한다.
		 * 왜냐하면 안드로이드 시스템의 알람 매니저는 리붓이 된다음에 다시 적용을 해주기 않기 때문이다.
		 * 그래서 저장을 해두었다가 다시 핸드폰이 켜졌을 때, DB에서 있는 정보를 가지고 다시 알람을 등록해 준다.*/
		public void setAlarmOnBoottingIsStart(){
			ArrayList<SingleAlarmListItemInfo> alarmListItemInfos = alarmDBAdapter.getAllArrayList();
			for(int i = 0 ; i < alarmListItemInfos.size(); i ++){
				setRegistAlarm(alarmListItemInfos.get(i));
			}
			/*1차출시*/
//			Toast.makeText(mContext, "setAlarmOnBoottingIsStart 끝", 0).show();
		}
		/**자동으로 하나 증가해준다. 알람을 추가할때 사용.
		 * 설명하자면 알람을 등록할 때에만 사용한다.
		 * 그것은 쉐어드 프리퍼런스를 사용해서 값을 가져오고 하나 증가하고 그 값을 다신 알람을 등록할 때 사용하기 때문이다.*/
		public int getReqeustCodeLast() {
			int sendInt = reqeustCodeLast++;//값을 하나 증가하기 전에 알람 등록할 코드 값을 넣어서 보내줄 변수에 저장한다.
			
			sharedEditor.putInt(REQUEST_CODE_LAST, reqeustCodeLast);//하나 저장된 값을 설정값을 저장한다.
			sharedEditor.commit();//실행.
			
			return sendInt;
		}
		/**지금 현재까지 얼마나 많은 알람을 등록했는지 알수 있다. 800번이 처음 번호이고, 그다음부터 801은 다음 알람이다.*/
		public int getRequestCode(){
			return reqeustCodeLast;
		}
		public void releseManager(){
			alarmDBAdapter.close();
			alarmManager = null;
			sharedPref = null;
		}
		private void setRegistAlarm(SingleAlarmListItemInfo info) {
				
				GregorianCalendar currentCalendar = new GregorianCalendar();
				GregorianCalendar gregorianCalendar = new GregorianCalendar();
				
				int currentYY = currentCalendar.get(Calendar.YEAR);
				int currentMM = currentCalendar.get(Calendar.MONTH);
				int currentDD = currentCalendar.get(Calendar.DAY_OF_MONTH);
				
				int hh = info.alarmTime.hh;
				int mm = info.alarmTime.mm;
				
				gregorianCalendar.set(currentYY, currentMM, currentDD, hh, mm,00);
		
				if(gregorianCalendar.getTimeInMillis() < currentCalendar.getTimeInMillis()){
					gregorianCalendar.set(currentYY, currentMM, currentDD+1, hh, mm,00);
					Log.i("TAG",gregorianCalendar.getTimeInMillis()+":");
				}
				
				
				
				
				PendingIntent pi = createSetAlarmPending(info.request_code.requestCode);
				alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,gregorianCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
//				alarmManager.set(AlarmManager.RTC_WAKEUP,java.lang.System.currentTimeMillis()+ 3000 , pi);//개발자 모드(바로 울림)
			}
		
		private PendingIntent createSetAlarmPending(int requestCode){
			Intent intent = new Intent(mContext, ServiceCheck.class);
			intent.putExtra("requestCode",requestCode);
			return  PendingIntent.getService(mContext,requestCode, intent,PendingIntent.FLAG_UPDATE_CURRENT);
		}
}