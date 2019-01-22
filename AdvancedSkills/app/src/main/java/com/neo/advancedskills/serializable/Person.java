package com.neo.advancedskills.serializable;

import java.io.Serializable;

/**
 * Created by Neo on 2019/1/22.
 * Description :
 */
public class Person implements Serializable
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
}
