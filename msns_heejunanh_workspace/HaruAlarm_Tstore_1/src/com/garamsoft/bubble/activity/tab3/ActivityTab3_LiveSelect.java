package com.garamsoft.bubble.activity.tab3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.garamsoft.bubble.R;

public class ActivityTab3_LiveSelect extends Activity {

//	private static final String HARU_LOG = "HARU_LOG";

	private Intent intent_receive = null;

	private Intent intent_send = null;

	private ListView live_list;

	private int live_num;

//	private DefaultLiveManager liveManager;

	private String str_live = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tab3_live_list);

//		liveManager = new DefaultLiveManager(getApplicationContext());

		intent_receive = getIntent();

		live_num = intent_receive.getIntExtra("live_select", 0);

		live_list = (ListView) findViewById(R.id.common_list);
		live_list.setBackgroundColor(Color.BLACK);

		if (live_num == 0) {
			getShow(R.array.kang_array);

		} else if (live_num == 1) {
			getShow(R.array.kyungki_array);

		} else if (live_num == 3) {
			getShow(R.array.kyung_south_array);

		} else if (live_num == 4) {
			getShow(R.array.kyung_north_array);

		} else if (live_num == 11) {
			getShow(R.array.jewonra_south_array);

		} else if (live_num == 12) {
			getShow(R.array.jewonra_north_array);

		} else if (live_num == 14) {
			getShow(R.array.chwong_south_array);

		} else if (live_num == 15) {
			getShow(R.array.chwong_north_array);
		}
	}

	private void getShow(int _live) {
		ArrayAdapter<CharSequence> adapter_live = ArrayAdapter
				.createFromResource(getApplicationContext(), _live,
						android.R.layout.simple_list_item_1);

		live_list.setAdapter(adapter_live);

		live_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long list_id) {

				str_live = live_list.getItemAtPosition(position).toString();

				intent_send = getIntent();

				intent_send.putExtra("LiveName", str_live);

				setResult(RESULT_OK, intent_send);
				finish();
			}
		});
	}
}
