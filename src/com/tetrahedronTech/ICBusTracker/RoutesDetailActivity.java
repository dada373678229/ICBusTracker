package com.tetrahedronTech.ICBusTracker;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;

public class RoutesDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routes_detail);
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
		
		String route=(String) getIntent().getExtras().get("route");
		Toast.makeText(this, route, Toast.LENGTH_SHORT).show();
	}

}
