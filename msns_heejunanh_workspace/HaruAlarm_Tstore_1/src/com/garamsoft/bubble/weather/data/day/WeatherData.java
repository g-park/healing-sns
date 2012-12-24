package com.garamsoft.bubble.weather.data.day;

public class WeatherData {
	/**날씨 상태에 대한 코드 데이터 클래스이다. 모든 정보는 퍼블릭으로 쉽게 접근 할 수 있도록 했다.
	 * 
	 * 1. 하늘 상태
	 * 2. 강수 상태
	 * 	-예상 강수량
	 * 	-예상 강설량
	 * 3. 바람
	 * -풍속
	 * -풍향
	 * 
	 * */
	public String hour;//>24/hour>동네예보 3시간단위(15시~18시까지)1
	public String day;//>0/day>1번째날(오늘/내일/모레 중 오늘)2
	public String temp;//>20.3/temp>현재시간온도(15시~18시)3
	public String tmx;//>-999.0/tmx>최고온도 missing(값이 없을 경우)4
	public String tmn;//>-999.0/tmn>최저온도 missing(값이 없을 경우)5
	
	/**
	 * ① 1 : 맑음
	 * ② 2 : 구름조금
	 * ③ 3 : 구름많음
	 * ④ 4 : 흐림
	 * */
	public String sky;//>3/sky>하늘상태코드6
	
	/**① 0 : 없음
	 * ② 1 : 비
	 * ③ 2 : 비/눈
	 * ④ 3 : 눈/비
	 * ⑤ 4 : 눈
	 * */
	public String pty;//>0/pty>강수상태코드7
	
	public String wfKor;//>구름많음/wfKor>날씨한국어
	public String wfEn;//>Mostly Cloudy/wfEn>날씨영어
	public String pop;//>20/pop>강수확률%
	public String r12;//>0.0/r12>12시간 예상강수량
	public String s12;//>0.0/s12>12시간 예상적설
	/**[강수량범주 및 표시방법 / 저장값]
	 * ① 0.1mm 미만 (0mm 또는 없음) / 0 <= x < 0.1
	 * ② 0.1mm 이상 1mm 미만(1mm 미만) / 0.1 <= x < 1
	 * ③ 1mm 이상 5mm 미만(1~4mm) / 1 <= x < 5
	 * ④ 5mm 이상 10mm 미만(5~9mm) /5 <= x < 10
	 * ⑤ 10mm 이상 25mm 미만(10~24mm) / 10 <= x < 25
	 * ⑥ 25mm 이상 50mm 미만(25~49mm) / 25 <= x < 50⑦ 50mm 이상(50mm 이상) / 50 <= x*/
	
	/**[적설량범주 및 표시방법 / 저장값]
	 * ① 0.1cm 미만 (0mm 또는 없음) / 0 <= x < 0.1
	 * ② 0.1mm 이상 1mm 미만(1mm 미만) / 0.1 <= x < 1
	 * ③ 1mm 이상 5mm 미만(1~4mm) / 1 <= x < 5
	 * ④ 5mm 이상 10mm 미만(5~9mm) /5 <= x < 10
	 * ⑤ 10mm 이상 25mm 미만(10~24mm) / 10 <= x < 20
	 * ⑥ 50mm 이상(50mm 이상) / 20 <= x
	 * */
	
	public String ws;//>1.8/ws 풍속.
	
	public String wd;//>3/wd>풍속(m/s) 반올림처리 값 이용(정수)
	/** 풍향 0~7 (북, 북동, 동, 남동, 남, 남서, 서, 북서)*/
	public String wdKor;//>남동/wdKor>풍향풍향한국어
	public String wdEn;//>SE/wdEn>풍향영어
	public String reh;//>48/reh>습도%
	
//	public Data(
//			String hour,//hour;;//>24/hour>동네예보 3시간단위(15시~18시까지)1
//			String day,//day;;//>0/day>1번째날(오늘/내일/모레 중 오늘)2
//			String temp,//temp;//>20.3/temp>현재시간온도(15시~18시)3
//			String tmx,//tmx;//>-999.0/tmx>최고온도 missing(값이 없을 경우)4
//			String tmn,//tmn;//>-999.0/tmn>최저온도 missing(값이 없을 경우)5
//			String sky,//sky;//>3/sky>하늘상태코드6
//			String pty,//pty;//>0/pty>강수상태코드7
//			String wfKor,//wfKor;//>구름많음/wfKor>날씨한국어
//			String wfEn,//wfEn;//>Mostly Cloudy/wfEn>날씨영어
//			String pop,//pop;//>20/pop>강수확률%
//			String r12,//r12;//>0.0/r12>12시간 예상강수량
//			String s12,//s12;//>0.0/s12>12시간 예상적설
//			String ws,//ws;//>1.8/ws 풍속.
//			String wd,//wd;//>3/wd>풍속(m/s) 반올림처리 값 이용(정수)
//			String wdKor,//wdKor;//>남동/wdKor>풍향풍향한국어
//			String wdEn,//wdEn;//>SE/wdEn>풍향영어
//			String reh//reh;//>48/reh>습도%
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
