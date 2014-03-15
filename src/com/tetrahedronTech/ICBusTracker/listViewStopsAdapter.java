package com.tetrahedronTech.ICBusTracker;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class listViewStopsAdapter extends ArrayAdapter<String>{
	private final Context context;
	private final List<String> values;
	
	public listViewStopsAdapter(Context listFragment, List<String> values) {
		super(listFragment, R.layout.listview_stops, values);
		this.context = listFragment;
		this.values = values;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView=inflater.inflate(R.layout.listview_stops, parent, false);
		TextView textView1=(TextView) rowView.findViewById(R.id.listview_stops_textView1);
		//TextView textView2=(TextView) rowView.findViewById(R.id.listview_stops_textView2);
		//ImageView imageView=(ImageView) rowView.findViewById(R.id.listview_stops_textView3);
		textView1.setText(values.get(position));

		return rowView;
	}
}
