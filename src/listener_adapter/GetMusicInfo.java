package listener_adapter;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class GetMusicInfo {
	public ContentResolver resolver;
	public Cursor cursor;
	public ArrayList<MusicInformation> list;
	public Context context;

	public GetMusicInfo(Context context) { // 构造器，在此获得ContentResolver
		this.context = context;
		resolver = context.getContentResolver();
	}

	public ArrayList<MusicInformation> getMusicList() {
		list = new ArrayList<MusicInformation>();
		String[] musicInfo = new String[] {
				MediaStore.Audio.Media.DISPLAY_NAME,
				MediaStore.Audio.Media.ALBUM, MediaStore.Audio.Media.ARTIST,
				MediaStore.Audio.Media.DURATION, MediaStore.Audio.Media._ID,
				MediaStore.Audio.Media.DATA };
		String isMusic = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
		cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
				musicInfo, isMusic, null, null);

		if (cursor.moveToFirst()) {
			for (int i = 0; i < cursor.getCount(); i++) {

				MusicInformation musicInformation = new MusicInformation();
				String name = cursor.getString(0);
				name = name.substring(0, name.lastIndexOf("."));
				musicInformation.setName(name);
				musicInformation.setAlbum(cursor.getString(1));
				musicInformation.setSinger(cursor.getString(2));
				musicInformation.setTime(toTime(cursor.getInt(3)));
				musicInformation.setId(cursor.getInt(4));
				musicInformation.setPath(cursor.getString(5));
				list.add(musicInformation);

				cursor.moveToNext();
			}
		}
		cursor.close();
		return list;
	}

	/**
	 * 歌曲时间格式化
	 * 
	 * @param time
	 * @return
	 */
	public static String toTime(int time) {

		time /= 1000;
		int minute = time / 60;
		int second = time % 60;
		minute %= 60;
		return String.format(" %02d:%02d ", minute, second);
	}

}
