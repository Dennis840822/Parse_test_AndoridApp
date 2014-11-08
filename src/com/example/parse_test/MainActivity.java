package com.example.parse_test;

import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;



public class MainActivity extends Activity 
{
	
	private Button Button_push,Button_get,Button_get2;
	private Button Button_update,Button_delete,Button_display;
	private Button Button_clear;
	
	private EditText Text_name; 
	private EditText Text_push,Text_push2;
	private EditText Text_get,Text_get2,Text_get3;
	
	private TextView run_log;

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
        Text_name =(EditText)findViewById(R.id.text_name);
        
        Text_push =(EditText)findViewById(R.id.text_input);
        Text_push2 =(EditText)findViewById(R.id.text_input2);
        
        Text_get =(EditText)findViewById(R.id.text_output);
        Text_get2 =(EditText)findViewById(R.id.text_output2);
        Text_get3 =(EditText)findViewById(R.id.text_output3);
        
        Button_push =(Button)findViewById(R.id.button_input);
        Button_get = (Button)findViewById(R.id.button_output);
        Button_get2 = (Button)findViewById(R.id.button_output2);
        
        Button_update = (Button)findViewById(R.id.button_update);
        Button_delete = (Button)findViewById(R.id.button_delete);
        Button_display = (Button)findViewById(R.id.button_display);
        
        Button_clear = (Button)findViewById(R.id.button_clear);
        
        run_log =(TextView)findViewById(R.id.text_log);
        
        Button_push.setOnClickListener(push_function);
        Button_get.setOnClickListener(get_function);
        Button_get2.setOnClickListener(get2_function);
        Button_update.setOnClickListener(update_function);
        Button_delete.setOnClickListener(delete_function);
        Button_display.setOnClickListener(display_function);
        
        Button_clear.setOnClickListener(new Button.OnClickListener(){public void onClick(View v)
        	{
        		Text_push.setText("");
        		Text_push2.setText("");
        	}});
    }

	
	private Button.OnClickListener push_function = new Button.OnClickListener ()
	{
		public void onClick(View v)  //push User_name: text_name , String_1 : text_input
		{
			long startTime = System.currentTimeMillis();
			run_log.setText("Push");
			//------------------------------------------------------------------
			ParseObject testObject = new ParseObject("TestObject");
			if(!Text_name.getText().toString().equals("") && !Text_push.getText().toString().equals(""))  // no null
			{
				
				testObject.put("User_name", Text_name.getText().toString());
				testObject.put("String_1", Text_push.getText().toString());
				//testObject.put("Android_id",android_id);
				testObject.saveInBackground();				//push action
			}
			Text_push2.setText(testObject.getObjectId());
			//------------------------------------------------------------------
			long totaltime = System.currentTimeMillis() - startTime;
			run_log.setText("Push done for "+totaltime+"(ms)");
		}
	
	};
	
	private Button.OnClickListener get_function = new Button.OnClickListener ()
	{
		public void onClick(View v)
		{
			long startTime = System.currentTimeMillis();
			run_log.setText("Search1");
			//-------------------------------------------------------------------------
			ParseQuery<ParseObject> query = ParseQuery.getQuery("TestObject");
			
			//request   所引條件
			query.whereEqualTo("User_name", Text_name.getText().toString());
			//
			
			query.findInBackground(new FindCallback<ParseObject>()  //與線上同步資料
					{
						@Override
						public void done(List<ParseObject> sList, ParseException e)   //資料會存於list中
						{
							if (e == null) 
							{
								String output_string = new String("");
								String output_string2 = new String("");
								String output_string3 = new String("");
								for(int i=0; i<sList.size(); i++)
								{
									output_string += sList.get(i).getString("User_name")+"\n";
									output_string2 += sList.get(i).getString("String_1") + "\n";
									output_string3 += sList.get(i).getObjectId() + "\n";
								}
								Text_get.setText(output_string);
								Text_get2.setText(output_string2);
								Text_get3.setText(output_string3);
							} 
						}
				
					});
			//-------------------------------------------------------------------------
			long totaltime = System.currentTimeMillis() - startTime;
			run_log.setText("Search1 done for"+totaltime+"(ms)");
				
		}
	
	};
	private Button.OnClickListener get2_function = new Button.OnClickListener ()
	{
		public void onClick(View v)
		{
			long startTime = System.currentTimeMillis();
			run_log.setText("Search2");
			//-------------------------------------------------------------------------
			ParseQuery<ParseObject> query = ParseQuery.getQuery("TestObject");
			
			//request   所引條件
			query.whereEqualTo("User_name", Text_name.getText().toString());
			query.whereEqualTo("String_1", Text_push.getText().toString());
			//
			
			query.findInBackground(new FindCallback<ParseObject>()  //與線上同步資料
					{
						@Override
						public void done(List<ParseObject> sList, ParseException e)   //資料會存於list中
						{
							if (e == null) 
							{
								String output_string = new String("");
								String output_string2 = new String("");
								String output_string3 = new String("");
								for(int i=0; i<sList.size(); i++)
								{
									output_string += sList.get(i).getString("User_name")+"\n";
									output_string2 += sList.get(i).getString("String_1") + "\n";
									output_string3 += sList.get(i).getObjectId() + "\n";
								}
								Text_get.setText(output_string);
								Text_get2.setText(output_string2);
								Text_get3.setText(output_string3);
								
								if(sList.size()==1) Text_push2.setText(sList.get(0).getObjectId());
							} 
						}
				
					});
			//-------------------------------------------------------------------------
			long totaltime = System.currentTimeMillis() - startTime;
			run_log.setText("Search done for"+totaltime+"(ms)");
				
		}
	
	};
	
	private Button.OnClickListener update_function = new Button.OnClickListener ()
	{
		public void onClick(View v)  
		{
			long startTime = System.currentTimeMillis();
			run_log.setText("Running update");
			//-------------------------------------------------------------------------
			if(!Text_push2.getText().toString().equals(""))
			{
				ParseQuery<ParseObject> query = ParseQuery.getQuery("TestObject");
				// Retrieve the object by id
				query.getInBackground(Text_push2.getText().toString(), new GetCallback<ParseObject>() 
						{
							@Override
							public void done(ParseObject testObject, ParseException e) 
							{
								if (e == null) 
								{
									// Now let's update it with some new data. In this case, only cheatMode and score
									// will get sent to the Parse Cloud. playerName hasn't changed.							
									if(!Text_push.getText().toString().equals(""))
									{
										testObject.put("String_1", Text_push.getText().toString());
										//testObject.put("Android_id",android_id);
										testObject.saveInBackground();
									}
								}
							}
						});
			}
			//-------------------------------------------------------------------------
			long totaltime = System.currentTimeMillis() - startTime;
			run_log.setText("update total use "+totaltime+"ms");
		
		}
	};
	
	
	private Button.OnClickListener delete_function = new Button.OnClickListener ()
	{
		public void onClick(View v)  
		{
			long startTime = System.currentTimeMillis();
			run_log.setText("Delete");
			//----------------------------------------------------------------------------
			ParseQuery<ParseObject> query = ParseQuery.getQuery("TestObject");
			//request   所引條件
			query.whereEqualTo("User_name", Text_name.getText().toString());
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
			run_log.setText("Delete done for"+totaltime+"(ms)");
		
		}
	};
	
	private Button.OnClickListener display_function = new Button.OnClickListener ()
	{
		public void onClick(View v)  
		{
			long startTime = System.currentTimeMillis();
			run_log.setText("Display");
			//----------------------------------------------------------------------------
			ParseQuery<ParseObject> query = ParseQuery.getQuery("TestObject");
			//request   所引條件      //改成無條件全部顯示
			//name_string = Text_name.getText().toString();
			//query.whereEqualTo("User_name", name_string);
			//
			query.findInBackground(new FindCallback<ParseObject>()  //與線上同步資料
					{
						@Override
						public void done(List<ParseObject> sList, ParseException e)   //資料會存於list中
						{
							if (e == null) 
							{	
								String output_string = new String("");
								String output_string2 = new String("");
								String output_string3 = new String("");
								for(int i=0; i<sList.size(); i++)
								{
									output_string += sList.get(i).getString("User_name")+"\n";
									output_string2 += sList.get(i).getString("String_1") + "\n";
									output_string3 += sList.get(i).getObjectId() + "\n";
								}
								Text_get.setText(output_string);
								Text_get2.setText(output_string2);
								Text_get3.setText(output_string3);
							} 
						}
				
					});
			//------------------------------------------------------------------------------
			long totaltime = System.currentTimeMillis() - startTime;
			run_log.setText("Display done for"+totaltime+"(ms)");
		}
	};
	

}
