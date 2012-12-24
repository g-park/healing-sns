package com.garamsoft.bubble.sqlite.request.alarm;




public abstract class AlarmDataRequest {
//	Handler mainHandler;
	abstract public void execute(AlarmDataDBOpenHelper dbHelper);
	OnProcessedListener mListener;//�������̽�(��Ʈ�ѷ�, ������).
	

	public interface OnProcessedListener {//�����ʸ� ����ϴ� �������̽�.
		public void onProcessed(AlarmDataRequest data);
	}
	
	public void setOnProcessedListener(OnProcessedListener listener) {//�����ʸ� Ŭ������ ����Ѵ�.
		mListener = listener;//�����ʴ� UI��Ʈ���� �� �ִ� ���� �����带 �־���Ѵ�.
	}

}
