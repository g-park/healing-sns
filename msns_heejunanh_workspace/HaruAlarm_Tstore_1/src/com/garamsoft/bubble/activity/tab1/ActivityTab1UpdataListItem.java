package com.garamsoft.bubble.activity.tab1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.garamsoft.bubble.R;
import com.garamsoft.bubble.alarmdata.DayOfTheWeek;
import com.garamsoft.bubble.alarmdata.SingleAlarmListItemInfo;
import com.garamsoft.bubble.manager.AlarmDataManager;
import com.garamsoft.bubble.view.JGTimePicker;

public class ActivityTab1UpdataListItem extends Activity {

	// ��
	private TextView alarmadd_text_songName;
	public static final int WARNING_DIALOG = 11;

	protected static final int REQUST_SONG = 101;
	/**
	 * �� ��Ƽ��Ƽ�� �˶��� ���� ������ �����ϴ� ��Ƽ��Ƽ�̴�. �˶�����Ʈ���� �˶��߰��� ������ ������ ��Ƽ��Ƽ.
	 * */
	private Context mContext;
	private SingleAlarmListItemInfo singleAlarmListItemInfo;
	private AlarmDataManager alarmDataManager; // ��.
	/**
	 * ���ؽ�Ʈ. �˶�����Ʈ������ SQLite�� ������ �ְ� ���� �ؾ��ϴ� ��. �˶�����Ʈ������ �־��� ���� ���� �ϴ� Ŭ����.
	 * */

	private int position;
	private int getReqCode;// ������ �������� ������Ʈ ��.

	private SeekBar seekbar_volumn_control;
	private JGTimePicker timePicker;
	private ToggleButton toggleMon, toggleTue, toggleWed, toggleThu, toggleFri,
			toggleSat, toggleSun;
	private ToggleButton toggleMid, toggleEnd, toggleEve;
	private ToggleButton toggleVib, toggleSound, toggleButtonONOFF;
	private Button btnComplete;
	
	// �� �˶� ��� ��ư.
		private Button btnCancel;
		private ToggleButton 
		toggleButton_00,
		toggleButton_03,
		toggleButton_05,
		toggleButton_10,
		toggleButton_15;

	private TextView text_sound_volumn;

	private StringBuilder banbok = new StringBuilder(); // �ݺ� ��¥ ����.

	// //////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * SingleAlarmListItemInfo�� ������� �������. ( id�� ���� �� 11�� ) -> Id 1��, ���� 10��.
	 */

	// �ð��� timePicker.getCurrentHour();
	// ���� timePicker.getCurrentMinute();
	// private boolean vibrator = false; // ���� ����.
	// private boolean sound_on_off = false; // ���� on/off

	private String live = "����"; // ��� �� ����.
	private int snooze_time = 5; // ���� �ð� ����.
	private String song_path = ""; // ������ �뷡 ���.
	private int sound_volumn = 75; // ���� ����.
	private int request_code; // request_code;

	// //////////////////////////////////////////////////////////////////////////////////////////////////

	// �������� ��ư.
	private Spinner alarm_spinner_local_select;

	// �̷�� �ð� ���� ȭ�� ( spinner�� ���� )

	// �뷡 ���� ��ư.
	private Button alarm_btn_song_select;
	protected String song_name;

	/**
	 * Ÿ����Ŀ ���� ��� ��ư�� ����,�Ҹ� ��� ��ư.
	 * */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab1_addlist);

		mContext = getApplicationContext();// ���ؽ��� �޾ƿ´�.

		Intent intent = getIntent();

		position = intent.getIntExtra(
				ActivityTab1HaruAlarmLIst.REQUEST_CODE_POSITION, -1);
		getReqCode = intent.getIntExtra(ActivityTab1HaruAlarmLIst.REQUEST_CODE, 0);

		if (position == -1 || getReqCode == 0) {
			setResult(RESULT_CANCELED);
			finish();// �Ϸ� ��ư�� ������ �Ϸ�Ǹ� �˶�����Ʈ��Ƽ��Ƽ�� �̵��Ѵ�.
		}

		alarmDataManager = AlarmDataManager.getInstance(mContext);// �̱������� ������
																	// �Ŵ�����
																	// �ν��Ͻ���
																	// �޾ƿ´�.
		singleAlarmListItemInfo = alarmDataManager
				.getSingleAlarmListItemInfoAtReqCode(getReqCode);
		song_path = singleAlarmListItemInfo.song.path;
		sound_volumn = singleAlarmListItemInfo.soundvolumn.volumn_size;
		song_name = singleAlarmListItemInfo.song.name;
		snooze_time = singleAlarmListItemInfo.snoozeTime.time;

		// ///������ �ʿ���.

		//
		alarmadd_text_songName = (TextView) findViewById(R.id.alarmadd_text_songName);

		seekbar_volumn_control = (SeekBar) findViewById(R.id.seekbar_voulme_control);
		timePicker = (JGTimePicker) findViewById(R.id.jGTimePicker1);
		toggleMon = (ToggleButton) findViewById(R.id.toggleMon);
		toggleTue = (ToggleButton) findViewById(R.id.toggleTue);
		toggleWed = (ToggleButton) findViewById(R.id.toggleWed);
		toggleThu = (ToggleButton) findViewById(R.id.toggleThu);
		toggleFri = (ToggleButton) findViewById(R.id.toggleFri);
		toggleSat = (ToggleButton) findViewById(R.id.toggleSat);
		toggleSun = (ToggleButton) findViewById(R.id.toggleSun);

		toggleMid = (ToggleButton) findViewById(R.id.toggleMid);
		toggleEnd = (ToggleButton) findViewById(R.id.toggleEnd);
		toggleEve = (ToggleButton) findViewById(R.id.toggleEve);

		toggleVib = (ToggleButton) findViewById(R.id.toggleVib);
		toggleSound = (ToggleButton) findViewById(R.id.toggleSound);
		toggleButtonONOFF = (ToggleButton) findViewById(R.id.toggleButtonONOFF);

		alarm_spinner_local_select = (Spinner) findViewById(R.id.alarm_spinner_local_select);
		alarm_btn_song_select = (Button) findViewById(R.id.alarm_btn_song_select);
		text_sound_volumn = (TextView) findViewById(R.id.text_soundVolumn);

		btnComplete = (Button) findViewById(R.id.btnComplete);

		toggleMon.setOnCheckedChangeListener(onCheckedChangeListener);
		toggleTue.setOnCheckedChangeListener(onCheckedChangeListener);
		toggleWed.setOnCheckedChangeListener(onCheckedChangeListener);
		toggleThu.setOnCheckedChangeListener(onCheckedChangeListener);
		toggleFri.setOnCheckedChangeListener(onCheckedChangeListener);
		toggleSat.setOnCheckedChangeListener(onCheckedChangeListener);
		toggleSun.setOnCheckedChangeListener(onCheckedChangeListener);

		toggleMid.setOnClickListener(onClickListener);
		toggleEnd.setOnClickListener(onClickListener);
		toggleEve.setOnClickListener(onClickListener);

		toggleVib.setOnCheckedChangeListener(onCheckedChangeListener);
		toggleSound.setOnCheckedChangeListener(onCheckedChangeListener);

		
		toggleButton_00 = (ToggleButton)findViewById(R.id.toggleButton_00);
		toggleButton_03 = (ToggleButton)findViewById(R.id.toggleButton_03);
		toggleButton_05 = (ToggleButton)findViewById(R.id.toggleButton_05);
		toggleButton_10 = (ToggleButton)findViewById(R.id.toggleButton_10);
		toggleButton_15 = (ToggleButton)findViewById(R.id.toggleButton_15);
		
		
		toggleButton_00.setOnClickListener(listenerSnooze);
		toggleButton_03.setOnClickListener(listenerSnooze);
		toggleButton_05.setOnClickListener(listenerSnooze);
		toggleButton_10.setOnClickListener(listenerSnooze);
		toggleButton_15.setOnClickListener(listenerSnooze);
		
		switch (snooze_time) {
		case 0:
			toggleButton_00.setChecked(true);
			break;
		case 3:
			toggleButton_03.setChecked(true);
			break;
		case 5:
			toggleButton_05.setChecked(true);
			break;
		case 10:
			toggleButton_10.setChecked(true);
			break;
		case 15:
			toggleButton_15.setChecked(true);
			break;

		}
		
		
		
		
		// ��
		alarmadd_text_songName.setText(song_name);

		// ��� �� ����. ( �ϴ� �ӽ÷� )
		ArrayAdapter<CharSequence> live_adapter = ArrayAdapter
				.createFromResource(this, R.array.first_live_array,
						android.R.layout.simple_spinner_item);
		live_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		alarm_spinner_local_select.setPrompt("���� ����");
		alarm_spinner_local_select.setAdapter(live_adapter);

		// ��
		if (singleAlarmListItemInfo.city.live_city.equals("���ֱ�����")) {
			alarm_spinner_local_select.setSelection(1);
		} else if (singleAlarmListItemInfo.city.live_city.equals("��������")) {
			alarm_spinner_local_select.setSelection(2);
		} else if (singleAlarmListItemInfo.city.live_city.equals("��������")) {
			alarm_spinner_local_select.setSelection(3);
		} else if (singleAlarmListItemInfo.city.live_city.equals("��Ⳳ��")) {
			alarm_spinner_local_select.setSelection(4);
		} else if (singleAlarmListItemInfo.city.live_city.equals("���Ϻ�")) {
			alarm_spinner_local_select.setSelection(5);
		} else if (singleAlarmListItemInfo.city.live_city.equals("���ϵ�")) {
			alarm_spinner_local_select.setSelection(6);
		} else if (singleAlarmListItemInfo.city.live_city.equals("��󳲵�")) {
			alarm_spinner_local_select.setSelection(7);
		} else if (singleAlarmListItemInfo.city.live_city.equals("�뱸������")) {
			alarm_spinner_local_select.setSelection(8);
		} else if (singleAlarmListItemInfo.city.live_city.equals("����������")) {
			alarm_spinner_local_select.setSelection(9);
		} else if (singleAlarmListItemInfo.city.live_city.equals("�λ걤����")) {
			alarm_spinner_local_select.setSelection(10);
		} else if (singleAlarmListItemInfo.city.live_city.equals("����Ư����")) {
			alarm_spinner_local_select.setSelection(11);
		} else if (singleAlarmListItemInfo.city.live_city.equals("��û�ϵ�")) {
			alarm_spinner_local_select.setSelection(12);
		} else if (singleAlarmListItemInfo.city.live_city.equals("��û����")) {
			alarm_spinner_local_select.setSelection(13);
		} else if (singleAlarmListItemInfo.city.live_city.equals("����ϵ�")) {
			alarm_spinner_local_select.setSelection(14);
		} else if (singleAlarmListItemInfo.city.live_city.equals("���󳲵�")) {
			alarm_spinner_local_select.setSelection(15);
		} else if (singleAlarmListItemInfo.city.live_city.equals("���ֵ�")) {
			alarm_spinner_local_select.setSelection(16);
		} else if (singleAlarmListItemInfo.city.live_city.equals("��õ������")) {
			alarm_spinner_local_select.setSelection(17);
		} else if (singleAlarmListItemInfo.city.live_city.equals("��걤����")) {
			alarm_spinner_local_select.setSelection(18);
		}

		// ���ǳ� ������.
		alarm_spinner_local_select
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						if (position == 0) {

						} else {
							/*1�����*/
//							Toast.makeText(
//									parent.getContext(),
//									(String) parent.getItemAtPosition(position)
//											+ "�� �����Ͽ����ϴ�.", 1).show();
							// �̰� ����Ʈ�� �Ѱ������.
							live = (String) parent.getItemAtPosition(position);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

		// �뷡 ���� ��ư.
		alarm_btn_song_select.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						SongSelect.class);
				startActivityForResult(intent, REQUST_SONG);
			}
		});

		// ����Ʈ ���� ũ��.
		seekbar_volumn_control.setProgress(sound_volumn);

		toggleButtonONOFF
				.setChecked(singleAlarmListItemInfo.alaramONOFF.onoff == 1 ? true
						: false);

		// ���� ���� ��Ʈ�ѷ�.
		seekbar_volumn_control
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					// �ٲ�.
					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub

						sound_volumn = progress;

						text_sound_volumn.setText(Integer
								.toString(sound_volumn));

					}

					// ���⶧.
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					// �����Ҷ�.
					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}
				});

		// �� �˶� �߰� ��ư.
		btnComplete.setOnClickListener(new OnClickListener() {
			/** �Ϸ��ư */
			@Override
			public void onClick(View v) {
				// �˶� ���� ����.
				if ((!toggleSound.isChecked()) && (!toggleVib.isChecked())) {
					// �� ���̾�α� ����.

					showDialog(WARNING_DIALOG);

				} else {

					if (toggleMon.isChecked())
						banbok.append("��");
					if (toggleTue.isChecked())
						banbok.append("ȭ");
					if (toggleWed.isChecked())
						banbok.append("��");
					if (toggleThu.isChecked())
						banbok.append("��");
					if (toggleFri.isChecked())
						banbok.append("��");
					if (toggleSat.isChecked())
						banbok.append("��");
					if (toggleSun.isChecked())
						banbok.append("��");

					// Toast.makeText(getApplicationContext(),"������ ������" +
					// banbok.toString() + "�Դϴ�.", 0).show();

					// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

					// ��. ( int )
					singleAlarmListItemInfo.alarmTime.hh = timePicker
							.getCurrentHour();// ���� ��Ŀ�� �ִ� �ð��� ���� ������ ������ ��ü��
												// ����.

					// ��. ( int )
					singleAlarmListItemInfo.alarmTime.mm = timePicker
							.getCurrentMinute();// ���� ��Ŀ�� �ִ� �ð��� ���� ������ ������ ��ü��
												// ����.

					// �����ð� ����. ( int )
					singleAlarmListItemInfo.snoozeTime.time = snooze_time;

					// ���� ����. ( boolean )
					singleAlarmListItemInfo.alarmType.vibration = toggleVib
							.isChecked() ? 1 : 0;

					// �Ҹ� �︱�� ���� ����. ( boolean)
					singleAlarmListItemInfo.alarmType.Wave = toggleSound
							.isChecked() ? 1 : 0;

					// �� �뷡 ���� ����. ( �̰� ��� �ȵ��� �ȴ�. )
					singleAlarmListItemInfo.song.name = alarmadd_text_songName
							.getText().toString();

					// �뷡 ��� ����. ( string)
					singleAlarmListItemInfo.song.path = song_path;

					// ���� ũ�� ����. ( int )
					singleAlarmListItemInfo.soundvolumn.volumn_size = sound_volumn;

					// request_code ���� ( int )
					request_code = getReqCode;
					/*1�����*/
//					Toast.makeText(mContext, "request_code " + request_code, 0).show();
					singleAlarmListItemInfo.request_code.requestCode = getReqCode;

					// ��� �� ����. ( String )
					singleAlarmListItemInfo.city.live_city = live;

					// ��¥ ����. ( String )
					singleAlarmListItemInfo.dayOfTheWeek = new DayOfTheWeek(banbok.toString());
							

					singleAlarmListItemInfo.alaramONOFF.turn(toggleButtonONOFF
							.isChecked() ? 1 : 0);

					alarmDataManager.updateItem(position,
							singleAlarmListItemInfo);

					setResult(RESULT_OK);
					finish();// �Ϸ� ��ư�� ������ �Ϸ�Ǹ� �˶�����Ʈ��Ƽ��Ƽ�� �̵��Ѵ�.
				}
			}
		});


		// ���� ũ�� ����. ( int )
		seekbar_volumn_control
				.setKeyProgressIncrement(singleAlarmListItemInfo.soundvolumn.volumn_size);
		text_sound_volumn.setText(String
				.valueOf(singleAlarmListItemInfo.soundvolumn.volumn_size));

		singleAlarmListItemInfo.request_code.requestCode = getReqCode;

		// ��� �� ����. ( String )
		// singleAlarmListItemInfo.city.live_city;//live;

		// ��¥ ����. ( String )

		String banbok = singleAlarmListItemInfo.dayOfTheWeek.getBanbok();
		
		for (int i = 0; i < banbok.length(); i++) {
			char day = banbok.charAt(i);
			switch (day) {
			case '��':
				toggleMon.setChecked(true);
				break;
			case 'ȭ':
				toggleTue.setChecked(true);
				break;
			case '��':
				toggleWed.setChecked(true);
				break;
			case '��':
				toggleThu.setChecked(true);
				break;
			case '��':
				toggleFri.setChecked(true);
				break;
			case '��':
				toggleSat.setChecked(true);
				break;
			case '��':
				toggleSun.setChecked(true);
				break;

			}

		}
		

		toggleVib
				.setChecked(singleAlarmListItemInfo.alarmType.isVibration() == 1);
		toggleSound.setChecked(singleAlarmListItemInfo.alarmType.isWave() == 1);
		
		// ��
				snooze_time = singleAlarmListItemInfo.snoozeTime.time;

				// �� �˶� ��� ��ư.
				btnCancel = (Button) findViewById(R.id.alarmadd_btn_cancel);

				btnCancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});


	}

	private void setAllDay(boolean isChecked) {
		toggleMon.setChecked(isChecked);
		toggleTue.setChecked(isChecked);
		toggleWed.setChecked(isChecked);
		toggleThu.setChecked(isChecked);
		toggleFri.setChecked(isChecked);
		toggleSat.setChecked(isChecked);
		toggleSun.setChecked(isChecked);
	}

	OnClickListener onClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			// ���߿��� �︮��.
			case R.id.toggleMid:
				toggleEnd.setChecked(false);
				toggleEve.setChecked(false);
				if (toggleMid.isChecked()) {
					toggleMon.setChecked(true);
					toggleTue.setChecked(true);
					toggleWed.setChecked(true);
					toggleThu.setChecked(true);
					toggleFri.setChecked(true);
					toggleSat.setChecked(false);
					toggleSun.setChecked(false);
					break;
				} else {
					setAllDay(false);
					break;
				}

				// 1���� ���� �︮��.
			case R.id.toggleEve:
				toggleEnd.setChecked(false);
				toggleMid.setChecked(false);
				if (toggleEve.isChecked()) {
					toggleMon.setChecked(true);
					toggleTue.setChecked(true);
					toggleWed.setChecked(true);
					toggleThu.setChecked(true);
					toggleFri.setChecked(true);
					toggleSat.setChecked(true);
					toggleSun.setChecked(true);
				} else {
					setAllDay(false);
				}
				break;

			// �ָ����� �︮��.
			case R.id.toggleEnd:
				toggleMid.setChecked(false);
				toggleEve.setChecked(false);
				if (toggleEnd.isChecked()) {
					toggleMon.setChecked(false);
					toggleTue.setChecked(false);
					toggleWed.setChecked(false);
					toggleThu.setChecked(false);
					toggleFri.setChecked(false);
					toggleSat.setChecked(true);
					toggleSun.setChecked(true);
				} else {
					setAllDay(false);
				}
				break;
			}
		}
	};

	OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			switch (buttonView.getId()) {
			case R.id.toggleMon:
				singleAlarmListItemInfo.dayOfTheWeek.set��(isChecked);
				break;
			case R.id.toggleTue:
				singleAlarmListItemInfo.dayOfTheWeek.setȭ(isChecked);
				break;
			case R.id.toggleWed:
				singleAlarmListItemInfo.dayOfTheWeek.set��(isChecked);
				break;
			case R.id.toggleThu:
				singleAlarmListItemInfo.dayOfTheWeek.set��(isChecked);
				break;
			case R.id.toggleFri:
				singleAlarmListItemInfo.dayOfTheWeek.set��(isChecked);
				break;
			case R.id.toggleSat:
				singleAlarmListItemInfo.dayOfTheWeek.set��(isChecked);
				break;
			case R.id.toggleSun:
				singleAlarmListItemInfo.dayOfTheWeek.set��(isChecked);
				break;
			case R.id.toggleVib:
				singleAlarmListItemInfo.alarmType.setVibration(isChecked ? 1
						: 0);
				break;
			case R.id.toggleSound:
				singleAlarmListItemInfo.alarmType.setWave(isChecked ? 1 : 0);
				break;
			}
		}
	};

	// SongActivirty�������� ���� intent�� �޴� �κ�.
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) { // ��Ƽ��Ƽ�� ���������� ����Ǿ��� ���
			if (requestCode == REQUST_SONG) {

				// �뷡 ����� ���.
				song_path = data.getStringExtra("PathName");
				song_name = data.getStringExtra("SongName");
				// SongSelect�� ���� ����� �ް�,
//				alarm_btn_song_select.setText(song_name); // �뷡����.
				alarmadd_text_songName.setText(song_name);
			}
		}
	}
	
	OnClickListener listenerSnooze = new OnClickListener() {
		
		@Override
		public void onClick(View v) {

			setSnoozeToggleChecked(v.getId());
			switch (v.getId()) {
			case R.id.toggleButton_00:
				snooze_time = 0;
				break;
			case R.id.toggleButton_03:
				snooze_time = 3;
				break;
			case R.id.toggleButton_05:
				snooze_time = 5;
				break;
			case R.id.toggleButton_10:
				snooze_time = 10;
				break;
			case R.id.toggleButton_15:
				snooze_time = 15;
				break;
			default:
				snooze_time = 5;
				break;
			}
					
		}
	};
	
	private void setSnoozeToggleChecked(int data){
		toggleButton_00.setChecked(data==R.id.toggleButton_00?true:false);
		toggleButton_03.setChecked(data==R.id.toggleButton_03?true:false);
		toggleButton_05.setChecked(data==R.id.toggleButton_05?true:false);
		toggleButton_10.setChecked(data==R.id.toggleButton_10?true:false);
		toggleButton_15.setChecked(data==R.id.toggleButton_15?true:false);
	}

	@Override
	protected void onStart() {
		timePicker.setCurrentHour(singleAlarmListItemInfo.alarmTime.hh);
		timePicker.setCurrentMinute(singleAlarmListItemInfo.alarmTime.mm);
		super.onStart();
	}
	
	
	// �� ���̾�α� ����.
	@Override
	protected Dialog onCreateDialog(int id) {
		
		switch(id){
		case WARNING_DIALOG :
			return new AlertDialog.Builder(ActivityTab1UpdataListItem.this)
			.setTitle("���")
			.setMessage("�뷡�� ���� �� �� �ϳ��� �����ϼ���!!")
			.setPositiveButton("Ȯ��",null)
			.create();
		}
		return null;
	}
	RadioGroup.OnCheckedChangeListener mRadioCheck = new android.widget.RadioGroup.OnCheckedChangeListener(){
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			if(group.getId()==R.id.radioGroup_snooze){
				switch(checkedId){
				case R.id.radio_btn_snooze0 :

					snooze_time = 0;
					
					break;
				case R.id.radio_btn_snooze3 :
				
					snooze_time = 3;
					
					break;
				case R.id.radio_btn_snooze5 :
					
					snooze_time = 5;
					
					break;
				case R.id.radio_btn_snooze10 :
					
					snooze_time = 10;
					
					break;
				case R.id.radio_btn_snooze15 :
					
					snooze_time = 15;
					
					break;
				}
			}
		}
	};	
	
}
