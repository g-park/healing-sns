package com.garamsoft.bubble.view;

import java.util.GregorianCalendar;

import com.garamsoft.bubble.R;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

public class JGTimePicker extends LinearLayout {
	
	public int getCurrentHour() {
		if(toggleButtonAMPM.isChecked()){
			if(timePickerDataHH == 12)
			{
				return timePickerDataHH-12;
			}
				return timePickerDataHH;
		}
		else {
			return timePickerDataHH+12;
		}
		
	}

	public int getCurrentMinute() {
		return timePickerDataMM;
	}

	public void setCurrentHour(int hh) {
		toggleButtonAMPM.setChecked(isAM(hh));
		timePickerDataHH = setHHTime(hh);
		editTextHH.setText(String.valueOf(timePickerDataHH));
	}

	public void setCurrentMinute(int mm) {
		timePickerDataMM = setMMTime(mm);
		editTextMM.setText(String.valueOf(mm));
	}

	/**
	 * true is AM, false is PM*/
	public boolean getAMPM() {
		return toggleButtonAMPM.isChecked();
	}


	static final String tag = "HARU";
	
	PlusNumberClass plusNumberClass;
		
	
	private static final int PLUS_HH = R.id.btnHHadd;
	private static final int PLUS_MM = R.id.btnMMadd;
	private static final int SUB_HH = R.id.btnHHsb;
	private static final int SUB_MM = R.id.btnMMsb;
	
	int timePickerDataHH;
	int timePickerDataMM;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			setInteractUser(msg.arg1);
		}
	};
	
	GregorianCalendar gregorianCalendar;

	Button btnHHadd;
	Button btnMMadd;
	Button btnHHsb;
	Button btnMMsb;

	EditText editTextHH;
	EditText editTextMM;

	ToggleButton toggleButtonAMPM;

	protected boolean longTouchFlag;
	private Context mContext;

	public JGTimePicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		mContext = context;

		gregorianCalendar = new GregorianCalendar();
		timePickerDataHH = setHHTime(gregorianCalendar.getTime().getHours());
		timePickerDataMM = setHHTime(gregorianCalendar.getTime().getMinutes());

		setLayoutParams(new LayoutParams(500, 500));
		setGravity(Gravity.CENTER);

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.view_jgtimepickerlayout, this);
		btnHHadd = (Button) layout.findViewById(R.id.btnHHadd);
		btnMMadd = (Button) layout.findViewById(R.id.btnMMadd);
		btnHHsb = (Button) layout.findViewById(R.id.btnHHsb);
		btnMMsb = (Button) layout.findViewById(R.id.btnMMsb);
		editTextHH = (EditText) layout.findViewById(R.id.editTextHH);
		editTextMM = (EditText) layout.findViewById(R.id.editTextMM);

		btnHHadd.setOnClickListener(clickListener);
		btnMMadd.setOnClickListener(clickListener);
		btnHHsb.setOnClickListener(clickListener);
		btnMMsb.setOnClickListener(clickListener);
		editTextHH.setOnClickListener(clickListener);
		editTextMM.setOnClickListener(clickListener);
		
		btnHHadd.setOnLongClickListener(longClickListener);
		btnMMadd.setOnLongClickListener(longClickListener);
		btnHHsb.setOnLongClickListener(longClickListener);
		btnMMsb.setOnLongClickListener(longClickListener);
		editTextHH.setOnLongClickListener(longClickListener);
		editTextMM.setOnLongClickListener(longClickListener);
		
		btnHHadd.setOnTouchListener(onTouchListener);
		btnMMadd.setOnTouchListener(onTouchListener);
		btnHHsb.setOnTouchListener(onTouchListener);
		btnMMsb.setOnTouchListener(onTouchListener);
		editTextHH.setOnTouchListener(onTouchListener);
		editTextMM.setOnTouchListener(onTouchListener);
		
		

		toggleButtonAMPM = (ToggleButton) layout.findViewById(R.id.toggleButtonAMPM);
		toggleButtonAMPM.setChecked(isAM(gregorianCalendar.getTime().getHours()));

		editTextHH.setText(String.valueOf(timePickerDataHH));
		editTextMM.setText(String.valueOf(timePickerDataMM));

		editTextHH.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					((EditText)v).selectAll();
				}
			}
		});
		editTextMM.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					((EditText)v).selectAll();
				}
				else {
//					((EditText)v).setK
				}
			}
		});
//		editTextHH.setOnClickListener(clickListener);
//		editTextMM.setOnClickListener(clickListener);
		
		editTextMM.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					((EditText)v).selectAll();
				}
			}
		});
		
		editTextHH.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				EditText editText = (EditText) v;
				String string = editText.getText().toString();
				if (!string.equals("")) {
					int time = Integer.parseInt(string);
					if (time > 12 && !string.equals("")) {
						editText.setText(String.valueOf(timePickerDataHH));
						String indexString = editText.getText().toString();
						int index = indexString.length();
						editText.setSelection(index);
					}
					timePickerDataHH = setHHTime(setHHTime(Integer
							.parseInt(string)));
				}
				return false;
			}
		});
		editTextMM.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				EditText editText = (EditText) v;
				String string = editText.getText().toString();
				if (!string.equals("")) {
					int time = Integer.parseInt(string);
					if (time >= 60) {
						editText.setText(String.valueOf(timePickerDataMM));
						String indexString = editText.getText().toString();
						int index = indexString.length();
						editText.setSelection(index);
					}
					timePickerDataMM = setMMTime(setMMTime(Integer
							.parseInt(string)));
				}
				return false;
			}
		});
	
	}

	public JGTimePicker(Context context) {
		super(context);
		mContext = context;

		gregorianCalendar = new GregorianCalendar();
		timePickerDataHH = setHHTime(gregorianCalendar.getTime().getHours());
		timePickerDataMM = setHHTime(gregorianCalendar.getTime().getMinutes());

		setBackgroundColor(Color.WHITE);
		setLayoutParams(new LayoutParams(500, 500));
		setGravity(Gravity.CENTER);

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.view_jgtimepickerlayout, this);
		btnHHadd = (Button) layout.findViewById(R.id.btnHHadd);
		btnMMadd = (Button) layout.findViewById(R.id.btnMMadd);
		btnHHsb = (Button) layout.findViewById(R.id.btnHHsb);
		btnMMsb = (Button) layout.findViewById(R.id.btnMMsb);
		editTextHH = (EditText) layout.findViewById(R.id.editTextHH);
		editTextMM = (EditText) layout.findViewById(R.id.editTextMM);

		btnHHadd.setOnClickListener(clickListener);
		btnMMadd.setOnClickListener(clickListener);
		btnHHsb.setOnClickListener(clickListener);
		btnMMsb.setOnClickListener(clickListener);
		editTextHH.setOnClickListener(clickListener);
		editTextMM.setOnClickListener(clickListener);
		
		btnHHadd.setOnLongClickListener(longClickListener);
		btnMMadd.setOnLongClickListener(longClickListener);
		btnHHsb.setOnLongClickListener(longClickListener);
		btnMMsb.setOnLongClickListener(longClickListener);
		editTextHH.setOnLongClickListener(longClickListener);
		editTextMM.setOnLongClickListener(longClickListener);
		
		btnHHadd.setOnTouchListener(onTouchListener);
		btnMMadd.setOnTouchListener(onTouchListener);
		btnHHsb.setOnTouchListener(onTouchListener);
		btnMMsb.setOnTouchListener(onTouchListener);
		editTextHH.setOnTouchListener(onTouchListener);
		editTextMM.setOnTouchListener(onTouchListener);
		
		

		toggleButtonAMPM = (ToggleButton) layout.findViewById(R.id.toggleButtonAMPM);
		toggleButtonAMPM.setChecked(isAM(gregorianCalendar.getTime().getHours()));

		editTextHH.setText(String.valueOf(timePickerDataHH));
		editTextMM.setText(String.valueOf(timePickerDataMM));

		editTextHH.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					((EditText)v).selectAll();
				}
			}
		});
		editTextMM.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					((EditText)v).selectAll();
				}
				else {
//					((EditText)v).setK
				}
			}
		});
//		editTextHH.setOnClickListener(clickListener);
//		editTextMM.setOnClickListener(clickListener);
		
		editTextMM.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					((EditText)v).selectAll();
				}
			}
		});
		
		editTextHH.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				EditText editText = (EditText) v;
				String string = editText.getText().toString();
				if (!string.equals("")) {
					int time = Integer.parseInt(string);
					if (time > 12 && !string.equals("")) {
						editText.setText(String.valueOf(timePickerDataHH));
						String indexString = editText.getText().toString();
						int index = indexString.length();
						editText.setSelection(index);
					}
					timePickerDataHH = setHHTime(setHHTime(Integer
							.parseInt(string)));
				}
				return false;
			}
		});
		editTextMM.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				EditText editText = (EditText) v;
				String string = editText.getText().toString();
				if (!string.equals("")) {
					int time = Integer.parseInt(string);
					if (time > 59) {
						editText.setText(String.valueOf(timePickerDataMM));
						String indexString = editText.getText().toString();
						int index = indexString.length();
						editText.setSelection(index);
					}
					timePickerDataMM = setMMTime(setMMTime(Integer
							.parseInt(string)));
				}
				return false;
			}
		});
	}
	

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnHHadd:
				timePickerDataHH += 1;
				timePickerDataHH = setHHTime(timePickerDataHH);
				editTextHH.setText(String.valueOf(timePickerDataHH));
				break;
			case R.id.btnMMadd:
				timePickerDataMM += 1;
				timePickerDataMM = setMMTime(timePickerDataMM);
				editTextMM.setText(String.valueOf(timePickerDataMM));
				break;
			case R.id.btnHHsb:
				timePickerDataHH -= 1;
				timePickerDataHH = setHHTime(timePickerDataHH);
				editTextHH.setText(String.valueOf(timePickerDataHH));
				break;
			case R.id.btnMMsb:
				timePickerDataMM -= 1;
				timePickerDataMM=setMMTime(timePickerDataMM);
				editTextMM.setText(String.valueOf(timePickerDataMM));
				break;
			case R.id.editTextHH:
				editTextHH.selectAll();
				break;
			case R.id.editTextMM:
				editTextMM.selectAll();
				break;
			}
		}
	};
	
	OnLongClickListener longClickListener = new OnLongClickListener() {
		
		@Override
		public boolean onLongClick(View v) {
			longTouchFlag = true;
			
			plusNumberClass = new PlusNumberClass(v.getId());
			plusNumberClass.start();
			
//			switch (v.getId()) {
//			case R.id.btnHHadd:setInteractUser(PLUS_HH);break;
//			case R.id.btnMMadd:setInteractUser(PLUS_MM);break;
//			case R.id.btnHHsb:setInteractUser(SUB_HH);break;
//			case R.id.btnMMsb:setInteractUser(SUB_MM);break;
//			}
			setInteractUser(v.getId());
			
			return true;
		}
	};
	
	OnTouchListener onTouchListener = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			Log.i(tag, "onTouchListener : "+event.getAction());
			if(event.getAction() == MotionEvent.ACTION_UP){
				longTouchFlag = false;
				Log.i(tag, "onTouchListener : "+longTouchFlag);
				if (plusNumberClass != null){
					plusNumberClass.interrupt();
				}
			}
			return false;
		}
	};

	OnCheckedChangeListener checkedChangeListener = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if(buttonView.getId() == R.id.toggleButtonAMPM){
				buttonView.setChecked(isChecked);
			}
		}
	};
	
	int setHHTime(int hh) {
		
		if(hh ==  13)
			hh = 1;
		else if(hh == 0)
			hh = 12;
		else if(12<hh){
			hh -=12;
		}
		

		return hh;
	}
	
	boolean isAM(int hh){
		if(12>hh){
			return true;
		}
		return false;
	}
	
	int setMMTime(int mm){
		if(mm > 60){
			return mm;
		}
		if(mm == 60){
			mm = 0;
		}
		if(mm == -1){
			mm = 59;
		}
		return mm;
	}
	int seMMTime() {
		return 0;
	}
	
	void setInteractUser(int flag){
		switch (flag) {
		case PLUS_HH:
			timePickerDataHH += 1;
			timePickerDataHH = setHHTime(timePickerDataHH);
			editTextHH.setText(String.valueOf(timePickerDataHH));				
			break;
		case SUB_HH:
			timePickerDataHH -= 1;
			timePickerDataHH = setHHTime(timePickerDataHH);
			editTextHH.setText(String.valueOf(timePickerDataHH));
			break;
		case PLUS_MM:
			timePickerDataMM += 1;
			timePickerDataMM = setMMTime(timePickerDataMM);
			editTextMM.setText(String.valueOf(timePickerDataMM));
			break;
		case SUB_MM:
			timePickerDataMM -= 1;
			timePickerDataMM=setMMTime(timePickerDataMM);
			editTextMM.setText(String.valueOf(timePickerDataMM));
			break;
		}
	}
	
	
	class PlusNumberClass extends Thread{
		int flag;
		public PlusNumberClass(int flag) {
			super();
			this.flag = flag;
		}
		
		@Override
		public void run() {
			try {
				while (longTouchFlag) {
					Message message = Message.obtain();
					message.arg1 = flag;
					mHandler.sendMessage(message);
					if(this!=null)
					sleep(100);
				}
			} catch (InterruptedException e) {
				Log.i(tag,"PlusNumberClass : threadException sleep");
			}
		}
	}

}
