package com.example.parse_test;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
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
	private String Table_name,col_1,col_2;
	private EditText Text_name,Text_data,Text_id;
	private EditText Text_get1,Text_get2,Text_get3;
	private EditText Text_search;
	private Button Button_push,Button_display;
	private Button Button_serach1,Button_serach2;
	private Button Button_update,Button_delete;
	private runtime clock;
	private ParseQuery<ParseObject> query;
	
	//private MainApplication a;

	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //init for parse
        Parse.initialize(this, "JDvr3ElOQ3pYXlBqT1twQHx3XvGmpO37XlROoThG", "ISLvJ6xMzRkcpZRi3i3aCNTbAflt9hDl5cERONGP");
        ParseUser.enableAutomaticUser();
        //
        
        Intent intent = new Intent(MainActivity.this, MainApplication.class);
        startService(intent);
        
        Table_name = getString(R.string.table_name);
        col_1 = getString(R.string.col_1);
        col_2 = getString(R.string.col_2);
        
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
        Button_serach1.setOnClickListener(display_function);
        Button_serach2.setOnClickListener(display_function);
        Button_update .setOnClickListener(update_function);
        Button_delete .setOnClickListener(delete_function);
        Button_display.setOnClickListener(display_function);
       
    }
	private Button.OnClickListener push_function = new Button.OnClickListener ()
	{
		public void onClick(View v) 
		{
			clock = new runtime(v);
			//------------------------------------------------------------------
			if(!Text_name.getText().toString().equals("") && !Text_data.getText().toString().equals(""))  // no null
			{
				ParseObject pushObject = new ParseObject(Table_name);
				pushObject.put(col_1, Text_name.getText().toString());
				pushObject.put(col_2, Text_data.getText().toString());
				pushObject.saveInBackground();		
				
				Text_get1.setText(pushObject.getString(col_1));
				Text_get2.setText(pushObject.getString(col_2));
			}
			//------------------------------------------------------------------
			clock.show_runtime();
		}
	
	};
	private Button.OnClickListener display_function = new Button.OnClickListener ()
	{
		public void onClick(View v)  
		{
			clock = new runtime(v);
			//----------------------------------------------------------------------------
			query = ParseQuery.getQuery(Table_name);
			if(v.getId() == Button_display.getId() ) 
			{
				//display with no function
			}
			else if  (v.getId() == Button_serach1.getId()) 
			{
				String[] KeyWord = Text_search.getText().toString().split(" ");
				query.whereContainedIn(col_1, Arrays.asList(KeyWord));
			}
			else if  (v.getId() == Button_serach2.getId()) 
			{
				query.whereContains(col_2, Text_search.getText().toString());
			}
			//
			query.findInBackground(new FindCallback<ParseObject>()  //與線上同步資料
					{
						@Override
						public void done(List<ParseObject> sList, ParseException e)   //資料會存於list中
						{
							if (e == null) 	
							{
								String output_string1 = new String("");
								String output_string2 = new String("");
								String output_string3 = new String("");
								for(ParseObject temp : sList )
								{
									output_string1 += temp.getString(col_1)+"\n";
									output_string2 += temp.getString(col_2) + "\n";
									output_string3 += temp.getObjectId() + "\n";
								}
								Text_get1.setText(output_string1);
								Text_get2.setText(output_string2);
								Text_get3.setText(output_string3);
							}
						}
				
					});
			//------------------------------------------------------------------------------
			clock.show_runtime();
		}
	};	
	private Button.OnClickListener update_function = new Button.OnClickListener ()
	{
		public void onClick(View v)  
		{
			clock = new runtime(v);
			//-------------------------------------------------------------------------
			if(!Text_id.getText().toString().equals(""))
			{
				ParseQuery<ParseObject> query = ParseQuery.getQuery(Table_name);
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
										testObject.put(col_2, Text_data.getText().toString());
										testObject.saveInBackground();
									}
								}
							}
						});
			}
			//-------------------------------------------------------------------------
			clock.show_runtime();
		
		}
	};
	private Button.OnClickListener delete_function = new Button.OnClickListener ()
	{
		public void onClick(View v)  
		{
			clock = new runtime(v);
			//----------------------------------------------------------------------------
			//ParseQuery<ParseObject> query = ParseQuery.getQuery(Table_name);
			//request   所引條件
			//query.whereEqualTo(col_1, Text_name.getText().toString());
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
			clock.show_runtime();
		}
	};
	
	
	

}
