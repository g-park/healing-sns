package com.example.testapp_android_market_api_01;



import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gc.android.market.api.MarketSession;
import com.gc.android.market.api.MarketSession.Callback;
import com.gc.android.market.api.model.Market.AppsRequest;
import com.gc.android.market.api.model.Market.AppsResponse;
import com.gc.android.market.api.model.Market.ResponseContext;

public class MainActivity extends Activity {

	private TextView textView_BeforeParsingData;
	
	private EditText editText_Search;// = (EditText)findViewById(R.id.editText_Search);
	private Button button_Search;// = (Button)findViewById(R.id.button_Search);
	String str;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textView_BeforeParsingData = (TextView)findViewById(R.id.textView_BeforeParsingData);
        editText_Search = (EditText)findViewById(R.id.editText_Search);
    	button_Search = (Button)findViewById(R.id.button_Search);
    	button_Search.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Thread thread = new Thread(new Runnable() {
					
					public void run() {
						 str = searchAnd();
						
					}
				});
				
				thread.start();
			
				textView_BeforeParsingData.setText(str);
			}
		});
    	//searchAnd();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    String strReturn;
    private String searchAnd(){
    	
    	MarketSession session = new MarketSession();
    	
    	//String AndroidId = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);

    	session.login("netgarng88@gmail.com","gh4277g1");
    	//session.getContext().setAndroidId(AndroidId);
    	

    	String query = "maps";
    	AppsRequest appsRequest = AppsRequest.newBuilder()
    	                                .setQuery(query)
    	                                .setStartIndex(0).setEntriesCount(10)
    	                                .setWithExtendedInfo(true)
    	                                .build();
    	                       
    	session.append(appsRequest, new Callback<AppsResponse>() {
    	         public void onResult(ResponseContext context, AppsResponse response) {
    	        	 
    	        	 strReturn = response.getApp(0).toString();
    	                
    	         }
    	});
    	session.flush();
		return strReturn;
    }
}


