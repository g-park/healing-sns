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
        
        /**�˶������� �Ŵ��� �ȿ��� DB�Ŵ����� �����ϰ� �ֱ⶧���� ���ؽ�Ʈ�� �ʿ��ϴ�*/
        alarmDataManager = AlarmDataManager.getInstance(mContext);

        /*�˶������͸Ŵ������� ����Ʈ�� �޾ƿ��� ���̴�.*/
        singleItemDatas = alarmDataManager.getAlarmListItemInfos();
        sortList();
        
        listAlarm = (ListView)findViewById(R.id.listAlarm);
        btnListItemadd = (Button)findViewById(R.id.btnListItemadd);
        btnListItemEdit = (ToggleButton)findViewById(R.id.btnListItemEdit);
        
        
        listAlarm.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long reqCode) {
				/*1�����*/
//				Toast.makeText(mContext, ""+reqCode, 0).show();
				Intent intent = new Intent(mContext, ActivityTab1UpdataListItem.class);
				intent.putExtra(REQUEST_CODE, (int)reqCode);//��ġ����.
				intent.putExtra(REQUEST_CODE_POSITION, position);//reqCode�� ��.
				startActivityForResult(intent, UPDATA_LIST_ITME);
			}
		});
        
        
        /***/
        textViewReqLast = (TextView)findViewById(R.id.textViewReqLast);
        textViewReqLast.setText("reqCode : "+alarmDataManager.getRequestCode());
        /***/
        
        
        
        adaterAlarmList = new AdaterAlarmList(mContext, singleItemDatas);
        
        listAlarm.setAdapter(adaterAlarmList);
       
        // �˶� �߰� ��ư.
        btnListItemadd.setOnClickListener(clickListenerAdd);
        
        
        // �� �˶� ���� ��ư.   ( �ִϸ��̼� �߰� )
        btnListItemEdit.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				// ���� ������.
				if(btnListItemEdit.isChecked()){
					
					
					btnListItemadd.setEnabled(false);
					
					Animation b = new RotateAnimation(0,90,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
					
					b.setDuration(500);						// �� ���.
					b.setRepeatCount(0);						// �ݺ� Ƚ��.
					b.setFillAfter(true);							// ������ ������ �� ����.
						btnListItemEdit.startAnimation(b);
					adaterAlarmList.setVisibleDeleteButton(View.VISIBLE);
					
					
					
					// ���� ������.
				}else if(!btnListItemEdit.isChecked()){
					
					
					btnListItemadd.setEnabled(true);
					
					Animation b = new RotateAnimation(90,0,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
					
					b.setDuration(500);						// �� ���
					b.setRepeatCount(0);						// �ݺ� Ƚ��.
					b.setFillAfter(true);							// ������ ������ �� ����
					
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
	
	// ActivityAddListItem���� ������ ������ �޾ƿ�.
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
		
    	SingleAlarmListItemInfo temp_info;//�䰡 ������ ����Ʈ �������� ������
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
    
 // �߰� �̳� Ŭ����.
    class MinHour_compare {
    	int hour = 0;
    	int minute = 0;
    	
    	MinHour_compare(int _hour, int _minute){
    		hour = _hour;
    		minute = _minute;
    	}
    }
    
    

//	��  Back ��ư�� ������ ��� ��� ���̾�α�
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
		else{String alertTitle = "�Ϸ�˶�";
		String buttonMessage = "�Ϸ�˶��� �����Ͻðڽ��ϱ�?";
		String buttonYes = "��";
		String buttonNo = "�ƴϿ�";

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