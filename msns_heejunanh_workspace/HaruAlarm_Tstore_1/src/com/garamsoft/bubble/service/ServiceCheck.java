package com.garamsoft.bubble.service;

import java.util.Calendar;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.garamsoft.bubble.activity.wanning.ActivitiyWanning;
import com.garamsoft.bubble.alarmdata.SingleAlarmListItemInfo;
import com.garamsoft.bubble.manager.AlarmDataManager;

public class ServiceCheck extends Service {

	AlarmDataManager alarmDataManager;

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		int reqCode = intent.getIntExtra("requestCode", -1);
		if (reqCode != -1) {
			/** 1�ܰ� : �¿��� Ȯ�� */
			alarmDataManager = AlarmDataManager
					.getInstance(getApplicationContext());
			SingleAlarmListItemInfo info = alarmDataManager
					.getSingleAlarmListItemInfoAtReqCode(reqCode);

			if (info.alaramONOFF.onoff == 1) {

				Calendar today = Calendar.getInstance();
				int int_today_yoil = today.get(Calendar.DAY_OF_WEEK); // int��
																		// �޾Ƽ�
																		// case������
				int char_today_yoil = getTodayDate(int_today_yoil);
				String banbok = info.dayOfTheWeek.getBanbok();
				for (int j = 0; j < banbok.length(); j++) {
					if (char_today_yoil == banbok.charAt(j)) {
						Intent startIntent = new Intent(getApplicationContext(),ActivitiyWanning.class);
						startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startIntent.putExtra("requestCode", reqCode);
						startActivity(startIntent);
					}
				}
			}
			
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**Ķ�������� �޴� ������ ���� �������� �ƴ� �Լ���.
	 * ���� �Ķ���Ͱ� 1�̸� �Ͽ��� 2�̸� �������̴�.*/
	private char getTodayDate(int int_today_yoil) {
		char char_temp = 0;

		switch (int_today_yoil) {
		case 1:
			char_temp = '��';
			break;
		case 2:
			char_temp = '��';
			break;
		case 3:
			char_temp = 'ȭ';
			break;
		case 4:
			char_temp = '��';
			break;
		case 5:
			char_temp = '��';
			break;
		case 6:
			char_temp = '��';
			break;
		case 7:
			char_temp = '��';
			break;
		}

		return char_temp;
	}

}
