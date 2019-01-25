package com.neo.coolweather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.neo.coolweather.db.City;
import com.neo.coolweather.db.County;
import com.neo.coolweather.db.Province;
import com.neo.coolweather.util.HttpUtil;
import com.neo.coolweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Neo on 2019/1/25.
 * Description :
 */
public class ChooseAreaFragment extends Fragment
{
	public static final int LEVEL_PROVINCE = 0;
	public static final int LEVEL_CITY = 1;
	public static final int LEVEL_COUNTY = 2;
	
	private ProgressBar progressBar;//替代ProgressDialog
	private TextView titleView;
	private Button backButton;
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private List<String> dataList = new ArrayList<>();
	
	//省列表
	private List<Province> provinceList;
	//市列表
	private List<City> cityList;
	//县列表
	private List<County> countyList;
	
	//选中的省
	private Province selectProvince;
	//选中的市
	private City selectCity;
	//当前选中的级别
	private int currentLevel;
	
	@Nullable
	@Override
	public View onCreateView(
			@NonNull LayoutInflater inflater ,
			@Nullable ViewGroup container ,
			@Nullable Bundle savedInstanceState )
	{
		View view = inflater.inflate( R.layout.choose_area , container , false );
		titleView = ( TextView )view.findViewById( R.id.title_text );
		backButton = ( Button )view.findViewById( R.id.back_button );
		listView = ( ListView )view.findViewById( R.id.list_view );
		adapter = new ArrayAdapter<>( getContext() , android.R.layout.simple_list_item_1 , dataList );
		listView.setAdapter( adapter );
		return view;
	}
	
	@Override
	public void onActivityCreated( @Nullable Bundle savedInstanceState )
	{
		super.onActivityCreated( savedInstanceState );
		listView.setOnItemClickListener( ( parent , view , position , id ) -> {
			if( currentLevel == LEVEL_PROVINCE )
			{
				selectProvince = provinceList.get( position );
				queryCities();
			}
			else if( currentLevel == LEVEL_CITY )
			{
				selectCity = cityList.get( position );
				queryCounties();
			}
		} );
		backButton.setOnClickListener( ( view ) -> {
			if( currentLevel == LEVEL_COUNTY )
			{
				queryCities();
			}
			else if( currentLevel == LEVEL_CITY )
			{
				queryProvinces();
			}
		} );
		queryProvinces();
	}
	
	private void queryProvinces()
	{
		titleView.setText( "中国" );
		backButton.setVisibility( View.GONE );
		provinceList = DataSupport.findAll( Province.class );
		if( provinceList.size() > 0 )
		{
			dataList.clear();
			for( Province province : provinceList )
			{
				dataList.add( province.getProvinceName() );
			}
			adapter.notifyDataSetChanged();
			listView.setSelection( 0 );
			currentLevel = LEVEL_PROVINCE;
		}
		else
		{
			String address = "http://guolin.tech/api/china";
			queryFromServer( address , "province" );
		}
	}
	
	private void queryCities()
	{
		titleView.setText( selectProvince.getProvinceName() );
		backButton.setVisibility( View.VISIBLE );
		cityList = DataSupport.where( "province = ?" , String.valueOf( selectProvince.getId() ) ).find( City.class );
		if( cityList.size() > 0 )
		{
			dataList.clear();
			for( City city : cityList )
			{
				dataList.add( city.getCityName() );
			}
			adapter.notifyDataSetChanged();
			listView.setSelection( 0 );
			currentLevel = LEVEL_CITY;
		}
		else
		{
			int provinceCode = selectProvince.getProvinceCode();
			String address = "http://guolin.tech/api/china/" + provinceCode;
			queryFromServer( address , "city" );
		}
	}
	
	private void queryCounties()
	{
		titleView.setText( selectCity.getCityName() );
		backButton.setVisibility( View.VISIBLE );
		countyList = DataSupport.where( "city = ?" , String.valueOf( selectCity.getId() ) ).find( County.class );
		if( countyList.size() > 0 )
		{
			dataList.clear();
			for( County county : countyList )
			{
				dataList.add( county.getCountyName() );
			}
			adapter.notifyDataSetChanged();
			listView.setSelection( 0 );
			currentLevel = LEVEL_COUNTY;
		}
		else
		{
			int provinceCode = selectProvince.getProvinceCode();
			int cityCode = selectCity.getCityCode();
			String address = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
			queryFromServer( address , "county" );
		}
	}
	
	private void queryFromServer(
			String address ,
			final String type )
	{
		showProgressBar();
		HttpUtil.sendOkHttpRequest( address , new Callback()
		{
			@Override
			public void onFailure(
					Call call ,
					IOException e )
			{
				getActivity().runOnUiThread( () -> {
					closeProgressBar();
					Toast.makeText( getContext() , "加载失败" , Toast.LENGTH_SHORT ).show();
				} );
			}
			
			@Override
			public void onResponse(
					Call call ,
					Response response ) throws IOException
			{
				String respondeText = response.body().string();
				boolean result = false;
				if( "province".equals( type ) )
				{
					result = Utility.handleProvinceResponse( respondeText );
				}
				else if( "city".equals( type ) )
				{
					result = Utility.handleCityResponse( respondeText , selectProvince.getId() );
				}
				else if( "county".equals( type ) )
				{
					result = Utility.handleCountyResponse( respondeText , selectCity.getId() );
				}
				if( result )
				{
					getActivity().runOnUiThread( () -> {
						closeProgressBar();
						if( "province".equals( type ) )
						{
							queryProvinces();
						}
						else if( "city".equals( type ) )
						{
							queryCities();
						}
						else if( "county".equals( type ) )
						{
							queryCounties();
						}
					} );
				}
			}
		} );
	}
	
	private void showProgressBar()
	{
		if( progressBar == null )
		{
			progressBar = new ProgressBar( getActivity() );
			progressBar.setVisibility( View.VISIBLE );
		}
		else
		{
			progressBar.setVisibility( View.VISIBLE );
		}
	}
	
	private void closeProgressBar()
	{
		progressBar.setVisibility( View.GONE );
	}
	
}
