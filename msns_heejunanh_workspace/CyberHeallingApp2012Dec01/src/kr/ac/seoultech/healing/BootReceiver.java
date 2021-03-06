package kr.ac.seoultech.healing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

	static boolean isScreenRecieverRegistered = false;
	ScreenReceiver receiver;
	
	@Override
	 public void onReceive(Context ctx, Intent intent) {
		
		
	    if (intent.getAction().equalsIgnoreCase("android.intent.action.BOOT_COMPLETED")){
	      Log.d("BroadcastReceiver", "Received BOOT_COMPLETED intent...");
	      //new AppUsageTrackingStartService(paramContext);
	      ctx.startService(new Intent(ctx, AppUsageTrackingService.class));	      
	    }else if(intent.getAction().equalsIgnoreCase("android.intent.action.PACKAGE_CHANGED")){
	    	
	    	
	    	
	    }	  
	    
	    if(isScreenRecieverRegistered == false){
	    	registerScreenReceiver(ctx) ;
	    	isScreenRecieverRegistered = true;
	    }
	  }


	
	private void registerScreenReceiver(Context ctx) {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.SCREEN_OFF");
		filter.addAction("android.intent.action.SCREEN_ON");
		
		ScreenReceiver receiver = new ScreenReceiver();
	
		ctx.registerReceiver(receiver, filter);
	}

	private void unregisterScreenReceiver(Context ctx) {
		// TODO Auto-generated method stub
		ctx.unregisterReceiver(receiver);
	}
	/*
	
	  private boolean isServiceNeedToRun(Context paramContext)
	  {
		  return true;
	
		  Uri localUri = AppUsage.AppUsageColumns.CONTENT_URI_SETTINGS;
	    Cursor localCursor = paramContext.getContentResolver().query(localUri, this.columnsSettings, null, new String[] { "tracking_started" }, null);
	    boolean bool;
	    if ((localCursor.moveToFirst()) && (localCursor.getCount() == 1))
	      if (localCursor.getString(2).equals("true"))
	        bool = true;
	    while (true)
	    {
	      localCursor.close();
	      return bool;
	      bool = false;
	      continue;
	      bool = true;
	    }
    
	  }
	  	*/
	
	
}
