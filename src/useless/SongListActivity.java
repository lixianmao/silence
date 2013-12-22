package useless;

import listener_adapter.ListAdapter;

import org.hustunique.silence.PlayerActivity;
import org.hustunique.silence.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SongListActivity extends Activity {

	public ListView songList;
	public ListAdapter listAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);
        
        songList = (ListView) findViewById(R.id.lv_songlist);
        listAdapter = new ListAdapter(this);
        songList.setAdapter(listAdapter);
        
        songList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SongListActivity.this, PlayerActivity.class);
				intent.putExtra("itemId", position);
				startActivity(intent);
			}
        	
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.song_list, menu);
        return true;
    }
    
}
