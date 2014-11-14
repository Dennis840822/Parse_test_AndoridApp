package com.example.parse_test;

//import java.util.Date;

//import android.app.Application;
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;

import android.os.IBinder;
//import android.util.Log;

import com.parse.Parse;
import com.parse.ParseInstallation;
//import com.parse.ParsePush;
import com.parse.PushService;
//import com.parse.SaveCallback;

public class MainApplication extends Service
{
	private Handler my_handler;
	private HandlerThread my_handlerthread ;
	@Override
	public IBinder onBind(Intent intent) 
	{
	    return null;
	}
	
    public void onCreate() 
    {  
    	super.onCreate();
    	
    	
    }
	 
	@SuppressWarnings("deprecation")
	@Override
	public void onStart(Intent intent, int startId) 
	{
		super.onStart(intent, startId);
		
		Parse.initialize(this, "JDvr3ElOQ3pYXlBqT1twQHx3XvGmpO37XlROoThG", "ISLvJ6xMzRkcpZRi3i3aCNTbAflt9hDl5cERONGP");
        PushService.setDefaultPushCallback(getApplication(), MainActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        
        my_handlerthread = new HandlerThread("my");
    	my_handlerthread.start();
        my_handler = new Handler(my_handlerthread.getLooper());
        my_handler.post(r1);
        
        
//       NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//        
//        Intent notifyIntent = new Intent(getApplicationContext(),MainActivity.class);
//        notifyIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK);
//        PendingIntent appIntent=PendingIntent.getActivity(this,0,notifyIntent,0);
//        
//        notification.setLatestEventInfo(NotificationExample.this,"Title","content",appIntent);
//        
//        Notification notification = new Notification();
//        
//        notificationManager.notify();
        
	}
	public Runnable r1 = new Runnable()
	{
		public void run()
		{
			try 
			{
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	};
	
	@Override
	public void onDestroy() 
	{
	    super.onDestroy();
	    if(my_handler !=null)
	    	my_handler.removeCallbacks(r1);
	    if(my_handlerthread!=null)
	    	my_handlerthread.quit();
	}
	
	/*
    @SuppressWarnings("deprecation")
	@Override
    public void onCreate() 
    {
        super.onCreate();
        
        Parse.initialize(this, "JDvr3ElOQ3pYXlBqT1twQHx3XvGmpO37XlROoThG", "ISLvJ6xMzRkcpZRi3i3aCNTbAflt9hDl5cERONGP");
        
        PushService.setDefaultPushCallback(this, MainActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();
        
        //PushService.subscribe(this, "Barca", MainActivity.class);

        
        //Intent intent = new Intent(this, MainActivity.class);
        //ParseAnalytics.trackAppOpened(getIntent());
        
        ParsePush.subscribeInBackground("", new SaveCallback() 
        {
			@Override
			public void done(com.parse.ParseException e) 
			{
		   	    if (e == null) 
		   	    {
		     	      Log.d("parse_test", "successfully subscribed to the broadcast channel.");
		     	} 
		   	    else 
		   	    {
		     	      Log.e("parse_test", "failed to subscribe for push", e);
		     	}
			}

        });
       
         
    }*/
}