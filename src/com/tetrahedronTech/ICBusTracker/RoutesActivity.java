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

public class RoutesActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routes);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("routes");
		
		ArrayList<String> data = new ArrayList<String>();
		data =setListItem();
		//find a specific fragment that you want to display your list
		Fragment f = (Fragment) getFragmentManager().findFragmentById(R.id.route_list);
		//create a key-value bundle and pass the bundle to the fragment
		Bundle bd = new Bundle();
		bd.putStringArrayList("key", data);
		f.setArguments(bd);
	}
	//animation when back button is pressed
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
	}
	
	private ArrayList<String> setListItem(){
		ArrayList<String> result=new ArrayList<String>();
		try{
			AssetManager am=this.getAssets();
			InputStream in = am.open("allRoutes.txt");
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br= new BufferedReader(isr);
			String line = br.readLine();
			String data[];
			while (line != null){
				data=line.split(",");
				result.add(data[0]);
				line=br.readLine();
			}
		}
		catch (Exception e){}
		return result;
	}
}
