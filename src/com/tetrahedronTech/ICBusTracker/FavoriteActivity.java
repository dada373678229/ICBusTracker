package com.tetrahedronTech.ICBusTracker;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

public class FavoriteActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("favorite");
		
		//al contains the data you want to display in a list view
		ArrayList<String> al = new ArrayList<String>(Arrays.asList("0001","0002","0003","0004","0001","0002","0003","0004","0001","0002","0003","0004"));
		//find a specific fragment that you want to display your list
		Fragment f = (Fragment) getFragmentManager().findFragmentById(R.id.favorite_list);
		//create a key-value bundle and pass the bundle to the fragment
		Bundle bd = new Bundle();
		bd.putStringArrayList("key", al);
		f.setArguments(bd);
	}
	
	//animation when back button is pressed
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
	}
}
