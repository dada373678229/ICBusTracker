package com.tetrahedronTech.ICBusTracker;


import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Fragment;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class listViewThreePartsText extends ListFragment { 

	//values contains the data you want to display in a list
	private List<String> values=new ArrayList<String>();
	private List<String> values2=new ArrayList<String>();
	private List<String> values3=new ArrayList<String>();
	private listViewThreePartsTextAdapter myAdapter;
	
	//receive data from outside and store it into values
	@Override
	public void setArguments(Bundle args){
		values=args.getStringArrayList("key");
		values2=args.getStringArrayList("key2");
		values3=args.getStringArrayList("key3");
		//myAdapter.clear();
		//myAdapter.addAll(values);
		//myAdapter.notifyDataSetChanged();
		myAdapter=new listViewThreePartsTextAdapter(getActivity(), values, values2, values3);
	    setListAdapter(myAdapter);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	}
	/*
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		//super.onCreate(savedInstanceState);
	    //listAdapter myAdapter=new listAdapter(getActivity(), values);
	    //setListAdapter(myAdapter);
		return inflater.inflate(R.layout.list_fragment, container, false);
	}*/
	
	//when a list item is clicked, what you want to do
	@Override
	public void onListItemClick(ListView l, View v, int pos, long id) {
	  super.onListItemClick(l, v, pos, id);
	  
	}
	
}