package com.garamsoft.bubble.activity.tab3;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.garamsoft.bubble.R;
import com.garamsoft.bubble.list.tab3_commonList_Adapter;
import com.garamsoft.bubble.list.tab3_commonList_Holder;

public class ActivityTab3_Version extends Activity {
	ArrayList<tab3_commonList_Holder> tab3_version_customList;
	private tab3_commonList_Adapter tab3_version_adapter;

	// tab3�� �� ��ü ����Ʈ��.
	private ListView tab3_version_listView;

	// ������� ������ ��Ÿ��.
	private float current_app_version = 1.0f;

	// �ֽŹ��� ������ ��Ÿ��.
	private float new_app_version = 1.0f;

	// Ȧ������ String���� �����ϹǷ� �Ǽ��� String�� �ٲ�.
	
	// ���� ����.
	private String current_app_str = Float.toString(current_app_version) + " v";

	// �ֽ� ����.
	private String new_app_str = Float.toString(new_app_version) + " v";
	
	private String shake_front_name[] = {"���� ����","�ֽ� ����", "������Ʈ �Ϸ� ����"};
	
	private String shake_after_name[]={"2012.6.28"};
	
	private LinearLayout tab3_version_linear;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tab3_version);
		
		tab3_version_linear = (LinearLayout)findViewById(R.id.tab3_version_linear);
		
		tab3_version_linear.setBackgroundDrawable((getResources().getDrawable(R.drawable.default_background)));
		
		tab3_version_listView = (ListView) findViewById(R.id.tab3_version_listView);

		tab3_version_customList = new ArrayList<tab3_commonList_Holder>();

		tab3_version_customList.add(new tab3_commonList_Holder(shake_front_name[0],
				current_app_str));

		tab3_version_customList.add(new tab3_commonList_Holder(shake_front_name[1],
				new_app_str));

		tab3_version_customList.add(new tab3_commonList_Holder(shake_front_name[2],
				shake_after_name[0]));

		tab3_version_adapter = new tab3_commonList_Adapter(ActivityTab3_Version.this,
				R.layout.view_tab3_common_list_layout, tab3_version_customList);

		tab3_version_listView.setAdapter(tab3_version_adapter);

		// ����Ʈ�� ���.
		tab3_version_listView.setOnItemClickListener(new OnItemClickListener() {
			// �������� ��� �ش� ��°�� TextView�� ǥ���ϴ� ��.
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long view) {


				switch (position) {
				
				// �������.
				case 0 :
					Toast.makeText(getApplicationContext(), "���������" + current_app_str + "�Դϴ�.", 0).show();

					break;
				// �ֽŹ���.
				case 1 :
					Toast.makeText(getApplicationContext(), "�ֽŹ�����" + new_app_str + "�Դϴ�.", 0).show();
					
					break;
				// �ֽŹ���.
				case 2:
					if (current_app_version == new_app_version) {
						Toast.makeText(getApplicationContext(), "�ֽŹ����Դϴ�.", 0)
								.show();
					}

					else if (current_app_version != new_app_version) {
						Intent marketLaunch = new Intent(Intent.ACTION_VIEW);
						marketLaunch.setData(Uri
								.parse("market://search?q=�Ϸ�˶�"));
						startActivity(marketLaunch);
					}
					
					break;
				}
			}
		});
	}
}
