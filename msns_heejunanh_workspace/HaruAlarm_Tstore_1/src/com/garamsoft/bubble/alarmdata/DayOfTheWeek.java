package com.garamsoft.bubble.alarmdata;

// ���� ���� Ŭ����. ( üũ�ڽ� )
public class DayOfTheWeek {
	public static boolean ON = true;
	public static boolean OFF = false;
	
	private boolean ��,ȭ,��,��,��,��,�� = true;

	public DayOfTheWeek() {
	}
	
	public DayOfTheWeek(String banbok){
		setDayofTheWeek(banbok);
	}
	
	public String getBanbok(){
		return (��?"��":"")
				+(ȭ?"ȭ":"")
				+(��?"��":"")
				+(��?"��":"")
				+(��?"��":"")
				+(��?"��":"")
				+(��?"��":"");
	}

	private void  setDayofTheWeek(String weeks){
		�� = OFF;
		ȭ = OFF;
		�� = OFF;
		�� = OFF;
		�� = OFF;
		�� = OFF;
		�� = OFF;
		for (int i = 0; i < weeks.length(); i++) {
			char day = weeks.charAt(i);
			if( day =='��'){
				�� = true;
			}
			switch (day) {
			case '��':
				�� =true;
				break;
			case 'ȭ':
				ȭ =true;
				break;
			case '��':
				�� =true;
				break;
			case '��':
				�� =true;
				break;
			case '��':
				�� =true;
				break;
			case '��':
				�� =true;
				break;
			case '��':
				�� =true;
				break;

			}
			
		    }
	}

	



	public void set��(boolean ��) {
		this.�� = ��;
	}

	public void setȭ(boolean ȭ) {
		this.ȭ = ȭ;
	}

	public void set��(boolean ��) {
		this.�� = ��;
	}

	public void set��(boolean ��) {
		this.�� = ��;
	}

	public void set��(boolean ��) {
		this.�� = ��;
	}

	public void set��(boolean ��) {
		this.�� = ��;
	}

	public void set��(boolean ��) {
		this.�� = ��;
	}

}
