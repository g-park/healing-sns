package com.garamsoft.bubble.activity.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.garamsoft.bubble.view.ViewTab2CalendarSingleItem;

public class Adapter_tab2_calendar_single_item extends BaseAdapter {

	Context mContext;
	List<String> strings;
	LayoutInflater mInflate;
	
	public Adapter_tab2_calendar_single_item(List<String> items,Context context) {
		mContext = context;
		strings = items;
		mInflate = LayoutInflater.from(mContext);
		
		}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return strings.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return strings.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewTab2CalendarSingleItem calendarSingleItem = (ViewTab2CalendarSingleItem)convertView;
		
		if(calendarSingleItem == null){
			calendarSingleItem = new ViewTab2CalendarSingleItem(mContext);
		}
		calendarSingleItem.setText((position+1)+". "+getItem(position).toString());
		return calendarSingleItem;
	}

}
