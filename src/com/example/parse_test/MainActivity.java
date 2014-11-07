package com.example.parse_test;

import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
//import com.parse.GetCallback;
import com.parse.FindCallback;
import com.parse.Parse;
//import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class MainActivity extends Activity 
{
	
	private Button Button_push,Button_get;
	private EditText Text_push,Text_get,Text_name; 
	
	private String name_string;
	
	//private ParseObject testObject = new ParseObject("TestObject");;
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
        Text_push =(EditText)findViewById(R.id.text_input);
        Text_get =(EditText)findViewById(R.id.text_output);
        Text_name =(EditText)findViewById(R.id.text_name);
        
        Button_push =(Button)findViewById(R.id.button_input);
        Button_get = (Button)findViewById(R.id.button_output);

        Button_push.setOnClickListener(push_function);
        Button_get.setOnClickListener(get_function);
    }
	
	private Button.OnClickListener push_function = new Button.OnClickListener ()
	{
		public void onClick(View v)  //push User_name: text_name , String_1 : text_input
		{
			ParseObject testObject = new ParseObject("TestObject");
			
			name_string = Text_name.getText().toString();
			String test_string =  Text_push.getText().toString();

			if(!test_string.equals("") && !name_string.equals(""))  // no null
			{
				testObject.put("User_name", name_string);
				testObject.put("String_1", test_string);
				testObject.saveInBackground();				//push action
			}
		}
	
	};
	
	private Button.OnClickListener get_function = new Button.OnClickListener ()
	{
		public void onClick(View v)
		{
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
							else 
							{
								
							}
						}
				
					});
				
		}
	
	};
	
	

}
