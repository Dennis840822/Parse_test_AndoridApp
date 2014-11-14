package com.example.parse_test;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;

import android.widget.Toast;


public class Receiver extends BroadcastReceiver 
{  
  
    @Override  
    public void onReceive(Context context, Intent intent) 
    {  
    	try {
            String action = intent.getAction();
            String channel = intent.getExtras().getString("com.parse.Channel");
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            String title = "New alert!";
            if (json.has("header"))
                title = json.getString("header");
            //generateNotification(context, title);
        } catch (Exception e) 
        {
            //Log.d(TAG, "JSONException: " + e.getMessage());
        }
    }  
    /*
    public static void generateNotification(Context context,  String message) {
        // Show the notification
        long when = System.currentTimeMillis();
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification( 0, message, when);
        String title = context.getString(R.string.app_name);
        Intent notificationIntent = new Intent(context, MainActivity.class);

        // set intent so it does not start a new activity
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, title, message, intent);
        notification.vibrate = new long[] { 500, 500 };
        notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        notification.flags = 
            Notification.FLAG_AUTO_CANCEL | 
            Notification.FLAG_SHOW_LIGHTS;

        notificationManager.notify(0, notification);
    }*/
}  