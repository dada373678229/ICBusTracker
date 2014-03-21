package com.tetrahedronTech.ICBusTracker;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

public class FavoriteActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("favorite");
		
		//al contains the data you want to display in a list view
		ArrayList<String> al = new ArrayList<String>();
		ArrayList<String> al2=new ArrayList<String>();
		
		try{
			AssetManager am=this.getAssets();
			InputStream in = am.open("allStops.txt");
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br= new BufferedReader(isr);
			String line = br.readLine();
			String data[];
			while (line != null){
				data=line.split(",");
				al.add(data[0]);
				al2.add(data[1]);
				line=br.readLine();
			}
		}
		catch(Exception e){}
		
		//find a specific fragment that you want to display your list
		Fragment f = (Fragment) getFragmentManager().findFragmentById(R.id.favorite_list);
		//create a key-value bundle and pass the bundle to the fragment
		Bundle bd = new Bundle();
		bd.putStringArrayList("key", al);
		bd.putStringArrayList("key2", al2);
		f.setArguments(bd);
	}
	
	//animation when back button is pressed
	@Override
	public void onBackPressed() {
	    super.onBackPressed();
	    overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
	}
}
