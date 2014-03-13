package com.tetrahedronTech.ICBusTracker;


import android.os.Bundle;
import android.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class listviewRoutes extends ListFragment { 

	private String[] routes;
	@Override
	public void setArguments(Bundle args){
		routes=args.getStringArray("key");
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.listview_routes, routes);
	    setListAdapter(adapter);
	 }
	
	@Override 
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Do something when a list item is clicked
    }
}