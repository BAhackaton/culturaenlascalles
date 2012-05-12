package com.CulturaEnLasCalles;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Service;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.widget.Toast;

public class RSSService extends Service{
	private String url;
	 private ContextWrapper ctx;
	/*class IncomingHandler extends Handler {
		 // Handler of incoming messages from clients.
		         @Override
		         public void handleMessage(Message msg) {
		            
		             animacion=msg.arg1;
		         }
		     }*/
	
	@Override
	public IBinder onBind(Intent intent) {
		Bundle data=intent.getExtras();
		if(data!=null&&data.containsKey("url"))
			url=data.getString("url");
		else
			url="";
		
		return null;
	}
	public void onCreate() {
        super.onCreate();
        getFeed(url);
    }
	
	private RSSFeed getFeed(String urlToRssFeed)
    {
    	try
    	{
    		// setup the url
    	   URL url = new URL(urlToRssFeed);
    	  
           // create the factory
           SAXParserFactory factory = SAXParserFactory.newInstance();
           // create a parser
           SAXParser parser = factory.newSAXParser();

           // create the reader (scanner)
           XMLReader xmlreader = parser.getXMLReader();
           // instantiate our handler
           RSSHandler theRssHandler = new RSSHandler();
           // assign our handler
           xmlreader.setContentHandler(theRssHandler);
           // get our data via the url class
           if (url.openStream()== null)
        	   Toast.makeText(this,"error",Toast.LENGTH_SHORT).show();
           InputSource is = new InputSource(url.openStream());
           // perform the synchronous parse           
          xmlreader.parse(is);
           // get the results - should be a fully populated RSSFeed instance, or null on error
           return theRssHandler.getFeed();
         
    	}
    	catch (Exception ee)
    	{
    		// if we have a problem, simply return null	
    		return null;
    	}
    }
	
	
	private class RSSBusqueda extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... _url) {
        	getFeed(url);
        	

            return null;

        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            Intent i = new Intent();
            i.setAction(CUSTOM_INTENT);
            i.setFlags(progress[0]);
           
			ctx.sendBroadcast(i);
        }
    }

    private String splitName(String url) {
        String[] output = url.split("/");
        return output[output.length - 1];
    }

    public static final String CUSTOM_INTENT = "es.tempos21.sync.client.ProgressReceiver";

    private final IDownloadService mBinder = new IDownloadService() {

        public void downloadAsynFile(String url) {
            try {
               RSSBusqueda d = new RSSBusqueda();
                d.execute(url);
            } catch (Exception e) {
               
               }
        }
    };

}
interface IDownloadService {

    void downloadAsynFile(String url);    
}
