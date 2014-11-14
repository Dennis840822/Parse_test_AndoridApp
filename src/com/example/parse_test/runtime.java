package com.example.parse_test;

import android.view.View;
import android.widget.Toast;

public class runtime 
{
	private long starttime;
	private View v; 
	
	public runtime(View ve)
	{
		this.starttime = System.currentTimeMillis();
		this.v = ve;
	}
	public long get_starttime()
	{
		return this.starttime;
	}
	public void reset_starttime()
	{
		this.starttime = System.currentTimeMillis();
	}
	public void show_runtime()
	{
		long totaltime = System.currentTimeMillis() - this.get_starttime();
		Toast.makeText(v.getContext(),"update done for  "+totaltime+"(ms)", Toast.LENGTH_LONG).show();
	}

}
