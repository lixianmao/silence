package useless;

import org.hustunique.silence.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class FavoriteActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.favorite);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.favorite, menu);
		return true;
	}

}
