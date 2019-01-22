package com.neo.advancedskills.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Neo on 2019/1/22.
 * Description :
 */
public class Person2 implements Parcelable
{
	private String name;
	private int age;
	
	public String getName()
	{
		return name;
	}
	
	public void setName( String name )
	{
		this.name = name;
	}
	
	public int getAge()
	{
		return age;
	}
	
	public void setAge( int age )
	{
		this.age = age;
	}
	
	@Override
	public int describeContents()
	{
		return 0;
	}
	
	@Override
	public void writeToParcel(
			Parcel dest ,
			int flags )
	{
		dest.writeString( name );
		dest.writeInt( age );
	}
	
	public static final Creator<Person2> CREATOR = new Creator<Person2>()
	{
		@Override
		public Person2 createFromParcel( Parcel in )
		{
			Person2 person = new Person2();
			person.name = in.readString();
			person.age = in.readInt();
			return person;
		}
		
		@Override
		public Person2[] newArray( int size )
		{
			return new Person2[ size ];
		}
	};
}
