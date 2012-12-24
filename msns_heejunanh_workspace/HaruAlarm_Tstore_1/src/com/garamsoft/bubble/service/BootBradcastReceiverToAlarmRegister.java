package com.garamsoft.bubble.service;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.garamsoft.bubble.manager.AlarmDataManager;

public class BootBradcastReceiverToAlarmRegister extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
//		Toast.makeText(context, "���ù� ����..", 0).show();
		AlarmDataManager alarmDataManager = AlarmDataManager.getInstance(context);
		alarmDataManager.setAlarmOnBoottingIsStart();
//		Toast.makeText(context, "���ù� ��", 0).show();
	}

}
