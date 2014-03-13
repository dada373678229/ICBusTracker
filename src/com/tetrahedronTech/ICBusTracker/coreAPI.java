package com.tetrahedronTech.ICBusTracker;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import java.util.List;

import android.os.AsyncTask;
import android.util.Log;


public class coreAPI {
	
	private String key="xApBvduHbU8SRYvc74hJa7jO70Xx4XNO";
	private String output="";
	
	private InputStream openHttpConnection(String urlString) throws IOException
	{
		InputStream in =null;
		int response = -1;
		
		URL url =new URL(urlString);
		URLConnection conn = url.openConnection();
		
		if(!(conn instanceof HttpURLConnection))
			throw new IOException("Not an HTTP conection");
		try
		{
			HttpURLConnection httpConn =(HttpURLConnection) conn;
		    httpConn.setAllowUserInteraction(false);
		    httpConn.setInstanceFollowRedirects(true);
		    httpConn.setRequestMethod("GET");
		    httpConn.connect();
		    response=httpConn.getResponseCode();
		    if(response == HttpURLConnection.HTTP_OK) 
		    {
		    	in = httpConn.getInputStream();
		    }		    
		}
		catch (Exception ex)
		{
			Log.d("Networking",ex.getLocalizedMessage());
			throw new IOException("Error connecting");
		}
		return in;
	}
	private String toStringBL(List<Information> Infor){
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
				result+=temp.getTitle()+","+temp.getTag()+","+temp.getMinutes()+","+temp.getAgency()+","+temp.getDirection()+","+temp.getStopname()+";";
			}
			else{
				result+=temp.getTitle()+","+temp.getTag()+","+temp.getMinutes()+","+temp.getAgency()+","+temp.getDirection()+","+temp.getStopname();
			}
		}
		return result;
	}

	public String outPutBL(String URL){
		
		List<Information> Infor = null;
		
        try 
        {
            XmlPullParserBL parser = new XmlPullParserBL();
            InputStream in= openHttpConnection(URL);
            Infor = parser.parse(in);
            in.close();
        } 
        catch (IOException e) 
        {       	
        }
        return toStringBL(Infor);

	}
	
private String outPutBP(String URL){
		
		List<Information> Infor = null;
		
        try 
        {
            XmlPullParserBP parser = new XmlPullParserBP();
            InputStream in= openHttpConnection(URL);
            Infor = parser.parse(in);
            in.close();
        } 
        catch (IOException e) 
        {       	
        }
        return toStringBP(Infor);

	}
	
	private class finalOutPutBL extends AsyncTask<String,Void,String>{
    	protected String doInBackground(String... urls){
    		return outPutBL(urls[0]);
    	}    	
    	protected void onPostExecute(String result){
    		output=result;
    	}
    }
	private class finalOutPutBP extends AsyncTask<String,Void,String>{
    	protected String doInBackground(String... urls){
    		return outPutBP(urls[0]);
    	}    	
    	protected void onPostExecute(String result){
    		output=result;
    	}
    }

	public String busPrediction(int stopNumber){
		finalOutPutBP out=new finalOutPutBP();
		out.execute("http://api.ebongo.org/prediction?stopid="+stopNumber+"&api_key="+key);
		return out.toString();

	}
	
	public String busLocations(String agency, String route){
		finalOutPutBL out=new finalOutPutBL();
		out.execute("http://api.ebongo.org/buslocation?agency="+agency+"&route="+route+"&api_key="+key);
		return out.toString();
	}

}

