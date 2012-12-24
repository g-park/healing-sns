package com.garamsoft.bubble.activity.tab1;

import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
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
import com.garamsoft.bubble.manager.DefaultLiveManager;
import com.garamsoft.bubble.view.JGTimePicker;

/**
 * 이 액티비티는 알람에 대한 정보를 추가하는 액티비티이다. 알람리스트에서 알람추가를 누르면 나오는 액티비티.
 * */
public class ActivityTab1AddListItem extends Activity {
	
	private static final String HARU_LOG = "HARU_LOG";
	
	private TextView alarmadd_text_songName;
	public static final int WARNING_DIALOG = 11;
	//
	
	private Button btnCancel;
	
	/** 알람 소리(노래)를 설정할때 사용하는 리퀘스트 코드 값이다. */
	public static final int REQUST_SONG = 101;
	/**
	 * 알람을 등록할때, 두가지 요소로 구분한다. 하나는 서비스 리퀘스트 값과 인텐트 값이다. 그중에 리퀘스트 값을 시작을 800으로
	 * 잡은것.
	 */
	private static final int DEFAULT_REQUEST_CODE = 800;

	private Context mContext;
	private SingleAlarmListItemInfo singleAlarmListItemInfo = new SingleAlarmListItemInfo();
	private AlarmDataManager alarmDataManager; // 것.

	private SeekBar seekbar_volumn_control;
	// private TimePicker timePicker;
	private ToggleButton toggleMon, toggleTue, toggleWed, toggleThu, toggleFri,
			toggleSat, toggleSun;
	private ToggleButton toggleMid, toggleEnd, toggleEve;
	private ToggleButton toggleVib, toggleSound, toggleButtonONOFF;
	private Button btnComplete;

	private JGTimePicker jgTimePicker;

	private TextView text_sound_volumn;

	private StringBuilder banbok = new StringBuilder(); // 반복 날짜 선택.

	private String live = "서울"; // 사는 곳 설정.
	private int snooze_time = 5; // 지연 시간 설정.
	private String song_path = ""; // 설정한 노래 경로.
	private int sound_volumn = 75; // 사운드 볼륨.
	private int request_code; // request_code;
	

	// //////////////////////////////////////////////////////////////////////////////////////////////////

	/** 지역설정 버튼. */
	private Spinner alarm_spinner_local_select;


	/** 노래 설정 버튼. */
	private Button alarm_btn_song_select;
	protected String song_name;
	
	private ToggleButton 
	toggleButton_00,
	toggleButton_03,
	toggleButton_05,
	toggleButton_10,
	toggleButton_15;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab1_addlist);
		mContext = getApplicationContext();// 컨텍스를 받아온다.

		DefaultLiveManager liveManager = new DefaultLiveManager(mContext);

		alarmDataManager = AlarmDataManager.getInstance(mContext);
		// timePicker = (TimePicker) findViewById(R.id.timePicker1);
		jgTimePicker = (JGTimePicker) findViewById(R.id.jGTimePicker1);

		seekbar_volumn_control = (SeekBar) findViewById(R.id.seekbar_voulme_control);

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
		toggleEve.setChecked(true);
		
		toggleButton_00 = (ToggleButton)findViewById(R.id.toggleButton_00);
		toggleButton_03 = (ToggleButton)findViewById(R.id.toggleButton_03);
		toggleButton_05 = (ToggleButton)findViewById(R.id.toggleButton_05);
		toggleButton_10 = (ToggleButton)findViewById(R.id.toggleButton_10);
		toggleButton_15 = (ToggleButton)findViewById(R.id.toggleButton_15);
		
		toggleButton_05.setChecked(true);
		
		toggleButton_00.setOnClickListener(listenerSnooze);
		toggleButton_03.setOnClickListener(listenerSnooze);
		toggleButton_05.setOnClickListener(listenerSnooze);
		toggleButton_10.setOnClickListener(listenerSnooze);
		toggleButton_15.setOnClickListener(listenerSnooze);
		
		// ★ 기본 노래 삽입.
		alarmadd_text_songName = (TextView)findViewById(R.id.alarmadd_text_songName);
		
		// 기본 알람 뺄꺼면 그냥 여기 그냥 지워버리면됨.
		alarmadd_text_songName.setText("기본 노래.mp3");
//		song_path = Integer.toString(R.raw.default_alarm);
		
		
		Log.i(HARU_LOG,"저장된 기본 노래 경로 : " + song_path);
		
		
		text_sound_volumn = (TextView) findViewById(R.id.text_soundVolumn);
		toggleVib = (ToggleButton) findViewById(R.id.toggleVib);
		toggleSound = (ToggleButton) findViewById(R.id.toggleSound);
		toggleButtonONOFF = (ToggleButton) findViewById(R.id.toggleButtonONOFF);

		toggleButtonONOFF.setChecked(true);

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
		toggleVib
				.setChecked(singleAlarmListItemInfo.alarmType.isVibration() == 1);
		toggleSound.setChecked(singleAlarmListItemInfo.alarmType.isWave() == 1);

		// 모든 날에 알람이 울리도록 설정.
		setAllDay(true);

		alarm_spinner_local_select = (Spinner) findViewById(R.id.alarm_spinner_local_select);


		alarm_btn_song_select = (Button) findViewById(R.id.alarm_btn_song_select);

		btnComplete = (Button) findViewById(R.id.btnComplete);

		// 사는 곳 설정. ( 일단 임시로 )
		ArrayAdapter<CharSequence> live_adapter = ArrayAdapter
				.createFromResource(this, R.array.first_live_array,
						android.R.layout.simple_spinner_item);

		live_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		alarm_spinner_local_select.setPrompt("지역 설정");
		alarm_spinner_local_select.setAdapter(live_adapter);

		String delive = liveManager.readLive();
		
		if (delive.equals("광주광역시")) {
			alarm_spinner_local_select.setSelection(1);
		} else if (delive.equals("강원영동")) {
			alarm_spinner_local_select.setSelection(2);
		} else if (delive.equals("강원영서")) {
			alarm_spinner_local_select.setSelection(3);
		} else if (delive.equals("경기남부")) {
			alarm_spinner_local_select.setSelection(4);
		} else if (delive.equals("경기북부")) {
			alarm_spinner_local_select.setSelection(5);
		} else if (delive.equals("경상북도")) {
			alarm_spinner_local_select.setSelection(6);
		} else if (delive.equals("경상남도")) {
			alarm_spinner_local_select.setSelection(7);
		} else if (delive.equals("대구광역시")) {
			alarm_spinner_local_select.setSelection(8);
		} else if (delive.equals("대전광역시")) {
			alarm_spinner_local_select.setSelection(9);
		} else if (delive.equals("부산광역시")) {
			alarm_spinner_local_select.setSelection(10);
		} else if (delive.equals("서울특별시")) {
			alarm_spinner_local_select.setSelection(11);
		} else if (delive.equals("충청북도")) {
			alarm_spinner_local_select.setSelection(12);
		} else if (delive.equals("충청남도")) {
			alarm_spinner_local_select.setSelection(13);
		} else if (delive.equals("전라북도")) {
			alarm_spinner_local_select.setSelection(14);
		} else if (delive.equals("전라남도")) {
			alarm_spinner_local_select.setSelection(15);
		} else if (delive.equals("제주도")) {
			alarm_spinner_local_select.setSelection(16);
		} else if (delive.equals("인천광역시")) {
			alarm_spinner_local_select.setSelection(17);
		} else if (delive.equals("울산광역시")) {
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

				// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
					singleAlarmListItemInfo.alarmTime.hh = jgTimePicker
							.getCurrentHour();// timePicker.getCurrentHour();//
												// 현재 피커에 있는 시간과 분을 가져와 저장할 객체에
												// 넣음.

					// 분. ( int )
					singleAlarmListItemInfo.alarmTime.mm = jgTimePicker
							.getCurrentMinute();// timePicker.getCurrentMinute();//
												// 현재 피커에 있는 시간과 분을 가져와 저장할 객체에
												// 넣음.

					// 지연시간 설정. ( int )
					singleAlarmListItemInfo.snoozeTime.time = snooze_time;

					// 진동 설정. ( boolean )
					singleAlarmListItemInfo.alarmType.vibration = toggleVib
							.isChecked() ? 1 : 0;

					// 소리 울릴지 말지 설정. ( boolean)
					singleAlarmListItemInfo.alarmType.Wave = toggleSound
							.isChecked() ? 1 : 0;

					// ★ 노래 제목 설정. 
					singleAlarmListItemInfo.song.name = alarmadd_text_songName
							.getText().toString();

					// 노래 경로 설정. ( string)
					singleAlarmListItemInfo.song.path = song_path;

					// 사운드 크기 설정. ( int )
					singleAlarmListItemInfo.soundvolumn.volumn_size = sound_volumn;

					// request_code 설정 ( int )
					request_code = DEFAULT_REQUEST_CODE
							+ alarmDataManager.getReqeustCodeLast();
					/*1차출시*/
//					Toast.makeText(mContext, "request_code " + request_code, 0).show();
					
					singleAlarmListItemInfo.request_code.requestCode = request_code;

					// 사는 곳 설정. ( String )
					singleAlarmListItemInfo.city.live_city = live;

					// 날짜 설정. ( String )
					singleAlarmListItemInfo.dayOfTheWeek = new DayOfTheWeek(banbok.toString());

					singleAlarmListItemInfo.alaramONOFF.turn(toggleButtonONOFF
							.isChecked() ? 1 : 0);
					
					
					Log.i(HARU_LOG,"기본경로 : " + singleAlarmListItemInfo.song.path);
					
					

					// Toast.makeText(getApplicationContext(),"리쾌스트코드 : " +
					// singleAlarmListItemInfo.request_code.requestCode,0).show();

					alarmDataManager.addListItem(singleAlarmListItemInfo);// DB와
																			// ArrayList에
																			// 둘다
																			// 넣게
																			// 소스
																			// 수정.
					/** 알람 데이터 매니저에서 sqlite를 써서 DB에 저장. */

					setResult(RESULT_OK);
					finish();// 완료 버튼의 동작이 완료되면 알람리스트액티비티로 이동한다.
				}
			}
		});
		
				

				 // ★ 알람 취소 버튼.
			    btnCancel = (Button)findViewById(R.id.alarmadd_btn_cancel);
			    
			    btnCancel.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						finish();
					}
				});
	}

	/** 모든 날짜를 세팅하는 것. */
	private void setAllDay(boolean isChecked) {/* ㄴㄹㄴㅇㅁㄹ */
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
		GregorianCalendar calendar = new GregorianCalendar();
		jgTimePicker.setCurrentHour(calendar.getTime().getHours());
		jgTimePicker.setCurrentMinute(calendar.getTime().getMinutes());
		super.onStart();
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) { // 액티비티가 정상적으로 종료되었을 경우
			// SongActivirty에서받은 값을 intent로 받는 부분.
			if (requestCode == REQUST_SONG) {

				// 노래 재생할 목록.
				song_path = data.getStringExtra("PathName");
				song_name = data.getStringExtra("SongName");

				// ★ SongSelect로 부터 결과값 받고,
				alarmadd_text_songName.setText(song_name); // 노래제목.
			} else {

			}
		}
	}

	// ★ 다이얼로그 생성.
	@Override
	protected Dialog onCreateDialog(int id) {
		
		switch(id){
		case WARNING_DIALOG :
			return new AlertDialog.Builder(ActivityTab1AddListItem.this)
			.setTitle("경고")
			.setMessage("노래나 진동 둘 중 하나를 설정하세요!!")
			.setPositiveButton("확인",null)
			.create();
		}
		
		
		return null;
	}
}
