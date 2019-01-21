package com.neo.materialdesignstudy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
	private DrawerLayout drawerLayout;
	
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
