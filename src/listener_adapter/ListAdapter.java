package listener_adapter;

import java.util.List;

import org.hustunique.silence.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter{

	public static List<MusicInformation> list;
	public Context context;
	
	public ListAdapter(Context context) {
		this.context = context;
		GetMusicInfo getMusicInfo = new GetMusicInfo(context);
		list = getMusicInfo.getMusicList();
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
		
		song.setText(position + 1 + ". " + list.get(position).getName());
		singer.setText(list.get(position).getSinger());
		
		return convertView;
	}
	
}
