package com.garamsoft.bubble.alarmdata;

// 요일 설정 클래스. ( 체크박스 )
public class DayOfTheWeek {
	public static boolean ON = true;
	public static boolean OFF = false;
	
	private boolean 월,화,수,목,금,토,일 = true;

	public DayOfTheWeek() {
	}
	
	public DayOfTheWeek(String banbok){
		setDayofTheWeek(banbok);
	}
	
	public String getBanbok(){
		return (월?"월":"")
				+(화?"화":"")
				+(수?"수":"")
				+(목?"목":"")
				+(금?"금":"")
				+(토?"토":"")
				+(일?"일":"");
	}

	private void  setDayofTheWeek(String weeks){
		월 = OFF;
		화 = OFF;
		수 = OFF;
		목 = OFF;
		금 = OFF;
		토 = OFF;
		일 = OFF;
		for (int i = 0; i < weeks.length(); i++) {
			char day = weeks.charAt(i);
			if( day =='월'){
				월 = true;
			}
			switch (day) {
			case '월':
				월 =true;
				break;
			case '화':
				화 =true;
				break;
			case '수':
				수 =true;
				break;
			case '목':
				목 =true;
				break;
			case '금':
				금 =true;
				break;
			case '토':
				토 =true;
				break;
			case '일':
				일 =true;
				break;

			}
			
		    }
	}

	



	public void set월(boolean 월) {
		this.월 = 월;
	}

	public void set화(boolean 화) {
		this.화 = 화;
	}

	public void set수(boolean 수) {
		this.수 = 수;
	}

	public void set목(boolean 목) {
		this.목 = 목;
	}

	public void set금(boolean 금) {
		this.금 = 금;
	}

	public void set토(boolean 토) {
		this.토 = 토;
	}

	public void set일(boolean 일) {
		this.일 = 일;
	}

}
