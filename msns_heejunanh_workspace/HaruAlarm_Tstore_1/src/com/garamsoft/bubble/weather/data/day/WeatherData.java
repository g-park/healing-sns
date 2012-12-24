package com.garamsoft.bubble.weather.data.day;

public class WeatherData {
	/**���� ���¿� ���� �ڵ� ������ Ŭ�����̴�. ��� ������ �ۺ����� ���� ���� �� �� �ֵ��� �ߴ�.
	 * 
	 * 1. �ϴ� ����
	 * 2. ���� ����
	 * 	-���� ������
	 * 	-���� ������
	 * 3. �ٶ�
	 * -ǳ��
	 * -ǳ��
	 * 
	 * */
	public String hour;//>24/hour>���׿��� 3�ð�����(15��~18�ñ���)1
	public String day;//>0/day>1��°��(����/����/�� �� ����)2
	public String temp;//>20.3/temp>����ð��µ�(15��~18��)3
	public String tmx;//>-999.0/tmx>�ְ�µ� missing(���� ���� ���)4
	public String tmn;//>-999.0/tmn>�����µ� missing(���� ���� ���)5
	
	/**
	 * �� 1 : ����
	 * �� 2 : ��������
	 * �� 3 : ��������
	 * �� 4 : �帲
	 * */
	public String sky;//>3/sky>�ϴû����ڵ�6
	
	/**�� 0 : ����
	 * �� 1 : ��
	 * �� 2 : ��/��
	 * �� 3 : ��/��
	 * �� 4 : ��
	 * */
	public String pty;//>0/pty>���������ڵ�7
	
	public String wfKor;//>��������/wfKor>�����ѱ���
	public String wfEn;//>Mostly Cloudy/wfEn>��������
	public String pop;//>20/pop>����Ȯ��%
	public String r12;//>0.0/r12>12�ð� ���󰭼���
	public String s12;//>0.0/s12>12�ð� ��������
	/**[���������� �� ǥ�ù�� / ���尪]
	 * �� 0.1mm �̸� (0mm �Ǵ� ����) / 0 <= x < 0.1
	 * �� 0.1mm �̻� 1mm �̸�(1mm �̸�) / 0.1 <= x < 1
	 * �� 1mm �̻� 5mm �̸�(1~4mm) / 1 <= x < 5
	 * �� 5mm �̻� 10mm �̸�(5~9mm) /5 <= x < 10
	 * �� 10mm �̻� 25mm �̸�(10~24mm) / 10 <= x < 25
	 * �� 25mm �̻� 50mm �̸�(25~49mm) / 25 <= x < 50�� 50mm �̻�(50mm �̻�) / 50 <= x*/
	
	/**[���������� �� ǥ�ù�� / ���尪]
	 * �� 0.1cm �̸� (0mm �Ǵ� ����) / 0 <= x < 0.1
	 * �� 0.1mm �̻� 1mm �̸�(1mm �̸�) / 0.1 <= x < 1
	 * �� 1mm �̻� 5mm �̸�(1~4mm) / 1 <= x < 5
	 * �� 5mm �̻� 10mm �̸�(5~9mm) /5 <= x < 10
	 * �� 10mm �̻� 25mm �̸�(10~24mm) / 10 <= x < 20
	 * �� 50mm �̻�(50mm �̻�) / 20 <= x
	 * */
	
	public String ws;//>1.8/ws ǳ��.
	
	public String wd;//>3/wd>ǳ��(m/s) �ݿø�ó�� �� �̿�(����)
	/** ǳ�� 0~7 (��, �ϵ�, ��, ����, ��, ����, ��, �ϼ�)*/
	public String wdKor;//>����/wdKor>ǳ��ǳ���ѱ���
	public String wdEn;//>SE/wdEn>ǳ�⿵��
	public String reh;//>48/reh>����%
	
//	public Data(
//			String hour,//hour;;//>24/hour>���׿��� 3�ð�����(15��~18�ñ���)1
//			String day,//day;;//>0/day>1��°��(����/����/�� �� ����)2
//			String temp,//temp;//>20.3/temp>����ð��µ�(15��~18��)3
//			String tmx,//tmx;//>-999.0/tmx>�ְ�µ� missing(���� ���� ���)4
//			String tmn,//tmn;//>-999.0/tmn>�����µ� missing(���� ���� ���)5
//			String sky,//sky;//>3/sky>�ϴû����ڵ�6
//			String pty,//pty;//>0/pty>���������ڵ�7
//			String wfKor,//wfKor;//>��������/wfKor>�����ѱ���
//			String wfEn,//wfEn;//>Mostly Cloudy/wfEn>��������
//			String pop,//pop;//>20/pop>����Ȯ��%
//			String r12,//r12;//>0.0/r12>12�ð� ���󰭼���
//			String s12,//s12;//>0.0/s12>12�ð� ��������
//			String ws,//ws;//>1.8/ws ǳ��.
//			String wd,//wd;//>3/wd>ǳ��(m/s) �ݿø�ó�� �� �̿�(����)
//			String wdKor,//wdKor;//>����/wdKor>ǳ��ǳ���ѱ���
//			String wdEn,//wdEn;//>SE/wdEn>ǳ�⿵��
//			String reh//reh;//>48/reh>����%
//			) {
//		
//	}
	
	public WeatherData() {
	}

	public WeatherData(String hour, String day, String temp, String tmx, String tmn,
			String sky, String pty, String wfKor, String wfEn, String pop,
			String r12, String s12, String ws, String wd, String wdKor,
			String wdEn, String reh) {
		super();
		this.hour = hour;
		this.day = day;
		this.temp = temp;
		this.tmx = tmx;
		this.tmn = tmn;
		this.sky = sky;
		this.pty = pty;
		this.wfKor = wfKor;
		this.wfEn = wfEn;
		this.pop = pop;
		this.r12 = r12;
		this.s12 = s12;
		this.ws = ws;
		this.wd = wd;
		this.wdKor = wdKor;
		this.wdEn = wdEn;
		this.reh = reh;
	}
	
}
