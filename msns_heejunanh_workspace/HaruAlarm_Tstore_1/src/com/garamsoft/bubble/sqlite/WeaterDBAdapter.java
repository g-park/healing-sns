//package com.garamsoft.bubble.sqlite;
//
//import java.util.ArrayList;
//import java.util.GregorianCalendar;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteDatabase.CursorFactory;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//
//import com.garamsoft.bubble.calendar.data.CalendarDataForHaruAlarm;
//import com.garamsoft.bubble.calendar.data.EventData;
//import com.garamsoft.bubble.calendar.data.EventDataForAlarm;
//import com.garamsoft.bubble.calendar.data.EventTime;
//
///**SQLite�� �����ϴ� DB �Ŵ���*/
//public class WeaterDBAdapter {
//	
//	private SQLiteDatabase CalendarDB;
//	private final Context context;
//	
//	private final static String DATABASE_NAME = "weatherdata.db";
//	private final static int DATABASE_VERSION = 1;
//	private final static String DATABASE_TABLE = "weatherTalbe";
//	
//	public static final String HEADER_TM = "HOUR";//>24/hour>���׿��� 3�ð�����(15��~18�ñ���)1
//	public static final String HEADER_TS = "HOUR";//>24/hour>���׿��� 3�ð�����(15��~18�ñ���)1
//	public static final String HOUR = "HOUR";//>24/hour>���׿��� 3�ð�����(15��~18�ñ���)1
//	public static final String DAY= "HOUR";//>0/day>1��°��(����/����/�� �� ����)2
//	public static final String TEMP= "HOUR";//>20.3/temp>����ð��µ�(15��~18��)3
//	public static final String TMX= "TMX";//>-999.0/tmx>�ְ�µ� missing(���� ���� ���)4
//	public static final String TMN= "TMN";//>-999.0/tmn>�����µ� missing(���� ���� ���)5
//	public static final String SKY= "SKY";//>3/sky>�ϴû����ڵ�6
//	public static final String PTY= "PTY";//>0/pty>���������ڵ�7
//	public static final String WFKOR= "WFKOR";//>��������/wfKor>�����ѱ���
//	public static final String WFEN= "WFEN";//>Mostly Cloudy/wfEn>��������
//	public static final String POP= "POP";//>20/pop>����Ȯ��%
//	public static final String R12= "POP";//>0.0/r12>12�ð� ���󰭼���
//	public static final String S12= "S12";//>0.0/s12>12�ð� ��������
//	public static final String WS= "WS";//>1.8/ws ǳ��.
//	public static final String WD= "WD";//>3/wd>ǳ��(m/s) �ݿø�ó�� �� �̿�(����)
//	public static final String WDKOR= "WDKOR";//>����/wdKor>ǳ��ǳ���ѱ���
//	public static final String WDEN= "WDEN";//>SE/wdEn>ǳ�⿵��
//	public static final String REH= "REH";//>48/reh>����%
//
//	// boolean���� ��������.
//	private static class CalendarDBOpenHelper extends SQLiteOpenHelper{
//		private final static String DATABASE_CREATE = "create table "
//			+ DATABASE_TABLE 
//			+ " (" 
//			+ EVENT_ID+" TEXT primary key, "
//			+ EVENT_SUMMARY + " TEXT,"
//			+ STATUS + " TEXT,"
//			
//			+ START_DATE + " TEXT,"
//			+ START_TIME + " TEXT,"
//			+ START_TIMEZONE + " TEXT,"
//			
//			+ END_DATE + " TEXT,"
//			+ END_TIME + " TEXT,"
//			+ END_TIMEZONE + " TEXT,"
//			
//			+ DESCRIPTION + " TEXT,"
//			
//			+ CALENDAR_ID + " TEXT,"
//			+ CALENDAR_SUMMARY + " TEXT);";
//		
//		// ������.
//		public CalendarDBOpenHelper(Context context, String name,CursorFactory factory, int version) {
//			super(context, name, factory, version);
//		}
//
//		@Override
//		public void onCreate(SQLiteDatabase db) {
//			// TODO Auto-generated method stub
//			db.execSQL(DATABASE_CREATE);
//		}
//
//		@Override
//		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//			// TODO Auto-generated method stub
//			db.execSQL("DROP TABLE" + DATABASE_TABLE);
//			onCreate(db);
//		}
//	}//AlarmDBOpenHelper
//	private CalendarDBOpenHelper dbHelper;
//	
//	public WeaterDBAdapter(Context _context){
//		context = _context;
//		dbHelper = new CalendarDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
//	}
//	
//	public void close(){
//		CalendarDB.close();
//	}
//	
//	public void open(){
//		try{
//			CalendarDB = dbHelper.getWritableDatabase();
//		}catch(SQLException e){
//			CalendarDB = dbHelper.getReadableDatabase();
//		}
//	}
//	
//	// ����.
//	public long insertTask(EventDataForAlarm task){
//		ContentValues values = new ContentValues();
//		
//		values.put(EVENT_SUMMARY, task.eventData.eventSummary);
//		values.put(EVENT_ID, task.eventData.eventID);
//		values.put(STATUS, task.eventData.status);
//		
//		values.put(START_DATE, task.eventData.start.date);
//		values.put(START_TIME, task.eventData.start.time);
//		values.put(START_TIMEZONE, task.eventData.start.timeZone);
//		
//		values.put(END_DATE, task.eventData.end.date);
//		values.put(END_TIME, task.eventData.end.time);
//		values.put(END_TIMEZONE, task.eventData.end.timeZone);
//		
//		values.put(DESCRIPTION, task.eventData.description);
//		
//		values.put(CALENDAR_ID, task.calendarDataForHaruAlarm.calendarId);
//		values.put(CALENDAR_SUMMARY, task.calendarDataForHaruAlarm.calendarSummary);
//
//		return CalendarDB.insert(DATABASE_TABLE, null, values);
//	}
//	
//	// DB ����.  ( request_code���� ������ ����. )
//	public boolean removeTask(int eventId){
//		return CalendarDB.delete(DATABASE_TABLE, 
//			EVENT_ID +"="+eventId, null)>=0;
//	}
//	
//	// ������Ʈ. ���⿡�� updateTask�� AlarmActivty�� updateTask���� �ٸ���.
////	public boolean updateTask(SingleAlarmListItemInfo tmp, String _Start_end){
////		ContentValues values = new ContentValues();
////		
////		values.put(KEY_START_END, _Start_end);
////		
////		return AlarmDB.update(DATABASE_TABLE, values,
////				KEY_START_END+"="+ tmp.getStart_End(), null)>0;
////	}
//	
//	public int updateTask(EventDataForAlarm task){
//
//		ContentValues values = new ContentValues();
//		
//		values.put(EVENT_SUMMARY, task.eventData.eventSummary);
////		values.put(EVENT_ID, task.eventData.eventID);
//		values.put(STATUS, task.eventData.status);
//		
//		values.put(START_DATE, task.eventData.start.date);
//		values.put(START_TIME, task.eventData.start.time);
//		values.put(START_TIMEZONE, task.eventData.start.timeZone);
//		
//		values.put(END_DATE, task.eventData.end.date);
//		values.put(END_TIME, task.eventData.end.time);
//		values.put(END_TIMEZONE, task.eventData.end.timeZone);
//		
//		values.put(DESCRIPTION, task.eventData.description);
//		
//		values.put(CALENDAR_ID, task.calendarDataForHaruAlarm.calendarId);
//		values.put(CALENDAR_SUMMARY, task.calendarDataForHaruAlarm.calendarSummary);
//
////		int result = AlarmDB.update(DATABASE_TABLE, values, KEY_REQUEST_CODE+"=?", new String[]{ String.valueOf(singleAlarmListItemInfo.request_code.requestCode) });
//		int result = CalendarDB.update(DATABASE_TABLE, values, EVENT_ID+" = "+"'"+task.eventData.eventID+"'", null);
//		return result;
//	}
//	
//	public ArrayList<EventDataForAlarm> getAllArrayList(){
//		ArrayList<EventDataForAlarm> arrayList = new ArrayList<EventDataForAlarm>();
//		Cursor c = CalendarDB.query(DATABASE_TABLE, null, null, null, null, null, null);
//			c.moveToFirst();
//			while(!(c.isAfterLast())){
//
//				String eventID = c.getString(c.getColumnIndex(EVENT_ID));
//				String eventSummary=c.getString(c.getColumnIndex(EVENT_SUMMARY));
//				String eventStatus =c.getString(c.getColumnIndex(STATUS));
//				
//				String startDate=c.getString(c.getColumnIndex(START_DATE));
//				String startTime =c.getString(c.getColumnIndex(START_TIME));
//				String startTimeZone=c.getString(c.getColumnIndex(START_TIMEZONE));
//				
//				String endDate=c.getString(c.getColumnIndex(END_DATE));
//				String endTime=c.getString(c.getColumnIndex(END_TIME));
//				String endTimeZone=c.getString(c.getColumnIndex(END_TIMEZONE));
//				
//				String description=c.getString(c.getColumnIndex(DESCRIPTION));
//				String calendarID=c.getString(c.getColumnIndex(CALENDAR_ID));
//				
//				String calendarSummary = c.getString(c.getColumnIndex(CALENDAR_SUMMARY));
//				EventTime startEventTime = new EventTime(startDate, startTime, startTimeZone);
//				EventTime endEventTime = new EventTime(endDate, endTime, endTimeZone);
//				
//				CalendarDataForHaruAlarm calendarDataForHaruAlarm = new CalendarDataForHaruAlarm(calendarID, calendarSummary);
//				EventData eventData = new EventData(eventSummary, eventID, eventStatus, description, startEventTime, endEventTime) ;
//				arrayList.add(new EventDataForAlarm(eventData , calendarDataForHaruAlarm));
//				
//				c.moveToNext();
//			}
//			c.close();
//			return arrayList;
//			
//	}
//	
//	public ArrayList<EventDataForAlarm> getTodayArrayList(){
//		ArrayList<EventDataForAlarm> arrayList = new ArrayList<EventDataForAlarm>();
//		
//		GregorianCalendar todayCalendar = new GregorianCalendar();
//		
//		
//		Cursor c = CalendarDB.query(DATABASE_TABLE, null, null, null, null, null, null);c.moveToFirst();
//			while(!(c.isAfterLast())){
////				boolean startTimeFlag = false;
////				boolean endDateFlag= false;
////				boolean endTimeFlag= false;
////				boolean isToday = false;
//				String startDate=c.getString(c.getColumnIndex(START_DATE));
//				String startTime =c.getString(c.getColumnIndex(START_TIME));
//				String startTimeZone=c.getString(c.getColumnIndex(START_TIMEZONE));
//				String endDate=c.getString(c.getColumnIndex(END_DATE));
//				String endTime=c.getString(c.getColumnIndex(END_TIME));
//				String endTimeZone=c.getString(c.getColumnIndex(END_TIMEZONE));
//				
//				/**startDateStrings�� startDate�� ���� �ִٸ� �װ��� ��,��,�Ϸ� �־��ִ� �迭�̴�. 
//				 * startDateStrings�� '2012-04-11'�� ���� �������� �Ǿ��ִ�.
//				 * �װ��� split �Լ��� ���ؼ� ©�󳻴� ���̴�.
//				 * ex[0] = �� [1] = �� [2] = �� */
////				String[] startDateStrings = null;
//				
//				/**startTimeDateStrings startTime�� ���� �ִٸ� �װ��� ��,��,�Ϸ� �־��ִ� �迭�̴�.
//				 * startTimeDateStrings '2012-05-08T19:00:00.000+09:00'�� ���� �������� �Ǿ��ִ�.
//				 * �װ��� split �Լ��� ���ؼ� ©�󳻴� ���̴�. 
//				 * ex[0] = �� [1] = �� [2] = �� */
////				String[] startTimeDateStrings=null;
//				
//				String startStrings[] = null;
//				String endStrings[] = null;
//				
//				if(startDate != null){
////					startDateFlag = true;
////					startTimeFlag = false;
//					/*�ڸ���*/
//					startStrings = cutEventTimeDate(startDate);
//					
//					Log.i("TAH", "startDate ������ : " +startDate);
//				}
//				else if(startTime !=null){
////					startDateFlag = false;
////					startTimeFlag = true;
//					/*�ڸ���*/
//					String[] startTimeStrings = startTime.split("T");
////					startTimeDate = startTimeStrings[0];
////					for(int i = 0 ; i < startTimeStrings.length ; i ++){
////						if(i == 1){
////							String[] tmpString = startTimeStrings[i].split("\\+");
////							startTimeTime = tmpString[0];
////							for(int j = 0 ; j <tmpString.length ; j++){
////								System.out.println("j - " +j+" : "+tmpString[j]);
////							}
////						}
////					}
////					
//					startStrings = cutEventTimeDate(startTimeStrings[0]);
//					Log.i("TAH", "startTime ������ : " +startTime);
//				}
//				
//				if(endDate != null){
////					endDateFlag = true;
////					endTimeFlag = false;
//					/*�ڸ���*/
//					endStrings = cutEventTimeDate(endDate);
//					Log.i("TAH","���ᳯ : " + endDate);
//				}
//				else if(endTime != null){
////					endDateFlag = false;
////					endTimeFlag = true;;
//					/*�ڸ���*/
//					String[] endTimeStrings = endTime.split("T");
//					endStrings = cutEventTimeDate(endTimeStrings[0]);
//					Log.i("TAH", "���ᳯ : " +endTime);
//				}
//
//				GregorianCalendar startCalendar = new GregorianCalendar(Integer.parseInt(startStrings[0]), Integer.parseInt(startStrings[1])-1, Integer.parseInt(startStrings[2]));
//				GregorianCalendar endCalendar = new GregorianCalendar(Integer.parseInt(endStrings[0]), Integer.parseInt(endStrings[1])-1, Integer.parseInt(endStrings[2]));
////				Log.i("Test02", 
////						"���� :" +startStrings[0]+startStrings[1]+startStrings[2]+"\n"
////						+"�� :"+endStrings[0]+startStrings[1]+endStrings[2]);
//				if(isThatDayRight(startCalendar, endCalendar, todayCalendar)){
////					Log.i("TAH", "isToday : " +isToday +" date : "+startDate);
//					/*������ ������*/
//					String eventID = c.getString(c.getColumnIndex(EVENT_ID));
//					String eventSummary=c.getString(c.getColumnIndex(EVENT_SUMMARY));
//					String eventStatus =c.getString(c.getColumnIndex(STATUS));
//					
//					String description=c.getString(c.getColumnIndex(DESCRIPTION));
//					String calendarID=c.getString(c.getColumnIndex(CALENDAR_ID));
//					
//					String calendarSummary = c.getString(c.getColumnIndex(CALENDAR_SUMMARY));
//					EventTime startEventTime = new EventTime(startDate, startTime, startTimeZone);
//					EventTime endEventTime = new EventTime(endDate, endTime, endTimeZone);
//					/*�迭�� �ִ´�.*/
//					CalendarDataForHaruAlarm calendarDataForHaruAlarm = new CalendarDataForHaruAlarm(calendarID, calendarSummary);
//					EventData eventData = new EventData(eventSummary, eventID, eventStatus, description, startEventTime, endEventTime) ;
//					arrayList.add(new EventDataForAlarm(eventData , calendarDataForHaruAlarm));
//				}
//				
//				c.moveToNext();
//			}
//			c.close();
//			return arrayList;
//	}
//	
//	public EventDataForAlarm getSingleAlarmListItemInfo(String eventID){
//		Cursor c = CalendarDB.query(DATABASE_TABLE, null, null, null, null, null, null);
//		
//		
//
//		c.moveToFirst();
//		while(!(c.isAfterLast())){
//
//			String eventId=c.getString(c.getColumnIndex(EVENT_ID));
//			if(eventID.equals(eventId)){
//
//				String eventSummary=c.getString(c.getColumnIndex(EVENT_SUMMARY));
//				String eventStatus =c.getString(c.getColumnIndex(STATUS));
//				
//				String startDate=c.getString(c.getColumnIndex(START_DATE));
//				String startTime =c.getString(c.getColumnIndex(START_TIME));
//				String startTimeZone=c.getString(c.getColumnIndex(START_TIMEZONE));
//				
//				String endDate=c.getString(c.getColumnIndex(END_DATE));
//				String endTime=c.getString(c.getColumnIndex(END_TIME));
//				String endTimeZone=c.getString(c.getColumnIndex(END_TIMEZONE));
//				
//				String description=c.getString(c.getColumnIndex(DESCRIPTION));
//				String calendarID=c.getString(c.getColumnIndex(CALENDAR_ID));
//				
//				String calendarSummary = c.getString(c.getColumnIndex(CALENDAR_SUMMARY));
//				EventTime startEventTime = new EventTime(startDate, startTime, startTimeZone);
//				EventTime endEventTime = new EventTime(endDate, endTime, endTimeZone);
//				
//				CalendarDataForHaruAlarm calendarDataForHaruAlarm = new CalendarDataForHaruAlarm(calendarID, calendarSummary);
//				EventData eventData = new EventData(eventSummary, eventID, eventStatus, description, startEventTime, endEventTime) ;
//				
//				return new EventDataForAlarm(eventData , calendarDataForHaruAlarm); 
//			}
//			
//			c.moveToNext();
//		}
//		c.close();
//		return null;
//	}
//	/***/
//	boolean isThatDayRight(GregorianCalendar startCalendar,GregorianCalendar endCalendar, GregorianCalendar todayCalendar){
//		long today = todayCalendar.getTimeInMillis();
//		long start = startCalendar.getTimeInMillis();
//		long end = endCalendar.getTimeInMillis();
//		Log.i("Test02","(start<=today)"+(start<=today)+"\n"
//				+"(today<=end)"+(today<=end)+"\n"
//				+"����:"+startCalendar.getTime().getYear()+startCalendar.getTime().getMonth()+startCalendar.getTime().getDate()+"\n"
//				+"����:"+todayCalendar.getTime().getYear()+todayCalendar.getTime().getMonth()+todayCalendar.getTime().getDate()+"\n"
//				+"����:"+endCalendar.getTime().getYear()+endCalendar.getTime().getMonth()+endCalendar.getTime().getDate()
//				);
//		return start<=today&&today<=end;
//	}
//	
//	String[] cutEventTimeDate(String str){
//		String[] result = str.split("-");
//		if (result[1].startsWith("0"))
//			result[1] = result[1].subSequence(1, 2).toString();
//		if (result[2].startsWith("0"))
//			result[2] = result[2].subSequence(1, 2).toString();
//		return result;
//	}
//	
//}
//
//
