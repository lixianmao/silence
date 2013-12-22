package org.hustunique.silence;

import listener_adapter.ListAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SongList extends Fragment{

	public ListView songList;
	public ListAdapter listAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.song_list, container, false);
		return view;
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		songList = (ListView) getView().findViewById(R.id.lv_songlist);
		listAdapter = new ListAdapter(getActivity());
        songList.setAdapter(listAdapter);
        
        songList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), PlayerActivity.class);
				PlayerActivity.mId = position;		//静态变量赋值，待播放歌曲的id
				startActivity(intent);
			}
		});
	}
}
