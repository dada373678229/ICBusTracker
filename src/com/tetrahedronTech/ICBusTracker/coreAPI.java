package com.tetrahedronTech.ICBusTracker;

import java.util.List;

public class coreAPI 
{	
	private String key="xApBvduHbU8SRYvc74hJa7jO70Xx4XNO";
	private String output="";
	
	private String toStringBL(List<Information> Infor)
	{
		String result="";
		int listSize=Infor.size();
		for (int i=0;i<listSize;i++){
			Information temp=Infor.get(i);
			if(i<listSize-1){
				result+=temp.getId()+","+temp.getLat()+","+temp.getLng()+","+temp.getHeading()+";";
			}
			else{
				result+=temp.getId()+","+temp.getLat()+","+temp.getLng()+","+temp.getHeading();
			}
		}
		return result;
	}
	
	private String toStringBP(List<Information> Infor){
		String result="";
		int listSize=Infor.size();
		for (int i=0;i<listSize;i++){
			Information temp=Infor.get(i);
			if(i<listSize-1){
				result+=temp.getTitle()+","+temp.getMinutes()+","+temp.getAgency()+","+temp.getDirection()+","+temp.getStopname()+";";
			}
			else{
				result+=temp.getTitle()+","+temp.getMinutes()+","+temp.getAgency()+","+temp.getDirection()+","+temp.getStopname();
			}
		}
		return result;
	}
	
	public String busPrediction(int stopNumber)
	{	
		String finalUrl ="http://api.ebongo.org/prediction?stopid="+stopNumber+"&api_key="+key;
	    xmlPullParserBP obj = new xmlPullParserBP(finalUrl);
	    obj.fetchXML();
	    while(obj.parsingComplete);
		output=toStringBP(obj.getInfor());
		return output;

	}
	
	public String busLocations(String agency, String route)
	{
		String finalUrl ="http://api.ebongo.org/buslocation?agency="+agency+"&route="+route+"&api_key="+key;
		xmlPullParserBL obj = new xmlPullParserBL(finalUrl);
	    obj.fetchXML();
	    while(obj.parsingComplete);
		output=toStringBL(obj.getInfor());
		return output;
	}	
}
