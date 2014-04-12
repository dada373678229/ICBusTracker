package com.tetrahedronTech.ICBusTracker;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.tetrahedronTech.ICBusTracker.cards.routeListCard;
import com.tetrahedronTech.ICBusTracker.cards.stopListCard;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

public class StopsActivity extends Activity implements OnQueryTextListener{
	
	//this arraylist contains all stops
	private ArrayList<stopObject> stops=new ArrayList<stopObject>();
	
	ArrayList<Card> cards = new ArrayList<Card>();
	CardArrayAdapter mCardArrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stops);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("stops");
		
		//"cards" contains cards, each card is a stop
		cards =setListItem();
		//set cards to the card list
		mCardArrayAdapter = new CardArrayAdapter(this,cards);
		CardListView listView = (CardListView) findViewById(R.id.stopListView);
        if (listView!=null){
            listView.setAdapter(mCardArrayAdapter);
        }
	}

	//animation when back button is pressed
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the options menu from XML
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.options_menu, menu);

	    // Get the SearchView and set the searchable configuration
	    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
	    // Assumes current activity is the searchable activity
	    searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
	    searchView.setOnQueryTextListener(this);

	    return true;
	}
	
	//this method find all stops, convert them into cards and return an arraylist of them
	private ArrayList<Card> setListItem(){
		ArrayList<Card> result=new ArrayList<Card>();
		try{
			//open file and read stops
			AssetManager am=this.getAssets();
			InputStream in = am.open("allStops.txt");
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br= new BufferedReader(isr);
			String line = br.readLine();
			String data[];
			//get a single line and analyze stop infomation
			while (line != null){
				Card temp=new stopListCard(this);
				data=line.split(",");
				((stopListCard) temp).setContent(data[0],data[1]);
				String stopTitle=data[0]+","+data[1];
				temp.setId(stopTitle);
				result.add(temp);
				stops.add(new stopObject(data[0],data[1]));
				line=br.readLine();
			}
		}
		catch (Exception e){}
		return result;
	}
	
	//search stops according to user's input, and returns an arraylist of cards for adapter
	private ArrayList<Card> search(String newText){
		ArrayList<Card> result=new ArrayList<Card>();
		
		//scan all the stops
		for (int i=0;i<stops.size();i++){
			String stopId=stops.get(i).getStopId();
			String stopName=stops.get(i).getStopName();
			//if stopId contains query or stopName contains query, create a card of this stop and put it into arraylist
			if(stopId.toLowerCase().contains(newText.toLowerCase()) | stopName.toLowerCase().contains(newText.toLowerCase())){
				Card temp=new stopListCard(this);
				((stopListCard) temp).setContent(stopId,stopName);
				String stopTitle=stopId+","+stopName;
				temp.setId(stopTitle);
				result.add(temp);
			}
		}
		return result;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		//update the arraylist for adapter, clear the data in the adapter, add new data and notify change
		cards=search(newText);
		mCardArrayAdapter.clear();
		mCardArrayAdapter.addAll(cards);
		mCardArrayAdapter.notifyDataSetChanged();
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		return false;
	}
}
