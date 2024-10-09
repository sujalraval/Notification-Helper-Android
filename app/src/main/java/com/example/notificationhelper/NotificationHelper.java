package com.example.notificationhelper;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

public class NotificationHelper {
    private static final String CHANNEL_ID = "test";
    private Context notiContext;
    private NotificationManager notificationManager;


    public NotificationHelper(Context context){
        this.notiContext = context;
        this.notificationManager = context.getSystemService(NotificationManager.class);
    }

    public void createNotificationChannel(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"Channel Name",NotificationManager.IMPORTANCE_HIGH);
            if(notificationManager!=null){
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public void ShowNotification(int notificationId){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(notiContext,CHANNEL_ID)
                .setContentTitle("Notification Demo")
                .setContentText("this notification is for testing purpose")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.baseline_notifications_24);

        if(notificationManager != null){
            notificationManager.notify(notificationId,builder.build());
        }
    }

    public void requestNotificationPermission(ActivityResultLauncher activityResultLauncher){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ActivityCompat.checkSelfPermission(notiContext,android.Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED) {
            activityResultLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);
        } else{
            ShowNotification(1);
        }
    }

    }

