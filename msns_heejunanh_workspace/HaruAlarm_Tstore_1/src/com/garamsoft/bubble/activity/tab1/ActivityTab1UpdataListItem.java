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

	// ★
	private TextView alarmadd_text_songName;
	public static final int WARNING_DIALOG = 11;

	protected static final int REQUST_SONG = 101;
	/**
	 * 이 액티비티는 알람에 대한 정보를 수정하는 액티비티이다. 알람리스트에서 알람추가를 누르면 나오는 액티비티.
	 * */
	private Context mContext;
	private SingleAlarmListItemInfo singleAlarmListItemInfo;
	private AlarmDataManager alarmDataManager; // 것.
	/**
	 * 컨텍스트. 알람리스트임포는 SQLite로 정보를 넣고 빼고 해야하는 것. 알람리스트임포를 넣었다 빼는 것을 하는 클래스.
	 * */

	private int position;
	private int getReqCode;// 눌려진 아이템의 리퀘스트 값.

	private SeekBar seekbar_volumn_control;
	private JGTimePicker timePicker;
	private ToggleButton toggleMon, toggleTue, toggleWed, toggleThu, toggleFri,
			toggleSat, toggleSun;
	private ToggleButton toggleMid, toggleEnd, toggleEve;
	private ToggleButton toggleVib, toggleSound, toggleButtonONOFF;
	private Button btnComplete;
	
	// ★ 알람 취소 버튼.
		private Button btnCancel;
		private ToggleButton 
		toggleButton_00,
		toggleButton_03,
		toggleButton_05,
		toggleButton_10,
		toggleButton_15;

	private TextView text_sound_volumn;

	private StringBuilder banbok = new StringBuilder(); // 반복 날짜 선택.

	// //////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * SingleAlarmListItemInfo에 집어넣을 변수명들. ( id값 포함 총 11개 ) -> Id 1개, 변수 10개.
	 */

	// 시간은 timePicker.getCurrentHour();
	// 분은 timePicker.getCurrentMinute();
	// private boolean vibrator = false; // 진동 설정.
	// private boolean sound_on_off = false; // 사운드 on/off

	private String live = "서울"; // 사는 곳 설정.
	private int snooze_time = 5; // 지연 시간 설정.
	private String song_path = ""; // 설정한 노래 경로.
	private int sound_volumn = 75; // 사운드 볼륨.
	private int request_code; // request_code;

	// //////////////////////////////////////////////////////////////////////////////////////////////////

	// 지역설정 버튼.
	private Spinner alarm_spinner_local_select;

	// 미루기 시간 설정 화면 ( spinner로 구현 )

	// 노래 설정 버튼.
	private Button alarm_btn_song_select;
	protected String song_name;

	/**
	 * 타임피커 요일 토글 버튼들 진동,소리 토글 버튼.
	 * */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab1_addlist);

		mContext = getApplicationContext();// 컨텍스를 받아온다.

		Intent intent = getIntent();

		position = intent.getIntExtra(
				ActivityTab1HaruAlarmLIst.REQUEST_CODE_POSITION, -1);
		getReqCode = intent.getIntExtra(ActivityTab1HaruAlarmLIst.REQUEST_CODE, 0);

		if (position == -1 || getReqCode == 0) {
			setResult(RESULT_CANCELED);
			finish();// 완료 버튼의 동작이 완료되면 알람리스트액티비티로 이동한다.
		}

		alarmDataManager = AlarmDataManager.getInstance(mContext);// 싱글턴으로 데이터
																	// 매니저의
																	// 인스턴스를
																	// 받아온다.
		singleAlarmListItemInfo = alarmDataManager
				.getSingleAlarmListItemInfoAtReqCode(getReqCode);
		song_path = singleAlarmListItemInfo.song.path;
		sound_volumn = singleAlarmListItemInfo.soundvolumn.volumn_size;
		song_name = singleAlarmListItemInfo.song.name;
		snooze_time = singleAlarmListItemInfo.snoozeTime.time;

		// ///세팅이 필요함.

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
		
		
		
		
		// ★
		alarmadd_text_songName.setText(song_name);

		// 사는 곳 설정. ( 일단 임시로 )
		ArrayAdapter<CharSequence> live_adapter = ArrayAdapter
				.createFromResource(this, R.array.first_live_array,
						android.R.layout.simple_spinner_item);
		live_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		alarm_spinner_local_select.setPrompt("지역 설정");
		alarm_spinner_local_select.setAdapter(live_adapter);

		// ★
		if (singleAlarmListItemInfo.city.live_city.equals("광주광역시")) {
			alarm_spinner_local_select.setSelection(1);
		} else if (singleAlarmListItemInfo.city.live_city.equals("강원영동")) {
			alarm_spinner_local_select.setSelection(2);
		} else if (singleAlarmListItemInfo.city.live_city.equals("강원영서")) {
			alarm_spinner_local_select.setSelection(3);
		} else if (singleAlarmListItemInfo.city.live_city.equals("경기남부")) {
			alarm_spinner_local_select.setSelection(4);
		} else if (singleAlarmListItemInfo.city.live_city.equals("경기북부")) {
			alarm_spinner_local_select.setSelection(5);
		} else if (singleAlarmListItemInfo.city.live_city.equals("경상북도")) {
			alarm_spinner_local_select.setSelection(6);
		} else if (singleAlarmListItemInfo.city.live_city.equals("경상남도")) {
			alarm_spinner_local_select.setSelection(7);
		} else if (singleAlarmListItemInfo.city.live_city.equals("대구광역시")) {
			alarm_spinner_local_select.setSelection(8);
		} else if (singleAlarmListItemInfo.city.live_city.equals("대전광역시")) {
			alarm_spinner_local_select.setSelection(9);
		} else if (singleAlarmListItemInfo.city.live_city.equals("부산광역시")) {
			alarm_spinner_local_select.setSelection(10);
		} else if (singleAlarmListItemInfo.city.live_city.equals("서울특별시")) {
			alarm_spinner_local_select.setSelection(11);
		} else if (singleAlarmListItemInfo.city.live_city.equals("충청북도")) {
			alarm_spinner_local_select.setSelection(12);
		} else if (singleAlarmListItemInfo.city.live_city.equals("충청남도")) {
			alarm_spinner_local_select.setSelection(13);
		} else if (singleAlarmListItemInfo.city.live_city.equals("전라북도")) {
			alarm_spinner_local_select.setSelection(14);
		} else if (singleAlarmListItemInfo.city.live_city.equals("전라남도")) {
			alarm_spinner_local_select.setSelection(15);
		} else if (singleAlarmListItemInfo.city.live_city.equals("제주도")) {
			alarm_spinner_local_select.setSelection(16);
		} else if (singleAlarmListItemInfo.city.live_city.equals("인천광역시")) {
			alarm_spinner_local_select.setSelection(17);
		} else if (singleAlarmListItemInfo.city.live_city.equals("울산광역시")) {
			alarm_spinner_local_select.setSelection(18);
		}

		// 스피너 리스너.
		alarm_spinner_local_select
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						if (position == 0) {

						} else {
							/*1차출시*/
//							Toast.makeText(
//									parent.getContext(),
//									(String) parent.getItemAtPosition(position)
//											+ "을 선택하였습니다.", 1).show();
							// 이거 인텐트로 넘겨줘야함.
							live = (String) parent.getItemAtPosition(position);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

		// 노래 선택 버튼.
		alarm_btn_song_select.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						SongSelect.class);
				startActivityForResult(intent, REQUST_SONG);
			}
		});

		// 디폴트 사운드 크기.
		seekbar_volumn_control.setProgress(sound_volumn);

		toggleButtonONOFF
				.setChecked(singleAlarmListItemInfo.alaramONOFF.onoff == 1 ? true
						: false);

		// 볼륨 조절 컨트롤러.
		seekbar_volumn_control
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					// 바뀔때.
					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub

						sound_volumn = progress;

						text_sound_volumn.setText(Integer
								.toString(sound_volumn));

					}

					// 멈출때.
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}

					// 시작할때.
					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}
				});

		// ★ 알람 추가 버튼.
		btnComplete.setOnClickListener(new OnClickListener() {
			/** 완료버튼 */
			@Override
			public void onClick(View v) {
				// 알람 요일 설정.
				if ((!toggleSound.isChecked()) && (!toggleVib.isChecked())) {
					// ★ 다이얼로그 띄우기.

					showDialog(WARNING_DIALOG);

				} else {

					if (toggleMon.isChecked())
						banbok.append("월");
					if (toggleTue.isChecked())
						banbok.append("화");
					if (toggleWed.isChecked())
						banbok.append("수");
					if (toggleThu.isChecked())
						banbok.append("목");
					if (toggleFri.isChecked())
						banbok.append("금");
					if (toggleSat.isChecked())
						banbok.append("토");
					if (toggleSun.isChecked())
						banbok.append("일");

					// Toast.makeText(getApplicationContext(),"설정된 요일은" +
					// banbok.toString() + "입니다.", 0).show();

					// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

					// 시. ( int )
					singleAlarmListItemInfo.alarmTime.hh = timePicker
							.getCurrentHour();// 현재 피커에 있는 시간과 분을 가져와 저장할 객체에
												// 넣음.

					// 분. ( int )
					singleAlarmListItemInfo.alarmTime.mm = timePicker
							.getCurrentMinute();// 현재 피커에 있는 시간과 분을 가져와 저장할 객체에
												// 넣음.

					// 지연시간 설정. ( int )
					singleAlarmListItemInfo.snoozeTime.time = snooze_time;

					// 진동 설정. ( boolean )
					singleAlarmListItemInfo.alarmType.vibration = toggleVib
							.isChecked() ? 1 : 0;

					// 소리 울릴지 말지 설정. ( boolean)
					singleAlarmListItemInfo.alarmType.Wave = toggleSound
							.isChecked() ? 1 : 0;

					// ★ 노래 제목 설정. ( 이건 디비에 안들어가도 된다. )
					singleAlarmListItemInfo.song.name = alarmadd_text_songName
							.getText().toString();

					// 노래 경로 설정. ( string)
					singleAlarmListItemInfo.song.path = song_path;

					// 사운드 크기 설정. ( int )
					singleAlarmListItemInfo.soundvolumn.volumn_size = sound_volumn;

					// request_code 설정 ( int )
					request_code = getReqCode;
					/*1차출시*/
//					Toast.makeText(mContext, "request_code " + request_code, 0).show();
					singleAlarmListItemInfo.request_code.requestCode = getReqCode;

					// 사는 곳 설정. ( String )
					singleAlarmListItemInfo.city.live_city = live;

					// 날짜 설정. ( String )
					singleAlarmListItemInfo.dayOfTheWeek = new DayOfTheWeek(banbok.toString());
							

					singleAlarmListItemInfo.alaramONOFF.turn(toggleButtonONOFF
							.isChecked() ? 1 : 0);

					alarmDataManager.updateItem(position,
							singleAlarmListItemInfo);

					setResult(RESULT_OK);
					finish();// 완료 버튼의 동작이 완료되면 알람리스트액티비티로 이동한다.
				}
			}
		});


		// 사운드 크기 설정. ( int )
		seekbar_volumn_control
				.setKeyProgressIncrement(singleAlarmListItemInfo.soundvolumn.volumn_size);
		text_sound_volumn.setText(String
				.valueOf(singleAlarmListItemInfo.soundvolumn.volumn_size));

		singleAlarmListItemInfo.request_code.requestCode = getReqCode;

		// 사는 곳 설정. ( String )
		// singleAlarmListItemInfo.city.live_city;//live;

		// 날짜 설정. ( String )

		String banbok = singleAlarmListItemInfo.dayOfTheWeek.getBanbok();
		
		for (int i = 0; i < banbok.length(); i++) {
			char day = banbok.charAt(i);
			switch (day) {
			case '월':
				toggleMon.setChecked(true);
				break;
			case '화':
				toggleTue.setChecked(true);
				break;
			case '수':
				toggleWed.setChecked(true);
				break;
			case '목':
				toggleThu.setChecked(true);
				break;
			case '금':
				toggleFri.setChecked(true);
				break;
			case '토':
				toggleSat.setChecked(true);
				break;
			case '일':
				toggleSun.setChecked(true);
				break;

			}

		}
		

		toggleVib
				.setChecked(singleAlarmListItemInfo.alarmType.isVibration() == 1);
		toggleSound.setChecked(singleAlarmListItemInfo.alarmType.isWave() == 1);
		
		// ★
				snooze_time = singleAlarmListItemInfo.snoozeTime.time;

				// ★ 알람 취소 버튼.
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

			// 주중에만 울리기.
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

				// 1주일 내내 울리기.
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

			// 주말에만 울리기.
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
				singleAlarmListItemInfo.dayOfTheWeek.set월(isChecked);
				break;
			case R.id.toggleTue:
				singleAlarmListItemInfo.dayOfTheWeek.set화(isChecked);
				break;
			case R.id.toggleWed:
				singleAlarmListItemInfo.dayOfTheWeek.set수(isChecked);
				break;
			case R.id.toggleThu:
				singleAlarmListItemInfo.dayOfTheWeek.set목(isChecked);
				break;
			case R.id.toggleFri:
				singleAlarmListItemInfo.dayOfTheWeek.set금(isChecked);
				break;
			case R.id.toggleSat:
				singleAlarmListItemInfo.dayOfTheWeek.set토(isChecked);
				break;
			case R.id.toggleSun:
				singleAlarmListItemInfo.dayOfTheWeek.set일(isChecked);
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

	// SongActivirty에서받은 값을 intent로 받는 부분.
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) { // 액티비티가 정상적으로 종료되었을 경우
			if (requestCode == REQUST_SONG) {

				// 노래 재생할 목록.
				song_path = data.getStringExtra("PathName");
				song_name = data.getStringExtra("SongName");
				// SongSelect로 부터 결과값 받고,
//				alarm_btn_song_select.setText(song_name); // 노래제목.
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
	
	
	// ★ 다이얼로그 생성.
	@Override
	protected Dialog onCreateDialog(int id) {
		
		switch(id){
		case WARNING_DIALOG :
			return new AlertDialog.Builder(ActivityTab1UpdataListItem.this)
			.setTitle("경고")
			.setMessage("노래나 진동 둘 중 하나를 설정하세요!!")
			.setPositiveButton("확인",null)
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
