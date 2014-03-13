package com.tetrahedronTech.ICBusTracker;


import android.os.Bundle;
import android.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class listviewStops extends ListFragment { 

	private String[] stops;
	@Override
	public void setArguments(Bundle args){
		stops=args.getStringArray("key");
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.listview_stops, stops);
	    setListAdapter(adapter);
	 }
	
	@Override 
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Do something when a list item is clicked
    }
}