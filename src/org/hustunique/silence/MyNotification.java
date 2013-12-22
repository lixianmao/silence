package org.hustunique.silence;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

public class MyNotification {
	public Context context;
	public static final int NOTIFICATION_ID = 0;

	public MyNotification(Context context) {
		this.context = context;
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context);

		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ic_launcher);
		builder.setLargeIcon(bmp);
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setContentTitle("silence");

		Intent intent = new Intent(context, PlayerActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		
		int requestCode = 0;
		int flags = PendingIntent.FLAG_CANCEL_CURRENT;
		PendingIntent pi = PendingIntent.getActivity(context, requestCode,
				intent, flags);
		
		builder.setContentIntent(pi);

		Notification n = builder.build();
		n.flags = Notification.FLAG_ONGOING_EVENT;
		nm.notify(NOTIFICATION_ID, n);
	}
}
