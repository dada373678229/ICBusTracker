package com.tetrahedronTech.ICBusTracker;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class WeatherActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_weather);
		ActionBar actionBar = getActionBar();
	    //actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle("weather");
	}
	
	//animation when back button is pressed
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
	}

}
