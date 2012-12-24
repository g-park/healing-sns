package com.garamsoft.bubble.activity.tab1;

import java.util.ArrayList;
import java.util.List;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.garamsoft.bubble.alarmdata.MP3Item;
import com.garamsoft.bubble.alarmdata.RandomAlarmSelector;

public class SongSelect extends ListActivity{
	
	public static MediaPlayer mc = null;
	private ArrayList<MP3Item> songs;
	
	public String song_name;
	public String song_path;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    
		songs = new ArrayList<MP3Item>();
		List<String> songArray = getSongs();

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, songArray);
		setListAdapter(adapter);

		RandomAlarmSelector.getSingleton().setMP3InfoList(songs);
	}

	public List<String> getSongs() {
		List<String> list = new ArrayList<String>();
		String[] cursorColumns = new String[] { BaseColumns._ID,
				MediaColumns.DISPLAY_NAME,
				MediaColumns.DATA };
		Cursor cursor = getContentResolver().query(
				MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, cursorColumns,
				null, null, null);

		if (cursor == null) {
			return list;
		}
		if (cursor.moveToFirst()) {
			int songColumn = cursor
					.getColumnIndex(MediaColumns.DISPLAY_NAME);

			// MediaStore.Audio.Media.DATA 가 음악파일의 경로를 알아오는 곳.
			int songDataColumn = cursor
					.getColumnIndex(MediaColumns.DATA);
			do {
				song_name = cursor.getString(songColumn);
				song_path = cursor.getString(songDataColumn);
				list.add(song_name);
				songs.add(new MP3Item(song_name, song_path));
			} while (cursor.moveToNext());
		}
		cursor.close();
		return list;
	}
	
	// 여기에서 intent로 노래값 보내줄 것.

	// 리스트를 눌렀을때 반응할 리스너.
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
			MP3Item songItem = songs.get(position); // 해당 위치의 파일명을 받아옴.
			
			song_name = songItem.name;
			song_path = songItem.getPath();
			/*1차출시*/
//			Toast.makeText(getApplicationContext(), song_path, 0).show();

			// 현재 날짜에 맞는 song item을 건네 준다.
			// MP3Item songItem =
			// RandomAlarmSelector.getSingleton().getTodaySong();

			// 노래 값을 ActivityAddListItem2로 넘겨줌.
			Intent intent = getIntent();
			intent.putExtra("SongName",song_name);
			intent.putExtra("PathName",song_path);
			setResult(RESULT_OK,intent);
			
			finish();
	}
}
