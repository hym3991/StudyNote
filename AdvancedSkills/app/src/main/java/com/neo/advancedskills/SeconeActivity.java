package com.neo.advancedskills;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.neo.advancedskills.parcelable.Person2;
import com.neo.advancedskills.serializable.Person;

/**
 * Created by Neo on 2019/1/22.
 * Description :
 */
public class SeconeActivity extends Activity
{
	@Override
	protected void onCreate(  Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_secone );
		Person person = (Person)getIntent().getSerializableExtra( "person_data" );
		Person2 person2 = (Person2)getIntent().getParcelableExtra( "person_data" );
		if( person != null )
		{
			TextView age = (TextView )findViewById( R.id.sec_age );
			age.setText( "年龄是："+person.getAge() );
			
			TextView name = (TextView )findViewById( R.id.sec_name );
			name.setText( "名字是："+person.getName() );
		}else if( person2 != null )
		{
			TextView age = (TextView )findViewById( R.id.sec_age );
			age.setText( "年龄是："+person2.getAge() );
			
			TextView name = (TextView )findViewById( R.id.sec_name );
			name.setText( "名字是："+person2.getName() );
		}
	}
}
