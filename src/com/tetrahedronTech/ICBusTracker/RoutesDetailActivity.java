package com.tetrahedronTech.ICBusTracker;

import java.io.BufferedReader;

import com.tetrahedronTech.ICBusTracker.API.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
//import com.tetrahedron.ICBusTracker.R;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.Build;

public class RoutesDetailActivity extends Activity {

	//camera bound
		private final LatLngBounds redBound = new LatLngBounds(
				  new LatLng(41.6569098, -91.5541481), new LatLng(41.6768599, -91.53203));
		private GoogleMap map;
		private Marker bus;
		private Handler busHandler=new Handler();
		private LatLng busLocation;
		private LatLng preBusLocation;
		final coreAPI api = new coreAPI();
		
		Runnable busMarker=new Runnable(){
			@Override
			public void run(){
				String[] temp=api.busLocations("uiowa", "red").split(";");
				String[] temp1=temp[0].split(",");
				busLocation=new LatLng(Float.parseFloat(temp1[1]), Float.parseFloat(temp1[2]));
				if(busLocation!=preBusLocation){
					bus.remove();
					bus=map.addMarker(new MarkerOptions().position(busLocation).icon(BitmapDescriptorFactory.fromAsset("busIcon.png")).rotation(Float.parseFloat(temp1[3])).flat(true));
					busHandler.postDelayed(this, 1000);
				}
				preBusLocation=busLocation;
				
			}
		};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routes_detail);
		overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
		
		String route=(String) getIntent().getExtras().get("route");
		Toast.makeText(this, route, Toast.LENGTH_SHORT).show();
		
		//final coreAPI api = new coreAPI();
		initMap("red");
		String[] temp=api.busLocations("uiowa", "red").split(";");
		String[] temp1=temp[0].split(",");
		busLocation=new LatLng(Float.parseFloat(temp1[1]), Float.parseFloat(temp1[2]));
		preBusLocation=busLocation;
		bus=map.addMarker(new MarkerOptions().position(busLocation).icon(BitmapDescriptorFactory.fromAsset("busIcon.png")).rotation(Float.parseFloat(temp1[3])).flat(true));
		busHandler.postDelayed(busMarker, 1000);
		
	}
	
	@Override
    protected void onDestroy() {
        //busHandler.removeCallbacks(busMarker);
        super.onDestroy();
    }
	
	//animation when back button is pressed
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
	}
	
	//this method initiates the map fragment
	public void initMap(String route){
		String file;
		try{
	        if (map == null) {
	            //link map with fragment1
	            map =((MapFragment) getFragmentManager().findFragmentById(R.id.routeDetailMapFragment)).getMap();
	        }
	        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
	        
	        //get time to determine which map we are going to use
	        Calendar c = Calendar.getInstance();
	        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	        String strTime = sdf.format(c.getTime());
	        String[] time=strTime.split(":");
	        
	        if ((Integer.parseInt(time[0])==19 && Integer.parseInt(time[1])>=25) || (Integer.parseInt(time[0])>19)){
	        	file=route+"Night.txt";
	        }
	        else{
	        	file=route+"Day.txt";
	        }
	        
	        //read stop information
	        AssetManager am = this.getAssets();
	        InputStream is = am.open("routeInfo/stops/"+file);
	        InputStreamReader isr = new InputStreamReader(is);
	        BufferedReader br = new BufferedReader(isr);
	        String line = br.readLine();
	        //rawData contains stopId, stopTitle, stopLat, stopLng
	        String[] rawData;
	        while (line != null){
	            rawData=line.split(",");
	            //add marker
	            map.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(rawData[2]), Double.parseDouble(rawData[3]))).title(rawData[0]+" "+rawData[1]).icon(BitmapDescriptorFactory.defaultMarker(200)).alpha(0.7f));
	            line=br.readLine();
	        }
	        is.close();
	        isr.close();
	        br.close();
	        
	        
	        //draw polyline for route path
	        is = am.open("routeInfo/path/"+file);
	        isr=new InputStreamReader(is);
	        br=new BufferedReader(isr);
	        line = br.readLine();
	        PolylineOptions routeOptions = new PolylineOptions();
	        while (line != null){
            	rawData=line.split(",");
            	routeOptions.add(new LatLng(Double.parseDouble(rawData[0]),Double.parseDouble(rawData[1])));
	            line=br.readLine();
	        }
	        routeOptions.color(Color.CYAN);
	        Polyline polyLine=map.addPolyline(routeOptions);
	        is.close();            
	        isr.close();
	        br.close();
	       
	        //for test uses
	        //map.addMarker(new MarkerOptions().position(center).icon(BitmapDescriptorFactory.fromAsset("busIcon.png")).flat(true).rotation(88));
	        /*
	        is=am.open("MoreredRoutePoints1.txt");
	        isr=new InputStreamReader(is);
	        br=new BufferedReader(isr);
	        line=br.readLine();
	        while(line!=null){
	          	rawData=line.split(",");
	           	map.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(rawData[1]), Double.parseDouble(rawData[2]))).title(rawData[0]).icon(BitmapDescriptorFactory.fromAsset("reddot.png")).alpha(0.7f));
	           	line=br.readLine();           
	        }*/
	            
	        //set camera position
	        //CameraPosition cameraPosition = new CameraPosition.Builder().target(center).zoom(13).build();
	        //map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	        map.animateCamera(CameraUpdateFactory.newLatLngZoom(redBound.getCenter(),13));
	        map.setMyLocationEnabled(true);
	        //map.setTrafficEnabled(true);
		}
		catch (Exception e){}
	}
}
