package com.tetrahedronTech.ICBusTracker;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.tetrahedronTech.ICBusTracker.cards.routeListCard;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.Fragment;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class RoutesActivity extends Activity{
	
	private ArrayList<Card> routeListCoralville=new ArrayList<Card>();
	private ArrayList<Card> routeListIC=new ArrayList<Card>();
	private ArrayList<Card> routeListCambus=new ArrayList<Card>();
	private ArrayList<Card> routeListAll=new ArrayList<Card>();
	
	private String[] routeAgencies=new String[]{"Show All","Cambus","Iowa-City","Coralville"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_routes);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("routes");

		initRouteList();
		setList(routeListAll);
		setActionBar();
		
		/*
		//create a key-value bundle and pass the bundle to the fragment
		Bundle bd = new Bundle();
		bd.putStringArrayList("key", data);
		f.setArguments(bd);*/
	}
	//animation when back button is pressed
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
	}
	
	private void initRouteList(){
		try{
			AssetManager am=this.getAssets();
			InputStream in = am.open("allRoutes.txt");
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br= new BufferedReader(isr);
			String line = br.readLine();
			String data[];
			
			while (line != null){
				Card temp=new routeListCard(this);
				data=line.split(",");
				((routeListCard) temp).setContent(data[0]);
				temp.setId(data[0]);
				
				if (data[2].equals("coralville")){
					temp.setBackgroundResourceId(R.drawable.card_selector_blue);
					routeListCoralville.add(temp);
				}
				else if(data[2].equals("iowa-city")){
					temp.setBackgroundResourceId(R.drawable.card_selector_red);
					routeListIC.add(temp);
				}
				else{
					temp.setBackgroundResourceId(R.drawable.card_selector_yellow);
					routeListCambus.add(temp);
				}
				routeListAll.add(temp);
				line=br.readLine();
			}
		}
		catch (Exception e){}
		
		
	}
	
	private void setList(ArrayList<Card> routeList){
		CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(this,routeList);
		CardListView listView = (CardListView) findViewById(R.id.routeListView);
        if (listView!=null){
            listView.setAdapter(mCardArrayAdapter);
        }
	}
	
	private void setActionBar(){
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, routeAgencies);
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		ActionBar.OnNavigationListener navigationListener = new OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int itemPosition, long itemId) {
            	if (routeAgencies[itemPosition]=="Show All"){
            		setList(routeListAll);
            	}
            	else if (routeAgencies[itemPosition]=="Iowa-City"){
            		setList(routeListIC);
            	}
            	else if (routeAgencies[itemPosition]=="Coralville"){
            		setList(routeListCoralville);
            	}
            	else{
            		setList(routeListCambus);
            	}
                
                return false;
            }
        };
        getActionBar().setListNavigationCallbacks(adapter, navigationListener);
	}
}
