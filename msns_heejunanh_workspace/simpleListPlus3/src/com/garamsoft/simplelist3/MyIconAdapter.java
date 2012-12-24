package com.garamsoft.simplelist3;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyIconAdapter extends BaseAdapter {
	
	Context mContext;
	ArrayList<String> mArrayList;
	LayoutInflater mInflater;

	public MyIconAdapter(Context context,ArrayList<String> maArrayList ) {
		
		mContext = context;
		mArrayList = maArrayList;
		mInflater = LayoutInflater.from(mContext);
		
	}
	
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrayList.size();
	}
	

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public void add(String item){
		mArrayList.add(item);
		notifyDataSetChanged();
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		MyCompoundWiget mcw;
		return null;
	}

}
