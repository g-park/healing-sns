package com.garamsoft.bubble.service;

import com.garamsoft.bubble.activity.tab0.ActivityTab0Main;

import android.app.PendingIntent;
import android.app.Service;
import android.app.PendingIntent.CanceledException;
import android.content.Intent;
import android.os.IBinder;

public class ServiceToHome extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Intent homeIntent = new Intent(getApplicationContext(), ActivityTab0Main.class);
		homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 306, homeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		try {
			pendingIntent.send();
		} catch (CanceledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.onStartCommand(intent, flags, startId);
	}

}
