package com.tetrahedronTech.ICBusTracker.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tetrahedronTech.ICBusTracker.R;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.Card.OnCardClickListener;

public class routeListDetailCard extends Card{
	protected TextView routeNameView;
    protected TextView routeDirectionView;
    protected TextView routeTimeView;
    
    private String routeName;
    private String routeDirection;
    private String routeTime;

	public routeListDetailCard(Context context) {
        this(context, R.layout.route_list_detail_card);
    }
	
	public routeListDetailCard(Context context, int layout){
		super(context, layout);
        init();
	}
	
	private void init(){

        //No Header

        //Set a OnClickListener listener
        setOnClickListener(new OnCardClickListener() {
			@Override
			public void onClick(Card card, View view) {
				// TODO Auto-generated method stub
				Toast.makeText(getContext(), "Route "+card.getId()+" clicked", Toast.LENGTH_SHORT).show();
			}
        });
    }
	
	@Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        //Retrieve elements
        routeNameView = (TextView) parent.findViewById(R.id.route_list_view_detail_routeName);
        routeDirectionView=(TextView) parent.findViewById(R.id.route_list_view_detail_routeDirection);
        routeTimeView = (TextView) parent.findViewById(R.id.route_list_view_detail_routeTime);


        if (routeName!=null && routeDirection!=null && routeTime!=null)
            routeNameView.setText(routeName);
        	routeDirectionView.setText(routeDirection);
        	routeTimeView.setText(routeTime);
    }
	
	public void setContent(String routeName, String routeDirection, String routeTime){
		this.routeName=routeName;
		this.routeDirection=routeDirection;
		this.routeTime=routeTime;
	}
}
