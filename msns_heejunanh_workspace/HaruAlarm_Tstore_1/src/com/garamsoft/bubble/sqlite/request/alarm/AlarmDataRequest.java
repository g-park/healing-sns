package com.garamsoft.bubble.sqlite.request.alarm;




public abstract class AlarmDataRequest {
//	Handler mainHandler;
	abstract public void execute(AlarmDataDBOpenHelper dbHelper);
	OnProcessedListener mListener;//인터페이스(컨트롤러, 리스너).
	

	public interface OnProcessedListener {//리스너를 사용하는 인터페이스.
		public void onProcessed(AlarmDataRequest data);
	}
	
	public void setOnProcessedListener(OnProcessedListener listener) {//리스너를 클레스에 등록한다.
		mListener = listener;//리스너는 UI컨트롤할 수 있는 메인 스레드를 주어야한다.
	}

}
