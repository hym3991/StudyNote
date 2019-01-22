package com.neo.advancedskills;

import android.app.Application;
import android.content.Context;

/**
 * Created by Neo on 2019/1/22.
 * Description :
 */
public class MyApplication extends Application
{
	private static Context context;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		context = getApplicationContext();
	}
	
	/**
	 * 全局获取Context
	 * @return
	 */
	public static Context getContext()
	{
		return context;
	}
}
