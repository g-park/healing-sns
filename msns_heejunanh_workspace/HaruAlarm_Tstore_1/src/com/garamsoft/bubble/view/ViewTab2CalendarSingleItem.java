package com.garamsoft.bubble.view;

import com.garamsoft.bubble.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewTab2CalendarSingleItem extends LinearLayout {

	TextView textView;
	Context mContext;
	public ViewTab2CalendarSingleItem(Context context) {
		super(context);
		mContext = context;
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.view_tab2_single_calendar_item, this);
		textView = (TextView)layout.findViewById(R.id.text);
	}

	
	public ViewTab2CalendarSingleItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public void setText(String str){
		textView.setText(str);
	}
}
