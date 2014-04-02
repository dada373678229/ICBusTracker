package com.tetrahedronTech.ICBusTracker;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.tetrahedronTech.ICBusTracker.API.coreAPI;
import com.tetrahedronTech.ICBusTracker.cards.routeListCard;
import com.tetrahedronTech.ICBusTracker.cards.routeListDetailCard;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

public class FavoriteActivity extends Activity{
	
	private Context context;
	
	private ArrayList<Card> cards = new ArrayList<Card>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorite);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("favorite");
		context=this;
		
		new LongOperation().execute("");
		
		
		/*
		ArrayList<Card> cards = new ArrayList<Card>();
		cards =setListItem();
		
		CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(this,cards);
		CardListView listView = (CardListView) findViewById(R.id.favoriteListView);
        if (listView!=null){
            listView.setAdapter(mCardArrayAdapter);
        }*/
        
	}
	
	private class LongOperation extends AsyncTask<String, Void, String> {
		@Override
        protected String doInBackground(String... params) {
			cards=setPredictionItem();
			if(cards != null){
				return "yes";
			}
			else {
				return "no";
			}
		}
		
		@Override
        protected void onPostExecute(String result) {
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
	
	private ArrayList<Card> setListItem(){
		ArrayList<Card> result=new ArrayList<Card>();
		try{
			AssetManager am=this.getAssets();
			InputStream in = am.open("allRoutes.txt");
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br= new BufferedReader(isr);
			String line = br.readLine();
			String data[];
			
			while (line != null){
				Card temp=new routeListDetailCard(this);
				data=line.split(",");
				((routeListDetailCard) temp).setContent(data[0],data[1],"100min");
				temp.setId(data[0]);
				result.add(temp);
				line=br.readLine();
			}
		}
		catch (Exception e){}
		return result;
	}
	
	private ArrayList<Card> setPredictionItem(){
		ArrayList<Card> result=new ArrayList<Card>();
		if(isOnline()){
			coreAPI api=new coreAPI();
			String p=api.busPrediction(1051);
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
	
	private boolean isOnline(){
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting() && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {
			return true;}
		return false;
	}
}
