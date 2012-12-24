package com.garamsoft.bubble.service;

import java.io.IOException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Vibrator;
import android.util.Log;

import com.garamsoft.bubble.R;
import com.garamsoft.bubble.alarmdata.SingleAlarmListItemInfo;
import com.garamsoft.bubble.manager.AlarmDataManager;

public class ServiceWarning extends Service {
	public static final int DEFALUT_REQUEST_CODE = -1;

	private MediaPlayer mPlayer = null;

	private AudioManager soundManager = null;

	private SingleAlarmListItemInfo mSingleAlarmListItemInfo;

	private AlarmDataManager alarmDataManager;

	private Vibrator mVibrator;

	private IBinder mBinder = new WanningServiceAIDL.Stub() {

		@Override
		public void stopSoundAndVibrate() throws RemoteException {
			Log.i("HARU_LOG", "WanningService: stopSoundAndVibrate");
			stopWarnning();
		}

		@Override
		public void startSoundAndVibrate() throws RemoteException {
			Log.i("HARU_LOG", "WanningService: startSoundAndVibrate");
			startWarning();
		}

	};

	@Override
	public IBinder onBind(Intent intent) {
		Log.i("HARU_LOG", "WanningService: onBind");
		alarmDataManager = AlarmDataManager.getInstance(getApplicationContext());
		int requestCode = intent.getIntExtra("requestCode", -1);
		mSingleAlarmListItemInfo = alarmDataManager.getSingleAlarmListItemInfoAtReqCode(requestCode);
		return mBinder;
	}

	@Override
	public void onDestroy() {
		Log.i("HARU_LOG", "WanningService: onDestroy");
		super.onDestroy();
	}
	/**�Ҹ����� ���� �Լ�*/
	private void stopWarnning() {
		if (mSingleAlarmListItemInfo.alarmType.vibration == 1) {
			if (mVibrator != null)
				mVibrator.cancel();
			mVibrator=null;
		}

		if (mSingleAlarmListItemInfo.alarmType.Wave == 1) {
			if (mPlayer != null)
				mPlayer.stop();
			mPlayer = null;
		}
	}
	
	/**�Ҹ����� Ű�� �Լ�*/
	private void startWarning() {
		if (mSingleAlarmListItemInfo != null) {
			if (mSingleAlarmListItemInfo.alarmType.vibration == 1) {
				if (mVibrator == null)
					mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				long pattern[] = { 100, 200, 100, 200 }; // Ȧ���� �︱�ð� ¦���� ���ð�.
				mVibrator.vibrate(pattern, 0);
			}

			// ���尡 ���� �ִٸ�.
			if (mSingleAlarmListItemInfo.alarmType.Wave == 1) {
				mp3play();
			}
		}
	}
	
	void mp3play() {
		if(mPlayer==null)
		mPlayer = new MediaPlayer();

		// ���� ũ�� ����. ( AudioManager�̿� )
		if(soundManager==null)
		soundManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		
		soundManager.setStreamVolume(AudioManager.STREAM_MUSIC,(int) (mSingleAlarmListItemInfo.soundvolumn.volumn_size * 0.14) + 1,AudioManager.FLAG_PLAY_SOUND);

		try {
			if(!mSingleAlarmListItemInfo.song.path.equals("")){
			mPlayer.setDataSource(mSingleAlarmListItemInfo.song.path);
			mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);}
			else {
				mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.default_alarm);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // ������ ����� ���� ���.
		
		mPlayer.setLooping(true); // ���� �ݺ�.

		try {
			mPlayer.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		mPlayer.start();
	}
}
