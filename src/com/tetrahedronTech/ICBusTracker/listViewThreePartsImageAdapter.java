package com.tetrahedronTech.ICBusTracker;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class listViewThreePartsImageAdapter extends ArrayAdapter<String>{
	
	//values contains the data you want to display in a list
	private final Context context;
	private ArrayList<String> values;
	private ArrayList<String> values2;
	
	//a custom adapter
	public listViewThreePartsImageAdapter(Context listFragment, ArrayList<String> values, ArrayList<String> values2) {
		super(listFragment, R.layout.listview_three_parts_image, values);
		this.context = listFragment;
		this.values = values;
		this.values2=values2;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//inflate the layout, and find a specific view by id
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView=inflater.inflate(R.layout.listview_three_parts_image, parent, false);
		TextView textView1=(TextView) rowView.findViewById(R.id.listview_three_parts_image_textView1);
		TextView textView2=(TextView) rowView.findViewById(R.id.listview_three_parts_image_textView2);
		//ImageView imageView=(ImageView) rowView.findViewById(R.id.listview_stops_textView3);
		
		//set what you want to display in the view
		textView1.setText(values.get(position));
		textView2.setText(values2.get(position));

		return rowView;
	}
}
