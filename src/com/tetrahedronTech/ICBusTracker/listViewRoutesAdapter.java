package com.tetrahedronTech.ICBusTracker;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class listViewRoutesAdapter extends ArrayAdapter<String>{
	private final Context context;
	private final List<String> values;
	
	public listViewRoutesAdapter(Context listFragment, List<String> values) {
		super(listFragment, R.layout.listview_routes, values);
		this.context = listFragment;
		this.values = values;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView=inflater.inflate(R.layout.listview_routes, parent, false);
		TextView textView1=(TextView) rowView.findViewById(R.id.listview_routes_textView1);
		//TextView textView2=(TextView) rowView.findViewById(R.id.listview_routes_textView2);
		//ImageView imageView=(ImageView) rowView.findViewById(R.id.listview_routes_imageView);
		textView1.setText(values.get(position));

		return rowView;
	}
}
