package com.neo.materialdesignstudy;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.neo.materialdesignstudy.data.Fruit;

import java.util.List;

/**
 * Created by Neo on 2019/1/22.
 * Description :
 */
public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder>
{
	private Context context;
	private List<Fruit> fruitList;
	
	public FruitAdapter(List<Fruit> list)
	{
		fruitList = list;
	}
	
	
	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(
			@NonNull ViewGroup viewGroup ,
			int i )
	{
		if( context == null )
		{
			context = viewGroup.getContext();
		}
		View view = LayoutInflater.from( context ).inflate( R.layout.fruit_item,viewGroup,false );
		final ViewHolder holder = new ViewHolder( view );
		holder.cardView.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				int pos = holder.getAdapterPosition();
				Fruit fruit = fruitList.get( pos );
				Intent intent = new Intent( context,FruitActivity.class );
				intent.putExtra( FruitActivity.FRUIT_IMAGE_ID,fruit.getImageId() );
				intent.putExtra( FruitActivity.FRUIT_NAME,fruit.getName());
				context.startActivity( intent );
			}
		} );
		
		return holder;
	}
	
	@Override
	public void onBindViewHolder(
			@NonNull ViewHolder viewHolder ,
			int i )
	{
		Fruit fruit = fruitList.get( i );
		viewHolder.fruitText.setText( fruit.getName() );
		Glide.with( context ).load( fruit.getImageId() ).into( viewHolder.fruitImage );
	}
	
	@Override
	public int getItemCount()
	{
		return fruitList.size();
	}
	
	static class ViewHolder extends RecyclerView.ViewHolder
	{
		CardView cardView;
		ImageView fruitImage;
		TextView fruitText;
		
		public ViewHolder( @NonNull View itemView )
		{
			super( itemView );
			cardView = (CardView)itemView;
			fruitImage = (ImageView)itemView.findViewById( R.id.fruit_image );
			fruitText = (TextView)itemView.findViewById( R.id.fruit_name );
		}
	}
}
