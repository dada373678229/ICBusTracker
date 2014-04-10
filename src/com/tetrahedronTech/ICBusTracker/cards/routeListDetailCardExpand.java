package com.tetrahedronTech.ICBusTracker.cards;

import com.tetrahedronTech.ICBusTracker.R;
import com.tetrahedronTech.ICBusTracker.API.coreAPI;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;
import it.gmariotti.cardslib.library.internal.CardExpand;

public class routeListDetailCardExpand extends CardExpand implements NumberPicker.OnValueChangeListener{
	
	private String routeName="";
	private String min="";
	private int stopId=-1;
	private int alertTime=-1;
	private int upperBound=-1;
	private coreAPI api=new coreAPI();
	//-1=no error, 0=times up, 1=lose bus prediction, 2=Internet Problem
	private int errorCode=-1;
	
	public routeListDetailCardExpand(Context context) {
        super(context, R.layout.stop_detail_expand_layout);
    }
	
	@Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        if (view == null) return;

        //Retrieve button elements
        Button button=(Button) view.findViewById(R.id.alarm);
        button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showNumberPicker();
			}
		});
    }
	
	public void setContent(String routeName, String min, int stopId){
		this.routeName=routeName;
		this.min=min;
		this.stopId=stopId;
	}
	
	private void showNumberPicker(){
		Log.i("mytag","time:before"+Integer.toString(alertTime));
		final Dialog d = new Dialog(getContext());
        d.setTitle("Remind me when the bus is");
        d.setContentView(R.layout.number_picker_layout);
        Button cancel = (Button) d.findViewById(R.id.cancel_btn);
        Button set = (Button) d.findViewById(R.id.set_btn);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.number_picker);
        np.setMaxValue(20);
        np.setMinValue(2);
        np.setWrapSelectorWheel(true);
        np.setOnValueChangedListener(this);
        cancel.setOnClickListener(new OnClickListener()
        {
         @Override
         public void onClick(View v) {
             d.dismiss();
          }    
         });
        set.setOnClickListener(new OnClickListener()
        {
         @Override
         public void onClick(View v) {
        	 alertTime=np.getValue();
        	 Toast.makeText(getContext(), "Alarm set for "+routeName+" when it is "+Integer.toString(alertTime)+" minutes away!", Toast.LENGTH_SHORT).show();
        	 setUpAlarm();
             d.dismiss();
          }    
         });
      d.show();
	}
	
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
    }
	
	private void setUpAlarm(){
		upperBound=Integer.valueOf(min);
		new alarmOperation().execute("");
	}
	
	private class alarmOperation extends AsyncTask<String, Void, String> {
		@Override
        protected String doInBackground(String... params) {
			while(errorCode==-1){
				String lines=api.busPrediction(stopId);
				checkTerminate(lines);
			}
			return "";
		}
	}
	
	private void checkTerminate(String lines){
		if (lines.length()==0){
			errorCode=1;
		}
	}
		
}
