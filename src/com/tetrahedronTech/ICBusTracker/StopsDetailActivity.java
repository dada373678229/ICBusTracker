package com.tetrahedronTech.ICBusTracker;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

import java.util.ArrayList;

import com.tetrahedronTech.ICBusTracker.API.coreAPI;
import com.tetrahedronTech.ICBusTracker.cards.routeListDetailCard;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

public class StopsDetailActivity extends Activity{
	private Context context;
	//"cards" contains cards where each card has bus prediction information
	private ArrayList<Card> cards = new ArrayList<Card>();
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite);
		
		String stopId=getIntent().getExtras().getString("stopId");
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(stopId);
		context=this;
		
		//now begin to do the heavy job: get bus predictions
		new LongOperation().execute(new String[]{stopId});
        
	}
	
	//this class can do heavy tasks in the background. Here, we want it to set cardlist
		private class LongOperation extends AsyncTask<String, Void, String> {
			//this method set the arraylist of cards, params can be viewed as String[]
			@Override
	        protected String doInBackground(String... params) {
				int stopId=Integer.parseInt(params[0]);
				cards=setPredictionItem(stopId);
				//if we get no prediction, return no, otherwise, return yes
				if(cards != null){
					return "yes";
				}
				else {
					return "no";
				}
			}
			
			//since we CANNOT update user interface UNTIL the doInBackground is finished, so we
			//have to update the UI AFTER getting the predictions
			@Override
	        protected void onPostExecute(String result) {
				//if the get any predictions, set the cardlist
				if (result=="yes"){
					CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(context,cards);
					CardListView listView = (CardListView) findViewById(R.id.favoriteListView);
			        if (listView!=null){
			            listView.setAdapter(mCardArrayAdapter);
			        }
				}
	        }
		}
		
		//animation when back button is pressed
		@Override
		public void onBackPressed() {
		    super.onBackPressed();
		    overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
		}
		
		//this methods returns an arraylist cards where each card is a bus prediction
		private ArrayList<Card> setPredictionItem(int stopId){
			ArrayList<Card> result=new ArrayList<Card>();
			if(isOnline()){
				coreAPI api=new coreAPI();
				String p=api.busPrediction(stopId);
				if (p.length()==0) return result;
				String data[]=p.split(";");
				for (int i=0; i<data.length;i++){
					Card temp=new routeListDetailCard(this);
					String line[]=data[i].split(",");
					((routeListDetailCard) temp).setContent(line[0],line[2],line[1]);
					result.add(temp);
				}
			}
			return result;
		}
		
		//this methods checks if the device is connected to the Internet and can receive data
		private boolean isOnline(){
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getActiveNetworkInfo();
			if (netInfo != null && netInfo.isConnectedOrConnecting() && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
				return true;}
			return false;
		}
}
