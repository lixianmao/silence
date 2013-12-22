package org.hustunique.silence;

import java.util.ArrayList;
import java.util.List;

import listener_adapter.GetMusicInfo;
import listener_adapter.LyricContent;
import listener_adapter.LyricView;
import listener_adapter.PlayerEngine;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerActivity extends Activity {

	public static final int STATE_PAUSED = 1;
	public static final int STATE_PLAY = 2;
	public static final int MODE_LOOP = 1;
	public static final int MODE_RANDOM = 2;
	public static final int MODE_SEQUENCE = 0;

	public static final String ACTION_PLAY = "play"; // 定义全局状态常量
	public static final String ACTION_PAUSE = "pause";
	public static final String ACTION_STOP = "stop";
	public static final String ACTION_SEEKTO = "seekto";
	public static final String ACTION_NEXT = "next";
	public static final String ACTION_PRE = "pre";

	private ImageButton stateButton;
	private ImageButton preButton;
	private ImageButton nextButton;
	private ImageButton modeButton;
	private SeekBar seekBar;
	private TextView currentTime;
	private TextView totalTime;
	private TextView songView;
	private TextView singerView;
	private BroadcastReceiver receiver;

	private int currentState = 2;
	public static int currentMode = 0;
	public static int mId = 0; // 定义被选中项id为全局变量

	private LyricView lyricView;
	private int index = 0;
	private List<LyricContent> lrcList = new ArrayList<LyricContent>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);

		stateButton = (ImageButton) findViewById(R.id.play_state);
		preButton = (ImageButton) findViewById(R.id.play_pre);
		nextButton = (ImageButton) findViewById(R.id.play_next);
		modeButton = (ImageButton) findViewById(R.id.play_mode);
		seekBar = (SeekBar) findViewById(R.id.seekbar);
		currentTime = (TextView) findViewById(R.id.tv_current_time);
		totalTime = (TextView) findViewById(R.id.tv_total_time);
		songView = (TextView) findViewById(R.id.tv_play_song);
		singerView = (TextView) findViewById(R.id.tv_play_singer);
		lyricView = (LyricView) findViewById(R.id.lyricView);

		stateButton.setOnClickListener(stateOnClickListener);
		seekBar.setOnSeekBarChangeListener(changeListener);
		modeButton.setOnClickListener(modeOnClickListener);
		preButton.setOnClickListener(shiftOnClickListener);
		nextButton.setOnClickListener(shiftOnClickListener);

		stop(); // 如果之前有音乐播放，先关闭再MediaPlayer再重新开启
		play();
	}

	private OnClickListener stateOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (currentState) {
			case STATE_PAUSED:
				play();
				break;
			case STATE_PLAY:
				pause();
				break;
			default:
				break;
			}
		}
	};

	private OnClickListener modeOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (currentMode) {
			case MODE_SEQUENCE:
				modeButton.setBackgroundResource(R.drawable.random);
				currentMode = MODE_RANDOM;
				Toast.makeText(getApplicationContext(), "随机播放",
						Toast.LENGTH_SHORT).show();
				break;
			case MODE_RANDOM:
				modeButton.setBackgroundResource(R.drawable.loop);
				currentMode = MODE_LOOP;
				Toast.makeText(getApplicationContext(), "单曲循环",
						Toast.LENGTH_SHORT).show();
				break;
			case MODE_LOOP:
				modeButton.setBackgroundResource(R.drawable.sequence);
				currentMode = MODE_SEQUENCE;
				Toast.makeText(getApplicationContext(), "顺序播放",
						Toast.LENGTH_SHORT).show();
			default:
				break;
			}
		}
	};
	private OnClickListener shiftOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.play_next:
				next();
				play();
				break;
			case R.id.play_pre:
				pre();
				play();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 播放状态执行的动作
	 */
	private void play() {
		currentState = STATE_PLAY;
		stateButton.setBackgroundResource(R.drawable.pause);

		Intent intent = new Intent();
		intent.setClass(PlayerActivity.this, PlayerService.class);
		intent.setAction(ACTION_PLAY);
		startService(intent);
	}

	/**
	 * 暂停状态执行的动作
	 */
	private void pause() {
		currentState = STATE_PAUSED;
		stateButton.setBackgroundResource(R.drawable.play);

		Intent intent = new Intent();
		intent.setClass(PlayerActivity.this, PlayerService.class);
		intent.setAction(ACTION_PAUSE);
		startService(intent);
	}

	/**
	 * 停止之前的播放
	 */
	private void stop() {
		Intent intent = new Intent();
		intent.setClass(PlayerActivity.this, PlayerService.class);
		intent.setAction(ACTION_STOP);
		startService(intent);
	}

	/**
	 * 向service传送播放下一首指令
	 */
	private void next() {
		Intent intent = new Intent();
		intent.setClass(PlayerActivity.this, PlayerService.class);
		intent.setAction(ACTION_NEXT);
		startService(intent);
	}

	/**
	 * 向service发送播放上一首指令
	 */
	private void pre() {
		Intent intent = new Intent();
		intent.setClass(PlayerActivity.this, PlayerService.class);
		intent.setAction(ACTION_PRE);
		startService(intent);
	}

	private OnSeekBarChangeListener changeListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			intent.setClass(PlayerActivity.this, PlayerService.class);
			intent.putExtra(PlayerService.CURRENT, seekBar.getProgress());
			intent.setAction(ACTION_SEEKTO);
			startService(intent);
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub
		}
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		IntentFilter filter = new IntentFilter();
		filter.addAction(PlayerService.ACTION_REFRESH); // 匹配由广播传送的intent
		if (receiver == null) {
			receiver = new BroadcastReceiver() {

				@Override
				public void onReceive(Context context, Intent intent) {
					// TODO Auto-generated method stub
					int progress = intent
							.getIntExtra(PlayerService.CURRENT, -1);
					int max = intent.getIntExtra(PlayerService.DURATION, -1);
					String song = intent.getStringExtra(PlayerService.SONG);
					String singer = intent.getStringExtra(PlayerService.SINGER);
					seekBar.setProgress(progress);
					seekBar.setMax(max);

					songView.setText(song);
					singerView.setText(singer);
					currentTime.setText(GetMusicInfo.toTime(progress));
					totalTime.setText(GetMusicInfo.toTime(max));
				}
			};
		}
		registerReceiver(receiver, filter);

		handler.post(showLRC);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		unregisterReceiver(receiver);
	}

	Handler handler = new Handler();
	Runnable showLRC = new Runnable() {
		public void run() {
			lrcList = PlayerEngine.lrcList;
			lyricView.setSentenceEntities(lrcList);
			lyricView.SetIndex(Index());
			lyricView.invalidate();
			handler.postDelayed(showLRC, 100);

		}
	};

	public int Index() {
		int currentTime = PlayerService.playerEngine.getCurrentPosition();

		for (int i = 0; i < lrcList.size(); i++) {
			if (i < lrcList.size() - 1) {
				if (currentTime < lrcList.get(i).getLyricTime() && i == 0) {
					index = i;
				}
				if (currentTime > lrcList.get(i).getLyricTime()
						&& currentTime < lrcList.get(i + 1).getLyricTime()) {
					index = i;
				}
			}
			if (i == lrcList.size() - 1
					&& currentTime > lrcList.get(i).getLyricTime()) {
				index = i;
			}
		}

		return index;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(PlayerActivity.this, ViewPagerActivity.class);
		startActivity(intent);
	}
}
