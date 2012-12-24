package com.garamsoft.simplelist3;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class SimpleListPlus3Activity extends Activity {
    /** Called when the activity is first created. */
	
	ListView listView;
	MyIconAdapter myIcoAdapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}