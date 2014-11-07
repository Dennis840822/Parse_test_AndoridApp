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
	
	private Button Button_push,Button_get,Button_update,Button_delete,Button_display;
	private Button Button_clear;
	private EditText Text_push,Text_get,Text_name; 
	private TextView run_log;
	private String name_string;

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
        run_log =(TextView)findViewById(R.id.text_log);
        
        Text_push =(EditText)findViewById(R.id.text_input);
        Text_get =(EditText)findViewById(R.id.text_output);
        Text_name =(EditText)findViewById(R.id.text_name);
        
        Button_push =(Button)findViewById(R.id.button_input);
        Button_get = (Button)findViewById(R.id.button_output);
        Button_update = (Button)findViewById(R.id.button_update);
        Button_delete = (Button)findViewById(R.id.button_delete);
        Button_display = (Button)findViewById(R.id.button_display);
        Button_clear = (Button)findViewById(R.id.button_clear);
        
        
        Button_push.setOnClickListener(push_function);
        Button_get.setOnClickListener(get_function);
        Button_update.setOnClickListener(update_function);
        Button_delete.setOnClickListener(delete_function);
        Button_display.setOnClickListener(display_function);
        Button_clear.setOnClickListener(new Button.OnClickListener(){public void onClick(View v){Text_push.setText("");}});
    }

	
	private Button.OnClickListener push_function = new Button.OnClickListener ()
	{
		public void onClick(View v)  //push User_name: text_name , String_1 : text_input
		{
			long startTime = System.currentTimeMillis();
			run_log.setText("Running push");
			//------------------------------------------------------------------
			ParseObject testObject = new ParseObject("TestObject");
			name_string = Text_name.getText().toString();
			String test_string =  Text_push.getText().toString();

			if(!test_string.equals("") && !name_string.equals(""))  // no null
			{
				testObject.put("User_name", name_string);
				testObject.put("String_1", test_string);
				testObject.saveInBackground();				//push action
			}
			//------------------------------------------------------------------
			long totaltime = System.currentTimeMillis() - startTime;
			run_log.setText("push total use "+totaltime+"ms");
		}
	
	};
	
	private Button.OnClickListener get_function = new Button.OnClickListener ()
	{
		public void onClick(View v)
		{
			long startTime = System.currentTimeMillis();
			run_log.setText("Running get");
			//-------------------------------------------------------------------------
			ParseQuery<ParseObject> query = ParseQuery.getQuery("TestObject");
			
			//request   所引條件
			name_string = Text_name.getText().toString();
			query.whereEqualTo("User_name", name_string);
			//
			
			query.findInBackground(new FindCallback<ParseObject>()  //與線上同步資料
					{
						@Override
						public void done(List<ParseObject> sList, ParseException e)   //資料會存於list中
						{
							if (e == null) 
							{
								String output_string = new String("");
								for(int i=0; i<sList.size(); i++)
								{
									output_string += sList.get(i).getString("String_1") + " ";
								}
								Text_get.setText(output_string);
							} 
							else {}
						}
				
					});
			//-------------------------------------------------------------------------
			long totaltime = System.currentTimeMillis() - startTime;
			run_log.setText("get total use "+totaltime+"ms");
				
		}
	
	};
	
	private Button.OnClickListener update_function = new Button.OnClickListener ()
	{
		public void onClick(View v)  
		{
			long startTime = System.currentTimeMillis();
			run_log.setText("Running update");
			//-------------------------------------------------------------------------
			ParseQuery<ParseObject> query = ParseQuery.getQuery("TestObject");
			// Retrieve the object by id
			query.getInBackground("tnwLUegyrK", new GetCallback<ParseObject>() 
					{
						@Override
						public void done(ParseObject testObject, ParseException e) 
						{
							if (e == null) 
							{
								String test_string =  Text_push.getText().toString();
								// Now let's update it with some new data. In this case, only cheatMode and score
								// will get sent to the Parse Cloud. playerName hasn't changed.							
								if(!test_string.equals(""))
								{
									testObject.put("String_1", test_string);
									testObject.saveInBackground();
								}
							}
						}
					});
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
			run_log.setText("Running delete");
			//----------------------------------------------------------------------------
			ParseQuery<ParseObject> query = ParseQuery.getQuery("TestObject");
			
			//request   所引條件
			name_string = Text_name.getText().toString();
			query.whereEqualTo("User_name", name_string);
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
							else {}
						}
				
					});
			//------------------------------------------------------------------------------
			long totaltime = System.currentTimeMillis() - startTime;
			run_log.setText("delete total use "+totaltime+"ms");
		
		}
	};
	
	private Button.OnClickListener display_function = new Button.OnClickListener ()
	{
		public void onClick(View v)  
		{
			long startTime = System.currentTimeMillis();
			run_log.setText("Running display");
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
								for(int i=0; i<sList.size(); i++)
								{
									output_string += sList.get(i).getString("User_name") + "   " + sList.get(i).getString("String_1") + "\n";
								}
								Text_get.setText(output_string);
							} 
							else {}
						}
				
					});
			//------------------------------------------------------------------------------
			long totaltime = System.currentTimeMillis() - startTime;
			run_log.setText("display total use "+totaltime+"ms");
		
		}
	};
	

}
