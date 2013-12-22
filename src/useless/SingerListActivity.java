package useless;

import org.hustunique.silence.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class SingerListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.singer_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.singer_list, menu);
		return true;
	}

}
