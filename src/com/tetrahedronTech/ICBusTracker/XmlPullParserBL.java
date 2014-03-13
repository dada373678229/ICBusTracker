package com.tetrahedronTech.ICBusTracker;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XmlPullParserBL {
	List<Information> Infor;
	private Information infor;
	private String text;
	
	public XmlPullParserBL(){
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
					
					if(tagname.equalsIgnoreCase("buses")){
						infor=new Information();
					}
				    break;
				    
				case XmlPullParser.TEXT:
					text=par.getText();
					break;
					
				case XmlPullParser.END_TAG:
					if(tagname.equalsIgnoreCase("bus")){
						Infor.add(infor);
					}
					 if(tagname.equalsIgnoreCase("id")){
						infor.setId(text);
					}
					 if(tagname.equalsIgnoreCase("lat")){
						infor.setLat(text);
					}
					 if(tagname.equalsIgnoreCase("lng")){
						infor.setLng(text);
					}
					 if(tagname.equalsIgnoreCase("heading")){
							infor.setHeading(text);
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

