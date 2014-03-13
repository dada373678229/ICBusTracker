package com.tetrahedronTech.ICBusTracker;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XmlPullParserBP {
	List<Information> Infor;
	private Information infor;
	private String text;
	
	public XmlPullParserBP(){
		Infor=new ArrayList<Information>();
	}
	public List<Information> getInfor(){
		return Infor;
	}
	
	public List<Information>parse(InputStream in){
		XmlPullParserFactory fac=null;
		
		XmlPullParser par=null;
		
		try{
			fac=XmlPullParserFactory.newInstance();
			fac.setNamespaceAware(true);
			par=fac.newPullParser();
			par.setInput(in, null);
			int event=par.getEventType();
			
			while(event!=XmlPullParser.END_DOCUMENT)
			{
				String tagname=par.getName();
				
				switch(event)
				{
				case XmlPullParser.START_TAG:
					
					if(tagname.equalsIgnoreCase("prediction")){
						infor=new Information();
					}
				    break;
				    
				case XmlPullParser.TEXT:
					text=par.getText();
					break;
					
				case XmlPullParser.END_TAG:
					if(tagname.equalsIgnoreCase("prediction")){
						Infor.add(infor);
					}
					 if(tagname.equalsIgnoreCase("title")){
						infor.setTitle(text);
					}
					 if(tagname.equalsIgnoreCase("tag")){
						infor.setTag(text);
					}
					 if(tagname.equalsIgnoreCase("minutes")){
						infor.setMinutes(text);
					}
					 if(tagname.equalsIgnoreCase("agency")){
							infor.setAgency(text);
				    }
					 if(tagname.equalsIgnoreCase("direction")){
							infor.setDirection(text);
					}
					 if(tagname.equalsIgnoreCase("stopname")){
							infor.setStopname(text);
					}
					break;
					
				default:
					break;
				}
				event=par.next();
			}
		}
		catch(XmlPullParserException e){}
		catch(IOException e){}
		return Infor;
	}

}

