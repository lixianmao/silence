package org.hustunique.silence;

import java.util.Timer;
import java.util.TimerTask;

import listener_adapter.PlayerEngine;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class PlayerService extends Service {

	public static PlayerEngine playerEngine;
	private Timer timer;

	public static final String ACTION_REFRESH = "refresh";
	public static final String DURATION = "duration";
	public static final String CURRENT = "current";
	public static final String SONG = "song";
	public static final String SINGER = "singer";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		if (action.equals(PlayerActivity.ACTION_PLAY)) {
			playMusic();
		} else if (action.equals(PlayerActivity.ACTION_PAUSE)) {
			playerEngine.pause();
			if (timer != null) { // 歌曲暂停后关闭Timer类
				timer.cancel();
				timer = null;
			}
		} else if (action.equals(PlayerActivity.ACTION_SEEKTO)) {
			int msec = intent.getIntExtra(CURRENT, -1);
			playerEngine.seekTo(msec);
		} else if (action.equals(PlayerActivity.ACTION_STOP)) {
			playerEngine.stop();
			playerEngine.release();
			if (timer != null) {
				timer.cancel();
				timer = null;
			}
		} else if (action.equals(PlayerActivity.ACTION_NEXT)) {
			playerEngine.next();
			if (timer != null) {
				timer.cancel();
				timer = null;
			}
		} else if (action.equals(PlayerActivity.ACTION_PRE)) {
			playerEngine.pre();
			if (timer != null) {
				timer.cancel();
				timer = null;
			}
		}
		return super.onStartCommand(intent, flags, startId);

	}
	private void playMusic() {
		playerEngine.play();

		if (timer == null) { // 第一次创建时初始化TimerTask类
			TimerTask task = new TimerTask() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setAction(ACTION_REFRESH);
					intent.putExtra(CURRENT,
							playerEngine.getCurrentPosition());
					intent.putExtra(DURATION,
							playerEngine.getDuration());
					intent.putExtra(SONG,
							playerEngine.getSong());
					intent.putExtra(SINGER,
							playerEngine.getSinger());
					sendBroadcast(intent);
				}
			};
			timer = new Timer();
			timer.schedule(task, 0, 1000); // 无时间间隔执行task，间隔1秒发送广播
		}
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		playerEngine = new PlayerEngine(this, PlayerActivity.mId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		playerEngine.stop();
		playerEngine = null;
	}

}
