package com.garamsoft.bubble.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.garamsoft.bubble.R;

public class ViewTabLinearController extends LinearLayout {
	
	private Button 
	buttonAlarmTab,
	buttonInfoTab,
	buttonSettingTab;

	public ViewTabLinearController(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.view_tab0_tabhost_controller, this);
		buttonAlarmTab = (Button)layout.findViewById(R.id.buttonAlarmTab);
		buttonInfoTab = (Button)layout.findViewById(R.id.buttonInfoTab);
		buttonSettingTab = (Button)layout.findViewById(R.id.buttonSettingTab);
		buttonAlarmTab.setOnClickListener(clickListener);
		buttonInfoTab.setOnClickListener(clickListener);
		buttonSettingTab.setOnClickListener(clickListener);
	}
	
//	public void setBtn();
	
	public interface BtnTab{
		public void onBtnTab1Clicked();
		public void onBtnTab2Clicked();
		public void onBtnTab3Clicked();
	}
	
	BtnTab btnTab;
	
	public void setOnBtnTab(BtnTab tab){
	btnTab = tab;
	}
	
	OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			switch (v.getId()) {
			case R.id.buttonAlarmTab:
				if(btnTab !=null)
					btnTab.onBtnTab1Clicked();
//				TabMain.setTabLocation(0);
				buttonAlarmTab.setEnabled(false);
				buttonInfoTab.setEnabled(true);
				buttonSettingTab.setEnabled(true);
				break;
			case R.id.buttonInfoTab:
				if(btnTab !=null)
					btnTab.onBtnTab2Clicked();
//				TabMain.setTabLocation(1);
				buttonAlarmTab.setEnabled(true);
				buttonInfoTab.setEnabled(false);
				buttonSettingTab.setEnabled(true);
				break;
			case R.id.buttonSettingTab:
				if(btnTab !=null)
					btnTab.onBtnTab3Clicked();
//				TabMain.setTabLocation(2);	
				buttonAlarmTab.setEnabled(true);
				buttonInfoTab.setEnabled(true);
				buttonSettingTab.setEnabled(false);
				break;
			}
			
		}
	};

	public void setCurrentBtn(int i) {
		switch (i) {
		case 0:
			if(btnTab !=null)
				btnTab.onBtnTab1Clicked();
//			TabMain.setTabLocation(0);
			buttonAlarmTab.setEnabled(false);
			buttonInfoTab.setEnabled(true);
			buttonSettingTab.setEnabled(true);
			break;
		case 1:
			if(btnTab !=null)
				btnTab.onBtnTab2Clicked();
//			TabMain.setTabLocation(1);
			buttonAlarmTab.setEnabled(true);
			buttonInfoTab.setEnabled(false);
			buttonSettingTab.setEnabled(true);
			break;
		case 2:
			if(btnTab !=null)
				btnTab.onBtnTab3Clicked();
//			TabMain.setTabLocation(2);	
			buttonAlarmTab.setEnabled(true);
			buttonInfoTab.setEnabled(true);
			buttonSettingTab.setEnabled(false);
			break;
		}
	
	}
	
//	public OnClickListener clickListener;
//	
//	public void setTabButton(OnClickListener clickListener){
//		this.clickListener = clickListener 
//	}

}
