package com.neo.materialdesignstudy.data;

/**
 * Created by Neo on 2019/1/22.
 * Description :
 */
public class Fruit
{
	private String name;
	private int imageId;
	
	public Fruit(String name ,int imageId)
	{
		this.name = name;
		this.imageId = imageId;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getImageId()
	{
		return imageId;
	}
}
