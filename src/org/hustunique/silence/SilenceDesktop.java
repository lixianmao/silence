package org.hustunique.silence;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class SilenceDesktop extends AppWidgetProvider {

	private Intent musicIntent;
	private PendingIntent pi;
	private RemoteViews views;
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		
		views = new RemoteViews(context.getPackageName(),
				R.layout.silence_widget);
		musicIntent = new Intent("widget_next");
		pi = PendingIntent.getBroadcast(context, 0, musicIntent, 0);
		views.setOnClickPendingIntent(R.id.widget_play_next, pi);
		appWidgetManager.updateAppWidget(R.id.widget_play_next, views);
		
		musicIntent = new Intent("widget_pre");
		pi = PendingIntent.getBroadcast(context, 0, musicIntent, 0);
		views.setOnClickPendingIntent(R.id.widget_play_pre, pi);
		appWidgetManager.updateAppWidget(R.id.widget_play_pre, views);
		Log.e("widget", "update");
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.e("widget", "receive1");
		Log.e("widget", "receive2");
		Intent musicIntent = new Intent();
		
		if (intent.getAction().equals("widget_next")) {
			musicIntent.setAction(PlayerActivity.ACTION_NEXT);
			context.startService(musicIntent);
		}
		if (intent.getAction().equals("widget_pre")) {
			musicIntent.setAction(PlayerActivity.ACTION_PRE);
			context.startService(musicIntent);
		}
		super.onReceive(context, intent);
	}
}
