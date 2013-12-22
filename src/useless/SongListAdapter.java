package useless;

import java.util.List;

import listener_adapter.GetMusicInfo;
import listener_adapter.MusicInformation;

import org.hustunique.silence.R;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SongListAdapter extends BaseAdapter{

	public ContentResolver resolver;
	public Cursor cursor;
	public MusicInformation mi;
	public static List<MusicInformation> list;
	public Context context;
	
	public SongListAdapter(Context context) {
		this.context = context;
		GetMusicInfo getInfo = new GetMusicInfo(context);
		list = getInfo.getMusicList();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(context);
		convertView = inflater.inflate(R.layout.list_item, null);
		
		TextView song = (TextView) convertView.findViewById(R.id.tv_song);
		TextView singer = (TextView) convertView.findViewById(R.id.tv_singer);
		
		song.setText(position + ". " +list.get(position).getName());
		singer.setText(list.get(position).getSinger());
		return convertView;
	}

}
