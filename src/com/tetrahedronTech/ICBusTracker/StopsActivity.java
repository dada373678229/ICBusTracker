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
import android.content.res.AssetManager;
import android.os.Bundle;

public class StopsActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stops);
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("stops");
		
		ArrayList<Card> cards = new ArrayList<Card>();
		cards =setListItem();
		
		CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(this,cards);
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
	
	private ArrayList<Card> setListItem(){
		ArrayList<Card> result=new ArrayList<Card>();
		try{
			AssetManager am=this.getAssets();
			InputStream in = am.open("allStops.txt");
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br= new BufferedReader(isr);
			String line = br.readLine();
			String data[];
			while (line != null){
				Card temp=new stopListCard(this);
				data=line.split(",");
				((stopListCard) temp).setContent(data[0],data[1]);
				temp.setId(data[0]);
				result.add(temp);
				line=br.readLine();
			}
		}
		catch (Exception e){}
		return result;
	}
}
