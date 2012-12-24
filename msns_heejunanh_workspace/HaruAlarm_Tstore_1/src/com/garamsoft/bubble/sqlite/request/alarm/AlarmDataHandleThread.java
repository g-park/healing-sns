package com.garamsoft.bubble.sqlite.request.alarm;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;


public class AlarmDataHandleThread extends Thread {

	Handler mChildHandler;//데이터 스레드를 핸들.
	
	AlarmDataDBOpenHelper mDbHelper;//데이터베이스를 건들 수 있는 헬퍼.
	
	public AlarmDataHandleThread(AlarmDataDBOpenHelper db) {//생성자.
		mDbHelper = db;//액티비티에서 받아온 데이터베이스 헬퍼.
	}
	@Override
	public void run() {
		Looper.prepare();//루퍼.
		mChildHandler = new Handler() {//루퍼를 사용하려면 핸들러가 꼭 필요하다.
			@Override
			public void handleMessage(Message msg) {//메시지를 받으면 실행된다.
				AlarmDataRequest req = (AlarmDataRequest)msg.obj;//데이터 리퀘스트(여기서 리퀘스트인데 추상클래스로 해야한다).
				req.execute(mDbHelper);//데이터가 리퀘스를 시행한다.(DataRequest클레스에서 확인).
			}
		};
		Looper.loop();//루퍼(다시 루퍼).
	}
	
	public void sendDataRequest(AlarmDataRequest req) {//액티비티클래스에서 실행한다. 그러면 메시지 큐에 쌓이게된다.(루퍼.)
		Message msg = mChildHandler.obtainMessage();//new Message()를 사용하면 안된다.
		msg.obj = req;//받아온 데이터리퀘스트 레퍼런스를 메시지에 붙여서.
		mChildHandler.sendMessage(msg);//핸들러로 보낸다.
	}
	
	public void loopClose() {
		mChildHandler.getLooper().quit();//종료될때 불른다.(루퍼를 종료해야하기 때문데)
	}
}
