package com.neo.materialdesignstudy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.neo.materialdesignstudy.data.Fruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
	private DrawerLayout drawerLayout;
	private Fruit[] fruits =
			{
					new Fruit( "葡萄",R.drawable.f1 ),
					new Fruit( "樱桃", R.drawable.f2 ),
					new Fruit( "樱桃", R.drawable.f3 ),
					new Fruit( "梨子", R.drawable.f4 ),
					new Fruit( "西红柿", R.drawable.f5 ),
					new Fruit( "苹果", R.drawable.f6 ),
					new Fruit( "梨子", R.drawable.f7 ),
					new Fruit( "桃子", R.drawable.f8 ),
					new Fruit( "苹果", R.drawable.f9 ),
					new Fruit( "草莓", R.drawable.f10 ),
					new Fruit( "猕猴桃", R.drawable.f11),
					new Fruit( "一堆水果", R.drawable.f12 ),
					new Fruit( "西瓜", R.drawable.f13 ),
					new Fruit( "苹果", R.drawable.f14 ),
					new Fruit( "香蕉", R.drawable.f15 ),
			};
	private List<Fruit> fruitList = new ArrayList<>( );
	private FruitAdapter fruitAdapter;
	private SwipeRefreshLayout swipeRefreshLayout;
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		Toolbar toolbar = (Toolbar)findViewById( R.id.toolbar );
		setSupportActionBar( toolbar );
		
		drawerLayout = (DrawerLayout)findViewById( R.id.drawer_layout );
		
		ActionBar actionBar = getSupportActionBar();
		if( actionBar != null )
		{
			//让导航按钮显示出来
			actionBar.setDisplayHomeAsUpEnabled( true );
			//设置导航按钮的图标 这个按钮就叫做HomeAsUp按钮
			actionBar.setHomeAsUpIndicator( R.drawable.rain );
		}
		
		NavigationView navigationView = (NavigationView )findViewById( R.id.nav_view );
		navigationView.setCheckedItem( R.id.nav_call );
		navigationView.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener()
		{
			@Override
			public boolean onNavigationItemSelected( @NonNull MenuItem menuItem )
			{
				drawerLayout.closeDrawers();
				return true;
			}
		} );
		
		FloatingActionButton fab = (FloatingActionButton)findViewById( R.id.fab );
		fab.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick( View v )
			{
				//Toast.makeText( MainActivity.this,"FAB click",Toast.LENGTH_SHORT ).show();
				Snackbar.make( v, "Data deleted",Snackbar.LENGTH_SHORT).setAction( "Undo" , new View.OnClickListener()
				{
					@Override
					public void onClick( View v )
					{
						Toast.makeText( MainActivity.this,"Data restored",Toast.LENGTH_SHORT ).show();
					}
				} ).show();
			}
		} );
		
		initFruits();
		RecyclerView recyclerView = (RecyclerView)findViewById( R.id.recycler_view );
		GridLayoutManager layoutManager = new GridLayoutManager( this,2 );
		recyclerView.setLayoutManager( layoutManager );
		fruitAdapter = new FruitAdapter( fruitList );
		recyclerView.setAdapter( fruitAdapter );
		
		swipeRefreshLayout = (SwipeRefreshLayout)findViewById( R.id.swipe_refresh );
		swipeRefreshLayout.setColorSchemeResources( R.color.colorPrimary );
		swipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener()
		{
			@Override
			public void onRefresh()
			{
				refresh();
			}
		} );
	}
	
	private void refresh()
	{
		new Thread( new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					Thread.sleep( 2000 );
				}catch( Exception e )
				{
					e.printStackTrace();
				}
				runOnUiThread( new Runnable()
				{
					@Override
					public void run()
					{
						initFruits();
						fruitAdapter.notifyDataSetChanged();
						swipeRefreshLayout.setRefreshing( false );
					}
				} );
			}
		} ).run();
	}
	
	private void initFruits()
	{
		fruitList.clear();
		for( int i = 0; i<50;i++ )
		{
			Random random = new Random(  );
			int index = random.nextInt(fruits.length);
			fruitList.add( fruits[index] );
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu( Menu menu )
	{
		getMenuInflater().inflate( R.menu.toolbar,menu );
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected( MenuItem item )
	{
		switch( item.getItemId() )
		{
			case R.id.atm:
				Toast.makeText( this,"click atm",Toast.LENGTH_SHORT ).show();
				break;
			case R.id.skip:
				Toast.makeText( this,"click skip",Toast.LENGTH_SHORT ).show();
				break;
			case R.id.phone:
				Toast.makeText( this,"click phone",Toast.LENGTH_SHORT ).show();
				break;
			case android.R.id.home:
				//HomeAsUp按钮的id永远都是android.R.id.home
				//openDrawer 中的参数要跟XML中的一致
				drawerLayout.openDrawer( GravityCompat.START );
				break;
			default:
				break;
		}
		return true;
	}
}
