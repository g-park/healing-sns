package kr.ac.seoultech.healing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ScreenReceiver extends BroadcastReceiver {

	private static boolean isScreenOn = true;
	private static int count = 0;
	 
	@Override
	public void onReceive(Context context, Intent intent) {
	     if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
	               // do whatever you need to do here
	    	 		isScreenOn = false;
	    	 		count++;
	     } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
	             // and do whatever you need to do here
	            	isScreenOn = true;
	            	count++;
	    }
	}

	public static boolean isOnStatus() {
		// TODO Auto-generated method stub
		return isScreenOn;
		
	}
	
	public static int getCount() {
		// TODO Auto-generated method stub
		return count;
		
	}
}
