package com.neo.advancedskills.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

/**
 * Created by Neo on 2019/1/22.
 * Description :
 */
public class LongRunningService extends Service
{
	@Override
	public IBinder onBind( Intent intent )
	{
		return null;
	}
	
	@Override
	public int onStartCommand(
			Intent intent ,
			int flags ,
			int startId )
	{
		new Thread( new Runnable()
		{
			@Override
			public void run()
			{
				//执行逻辑
			}
		} ).run();
		AlarmManager alarmManager = (AlarmManager)getSystemService( ALARM_SERVICE );
		int anHour = 60 * 60 * 2000;
		long tirggerAtTime = SystemClock.elapsedRealtime() + anHour;
		Intent i = new Intent( this,LongRunningService.class );
		PendingIntent pi = PendingIntent.getService( this,0,i,0 );
		alarmManager.set( AlarmManager.ELAPSED_REALTIME_WAKEUP,tirggerAtTime,pi );
		return super.onStartCommand( intent , flags , startId );
	}
}
