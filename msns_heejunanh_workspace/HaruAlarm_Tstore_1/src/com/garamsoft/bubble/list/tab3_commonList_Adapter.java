package com.garamsoft.bubble.list;

import java.util.ArrayList;

import com.garamsoft.bubble.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


// tab4 메인에서 리스트목록들을 눌렀을때 호출될 리스트를 담을 어댑터.

public class tab3_commonList_Adapter extends BaseAdapter{
	private Context mContext;
	private LayoutInflater mInflater;
	ArrayList<tab3_commonList_Holder> custumList;
	int layout;
	
	public tab3_commonList_Adapter(Context mContext, int layout, ArrayList<tab3_commonList_Holder> custumList) {
		this.mContext = mContext;
		mInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.custumList = custumList;
		this.layout = layout;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		
		return custumList.size();
	}

	public Object getKey(int position){
		
		 return custumList.get(position).tab3_list_item_left;
		
	}
	
	public Object getValue(int position) {
		// TODO Auto-generated method stub
		/*
		 *value값을 리턴해준다.
		 */
		return custumList.get(position).tab3_list_item_right;
	}
	

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
//		final int pos = position;
		
		if(convertView == null){
			convertView = mInflater.inflate(layout, parent, false);
		}
		
		TextView firstView = (TextView)convertView.findViewById(R.id.tab3_common_holder_text_left);
		TextView secondView = (TextView)convertView.findViewById(R.id.tab3_common_holder_text_right);
		
		firstView.setText(getKey(position).toString());
		secondView.setText(getValue(position).toString());
		
		return convertView;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return custumList.get(position).tab3_list_item_left;
	}
}
