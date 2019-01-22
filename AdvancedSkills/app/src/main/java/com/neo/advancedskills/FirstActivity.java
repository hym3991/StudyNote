package com.neo.advancedskills;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.neo.advancedskills.parcelable.Person2;
import com.neo.advancedskills.serializable.Person;

/**
 * Created by Neo on 2019/1/22.
 * Description :
 */
public class FirstActivity extends Activity
{
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_first);
		Button button = (Button)findViewById( R.id.first_btn );
		button.setOnClickListener( v -> {
			Person person = new Person();
			person.setAge( 18 );
			person.setName( "Neo" );
			Intent intent = new Intent( FirstActivity.this,SeconeActivity.class );
			intent.putExtra( "person_data",person );
			//startActivity( intent );
			
			Person2 person2 = new Person2();
			person2.setAge( 22 );
			person2.setName( "Has" );
			Intent intent2 = new Intent( FirstActivity.this,SeconeActivity.class );
			intent2.putExtra( "person_data",person2 );
			startActivity( intent2 );
		} );
		
		new Thread( ()->{
		
		} ).start();
		
		Runnable runnable = () -> {
		
		};
	}
	
}
