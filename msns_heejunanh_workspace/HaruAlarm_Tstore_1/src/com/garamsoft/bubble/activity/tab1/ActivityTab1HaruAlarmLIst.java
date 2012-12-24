package com.garamsoft.bubble.activity.tab1;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.garamsoft.bubble.R;
import com.garamsoft.bubble.activity.adapter.AdaterAlarmList;
import com.garamsoft.bubble.alarmdata.SingleAlarmListItemInfo;
import com.garamsoft.bubble.manager.AlarmDataManager;

/***/
public class ActivityTab1HaruAlarmLIst extends Activity {
    
	private static final int ADDLISTITEM = 0;
	private static final int UPDATA_LIST_ITME = 1;
	
	public static final String REQUEST_CODE = "REQUEST_CODE";
	public static final String REQUEST_CODE_POSITION ="REQUEST_CODE_POSITION";
	
	private Context mContext;
	
	
//	private Button btnCancel;
	
	
	private ListView listAlarm;
	private Button btnListItemadd;
	private ToggleButton btnListItemEdit;
	private TextView textViewReqLast;
	/** Called when the activity is first created. */
    
	private AlarmDataManager alarmDataManager;
	private AdaterAlarmList adaterAlarmList;
	private ArrayList<SingleAlarmListItemInfo> singleItemDatas;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_tab1_list);
        
        mContext = getApplicationContext();
        
        /**알람데이터 매니저 안에는 DB매니저를 포함하고 있기때문에 컨텍스트가 필요하다*/
        alarmDataManager = AlarmDataManager.getInstance(mContext);

        /*알람데이터매니저에서 리스트를 받아오는 것이다.*/
        singleItemDatas = alarmDataManager.getAlarmListItemInfos();
        sortList();
        
        listAlarm = (ListView)findViewById(R.id.listAlarm);
        btnListItemadd = (Button)findViewById(R.id.btnListItemadd);
        btnListItemEdit = (ToggleButton)findViewById(R.id.btnListItemEdit);
        
        
        listAlarm.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long reqCode) {
				/*1차출시*/
//				Toast.makeText(mContext, ""+reqCode, 0).show();
				Intent intent = new Intent(mContext, ActivityTab1UpdataListItem.class);
				intent.putExtra(REQUEST_CODE, (int)reqCode);//위치가감.
				intent.putExtra(REQUEST_CODE_POSITION, position);//reqCode가 감.
				startActivityForResult(intent, UPDATA_LIST_ITME);
			}
		});
        
        
        /***/
        textViewReqLast = (TextView)findViewById(R.id.textViewReqLast);
        textViewReqLast.setText("reqCode : "+alarmDataManager.getRequestCode());
        /***/
        
        
        
        adaterAlarmList = new AdaterAlarmList(mContext, singleItemDatas);
        
        listAlarm.setAdapter(adaterAlarmList);
       
        // 알람 추가 버튼.
        btnListItemadd.setOnClickListener(clickListenerAdd);
        
        
        // ★ 알람 삭제 버튼.   ( 애니메이션 추가 )
        btnListItemEdit.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				// 켜져 있으면.
				if(btnListItemEdit.isChecked()){
					
					
					btnListItemadd.setEnabled(false);
					
					Animation b = new RotateAnimation(0,90,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
					
					b.setDuration(500);						// 초 계산.
					b.setRepeatCount(0);						// 반복 횟수.
					b.setFillAfter(true);							// 기존에 끝났던 값 유지.
						btnListItemEdit.startAnimation(b);
					adaterAlarmList.setVisibleDeleteButton(View.VISIBLE);
					
					
					
					// 꺼져 있으면.
				}else if(!btnListItemEdit.isChecked()){
					
					
					btnListItemadd.setEnabled(true);
					
					Animation b = new RotateAnimation(90,0,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
					
					b.setDuration(500);						// 초 계산
					b.setRepeatCount(0);						// 반복 횟수.
					b.setFillAfter(true);							// 기존에 끝났던 값 유지
					
					btnListItemEdit.startAnimation(b);
					adaterAlarmList.setVisibleDeleteButton(View.INVISIBLE);
					
				}
			}
		});
    }
	
	OnClickListener clickListenerAdd = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnListItemadd:
				Intent intent = new Intent(mContext, ActivityTab1AddListItem.class);
				startActivityForResult(intent, ADDLISTITEM);
				break;
			}
			
		}
	};
	
	// ActivityAddListItem에서 설정한 값들을 받아옴.
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode==RESULT_OK)
		switch (requestCode) {
		case ADDLISTITEM:
			sortList();
			adaterAlarmList.notifyDataSetChanged();
			break;
		case UPDATA_LIST_ITME:
			sortList();
			adaterAlarmList.notifyDataSetChanged();
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
    @Override
    protected void onResume() {
    	super.onResume();
    	textViewReqLast.setText("reqCode : "+alarmDataManager.getRequestCode());
    }
    
    
    private void sortList() {
		
    	SingleAlarmListItemInfo temp_info;//뷰가 보여줄 리스트 아이템의 데이터
		//
		for(int i = 0 ;i< singleItemDatas.size(); i++){
			for (int k = i + 1; k < singleItemDatas.size(); k++) {
				if (singleItemDatas.get(i).alarmTime.hh > singleItemDatas.get(k).alarmTime.hh) {
					
					temp_info = singleItemDatas.get(i);
					
					singleItemDatas.set(i, singleItemDatas.get(k));
					singleItemDatas.set(k, temp_info);
					
				}else if(singleItemDatas.get(i).alarmTime.hh == singleItemDatas.get(k).alarmTime.hh){
					if(singleItemDatas.get(i).alarmTime.mm > singleItemDatas.get(k).alarmTime.mm){
						temp_info = singleItemDatas.get(i);
						singleItemDatas.set(i, singleItemDatas.get(k));
						singleItemDatas.set(k, temp_info);
					}
				}
			}
		}
	}
    
 // 추가 이너 클래스.
    class MinHour_compare {
    	int hour = 0;
    	int minute = 0;
    	
    	MinHour_compare(int _hour, int _minute){
    		hour = _hour;
    		minute = _minute;
    	}
    }
    
    

//	★  Back 버튼을 눌렀을 경우 띄울 다이얼로그
//	public boolean onKeyDown(int KeyCode, KeyEvent event) {
//		switch (KeyCode) {
//		
//		case KeyEvent.KEYCODE_BACK:
//			;
//		}
//		return true;
//	}
	
	@Override
	public void onBackPressed() {
		if(btnListItemEdit.isChecked()){
			btnListItemEdit.setChecked(false);
		}
		else{String alertTitle = "하루알람";
		String buttonMessage = "하루알람을 종료하시겠습니까?";
		String buttonYes = "예";
		String buttonNo = "아니오";

		new AlertDialog.Builder(ActivityTab1HaruAlarmLIst
				.this)
				.setTitle(alertTitle)
				.setMessage(buttonMessage)
				.setPositiveButton(buttonYes,
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								moveTaskToBack(true);
								finish();
							}
						}).setNegativeButton(buttonNo, null).show();
						}
	}
    
}