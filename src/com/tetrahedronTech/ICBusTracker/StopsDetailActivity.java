package com.tetrahedronTech.ICBusTracker;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import it.gmariotti.cardslib.library.view.CardListView;

import java.util.ArrayList;

import com.tetrahedronTech.ICBusTracker.API.coreAPI;
import com.tetrahedronTech.ICBusTracker.cards.routeListDetailCard;


import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager.BadTokenException;
import android.widget.TextView;

public class StopsDetailActivity extends Activity{
	private Context context;
	//"cards" contains cards where each card has bus prediction information
	private ArrayList<Card> cards = new ArrayList<Card>();
	private ProgressDialog progressDialog;
	//errorCode -1=no error, 0=no internet connection, 1=internet timeout 2=no predictions
	private int errorCode=-1;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stops_detail);
		
		String stopTitle=getIntent().getExtras().getString("stopTitle");
		String stopId=stopTitle.split(",")[0];
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(stopTitle.split(",")[1]);
		context=this;
		//show progress dialog
		progressDialog=createProgressDialog(this);
		progressDialog.show();
		
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
				Log.i("mytag",Integer.toString(errorCode));
				if (cards.size()==0 && errorCode!=0){
					errorCode=2;
				}
				else if (cards.size()!=0 && errorCode!=0){
					errorCode=-1;
				}
				return "";
			}
			
			//since we CANNOT update user interface UNTIL the doInBackground is finished, so we
			//have to update the UI AFTER getting the predictions
			@Override
	        protected void onPostExecute(String result) {
				//if the get any predictions, set the cardlist
				if (errorCode==-1){
					CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(context,cards);
					CardListView listView = (CardListView) findViewById(R.id.stopDetailListView);
			        if (listView!=null){
			            listView.setAdapter(mCardArrayAdapter);
			        }
				}
				
				progressDialog.cancel();
				errorHandler(errorCode);
				
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
				errorCode=-1;
				coreAPI api=new coreAPI();
				String p=api.busPrediction(stopId);
				if (p.length()==0) return result;
				String data[]=p.split(";");
				for (int i=0; i<data.length;i++){
					CardExpand expand = new CardExpand(this);
					expand.setInnerLayout(R.layout.stop_detail_expand_layout);
					Card temp=new routeListDetailCard(this);
					temp.addCardExpand(expand);
					ViewToClickToExpand viewToClickToExpand = ViewToClickToExpand.builder().setupCardElement(ViewToClickToExpand.CardElementUI.CARD);
		            temp.setViewToClickToExpand(viewToClickToExpand);
					
					String line[]=data[i].split(",");
					((routeListDetailCard) temp).setContent(line[0],line[2],line[1]);
					result.add(temp);
				}
			}
			else{
				errorCode=0;
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
		
		//thie method shows a progress dialog
		private static ProgressDialog createProgressDialog(Context mContext) {
	        ProgressDialog dialog = new ProgressDialog(mContext);
	        try {
	                dialog.show();
	        } catch (BadTokenException e) {

	        }
	        dialog.setCancelable(false);
	        dialog.setContentView(R.layout.progress_dialog);
	        // dialog.setMessage(Message);
	        return dialog;
		}
		
		private void errorHandler(int errorCode){
			if (errorCode!=-1){
				TextView t = (TextView) this.findViewById(R.id.stop_detail_textView);
				switch (errorCode){
				case 0: t.setText("No Internet Connection");
						break;
				case 1: t.setText("Internet Timeout");
						break;
				case 2: t.setText("No Upcomming Buses");
						break;
				}
			}
		}
}
