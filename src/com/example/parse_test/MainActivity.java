package com.example.parse_test;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.*;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Build;

import android.media.AudioManager;
import android.media.MediaPlayer;

public class MainActivity extends Activity 
{
	private EditText Text_name,Text_data,Text_id;
	private EditText Text_get1,Text_get2,Text_get3;
	private EditText Text_search;
	
	private Button Button_push,Button_display;
	private Button Button_serach1,Button_serach2;
	private Button Button_update,Button_delete;
	
	private ParseQuery<ParseObject> query;
	
	private TelephonyManager TM;
	private String model,app_ver,imei ;
	
	private MediaPlayer mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //init for parse
        Parse.initialize(this, "JDvr3ElOQ3pYXlBqT1twQHx3XvGmpO37XlROoThG", "ISLvJ6xMzRkcpZRi3i3aCNTbAflt9hDl5cERONGP");
        ParseUser.enableAutomaticUser();
        // ParseACL defaultACL = new ParseACL();
        //        
        model = Build.MODEL;
        app_ver = getString(R.string.version);
        TM=(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        imei = TM.getDeviceId();
        
        Text_name =(EditText)findViewById(R.id.text_input1);
        Text_data =(EditText)findViewById(R.id.text_input2);
        Text_id   =(EditText)findViewById(R.id.text_input3);
        
        Text_search=(EditText)findViewById(R.id.text_search);
        
        Text_get1  =(EditText)findViewById(R.id.text_output1);
        Text_get2 =(EditText)findViewById(R.id.text_output2);
        Text_get3 =(EditText)findViewById(R.id.text_output3);
        
        Button_push    =(Button)findViewById(R.id.button_push);
        Button_display =(Button)findViewById(R.id.button_display);
        
        Button_serach1  = (Button)findViewById(R.id.button_search1);
        Button_serach2 = (Button)findViewById(R.id.button_search2);
        
        Button_update = (Button)findViewById(R.id.button_update);
        Button_delete = (Button)findViewById(R.id.button_delete);
        
        Button_push   .setOnClickListener(push_function);
        Button_serach1.setOnClickListener(search1_function);
        Button_serach2.setOnClickListener(search2_function);
        Button_update .setOnClickListener(update_function);
        Button_delete .setOnClickListener(delete_function);
        Button_display.setOnClickListener(display_function);
           
        mPlayer = MediaPlayer.create(this, R.raw.sm23883589);
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mPlayer.setLooping(true);
        mPlayer.start();
        mPlayer.setVolume(0.8f, 0.8f);
        
        //mPlayer.pause();
        //mPlayer.stop();
        //mPlayer.release();   
    }
    
	private void DisplayList(List<ParseObject> sList)
	{
		String output_string1 = new String("");
		String output_string2 = new String("");
		String output_string3 = new String("");
		for(ParseObject temp : sList )
		{
			output_string1 += temp.getString("User_name")+"\n";
			output_string2 += temp.getString("String_1") + "\n";
			output_string3 += temp.getObjectId() + "\n";
		}
		Text_get1.setText(output_string1);
		Text_get2.setText(output_string2);
		Text_get3.setText(output_string3);
		Toast.makeText(this,"Totally get "+sList.size()+" data.", Toast.LENGTH_LONG).show();
	}
	private void DisplayObject(ParseObject obj)
	{
		Text_get1.setText(obj.getString("User_name"));
		Text_get2.setText(obj.getString("String_1"));
		Text_get3.setText(obj.getObjectId());
	}
    

	
	private Button.OnClickListener push_function = new Button.OnClickListener ()
	{
		public void onClick(View v) 
		{
			long startTime = System.currentTimeMillis();
			//------------------------------------------------------------------
			if(!Text_name.getText().toString().equals("") && !Text_data.getText().toString().equals(""))  // no null
			{
				ParseObject pushObject = new ParseObject("TestObject");
				pushObject.put("User_name", Text_name.getText().toString());
				pushObject.put("String_1", Text_data.getText().toString());
				pushObject.put("Phone_model",model);
				pushObject.put("APP_version",app_ver);
				pushObject.put("User_IMEI",imei);
				pushObject.saveInBackground();		
				
				DisplayObject(pushObject);
				Text_get3.setText(pushObject.getObjectId());
			}
			//------------------------------------------------------------------
			long totaltime = System.currentTimeMillis() - startTime;
			Toast.makeText(v.getContext(),"Push done for  "+totaltime+"(ms)", Toast.LENGTH_LONG).show();
		}
	
	};
	private Button.OnClickListener display_function = new Button.OnClickListener ()
	{
		public void onClick(View v)  
		{
			long startTime = System.currentTimeMillis();
			//----------------------------------------------------------------------------
			query = ParseQuery.getQuery("TestObject");
			//
			query.findInBackground(new FindCallback<ParseObject>()  //與線上同步資料
					{
						@Override
						public void done(List<ParseObject> sList, ParseException e)   //資料會存於list中
						{
							if (e == null) 	
								DisplayList(sList); 
						}
				
					});
			//------------------------------------------------------------------------------
			long totaltime = System.currentTimeMillis() - startTime;
			Toast.makeText(v.getContext(),"Display done for "+totaltime+"(ms)", Toast.LENGTH_LONG).show();
		}
	};	

	
	
	
	private Button.OnClickListener search1_function = new Button.OnClickListener ()
	{
		public void onClick(View v)
		{
			long startTime = System.currentTimeMillis();
			//-------------------------------------------------------------------------
			query = ParseQuery.getQuery("TestObject");
			//request   所引條件

			//query.whereEqualTo("User_name", Text_search.getText().toString());
			
			String[] KeyWord = Text_search.getText().toString().split(" ");
			query.whereContainedIn("User_name", Arrays.asList(KeyWord));
			
			//query.whereContains("User_name", Text_search.getText().toString());
						
			//query.whereContainsAll("User_name", Arrays.asList(KeyWord));
			
			query.findInBackground(new FindCallback<ParseObject>()  //與線上同步資料
					{
						@Override
						public void done(List<ParseObject> sList, ParseException e)   //資料會存於list中
						{
							if (e == null) 
								DisplayList(sList); 
						}
				
					});
			//-------------------------------------------------------------------------
			long totaltime = System.currentTimeMillis() - startTime;
			Toast.makeText(v.getContext(),"Search1 done for  "+totaltime+"(ms)", Toast.LENGTH_LONG).show();
				
		}
	
	};
	private Button.OnClickListener search2_function = new Button.OnClickListener ()
	{
		public void onClick(View v)
		{
			long startTime = System.currentTimeMillis();
			//-------------------------------------------------------------------------
			query = ParseQuery.getQuery("TestObject");
			//request   所引條件
			//query.whereEqualTo("String_1", Text_search.getText().toString());
			
			//String[] KeyWord = Text_search.getText().toString().split(" ");
			//query.whereContainedIn("String_1", Arrays.asList(KeyWord));
			
			query.whereContains("String_1", Text_search.getText().toString());
			
			//
			query.findInBackground(new FindCallback<ParseObject>()  //與線上同步資料
					{
						@Override
						public void done(List<ParseObject> sList, ParseException e)   //資料會存於list中
						{
							if (e == null) 
								DisplayList(sList); 
						}
				
					});
			//-------------------------------------------------------------------------
			long totaltime = System.currentTimeMillis() - startTime;
			Toast.makeText(v.getContext(),"Search2 done for "+totaltime+"(ms)", Toast.LENGTH_LONG).show();
				
		}
	
	};
	
	private Button.OnClickListener update_function = new Button.OnClickListener ()
	{
		public void onClick(View v)  
		{
			long startTime = System.currentTimeMillis();
			//-------------------------------------------------------------------------
			if(!Text_id.getText().toString().equals(""))
			{
				ParseQuery<ParseObject> query = ParseQuery.getQuery("TestObject");
				// Retrieve the object by id
				query.getInBackground(Text_id.getText().toString(), new GetCallback<ParseObject>() 
						{
							@Override
							public void done(ParseObject testObject, ParseException e) 
							{
								if (e == null) 
								{						
									if(!Text_data.getText().toString().equals(""))
									{
										testObject.put("String_1", Text_data.getText().toString());
										testObject.put("Phone_model",model);
										testObject.put("APP_version",app_ver);
										testObject.put("User_IMEI",imei);
										testObject.saveInBackground();
									}
								}
							}
						});
			}
			//-------------------------------------------------------------------------
			long totaltime = System.currentTimeMillis() - startTime;
			Toast.makeText(v.getContext(),"update done for  "+totaltime+"(ms)", Toast.LENGTH_LONG).show();
		
		}
	};
	
	private Button.OnClickListener delete_function = new Button.OnClickListener ()
	{
		public void onClick(View v)  
		{
			long startTime = System.currentTimeMillis();
			//----------------------------------------------------------------------------
			//ParseQuery<ParseObject> query = ParseQuery.getQuery("TestObject");
			//request   所引條件
			//query.whereEqualTo("User_name", Text_name.getText().toString());
			if(query != null)
			//
			query.findInBackground(new FindCallback<ParseObject>()  //與線上同步資料
					{
						@Override
						public void done(List<ParseObject> sList, ParseException e)   //資料會存於list中
						{
							if (e == null) 
							{	
								for(int i=0; i<sList.size(); i++)
								{
									sList.get(i).deleteInBackground();
								}
							} 
						}
				
					});
			//------------------------------------------------------------------------------
			long totaltime = System.currentTimeMillis() - startTime;
			Toast.makeText(v.getContext(),"Delete done for "+totaltime+"(ms)", Toast.LENGTH_LONG).show();
		}
	};
	
	@Override
    protected void onDestroy() {
       if(mPlayer != null)
    	   mPlayer.release();
       super.onDestroy();
    }
	

}
