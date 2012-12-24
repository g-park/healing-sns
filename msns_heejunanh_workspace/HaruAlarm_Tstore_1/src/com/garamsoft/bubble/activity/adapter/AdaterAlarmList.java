	package com.garamsoft.bubble.activity.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.garamsoft.bubble.alarmdata.SingleAlarmListItemInfo;
import com.garamsoft.bubble.manager.AlarmDataManager;
import com.garamsoft.bubble.view.ViewLayoutAlarmListItem;
import com.garamsoft.bubble.view.ViewLayoutAlarmListItem.OnDeleteButtonClickListener;
import com.garamsoft.bubble.view.ViewLayoutAlarmListItem.OnOnOffButtonClickListener;


// List에 들어갈 어댑터 설정.
public class AdaterAlarmList extends BaseAdapter {
	
	Context mContext;
	ArrayList<SingleAlarmListItemInfo> singleItemDatas;
	AlarmDataManager alarmDataManager;
	LayoutInflater mInflate;
	private int visiblity;
	
	public AdaterAlarmList(Context context,ArrayList<SingleAlarmListItemInfo> datas) {
		mContext = context;
		singleItemDatas = datas;
		mInflate = LayoutInflater.from(mContext);
		alarmDataManager = AlarmDataManager.getInstance(mContext);
		visiblity = View.INVISIBLE;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return singleItemDatas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return singleItemDatas.get(position);
	}
	
	public void add(SingleAlarmListItemInfo item) {
		singleItemDatas.add(item);
		notifyDataSetChanged();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return singleItemDatas.get(position).request_code.requestCode;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewLayoutAlarmListItem viewLayoutAlarmListItem = (ViewLayoutAlarmListItem)convertView;
		
		if (viewLayoutAlarmListItem == null) {
			viewLayoutAlarmListItem = new ViewLayoutAlarmListItem(mContext);
		}
		
		viewLayoutAlarmListItem.setData(singleItemDatas.get(position),position);
		
		viewLayoutAlarmListItem.setOnDeleteButtonClickListener(listener);
		viewLayoutAlarmListItem.setOnOnOffButtonClickListener(onOffButtonClickListener);
		viewLayoutAlarmListItem.setDeleteButtonVisiblity(visiblity);
		return viewLayoutAlarmListItem;
	}
	
	private OnDeleteButtonClickListener listener = new OnDeleteButtonClickListener() {
		
		@Override
		public void DeleteThis(SingleAlarmListItemInfo info, int position) {
			alarmDataManager.deleteItem(position, info);
			notifyDataSetChanged();
		}
	};
	
	private OnOnOffButtonClickListener onOffButtonClickListener = new OnOnOffButtonClickListener() {
		
		@Override
		public void ChangeOnOff(SingleAlarmListItemInfo info, int position) {
			alarmDataManager.updateItem(position, info);
		}
	};

	public void setDeleteBtnVisible(boolean checked) {
		for(int i = 0 ; i <singleItemDatas.size(); i++){
		}
	}
	

	public interface OnDeleteBtnVisibleListener{
		public void setDeleteBtnVisible(int visibility);
	}
	
	OnDeleteBtnVisibleListener deleteBtnVisibleListener;
	
	public void setOnDeleteBtnVisibleListener(OnDeleteBtnVisibleListener listener){
		deleteBtnVisibleListener = listener;
	}
	
	/**모든 뷰에 버튼의 가시성을 gone으로 한다.*/
	public void setVisibleDeleteButton(int visiblity){
		this.visiblity = visiblity;
		notifyDataSetChanged();
	}
}
