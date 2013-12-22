package listener_adapter;

import java.util.ArrayList;
import java.util.List;

import org.hustunique.silence.AlbumList;
import org.hustunique.silence.FavorList;
import org.hustunique.silence.SingerList;
import org.hustunique.silence.SongList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {

	public List<Fragment> list;
	public Fragment songList, singerList, albumList, favorList;

	public MyPagerAdapter(FragmentManager fm) {
		super(fm);
		songList = new SongList();
		singerList = new SingerList();
		albumList = new AlbumList();
		favorList = new FavorList();
		
		list = new ArrayList<Fragment>();
		list.add(songList);
		list.add(singerList);
		list.add(albumList);
		list.add(favorList);
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return (list != null) ? list.size() : 0;
	}
}
