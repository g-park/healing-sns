package com.garamsoft.bubble.activity.tab3;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.garamsoft.bubble.R;
import com.garamsoft.bubble.list.tab3_commonList_Adapter;
import com.garamsoft.bubble.list.tab3_commonList_Holder;

public class ActivityTab3_GongJi extends Activity {

	ArrayList<tab3_commonList_Holder> tab3_gongji_customList;
	private tab3_commonList_Adapter tab3_gongji_adapter;

	// tab4에 들어갈 전체 리스트뷰.
	private ListView tab3_gongji_listView;

	private LinearLayout tab3_gongji_linear;

	private String gongji_front_name[] = { "감사합니다", "업데이트 내역", "사용방법",
			"자주 듣는 질문(FAQ)" };

	private String gongji_after_name[] = { "2012.6.28" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tab3_gongji);

		tab3_gongji_linear = (LinearLayout) findViewById(R.id.tab3_gongji_linear);

		tab3_gongji_linear.setBackgroundDrawable((getResources()
				.getDrawable(R.drawable.default_background)));

		tab3_gongji_listView = (ListView) findViewById(R.id.tab3_gongji_listView);

		tab3_gongji_customList = new ArrayList<tab3_commonList_Holder>();

		tab3_gongji_customList.add(new tab3_commonList_Holder(
				gongji_front_name[0], gongji_after_name[0]));

		tab3_gongji_customList.add(new tab3_commonList_Holder(
				gongji_front_name[1], gongji_after_name[0]));
		
		tab3_gongji_customList.add(new tab3_commonList_Holder(
				gongji_front_name[2], gongji_after_name[0]));
		
		tab3_gongji_customList.add(new tab3_commonList_Holder(
				gongji_front_name[3], gongji_after_name[0]));

		tab3_gongji_adapter = new tab3_commonList_Adapter(
				ActivityTab3_GongJi.this, R.layout.view_tab3_common_list_layout,
				tab3_gongji_customList);

		tab3_gongji_listView.setAdapter(tab3_gongji_adapter);

		// 리스트뷰 띄귀.
		tab3_gongji_listView.setOnItemClickListener(new OnItemClickListener() {
			// 선택했을 경우 해당 번째의 TextView를 표시하는 법.
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long view) {

				Intent intent = null;

				switch (position) {
				case 0:
					intent = new Intent(getApplicationContext(),
							ActivityTab3_GongJiChild.class);

					intent.putExtra("position", position);
					intent.putExtra("text", gongji_front_name[0]);
					intent.putExtra("day", gongji_after_name[0]);

					break;

				case 1:
					intent = new Intent(getApplicationContext(),
							ActivityTab3_GongJiChild.class);

					intent.putExtra("position", position);
					intent.putExtra("text", gongji_front_name[1]);
					intent.putExtra("day", gongji_after_name[0]);

					break;

				case 2:
					intent = new Intent(getApplicationContext(),
							ActivityTab3_GongJiChild.class);

					intent.putExtra("position", position);
					intent.putExtra("text", gongji_front_name[1]);
					intent.putExtra("day", gongji_after_name[0]);

					break;
				case 3:
					intent = new Intent(getApplicationContext(),
							ActivityTab3_GongJiChild.class);

					intent.putExtra("position", position);
					intent.putExtra("text", gongji_front_name[1]);
					intent.putExtra("day", gongji_after_name[0]);

					break;
				}
				startActivity(intent);
			}
		});
	}
}
