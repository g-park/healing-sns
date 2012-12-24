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

/**�̱��� ����� ������ �Ŵ��� Ŭ�����̴�
 * 3���� ����� �Ѵ�.
 * 1. �˶����� DB�� �����Ѵ�.
 * 2. ����Ʈ ���� ����Ʈ�� �����Ѵ�.
 * 3. �˶� �Ŵ���(�ȵ���̵� �ý���)�� �����Ѵ�.
 * 
 * ���࿡ �˶�����Ʈ�� ���𰡸� ���� ��쿡 �����ؾ��ϴ°���
 * ���� ����� ��� SQLite DB, ����Ʈ���� ����Ʈ(ArrayList), �˶� �Ŵ����� �����ϰ� �ڵ��ؾ��Ѵ�.
 * �׷��� ������ ������ �߻��� �� �ִ�.*/
public class AlarmDataManager {
	
	/**�̰��� �˶��Ŵ������� �����ڷ� ���̴� request code�� �����ϴ� SharedPreperence�� ������ �� ���̴� String Flag �̴�.*/
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

	/**�˶�����Ʈ�������� ������ ������ �ش� ������ DB, ����Ʈ�� �ƾ���, �˶��Ŵ������� ������ ��ģ��*/
	public void updateItem(int position,SingleAlarmListItemInfo singleAlarmListItemInfo) {

		setRegistAlarm(singleAlarmListItemInfo);
		
		alarmListItemInfos.set(position, singleAlarmListItemInfo);
		int result = alarmDBAdapter.updateTask(singleAlarmListItemInfo);
		/*1�����*/
//		Toast.makeText(mContext, "result is "+result, 0).show();
	}
	
	public void deleteItem(int position, SingleAlarmListItemInfo info){
		
		alarmListItemInfos.remove(position);//view;
		alarmDBAdapter.removeTask(info.request_code.requestCode);//db

		PendingIntent pi = createSetAlarmPending(info.request_code.requestCode);
		alarmManager.cancel(pi);//�˶� �Ŵ���.
		
	}
	
		/**������ ���۵Ǹ� ���Ʈĳ�������� ��� �˶��� �ʱ� ������ ������Ѵ�.
		 * �ֳ��ϸ� �ȵ���̵� �ý����� �˶� �Ŵ����� ������ �ȴ����� �ٽ� ������ ���ֱ� �ʱ� �����̴�.
		 * �׷��� ������ �صξ��ٰ� �ٽ� �ڵ����� ������ ��, DB���� �ִ� ������ ������ �ٽ� �˶��� ����� �ش�.*/
		public void setAlarmOnBoottingIsStart(){
			ArrayList<SingleAlarmListItemInfo> alarmListItemInfos = alarmDBAdapter.getAllArrayList();
			for(int i = 0 ; i < alarmListItemInfos.size(); i ++){
				setRegistAlarm(alarmListItemInfos.get(i));
			}
			/*1�����*/
//			Toast.makeText(mContext, "setAlarmOnBoottingIsStart ��", 0).show();
		}
		/**�ڵ����� �ϳ� �������ش�. �˶��� �߰��Ҷ� ���.
		 * �������ڸ� �˶��� ����� ������ ����Ѵ�.
		 * �װ��� ����� �����۷����� ����ؼ� ���� �������� �ϳ� �����ϰ� �� ���� �ٽ� �˶��� ����� �� ����ϱ� �����̴�.*/
		public int getReqeustCodeLast() {
			int sendInt = reqeustCodeLast++;//���� �ϳ� �����ϱ� ���� �˶� ����� �ڵ� ���� �־ ������ ������ �����Ѵ�.
			
			sharedEditor.putInt(REQUEST_CODE_LAST, reqeustCodeLast);//�ϳ� ����� ���� �������� �����Ѵ�.
			sharedEditor.commit();//����.
			
			return sendInt;
		}
		/**���� ������� �󸶳� ���� �˶��� ����ߴ��� �˼� �ִ�. 800���� ó�� ��ȣ�̰�, �״������� 801�� ���� �˶��̴�.*/
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
//				alarmManager.set(AlarmManager.RTC_WAKEUP,java.lang.System.currentTimeMillis()+ 3000 , pi);//������ ���(�ٷ� �︲)
			}
		
		private PendingIntent createSetAlarmPending(int requestCode){
			Intent intent = new Intent(mContext, ServiceCheck.class);
			intent.putExtra("requestCode",requestCode);
			return  PendingIntent.getService(mContext,requestCode, intent,PendingIntent.FLAG_UPDATE_CURRENT);
		}
}