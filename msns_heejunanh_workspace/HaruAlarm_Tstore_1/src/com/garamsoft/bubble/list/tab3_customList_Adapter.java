package com.garamsoft.bubble.list;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.garamsoft.bubble.R;

// tab4 메인 화면에 들어갈 어댑터. (공지사항, 환경설정, 버전 정보, 흔들 알리기, 도움말, 제작자 정보 )
public class tab3_customList_Adapter extends BaseAdapter{
	private Context mContext;
	private LayoutInflater mInflater;
	ArrayList<tab3_customList_Holder> custumList;
	int layout;
	
	public tab3_customList_Adapter(Context mContext, int layout, ArrayList<tab3_customList_Holder> custumList) {
		this.mContext = mContext;
		mInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.custumList = custumList;
		this.layout = layout;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		return custumList.size();
	}

	public Object getKey(int position){
		
		 return custumList.get(position).tab3_list_item_up;
		
	}
	
	public Object getValue(int position) {
		// TODO Auto-generated method stub
		/*
		 *value값을 리턴해준다.
		 */
		return custumList.get(position).tab3_list_item_down;
	}
	

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
//		final int pos = position;
		
		if(convertView == null){
			convertView = mInflater.inflate(layout, parent, false);
		}
	
		TextView firstView = (TextView)convertView.findViewById(R.id.tab3_holder_text_up);
		TextView secondView = (TextView)convertView.findViewById(R.id.tab3_holder_text_down);

		
		firstView.setText(getKey(position).toString());
		secondView.setText(getValue(position).toString());
		
		return convertView;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return custumList.get(position).tab3_list_item_up;
	}
}

