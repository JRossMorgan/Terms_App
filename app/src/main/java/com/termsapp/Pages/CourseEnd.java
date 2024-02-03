package com.termsapp.Pages;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.termsapp.R;

public class CourseEnd extends BroadcastReceiver {
    String channelId = "EndCourse";
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getStringExtra("End Course"), Toast.LENGTH_LONG).show();
        createNotificationChannel(context, channelId);
        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(intent.getStringExtra("End Course"))
                .setContentTitle("Course Ending").build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(++MainActivity.alertIds, notification);
    }
    private void createNotificationChannel(Context context, String StartChannel){
        CharSequence name = "Course End";
        String description = "Course End Notification.";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(StartChannel, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}