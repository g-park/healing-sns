package com.garamsoft.bubble.sqlite.request.alarm;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;


public class AlarmDataHandleThread extends Thread {

	Handler mChildHandler;//������ �����带 �ڵ�.
	
	AlarmDataDBOpenHelper mDbHelper;//�����ͺ��̽��� �ǵ� �� �ִ� ����.
	
	public AlarmDataHandleThread(AlarmDataDBOpenHelper db) {//������.
		mDbHelper = db;//��Ƽ��Ƽ���� �޾ƿ� �����ͺ��̽� ����.
	}
	@Override
	public void run() {
		Looper.prepare();//����.
		mChildHandler = new Handler() {//���۸� ����Ϸ��� �ڵ鷯�� �� �ʿ��ϴ�.
			@Override
			public void handleMessage(Message msg) {//�޽����� ������ ����ȴ�.
				AlarmDataRequest req = (AlarmDataRequest)msg.obj;//������ ������Ʈ(���⼭ ������Ʈ�ε� �߻�Ŭ������ �ؾ��Ѵ�).
				req.execute(mDbHelper);//�����Ͱ� �������� �����Ѵ�.(DataRequestŬ�������� Ȯ��).
			}
		};
		Looper.loop();//����(�ٽ� ����).
	}
	
	public void sendDataRequest(AlarmDataRequest req) {//��Ƽ��ƼŬ�������� �����Ѵ�. �׷��� �޽��� ť�� ���̰Եȴ�.(����.)
		Message msg = mChildHandler.obtainMessage();//new Message()�� ����ϸ� �ȵȴ�.
		msg.obj = req;//�޾ƿ� �����͸�����Ʈ ���۷����� �޽����� �ٿ���.
		mChildHandler.sendMessage(msg);//�ڵ鷯�� ������.
	}
	
	public void loopClose() {
		mChildHandler.getLooper().quit();//����ɶ� �Ҹ���.(���۸� �����ؾ��ϱ� ������)
	}
}
