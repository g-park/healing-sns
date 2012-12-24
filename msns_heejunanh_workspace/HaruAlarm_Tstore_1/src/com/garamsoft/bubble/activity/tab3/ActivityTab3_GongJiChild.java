package com.garamsoft.bubble.activity.tab3;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import com.garamsoft.bubble.R;

public class ActivityTab3_GongJiChild extends Activity {
	private TextView tab3_GongGi_child_main;
	private TextView tab3_GongGi_child_day;
	private TextView tab3_GongGi_child_content;

	private int gonggi_number;

	private ScrollView tab3_gongji_child_ScrollView;

	private Intent intent = null;

//	private String xml;
//	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tab3_child);

		tab3_gongji_child_ScrollView = (ScrollView) findViewById(R.id.tab3_child_scrollView);
		tab3_gongji_child_ScrollView.setBackgroundDrawable((getResources()
				.getDrawable(R.drawable.default_background)));

		tab3_GongGi_child_main = (TextView) findViewById(R.id.tab3_child_text_left);
		tab3_GongGi_child_day = (TextView) findViewById(R.id.tab3_child_text_right);
		tab3_GongGi_child_content = (TextView) findViewById(R.id.tab3_child_text);

		intent = getIntent();

		gonggi_number = intent.getIntExtra("position", 0);

		switch (gonggi_number) {

		case 0:

			getShowText(R.raw.gongji_thanks);

			break;

		case 1:

			getShowText(R.raw.gongji_update);

			break;

		case 2:

			getShowText(R.raw.help_how_to_app);

			break;

		case 3:

			getShowText(R.raw.help_question_answer);

			break;
		}
	}

	// text값 읽어오기.
	private void getShowText(int rESOURCE2) {
		tab3_GongGi_child_main.setText(intent.getStringExtra("text"));
		tab3_GongGi_child_day.setText(intent.getStringExtra("day"));

		// 파일을 이용해서 값 얻어오기.
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();

		try {
			br = new BufferedReader(new InputStreamReader(getResources()
					.openRawResource(rESOURCE2)));

			String str = null;

			while ((str = br.readLine()) != null) {
				sb.append(str + "\n");
			}

		} catch (Exception e) {
			try {
				br.close();

			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		tab3_GongGi_child_content.setText(sb.toString());
	}
}

//
// switch(gonggi_number){
//
// // 감사합니다.
// case 0 :
//
// url = "http://netgarng88.hubweb.net/same/gongji_thanks.txt";
//
// xml = DownloadHtml(url);
//
// tab3_GongGi_child_content.setText(xml);
//
// break;
//
//
// // 업데이트 내역.
// case 1 :
//
// url = "http://netgarng88.hubweb.net/same/gongji_update.txt";
//
// xml = DownloadHtml(url);
//
// tab3_GongGi_child_content.setText(xml);
//
// break;
// }
// }
//
// String DownloadHtml(String addr) {
//
// tab3_GongGi_child_main.setText(intent.getStringExtra("text"));
// tab3_GongGi_child_day.setText(intent.getStringExtra("day"));
//
// StringBuilder html = new StringBuilder();
//
// try {
// URL url = new URL(addr);
// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
// if (conn != null) {
// conn.setConnectTimeout(10000);
// conn.setUseCaches(false);
//
// if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
// BufferedReader br = new BufferedReader(
// new InputStreamReader(conn.getInputStream()));
//
// for (;;) {
// String line = br.readLine();
// if (line == null){
// break;
// }
// html.append(line + '\n');
// }
// br.close();
// }
// conn.disconnect();
// }
// } catch (Exception ex){;}
// return html.toString();
// }
// }
