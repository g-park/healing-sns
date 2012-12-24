package com.garamsoft.bubble.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.garamsoft.bubble.R;
import com.garamsoft.bubble.alarmdata.SingleAlarmListItemInfo;

public class ViewLayoutAlarmListItem extends FrameLayout {
	int position;
	private TextView 
	txtTime,
	txtDays;//,
//	textViewSnoozeTime,
//	textViewAlarmType,
//	textViewCity,
//	textViewSong,
//	textViewVol,
//	textViewReqCode;
	
	private TextView  textViewAMPM;
	
	private ImageView 
	imageViewVib,
	imageViewSound,
	imageViewBar;
	
	private FrameLayout
	framelayoutRight,
	framelayoutTime,
	framelayoutSound,
	framelayoutOnOff,
	framelayoutLeft
	;
	
	private Button buttonDelete;
	
	ToggleButton toggleButtonListONOFF;
	
	SingleAlarmListItemInfo singleAlarmListItemInfo;
	
	Context mContext;
	
	int temp_hour;
	
	public ViewLayoutAlarmListItem(Context context) {
		super(context);
		mContext = context;
		LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.view_tab1_single_list_alarm_item, this);
		txtTime = (TextView) layout.findViewById(R.id.txtTime);
		txtDays = (TextView) layout.findViewById(R.id.txtDays);
		
//		textViewSnoozeTime=(TextView) layout.findViewById(R.id.textViewSnoozeTime);
//		textViewAlarmType=(TextView) layout.findViewById(R.id.textViewAlarmType);
//		textViewCity=(TextView) layout.findViewById(R.id.textViewCity);
//		textViewSong=(TextView) layout.findViewById(R.id.textViewSong);
//		textViewVol=(TextView) layout.findViewById(R.id.textViewVol);
//		textViewReqCode=(TextView) layout.findViewById(R.id.textViewReqCode);
		
		textViewAMPM = (TextView)layout.findViewById(R.id.textViewAMPM);
		
		imageViewVib = (ImageView)layout.findViewById(R.id.imageViewVib);
		imageViewSound =(ImageView)layout.findViewById(R.id.imageViewSound);
		imageViewBar = (ImageView)layout.findViewById(R.id.imageViewBar);
	
		framelayoutRight = (FrameLayout)layout.findViewById(R.id.framelayoutRight);
		framelayoutTime= (FrameLayout)layout.findViewById(R.id.framelayoutTime);
		framelayoutSound= (FrameLayout)layout.findViewById(R.id.framelayoutSound);
		framelayoutOnOff= (FrameLayout)layout.findViewById(R.id.framelayoutOnOff);
		framelayoutLeft= (FrameLayout)layout.findViewById(R.id.framelayoutLeft);
		
		
		buttonDelete=(Button)layout.findViewById(R.id.buttonDelete);
		
		toggleButtonListONOFF = (ToggleButton)layout.findViewById(R.id.toggleButtonListONOFF);
		
		toggleButtonListONOFF.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				setBalloomVisiblity(isChecked);
				singleAlarmListItemInfo.alaramONOFF.onoff = isChecked?1:0;
				if(onOfflistener !=null){
					onOfflistener.ChangeOnOff(singleAlarmListItemInfo, position);
				}
			}
		});
		
		buttonDelete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*1차출시*/
//				Toast.makeText(mContext, "reqCode : "+singleAlarmListItemInfo.request_code.requestCode+" position : "+position, 0).show();
				if(listener != null){
					listener.DeleteThis(singleAlarmListItemInfo, position);
				}
			}
		});
	}
	
	public interface OnDeleteButtonClickListener{
		public void DeleteThis(SingleAlarmListItemInfo info,int position);
	}
	
	public interface OnOnOffButtonClickListener{
		public void ChangeOnOff(SingleAlarmListItemInfo info,int position);
	}
	
	
	OnDeleteButtonClickListener listener;
	OnOnOffButtonClickListener onOfflistener;
	
	public void setOnDeleteButtonClickListener(OnDeleteButtonClickListener listener){
		this.listener = listener;
	}
	
	
	public void setOnOnOffButtonClickListener(OnOnOffButtonClickListener listener){
		this.onOfflistener = listener;
	}
	
	public boolean setData(SingleAlarmListItemInfo listItemInfo,int positoin) {
		this.position = positoin;

		singleAlarmListItemInfo = listItemInfo;
		
		
		// ★
		if(singleAlarmListItemInfo.alarmTime.hh==12){
			textViewAMPM.setText("오후");
			
			temp_hour = singleAlarmListItemInfo.alarmTime.hh;
			
		}else if(singleAlarmListItemInfo.alarmTime.hh>=13){
			textViewAMPM.setText("오후");
			
			temp_hour = singleAlarmListItemInfo.alarmTime.hh - 12;
			
		}else if(singleAlarmListItemInfo.alarmTime.hh<12){
			textViewAMPM.setText("오전");
			
			temp_hour = singleAlarmListItemInfo.alarmTime.hh;
		}
		
		String str_temp_hour="";
		String str_temp_minute="";
		
		str_temp_hour =  Integer.toString(temp_hour); 
		str_temp_minute = Integer.toString(singleAlarmListItemInfo.alarmTime.mm);
		
		if(str_temp_hour.length()==1){
			str_temp_hour = "0"+str_temp_hour;
		}
		
		if(str_temp_minute.length()==1){
			str_temp_minute = "0"+str_temp_minute;
		}
		
		txtTime.setText(str_temp_hour +":"+ str_temp_minute);
		txtDays.setText(singleAlarmListItemInfo.dayOfTheWeek.getBanbok());
		
//		textViewSnoozeTime.setText(singleAlarmListItemInfo.snoozeTime.time+"");
//		textViewAlarmType.setText(singleAlarmListItemInfo.alarmType.toString());
//		textViewCity.setText(singleAlarmListItemInfo.city.live_city);
//		textViewSong.setText(singleAlarmListItemInfo.song.toString());
//		textViewVol.setText(singleAlarmListItemInfo.soundvolumn.volumn_size+"");
//		textViewReqCode.setText(singleAlarmListItemInfo.request_code.requestCode+"");
//		toggleButtonListONOFF.setChecked(singleAlarmListItemInfo.alaramONOFF.onoff==1?true:false);
		
		toggleButtonListONOFF.setChecked(singleAlarmListItemInfo.alaramONOFF.onoff==1?true:false);
		setBalloomVisiblity(singleAlarmListItemInfo.alaramONOFF.onoff==1?true:false);
		switch (singleAlarmListItemInfo.alarmType.vibration) {
		case 1:
			imageViewVib.setBackgroundResource(R.drawable.vibration);
			break;
		case 0:
			imageViewVib.setBackgroundResource(R.drawable.vibration_off);
			break;
		}
		switch (singleAlarmListItemInfo.alarmType.Wave) {
		case 1:
			imageViewSound.setBackgroundResource(R.drawable.sound);			
			break;
		case 0:
			imageViewSound.setBackgroundResource(R.drawable.sound_off);
			break;
		}
		return true;
	}


	public void setDeleteButtonVisiblity(int i) {
		buttonDelete.setVisibility(i);
	}	
	
	
	private void setBalloomVisiblity(boolean onoff){
		framelayoutLeft.setBackgroundResource(onoff?R.drawable.balloom_list_left:R.drawable.balloom_list_left_off);
		framelayoutTime.setBackgroundResource(onoff?R.drawable.balloom_list_time:R.drawable.balloom_list_time_off);
		framelayoutSound.setBackgroundResource(onoff?R.drawable.balloom_list_vib_sound:R.drawable.balloom_list_vib_sound_off);
		framelayoutOnOff.setBackgroundResource(onoff?R.drawable.balloom_list_onoff:R.drawable.balloom_list_onoff_off);
		framelayoutRight.setBackgroundResource(onoff?R.drawable.balloom_list_right:R.drawable.balloom_list_right_off);
		imageViewBar.setBackgroundResource(onoff?R.drawable.bar:R.drawable.bar_off);
	}
	
}
