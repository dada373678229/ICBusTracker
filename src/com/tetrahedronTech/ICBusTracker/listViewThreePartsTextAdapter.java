package com.tetrahedronTech.ICBusTracker;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class listViewThreePartsTextAdapter extends ArrayAdapter<String>{
	
	//values contains the data you want to display in a list
	private final Context context;
	private final List<String> values;
	private final List<String> values2;
	private final List<String> values3;
	
	//a custom adapter
	public listViewThreePartsTextAdapter(Context listFragment, List<String> values, List<String> values2,List<String> values3) {
		super(listFragment, R.layout.listview_three_parts_text, values);
		this.context = listFragment;
		this.values = values;
		this.values2=values2;
		this.values3=values3;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//inflate the layout, and find a specific view by id
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView=inflater.inflate(R.layout.listview_three_parts_text, parent, false);
		TextView textView1=(TextView) rowView.findViewById(R.id.listview_three_parts_text_textView1);
		TextView textView2=(TextView) rowView.findViewById(R.id.listview_three_parts_text_textView2);
		TextView textView3=(TextView) rowView.findViewById(R.id.listview_three_parts_text_textView3);
		
		//set what you want to display in the view
		textView1.setText(values.get(position));
		textView2.setText(values2.get(position));
		textView3.setText(values3.get(position));

		return rowView;
	}
}
