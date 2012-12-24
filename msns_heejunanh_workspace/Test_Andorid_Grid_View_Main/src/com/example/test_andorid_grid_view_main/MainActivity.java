package com.example.test_andorid_grid_view_main;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ListAdapter;

public class MainActivity extends Activity {

	GridView mainGridView;
	Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        
        mainGridView =(GridView)findViewById(R.id.mainGridView);
        
        MainGridViewAdapter adapter = null;
        ArrayList<MainGridItem> GridItems = new ArrayList<MainGridItem>();
        
        GridItems.add(new MainGridItem(mContext));
        GridItems.add(new MainGridItem(mContext));
        GridItems.add(new MainGridItem(mContext));
        GridItems.add(new MainGridItem(mContext));
        
        
		adapter = new MainGridViewAdapter(mContext, GridItems );
		
		mainGridView.setAdapter(adapter);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
