package com.tetrahedronTech.ICBusTracker;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class StopsActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stops);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("stops");
	}

	//animation when back button is pressed
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
	}
}
