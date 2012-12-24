package com.garamsoft.bubble.activity.tab2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.garamsoft.bubble.R;
import com.garamsoft.bubble.activity.adapter.Adapter_tab2_calendar_single_item;
import com.garamsoft.bubble.activity.tab3.ActivityTab3_LiveSelect;
import com.garamsoft.bubble.manager.DefaultLiveManager;
import com.garamsoft.bubble.manager.WeatherDataManager;
import com.garamsoft.bubble.view.ViewWeatherInfoLinear;
import com.garamsoft.bubble.weather.data.day.Wid;
import com.garamsoft.bubble.weather.data.day.local_forecast;
import com.garamsoft.bubble.weather.network.NetworkRequest;
import com.garamsoft.bubble.weather.network.NetworkRequest.OnDownloadCompletedListener;
import com.garamsoft.bubble.weather.network.downloadthread.DownloadThread;
import com.garamsoft.bubble.weather.network.week.day.DayWetherRequest;

/** �˶��� �︮�� �����ִ� ��Ƽ��Ƽ, Ȥ�� ������ ������ ��� ���� ���� ��Ƽ��Ƽ�̱⵵ �ϴ� */
public class ActivityTab2WeatherEventList extends ListActivity {

	// �� Ķ���� �� ������.
	public static final int REQUEST_LIVE = 100;

	public static final String HARU_LOG = "HARU_LOG";

	ListView listView;

	Adapter_tab2_calendar_single_item mArrayAdapter;

	List<String> mItems;// = new ArrayList<String>();

	// � Calendar�� ������ �޾ƿ� ������.
	private Uri calendarUri;

	// �� Calendar�� ����ִ� �������� �̺�Ʈ. (����)
	private Uri eventsUri;

	private static int sApiLevel = 0;

	// ������ �⵵, ��, ���� �޾ƿ��� ����.
	private int todayYear;
	private int todayMonth;
	private int todayDay;

	// List�� ������ ���۽ð�, ���ð�, ������ �˸��� ����.
	private String startTime;
	private String endTime;
	private String title;
	private int allDay;

	// startTIme, endTime, title�� ���ļ� List�� �Ѹ� ����.
	private String show_list;

	// ����Ʈ�� ������ ������ �˸� ����.
//	private int count = 0;

	private ListView liveList; // ��� �� �޾ƿ���.

//	private String str_live;

	/**
	 * Return {@link VERSION#SDK} as an int. Value is cached locally.
	 */
	public static int getApiLevel() {
		if (sApiLevel == 0) {
			try {
				// SDK_INT only exists since API 4 so let's use the string
				// version.
				sApiLevel = Integer.parseInt(Build.VERSION.SDK);
			} catch (Exception e) {
				// This app doesn't run below 3 anyway
				sApiLevel = 3;
			}
		}

		return sApiLevel;
	}

	// ///////////////////////////////////////////////////////////////

	/** �Ŵ��� ���� �ʱ�ȭ �Ҷ� ���� ���ؽ�Ʈ ���. */
	Context mContext;
	/** ���� ������ �ִ� �Ŵ����� */
	private WeatherDataManager weatherDataManager;
	private ViewWeatherInfoLinear viewWeatherInfoLinear;
	private DefaultLiveManager defaultLiveManager;

	/** ��Ƽ��Ƽ���� ����� ��Ʈ�� �� �� �ֵ��� �����ִ� ��. */
	private Button btnPreWeather, btnNextWeather, btnRefreshWeather;

	private ProgressBar progressBar;

	private TextView textViewLocationName,textViewCalendar;

	/** ����� ��Ʈ�� �������� ���� �ð��� ������ �����ִ� ���ε� ���ʿ䰡 �ִµ� �𸣰����� ��������Ʈ�� ���ǹٶ�. */
	private int weatherDataPosition;
	private int weatherDateMax;

	// ������� �޾ƿ��� ArrayList.
	private NetworkRequest networkRequest;

	/** �ڵ鷯���� �޽����� ���� �� ����ϴ� �� */
	private static final int HANDLER_CODE_WATHER = 747;
	private static final int HANDLER_CODE_LIVE_TEXT_CHANGE = 848;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HANDLER_CODE_WATHER:
				if (weatherDataManager.popDatas() != null) {
					weatherDataPosition = 0;
					if (weatherDataManager.popDatas() != null)
						viewWeatherInfoLinear.setData(weatherDataManager
								.popDatas().get(msg.arg1), weatherDataPosition);
					textViewLocationName.setText(defaultLiveManager.readLive());
					setWeatherVisible(true);
				}
				break;
			case HANDLER_CODE_LIVE_TEXT_CHANGE:

				break;
			}
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab2_weather_event_layout);

		mContext = getApplicationContext();
		weatherDataPosition = 0;

		defaultLiveManager = new DefaultLiveManager(mContext);

		viewWeatherInfoLinear = (ViewWeatherInfoLinear) findViewById(R.id.viewWeatherInfo);

		btnPreWeather = (Button) findViewById(R.id.btnPreWeather);
		btnNextWeather = (Button) findViewById(R.id.btnNextWeather);
		btnRefreshWeather = (Button) findViewById(R.id.btnRefreshWeather);

		textViewLocationName = (TextView) findViewById(R.id.textViewLocationName);
		textViewCalendar = (TextView)findViewById(R.id.textViewCalendar);
		textViewCalendar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 ComponentName componentName = new ComponentName("com.android.calendar","com.android.calendar.LaunchActivity");
						 
				 if (componentName != null) {
						 Intent intent = new
						 Intent(android.content.Intent.ACTION_VIEW);
						 intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
						 intent.setComponent(componentName);
						 
						 try{
							 startActivity(intent);
						 }
						 catch (Exception e) {
							 Toast.makeText(mContext, "Ķ���� ���� ���ų� �˼� ���� ������ �߻��߽��ϴ�.", 0).show();
						}	
				 }
			}
		});

		progressBar = (ProgressBar) findViewById(R.id.progressBar);

		btnPreWeather.setOnClickListener(listener);
		btnNextWeather.setOnClickListener(listener);
		btnRefreshWeather.setOnClickListener(listener);

		weatherDataManager = WeatherDataManager.getInstance();
		listView = (ListView) findViewById(android.R.id.list);

		// �� Ķ���� �� ��.
		textViewLocationName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(0);
			}
		});

		// ����Ʈ�� ����Ʈ ���� �� ���ֱ�.
		listView.setDivider(new ColorDrawable(0x00000000));

		Calendar cal = Calendar.getInstance();

		// ����, ��, ��¥ �� �޾ƿ���.
		todayYear = cal.get(Calendar.YEAR);
		todayMonth = cal.get(Calendar.MONTH) + 1; // ��
		todayDay = cal.get(Calendar.DAY_OF_MONTH);

		if (getApiLevel() <= 8) {
			eventsUri = Uri.parse("content://calendar/events");
			calendarUri = Uri.parse("content://calendar/calendars");
		} else {
			eventsUri = Uri.parse("content://com.android.calendar/events");
			calendarUri = Uri.parse("content://com.android.calendar/calendars");
		}

//		queryCalendars();
		
		// ����Ʈ�� ���� �Ұ����ϰ�.
//		listView.setClickable(false);
//		listView.setItemsCanFocus(false);

		////////////////////
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				 Intent intent = new Intent("com.android.calendar");
//				 PendingIntent pendingIntent =
//				 PendingIntent.getActivity(mContext, 0, intent,
//				 PendingIntent.FLAG_ONE_SHOT);
//				 try {
//				 pendingIntent.send();
//				 } catch (CanceledException e) {
//				 // TODO Auto-generated catch block
//				 e.printStackTrace();
//				 }

				 ComponentName componentName = new
				 ComponentName("com.android.calendar",
				 "com.android.calendar.LaunchActivity");
				 if (componentName != null) {
				 Intent intent = new
				 Intent(android.content.Intent.ACTION_VIEW);
				 intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
				 intent.setComponent(componentName);
				 
				 try{
					 startActivity(intent);
				 }
				 catch (Exception e) {
					 Toast.makeText(mContext, "Ķ���� ���� ���ų� �˼� ���� ������ �߻��߽��ϴ�.", 0).show();
				}
				 }
			}
		});
	}

	// �� Ķ���� �� ��.
	private void queryCalendars() {
		// Run query
		Cursor cur = null;
		ContentResolver cr = getContentResolver();
		String[] projection = new String[] { "_id", "name" };

		// Submit the query and get a Cursor object back.
		cur = cr.query(calendarUri, projection, null, null, null);
		if (cur == null)
			return;

		// Use the cursor to step through the returned records
		while (cur.moveToNext()) {
			long calID = cur.getLong(0);

			queryEvents(calID);
		}
	}

	// ���� �޾ƿ���.
	private void queryEvents(long calID) {
		
//		mItems = new ArrayList<String>();
		// Run query
		Cursor cur = null;
		ContentResolver cr = getContentResolver();

		String query = "calendar_id=" + calID;

		if (calID < 0) {
			// all events
			query = null;
		}

		String[] projection = new String[] { "_id", "title", "dtstart",
				"dtend", "allDay" };

		cur = cr.query(eventsUri, projection, query, null, null);
		if (cur == null)
			return;

		while (cur.moveToNext()) {
			long eventID = cur.getLong(0);

			title = cur.getString(1);

			long dtstart = cur.getLong(2);

			// ���� ���� ���Ŀ� �°� ����.
			SimpleDateFormat start = new SimpleDateFormat("yyyyMMddHHmm");
			TimeZone st = TimeZone.getTimeZone("Asia/Seoul");
			start.setTimeZone(st);
			startTime = start.format(dtstart);

			long dtend = cur.getLong(3);

			// ������ ���Ŀ� �°� ����.
			SimpleDateFormat end = new SimpleDateFormat("yyyyMMddHHmm");
			TimeZone ed = TimeZone.getTimeZone("Asia/Seoul");
			end.setTimeZone(ed);
			endTime = end.format(dtend);

			allDay = cur.getInt(4);

			String calendar_start_Year = startTime.substring(0, 4);
			String calendar_start_Month = startTime.substring(4, 6);
			String calendar_start_Day = startTime.substring(6, 8);

			String calendar_end_Year = endTime.substring(0, 4);
			String calendar_end_Month = endTime.substring(4, 6);
			String calendar_end_Day = endTime.substring(6, 8);

			// ���� �⵵, ��, ��
			int calendar_start_int_Year = Integer.parseInt(calendar_start_Year);
			int calendar_start_int_Month = Integer
					.parseInt(calendar_start_Month);
			int calendar_start_int_Day = Integer.parseInt(calendar_start_Day);

			// �� �⵵, ��, ��
			int calendar_end_int_Year = Integer.parseInt(calendar_end_Year);
			int calendar_end_int_Month = Integer.parseInt(calendar_end_Month);
			int calendar_end_int_Day = Integer.parseInt(calendar_end_Day);

			// �⵵�� ��ġ�Ҷ�.
			if ((calendar_start_int_Year == todayYear)
					&& (todayYear == calendar_end_int_Year)) {
				// �˶����� �� < ���� �� < �˶��� ��
				if ((todayMonth > calendar_start_int_Month)
						&& (calendar_end_int_Month > todayMonth)) {

					showList();

					mItems.add(show_list);

					// �˶� ���� �� < ���� �� = �˶� �� ��
				} else if ((todayMonth > calendar_start_int_Month)
						&& (calendar_end_int_Month == todayMonth)) {
					// ���� ��, �˶� ���� �� ��
					if (todayDay <= calendar_end_int_Day) {

						showList();

						mItems.add(show_list);
					}

					// �˶� ���� �� = ���� �� < �˶� �� ��
				} else if ((todayMonth == calendar_start_int_Month)
						&& (calendar_end_int_Month > todayMonth)) {

					// �˶� �������� �����ϰ� �۰ų� ���ƾ�.
					if (calendar_start_int_Day <= todayDay) {

						showList();

						mItems.add(show_list);
					}

					// �˶� ���� �� = ���� �� = �˶� �� �� ��
				} else if ((todayMonth == calendar_start_int_Month)
						&& (calendar_end_int_Month == todayMonth)) {
					if ((todayDay == calendar_start_int_Day)
							&& (calendar_end_int_Day >= todayDay)) {

						showList();

						mItems.add(show_list);
					}
				}
			}

			// �⵵ ����ġ.

			// ���۳⵵ < ����⵵ < ���⵵ ( �����ҹ� �߰� )
			else if ((calendar_start_int_Year < todayYear)
					&& (todayYear < calendar_end_int_Year)) {

				showList();

				mItems.add(show_list);
			}

			// 2010 = 2010 < 2011
			else if ((calendar_start_int_Year == todayYear)
					&& (calendar_end_int_Year > todayYear)) {
				// �˶� ���� ���� ����ð������� ũ�ٸ�.
				if (todayMonth > calendar_start_int_Month) {

					showList();

					mItems.add(show_list);

					// �˶� ���� ���� ���� ���� ���ٸ�.
				} else if (todayMonth == calendar_start_int_Month) {
					// ���� ���� �˶������Ϻ��� ũ�ٸ�.
					if (todayDay >= calendar_start_int_Day) {

						showList();

						mItems.add(show_list);
					}
				}

				// 2010 < 2011 = 2011
			} else if ((todayYear > calendar_start_int_Year)
					&& (todayYear == calendar_end_int_Year)) {
				// ���� ���� �˶� �� ���� ���ٸ�
				if (todayMonth == calendar_end_int_Month) {
					// �ϱ��� ��.
					if (todayDay <= calendar_end_int_Day) {

						showList();

						mItems.add(show_list);
					}
					// ���� ���� �˶� �������� �۴ٸ�
				} else if (todayMonth < calendar_end_int_Month) {

					showList();

					mItems.add(show_list);
				}
			}
		}

//		mArrayAdapter = new ArrayAdapter<String>(this,
//				android.R.layout.simple_list_item_1, mItems);
		mArrayAdapter = new Adapter_tab2_calendar_single_item(mItems, mContext);

		listView.setAdapter(mArrayAdapter);
	}

	private void showList() {
//		count++;

		// show_list = count + ". �ð� : " + startTime.substring(8, 10) + "��"
		// + startTime.substring(10, 12) + "��" + " ~ "
		// + endTime.substring(8, 10) + "��"
		// + endTime.substring(10, 12) + "��" + ", ���� : "
		// + title;
		show_list = /*count + ". " +*/ title + ".";
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////

	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnPreWeather:
				setPreWeatherBtn();
				break;
			case R.id.btnNextWeather:
				setNextWeathBtn();
				break;
			case R.id.btnRefreshWeather:
				setWeatherInit();
				break;
			}
		}
	};

	@Override
	protected void onResume() {
		setCalendarEventListToView();
		getWeaterData(0);
		mItems = new ArrayList<String>();
		queryCalendars();
		mArrayAdapter.notifyDataSetChanged();
		super.onResume();
	}

	
	/** ���������͸� �����´�. position�� ���ϴ� ��������. */
	private void getWeaterData(int position) {
		if (weatherDataManager.popDatas() != null) {
			weatherDateMax = weatherDataManager.popDatas().size();
			viewWeatherInfoLinear.setData(
					weatherDataManager.popWid().datas.get(position),
					weatherDataPosition);
			textViewLocationName.setText(defaultLiveManager.readLive());
			setWeatherVisible(true);
		} else {
			setWeatherVisible(false);
			networkRequest = new DayWetherRequest(
					local_forecast.getLocationURL(defaultLiveManager.readLive()));
			DownloadThread th = new DownloadThread(mHandler, networkRequest);
			networkRequest
					.setOnDownloadCompletedListener(new OnDownloadCompletedListener() {
						@Override
						public void onDownloadCompleted(int result,
								NetworkRequest request) {
							weatherDataManager.inputData(((Wid) request
									.getResult()));
							Message msg = mHandler.obtainMessage();
							msg.what = HANDLER_CODE_WATHER;
							msg.arg1 = 0;
							if (weatherDataManager.popDatas() != null)
								mHandler.sendMessage(msg);
						}
					});
			th.start();
		}
	}

	
	@Override
	protected Dialog onCreateDialog(final int id) {
		/** showDialog(id)�� ȣ��Ǹ� ����Ǵ� �Լ�. */

		LayoutInflater mInflater; // ���÷�����.

		switch (id) {

		case 0:
			mInflater = LayoutInflater.from(this);
			final View view_live = mInflater.inflate(
					R.layout.activity_tab3_live_list, null);
			liveList = (ListView) view_live.findViewById(R.id.common_list);

			ArrayAdapter<CharSequence> adapter_live = ArrayAdapter
					.createFromResource(getApplicationContext(),
							R.array.first_live_array,
							android.R.layout.simple_list_item_1);

			liveList.setAdapter(adapter_live);

			// �� �̰��� ����.
			liveList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long list_id) {

					Intent intent = null;

					if (position == 2 || position == 5 || position == 6
							|| position == 7 || position == 8 || position == 9
							|| position == 10 || position == 13) {

//						str_live = liveList.getItemAtPosition(position).toString();
						defaultLiveManager.writeLive(liveList.getItemAtPosition(position).toString());
							weatherDataManager.inputData(null);
							getWeaterData(0);

						/* 1����� */
						// Toast.makeText(ActivityTab3_Setting.this,
						// str_live,Toast.LENGTH_SHORT).show();
//						textViewLocationName.setText(str_live);

					} else if (position == 0) {
						intent = new Intent(getApplicationContext(),
								ActivityTab3_LiveSelect.class);
						intent.putExtra("live_select", 0);
						intent.putExtra("position", 0);

						startActivityForResult(intent, REQUEST_LIVE);
					} else if (position == 1) {

						intent = new Intent(getApplicationContext(),
								ActivityTab3_LiveSelect.class);
						intent.putExtra("live_select", 1);

						startActivityForResult(intent, REQUEST_LIVE);
					} else if (position == 3) {
						intent = new Intent(getApplicationContext(),
								ActivityTab3_LiveSelect.class);
						intent.putExtra("live_select", 3);

						startActivityForResult(intent, REQUEST_LIVE);
					} else if (position == 4) {
						intent = new Intent(getApplicationContext(),
								ActivityTab3_LiveSelect.class);
						intent.putExtra("live_select", 4);

						startActivityForResult(intent, REQUEST_LIVE);
					} else if (position == 11) {
						intent = new Intent(getApplicationContext(),
								ActivityTab3_LiveSelect.class);
						intent.putExtra("live_select", 11);

						startActivityForResult(intent, REQUEST_LIVE);
					} else if (position == 12) {
						intent = new Intent(getApplicationContext(),
								ActivityTab3_LiveSelect.class);
						intent.putExtra("live_select", 12);

						startActivityForResult(intent, REQUEST_LIVE);
					} else if (position == 14) {
						intent = new Intent(getApplicationContext(),
								ActivityTab3_LiveSelect.class);
						intent.putExtra("live_select", 14);

						startActivityForResult(intent, REQUEST_LIVE);
					} else if (position == 15) {
						intent = new Intent(getApplicationContext(),
								ActivityTab3_LiveSelect.class);
						intent.putExtra("live_select", 15);

						startActivityForResult(intent, REQUEST_LIVE);
					}
					dismissDialog(id);
				}
			});

			return new AlertDialog.Builder(ActivityTab2WeatherEventList.this)
					.setTitle("��� �� ����").setView(view_live).create();
			// ���� ��.
		}

		return null;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////

	private void setWeatherInit() {

		weatherDataManager.inputData(null);
		getWeaterData(0);
	}

	/** ������ ������ �����´�. */
	private void setCalendarEventListToView() {
	}

	private void setPreWeatherBtn() {
		if (weatherDataPosition != 0)
			weatherDataPosition -= 1;
		getWeaterData(weatherDataPosition);

	}

	private void setNextWeathBtn() {
		if (weatherDataPosition < weatherDateMax - 1)
			weatherDataPosition += 1;
		getWeaterData(weatherDataPosition);
	}

	private void setWeatherVisible(boolean b) {
		viewWeatherInfoLinear.setVisibility(b ? View.VISIBLE : View.INVISIBLE);
		progressBar.setVisibility(!b ? View.VISIBLE : View.INVISIBLE);
	}

	public boolean onKeyDown(int KeyCode, KeyEvent event) {
		switch (KeyCode) {
		case KeyEvent.KEYCODE_BACK:
			String alertTitle = "�Ϸ�˶�";
			String buttonMessage = "�Ϸ�˶��� �����Ͻðڽ��ϱ�?";
			String buttonYes = "��";
			String buttonNo = "�ƴϿ�";

			new AlertDialog.Builder(ActivityTab2WeatherEventList.this)
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
		return true;
	}

	///////////////////////////////////////////////////

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_LIVE) {

//				str_live = data.getStringExtra("LiveName");

				// �߳Ѿ��.
//				Log.i(HARU_LOG, "activityResult : " + str_live);

				// �� ������ �ȵ�. ( �Ѥ� ) -> �翬�� �ȵ��� ��
//				textViewLocationName.setText(str_live);
				defaultLiveManager.writeLive(data.getStringExtra("LiveName"));

			} else {

			}
		}
	}

	//////////////////////////////////////////////////
}

//package com.garamsoft.bubble.activity.tab2;
//
//import android.app.AlertDialog;
//import android.app.ListActivity;
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.garamsoft.bubble.R;
//import com.garamsoft.bubble.manager.DefaultLiveManager;
//import com.garamsoft.bubble.manager.WeatherDataManager;
//import com.garamsoft.bubble.view.ViewWeatherInfoLinear;
//import com.garamsoft.bubble.weather.data.day.Wid;
//import com.garamsoft.bubble.weather.data.day.local_forecast;
//import com.garamsoft.bubble.weather.network.NetworkRequest;
//import com.garamsoft.bubble.weather.network.NetworkRequest.OnDownloadCompletedListener;
//import com.garamsoft.bubble.weather.network.downloadthread.DownloadThread;
//import com.garamsoft.bubble.weather.network.week.day.DayWetherRequest;
//
///** �˶��� �︮�� �����ִ� ��Ƽ��Ƽ, Ȥ�� ������ ������ ��� ���� ���� ��Ƽ��Ƽ�̱⵵ �ϴ� */
//public class ActivityTab2WeatherEventList extends ListActivity {
//
//	/** �Ŵ��� ���� �ʱ�ȭ �Ҷ� ���� ���ؽ�Ʈ ���. */
//	Context mContext;
//	/**���� ������ �ִ� �Ŵ�����*/
//	private WeatherDataManager weatherDataManager;
//	private ViewWeatherInfoLinear viewWeatherInfoLinear;
//	private DefaultLiveManager defaultLiveManager;
//	ListView listView;
//
//	/**��Ƽ��Ƽ���� ����� ��Ʈ�� �� �� �ֵ��� �����ִ� ��.*/
//	private Button 
//	btnPreWeather,
//	btnNextWeather, 
//	btnRefreshWeather;
//	
//	private ProgressBar progressBar;
//	
//	private TextView textViewLocationName;
//
//	/** ����� ��Ʈ�� �������� ���� �ð��� ������ �����ִ� ���ε� ���ʿ䰡 �ִµ� �𸣰����� ��������Ʈ�� ���ǹٶ�. */
//	private int weatherDataPosition;
//	private int weatherDateMax;
//	
//	// ������� �޾ƿ��� ArrayList.
//	private NetworkRequest networkRequest;
//	
//	/**�ڵ鷯���� �޽����� ���� �� ����ϴ� ��*/
//	private static final int HANDLER_CODE_WATHER = 747;
//	private static final int HANDLER_CODE_LIVE_TEXT_CHANGE = 848;
//
//	Handler mHandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case HANDLER_CODE_WATHER:
//				if (weatherDataManager.popDatas() != null){
//					weatherDataPosition = 0;
//					if(weatherDataManager.popDatas()!=null)
//					viewWeatherInfoLinear.setData(weatherDataManager.popDatas().get(msg.arg1),weatherDataPosition);
//					textViewLocationName.setText(defaultLiveManager.readLive()+"�� ����");
//					setWeatherVisible(true);
//					
//				}
//				break;
//			case HANDLER_CODE_LIVE_TEXT_CHANGE:
//				
//				break;
//			}
//			super.handleMessage(msg);
//		}
//	};
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_tab2_weather_event_layout);
//
//		mContext = getApplicationContext();
//		weatherDataPosition = 0;
//
//		defaultLiveManager = new DefaultLiveManager(mContext);
//
//		viewWeatherInfoLinear = (ViewWeatherInfoLinear) findViewById(R.id.viewWeatherInfo);
//
//		btnPreWeather = (Button) findViewById(R.id.btnPreWeather);
//		btnNextWeather = (Button) findViewById(R.id.btnNextWeather);
//		btnRefreshWeather = (Button) findViewById(R.id.btnRefreshWeather);
//		
//		textViewLocationName = (TextView)findViewById(R.id.textViewLocationName);
//		
//		progressBar = (ProgressBar)findViewById(R.id.progressBar);
//
//		btnPreWeather.setOnClickListener(listener);
//		btnNextWeather.setOnClickListener(listener);
//		btnRefreshWeather.setOnClickListener(listener);
//
//		weatherDataManager = WeatherDataManager.getInstance();
//		listView = (ListView)findViewById(android.R.id.list);
//		listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
//			
////				Intent intent = new Intent("com.android.calendar");
////				PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);
////				try {
////					pendingIntent.send();
////				} catch (CanceledException e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
//				
//				ComponentName componentName = new ComponentName("com.android.calendar",
//				        "com.android.calendar.LaunchActivity");
//				if (componentName != null) {
//				    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
//				    intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
//				    intent.setComponent(componentName);
//				    startActivity(intent);
//				} 
//				
//			}
//		});
//	}
//
//	OnClickListener listener = new OnClickListener() {
//
//		@Override
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.id.btnPreWeather:
//				setPreWeatherBtn();
//				break;
//			case R.id.btnNextWeather:
//				setNextWeathBtn();
//				break;
//			case R.id.btnRefreshWeather:
//				setWeatherInit();
//				break;
//			}
//
//		}
//	};
//	
//
//	@Override
//	protected void onResume() {
//		setCalendarEventListToView();
//		getWeaterData(0);
//		super.onResume();
//	}
//
//	/** ���������͸� �����´�. position�� ���ϴ� ��������.*/
//	private void getWeaterData(int position) {
//		
//		if (weatherDataManager.popDatas()  != null) {
//			weatherDateMax = weatherDataManager.popDatas().size();
//			viewWeatherInfoLinear.setData(weatherDataManager.popWid().datas.get(position),weatherDataPosition);
//			textViewLocationName.setText(defaultLiveManager.readLive());
//			setWeatherVisible(true);
//		} else {
//			setWeatherVisible(false);
//			networkRequest = new DayWetherRequest(local_forecast.getLocationURL(defaultLiveManager.readLive()));
//			DownloadThread th = new DownloadThread(mHandler, networkRequest);
//			networkRequest.setOnDownloadCompletedListener(new OnDownloadCompletedListener() {
//						@Override
//						public void onDownloadCompleted(int result,NetworkRequest request) {
//							weatherDataManager.inputData(((Wid) request.getResult()));
//							Message msg = mHandler.obtainMessage();
//							msg.what = HANDLER_CODE_WATHER;
//							msg.arg1 = 0;
//							if(weatherDataManager.popDatas()!=null)
//							mHandler.sendMessage(msg);
//						}
//					});
//			th.start();
//		}
//	}
//
//	private void setWeatherInit() {
//		
//		weatherDataManager.inputData(null);
//		getWeaterData(0);
//	}
//
//	/** ������ ������ �����´�. */
//	private void setCalendarEventListToView() {}
//	
//	private void setPreWeatherBtn(){
//		if(weatherDataPosition!=0)
//		weatherDataPosition-=1;
//		getWeaterData(weatherDataPosition);
//		
//	}
//	
//	private void setNextWeathBtn(){
//		if(weatherDataPosition<weatherDateMax-1)
//		weatherDataPosition+=1;
//		getWeaterData(weatherDataPosition);
//	}
//	
//	private void setWeatherVisible(boolean b){
//		viewWeatherInfoLinear.setVisibility(b?View.VISIBLE:View.INVISIBLE);
//		progressBar.setVisibility(!b?View.VISIBLE:View.INVISIBLE);
//	}
//	
//	public boolean onKeyDown(int KeyCode, KeyEvent event) {
//		switch (KeyCode) {
//		case KeyEvent.KEYCODE_BACK:
//			String alertTitle = "�Ϸ�˶�";
//			String buttonMessage = "�Ϸ�˶��� �����Ͻðڽ��ϱ�?";
//			String buttonYes = "��";
//			String buttonNo = "�ƴϿ�";
//
//			new AlertDialog.Builder(ActivityTab2WeatherEventList
//					.this)
//					.setTitle(alertTitle)
//					.setMessage(buttonMessage)
//					.setPositiveButton(buttonYes,
//							new DialogInterface.OnClickListener() {
//
//								public void onClick(DialogInterface dialog,
//										int which) {
//									moveTaskToBack(true);
//									finish();
//								}
//							}).setNegativeButton(buttonNo, null).show();
//		}
//		return true;
//	}
//}
