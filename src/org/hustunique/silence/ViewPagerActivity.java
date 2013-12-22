package org.hustunique.silence;

import listener_adapter.MyPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPagerActivity extends FragmentActivity {

	private ViewPager viewPager;
	private TextView song, singer, album, favorite;
	private static int countBack = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_pager);

		initViewPager();
		initTextView();
	}

	private void initViewPager() {
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initTextView() {
		song = (TextView) findViewById(R.id.tv_song);
		singer = (TextView) findViewById(R.id.tv_singer);
		album = (TextView) findViewById(R.id.tv_album);
		favorite = (TextView) findViewById(R.id.tv_favorite);

		song.setOnClickListener(new mOnClickListener(0));
		singer.setOnClickListener(new mOnClickListener(1));
		album.setOnClickListener(new mOnClickListener(2));
		favorite.setOnClickListener(new mOnClickListener(3));
	}

	public class mOnClickListener implements OnClickListener {
		private int index = 0;

		public mOnClickListener(int index) {
			this.index = index;
		}

		public void onClick(View v) {
			viewPager.setCurrentItem(index);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_pager, menu);
		return true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		countBack = 0; // 按back键次数设置为0
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		switch (countBack) {
		case 0:
			Toast.makeText(this, "再按一次返回到桌面", Toast.LENGTH_SHORT).show();
			countBack++;
			break;
		default:
			moveTaskToBack(true);		//回到桌面，程序退到后台
			break;
		}
	}

}
