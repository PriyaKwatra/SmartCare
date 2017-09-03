package com.example.acer.finder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by acer on 3.09.2017.
 */

public class TaskReceiver  extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i=new Intent(context,NotificationActivity.class);
        i.putExtra("Pending",intent.getStringExtra("HEALTH"));
        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,i,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager nm=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle(intent.getStringExtra("HEALTH"))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("today's  health tip")
                .setContentIntent(pendingIntent)
                .build();
        Log.e("tag","hii");
        nm.notify(100,notification);

    }
}

