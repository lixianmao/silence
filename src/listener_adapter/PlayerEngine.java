package listener_adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hustunique.silence.Load;
import org.hustunique.silence.Load.Fuck;
import org.hustunique.silence.PlayerActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;

public class PlayerEngine {

	private MediaPlayer mediaPlayer;
	private Context context;
	private int musicId;
	private ArrayList<MusicInformation> list;
	private GetMusicInfo getMusicInfo;
	
	private LrcRead lrcRead;
	public static List<LyricContent> lrcList = new ArrayList<LyricContent>();
	
	public PlayerEngine(Context context, int musicId) {
		this.context = context;
		this.musicId = musicId;
		getMusicInfo = new GetMusicInfo(context);
		list = getMusicInfo.getMusicList();
	}

	/**
	 * 根据文件路径创建并初始化一个MediaPlayer对象
	 * 
	 * @param path
	 */
	public void initMediaSource(String path) {
		Uri uri = Uri.parse(path);
		if (mediaPlayer != null) { // 对象不为空时先切换到stopped状态再恢复到Idle状态，最后置空
			mediaPlayer.stop();
			mediaPlayer.reset();
			mediaPlayer = null;
		}
		mediaPlayer = MediaPlayer.create(context, uri); // 调用create方法后随即处于prepared状态
		mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				next();
				play();
			}
		});
	}

	/**
	 * 根据id返回音乐文件的路径
	 * 
	 * @param id
	 * @return
	 */
	public String initMusicUri(int id) {
		musicId = id;
		if (list != null && list.size() != 0) {
			String path = list.get(musicId).getPath();

			return path;
		} else { // 列表不存在或列表数据为空时返回空字符串
			return "";
		}
	}

	public void play() {
		if (mediaPlayer == null) { // 对象为空时初始化一个MediaPlayer对象
			initMediaSource(initMusicUri(PlayerActivity.mId)); // 默认打开文件为列表中被点击的项
		}
		mediaPlayer.start();
		
		String string  = this.getSong();
		String songName = string.substring(0, string.lastIndexOf("_"));
		String singerName = string.substring(string.indexOf("_") + 1);
		lrcRead = new LrcRead();
		Load load = new Load(lrcRead, lrcList, songName, singerName,new Fuck() {
			
			@Override
			public void onReceive(List<LyricContent> list) {
				// TODO Auto-generated method stub
				lrcList = list;
			}
		});
		load.execute();
	}

	public void pause() {
		if (mediaPlayer != null) {
			mediaPlayer.pause();
		}
	}

	public void stop() {
		if (mediaPlayer != null) {
			mediaPlayer.stop();
		}
	}

	public void release() {
		if (mediaPlayer != null) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	public int getDuration() {
		if (mediaPlayer != null) {
			return mediaPlayer.getDuration();
		} else {
			return -1;
		}
	}

	public int getCurrentPosition() { // 此处返回的是以毫秒为单位的时间
		if (mediaPlayer != null) {
			return mediaPlayer.getCurrentPosition();
		}
		return -1;
	}

	public void seekTo(int msec) {
		if (mediaPlayer != null) {
			mediaPlayer.seekTo(msec);
		}
	}

	public String getSong() {
		return list.get(musicId).getName();
	}

	public String getSinger() {
		return list.get(musicId).getSinger();
	}
	/**
	 * 歌曲移位到下一曲
	 */
	public void next() {
		switch (PlayerActivity.currentMode) {
		case PlayerActivity.MODE_RANDOM:
			Random random = new Random();
			PlayerActivity.mId = random.nextInt(list.size());
			break;
		case PlayerActivity.MODE_SEQUENCE:
			if (PlayerActivity.mId == list.size() - 1) {
				PlayerActivity.mId = 0;
			} else {
				PlayerActivity.mId++;
			}
			break;
		case PlayerActivity.MODE_LOOP:
			break;
		default:
			break;
		}
		stop();
		release();
	}
	/**
	 * 歌曲移位到上一曲
	 */
	public void pre() {
		switch (PlayerActivity.currentMode) {
		case PlayerActivity.MODE_RANDOM:
			Random random = new Random();
			PlayerActivity.mId = random.nextInt(list.size());
			break;
		case PlayerActivity.MODE_SEQUENCE:
			if (PlayerActivity.mId == 0) {
				PlayerActivity.mId = list.size() - 1;
			} else {
				PlayerActivity.mId--;
			}
			break;
		case PlayerActivity.MODE_LOOP:
			break;
		default:
			break;
		}
		stop();
		release();
	}
}
