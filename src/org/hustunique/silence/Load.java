package org.hustunique.silence;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import listener_adapter.LrcRead;
import listener_adapter.LyricContent;
import android.os.AsyncTask;

public class Load extends AsyncTask<Void, Integer, LrcRead>{

	String song, singer;
	LrcRead lrcRead;
	List<LyricContent> lrcList;
	private Fuck fuck;
	
	public Load(LrcRead lrcRead, List<LyricContent> lrcList,String song, String singer,Fuck fuck) {
		this.lrcRead = lrcRead;
		this.lrcList = lrcList;
		this.song = song;
		this.singer = singer;
		this.fuck = fuck;
	}
	@Override
	protected LrcRead doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try {
			lrcRead.read(song, singer);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lrcRead;
	}
	
	@Override
	protected void onPostExecute(LrcRead result) {
		// TODO Auto-generated method stub
		
		lrcList = lrcRead.getLyricContent();
		fuck.onReceive(lrcList);
	}
	
	public interface Fuck{
		public void onReceive(List<LyricContent> list);
	}
	
}
