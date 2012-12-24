package com.garamsoft.bubble.activity.tab3;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.garamsoft.bubble.R;
import com.garamsoft.bubble.list.tab3_customList_Adapter;
import com.garamsoft.bubble.list.tab3_customList_Holder;
import com.garamsoft.bubble.manager.DefaultLiveManager;

public class ActivityTab3_Setting extends Activity {
	
//	private static final String HARU_LOG = "HARU_LOG";

	public static final int REQUEST_LIVE = 100;

	ArrayList<tab3_customList_Holder> tab3_customList;
	private tab3_customList_Adapter tab3_adapter;
	
	// tab3�� �� ��ü ����Ʈ��.
	private ListView tab3_listView; // ��1�� ��ü ����Ʈ

	private ListView liveList; // ��� �� �޾ƿ���.

	private String str_live = "";

	Intent intent = null;
	private DefaultLiveManager liveManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tab3_config);

		liveManager = new DefaultLiveManager(getApplicationContext());
		
		str_live = liveManager.readLive();

		tab3_listView = (ListView) findViewById(R.id.tab3_listView);

		tab3_customList = new ArrayList<tab3_customList_Holder>();

		tab3_customList.add(new tab3_customList_Holder("��������","������Ʈ ���� �� ���� ���� �˸�"));

		tab3_customList.add(new tab3_customList_Holder("���� ����","�ֽ� ���� ������Ʈ �� ������� Ȯ��"));

		tab3_customList.add(new tab3_customList_Holder("�Ϸ�˶� ���̽��� �ٷΰ���","�Ϸ�˶� ���̽����� ���� �ǰ� ������"));

		tab3_customList.add(new tab3_customList_Holder("���� ����", str_live));

		tab3_adapter = new tab3_customList_Adapter(ActivityTab3_Setting.this,R.layout.view_tab3_custom_list_layout, tab3_customList);

		tab3_listView.setAdapter(tab3_adapter);

		// ����Ʈ�� ���.
		tab3_listView.setOnItemClickListener(new OnItemClickListener() {
			// �������� ��� �ش� ��°�� TextView�� ǥ���ϴ� ��.
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long view) {

				switch (position) {
				case 0:
					intent = new Intent(getApplicationContext(),
							ActivityTab3_GongJi.class);

					startActivity(intent);
					break;

				case 1:
					intent = new Intent(getApplicationContext(),
							ActivityTab3_Version.class);

					startActivity(intent);
					break;

				case 2:
					intent = new Intent(getApplicationContext(),
							ActivityTab3_Facebook.class);

					startActivity(intent);
					break;

				case 3:
					showDialog(position);

					break;
				}
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(final int id) {
		/** showDialog(id)�� ȣ��Ǹ� ����Ǵ� �Լ�. */

		LayoutInflater mInflater; // ���÷�����.

		switch (id) {

		// ����.
		case 3:
			mInflater = LayoutInflater.from(this);
			final View view_live = mInflater.inflate(R.layout.activity_tab3_live_list, null);
			liveList = (ListView) view_live.findViewById(R.id.common_list);

			ArrayAdapter<CharSequence> adapter_live = ArrayAdapter
					.createFromResource(getApplicationContext(),
							R.array.first_live_array,
							android.R.layout.simple_list_item_1);

			liveList.setAdapter(adapter_live);

			// �� �̰��� ����.
			liveList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View view,
						int position, long list_id) {

					Intent intent = null;

					if (position == 2 || position == 5 || position == 6
							|| position == 7 || position == 8 || position == 9
							|| position == 10 || position == 13) {

						str_live = liveList.getItemAtPosition(position).toString();
						
						/*1�����*/
//						Toast.makeText(ActivityTab3_Setting.this, str_live,Toast.LENGTH_SHORT).show();

						liveManager.writeLive(str_live);

						tab3_customList_Holder tch = tab3_customList.get(id);

						tch.tab3_list_item_down = str_live;

						tab3_adapter.notifyDataSetChanged();

					} else if (position == 0) {
						intent = new Intent(getApplicationContext(),
								ActivityTab3_LiveSelect.class);
						intent.putExtra("live_select", 0);
						intent.putExtra("position", 0);

						startActivityForResult(intent, REQUEST_LIVE);
					} else if (position == 1) {

						intent = new Intent(getApplicationContext(),
								ActivityTab3_LiveSelect.class);
						intent.putExtra("live_select", 1);

						startActivityForResult(intent, REQUEST_LIVE);
					} else if (position == 3) {
						intent = new Intent(getApplicationContext(),
								ActivityTab3_LiveSelect.class);
						intent.putExtra("live_select", 3);

						startActivityForResult(intent, REQUEST_LIVE);
					} else if (position == 4) {
						intent = new Intent(getApplicationContext(),
								ActivityTab3_LiveSelect.class);
						intent.putExtra("live_select", 4);

						startActivityForResult(intent, REQUEST_LIVE);
					} else if (position == 11) {
						intent = new Intent(getApplicationContext(),
								ActivityTab3_LiveSelect.class);
						intent.putExtra("live_select", 11);

						startActivityForResult(intent, REQUEST_LIVE);
					} else if (position == 12) {
						intent = new Intent(getApplicationContext(),
								ActivityTab3_LiveSelect.class);
						intent.putExtra("live_select", 12);

						startActivityForResult(intent, REQUEST_LIVE);
					} else if (position == 14) {
						intent = new Intent(getApplicationContext(),
								ActivityTab3_LiveSelect.class);
						intent.putExtra("live_select", 14);

						startActivityForResult(intent, REQUEST_LIVE);
					} else if (position == 15) {
						intent = new Intent(getApplicationContext(),
								ActivityTab3_LiveSelect.class);
						intent.putExtra("live_select", 15);

						startActivityForResult(intent, REQUEST_LIVE);
					}
					dismissDialog(id);
				}
			});

			return new AlertDialog.Builder(ActivityTab3_Setting.this)
					.setTitle("��� �� ����").setView(view_live).create();
			// ���� ��.
		}

		return null;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) { 
			if (requestCode == REQUEST_LIVE) {

				int position = 3;
				
				str_live = data.getStringExtra("LiveName");
				
				tab3_customList_Holder tch = tab3_customList.get(position);
				
				tch.tab3_list_item_down = str_live;
				
				liveManager.writeLive(str_live);

				tab3_adapter.notifyDataSetChanged();
			} else {

			}
		}
	}
	public boolean onKeyDown(int KeyCode, KeyEvent event) {
		switch (KeyCode) {
		case KeyEvent.KEYCODE_BACK:
			String alertTitle = "�Ϸ�˶�";
			String buttonMessage = "�Ϸ�˶��� �����Ͻðڽ��ϱ�?";
			String buttonYes = "��";
			String buttonNo = "�ƴϿ�";

			new AlertDialog.Builder(ActivityTab3_Setting.this
					)
					.setTitle(alertTitle)
					.setMessage(buttonMessage)
					.setPositiveButton(buttonYes,
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									moveTaskToBack(true);
									finish();
								}
							}).setNegativeButton(buttonNo, null).show();
		}
		return true;
	}
}


