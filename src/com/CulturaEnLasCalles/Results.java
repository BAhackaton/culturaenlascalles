package com.CulturaEnLasCalles;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.CulturaEnLasCalles.RSSFeed;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Results extends Activity {
    /** Called when the activity is first created. */
	
	ProgressDialog progressBar;
	private int progressBarStatus = 0;
	private Handler progressBarHandler = new Handler();
	private boolean mReceiversRegistered =false;
	String category;
	String neighborhood;
	String from;
	String to;
	String url;
    String calName;
    String calId;
	RSSFeed resultados;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*String[] projection = new String[] { "_id", "name" };
        Uri calendars = Uri.parse("content://calendar/calendars");
           
        Cursor managedCursor =
          managedQuery(calendars, projection,
          null, null, null);
        if (managedCursor.moveToFirst()) {
        int nameColumn = managedCursor.getColumnIndex("name");
        int idColumn = managedCursor.getColumnIndex("_id");
        do {
           calName = managedCursor.getString(nameColumn);
           calId = managedCursor.getString(idColumn);
        } while (managedCursor.moveToNext());
        }*/
        calId="1";
       // setContentView(R.layout.results);
       		//reset progress bar status
		progressBarStatus = 0;
		   //setContentView(R.layout.loading);
        Bundle data=this.getIntent().getExtras();
        if(data!=null){
            category=(data.containsKey("category"))?data.getString("category"):"";
            neighborhood=(data.containsKey("neighborhood"))?data.getString("neighborhood"):"";
            from=(data.containsKey("from"))?data.getString("from"):"";
            to=(data.containsKey("length"))?data.getString("length"):"";
        }
        setContentView(R.layout.results);
        
        
        ((Button)this.findViewById(R.id.save)).setEnabled(true);
        LinearLayout layout=((LinearLayout)this.findViewById(R.id.result));
        long milli=convert(from).getTimeInMillis();
        //Toast.makeText(this, from, Toast.LENGTH_LONG).show();
       for(int dias=0;dias<Integer.parseInt(to);dias++){
    	   Calendar wantedDay=Calendar.getInstance();
    	   wantedDay.setTimeInMillis(milli);
    	   url="http://agendacultural.buenosaires.gob.ar/static/rssgen.php?";
           if(category!=""){
           	url+="categoria="+category+"&";
           }
           if(neighborhood!=""){
           	url+="barrio="+neighborhood+"&";
           }
           if(from!=""){
           	url+="fechaDesde="+wantedDay.get(Calendar.DAY_OF_MONTH)+"-"+(wantedDay.get(Calendar.MONTH)+1)+"-"+wantedDay.get(Calendar.YEAR)+"&";
           }
           if(to!=""){
           	url+="dias=1&";
           }
           url+="limite=20&";
           url=url.substring(0, url.length()-1);
           resultados= new RSSFeed();
           resultados=	this.getFeed(url);
        if (resultados==null)
        	
        	Toast.makeText(layout.getContext(),"Error al acceder al sistema de agenda cultural. Por favor re intente mas tarde." ,Toast.LENGTH_SHORT).show();
        else
        	if(resultados.getItemCount()==0){
        		TextView aux=new TextView(this);
	            aux.setText("No se han obtenido resultados");
	            layout.addView(aux);
        	}
        	else
	        for (int i=0; i< resultados.getItemCount(); i++){
	            ButtonWithLink aux=new ButtonWithLink(this);
	            EventButton addEvent=new EventButton(this);
	            aux.setText(resultados.getItem(i).getTitle());
	            aux.setLink(resultados.getItem(i).getLink());
	            addEvent.setText("+");
	            addEvent.setDescription(resultados.getItem(i).getDescription());
	            addEvent.setTitle(resultados.getItem(i).getTitle());
	            addEvent.setMilliseconds(milli);
	            aux.setOnClickListener(new Button.OnClickListener() 
	            {
	                public void onClick(View v) 
	                {
	                	Intent browserIntent=new Intent(Intent.ACTION_VIEW,Uri.parse(((ButtonWithLink)v).getLink()));
	                	startActivity(browserIntent);
	                }
	            });
	            addEvent.setOnClickListener(new Button.OnClickListener() 
	            {
	                public void onClick(View v) 
	                {
	                	/*ContentValues event = new ContentValues();
	                	event.put("calendar_id", calId);
	                	event.put("title", ((EventButton)v).getTitle());
	                	event.put("description", ((EventButton)v).getDescription());
	                	event.put("eventLocation", "lugar dado por el rss. ejemplo: teatro colon");
	                	event.put("dtstart", fromDate.getTime());
	                	event.put("dtend", fromDate.getTime()+24*60*60*1000);
	                	event.put("eventStatus", 0);
	                	event.put("transparency", 0);
	                	event.put("hasAlarm", 1);
	                	Uri eventsUri = Uri.parse("content://calendar/events");
	                	Uri url = getContentResolver().insert(eventsUri, event);*/
	                    
	                	Intent intent = new Intent(Intent.ACTION_EDIT);
	                	intent.setType("vnd.android.cursor.item/event");
	                	intent.putExtra("beginTime", ((EventButton)v).getMilliseconds());
	                	intent.putExtra("allDay", false);
	                	intent.putExtra("endTime", ((EventButton)v).getMilliseconds()+24*60*60*1000);
	                	intent.putExtra("title", ((EventButton)v).getTitle());
	                	startActivity(intent);

	                }
	            });
	            LinearLayout row=new LinearLayout(this);
	            row.setOrientation(LinearLayout.HORIZONTAL);
	            row.addView(aux); 
	            row.addView(addEvent);
	            layout.addView(row);
	            milli+=24*60*60*1000;
	        }
       }
              
	            
	        
        	//Toast.makeText(this,""+resultados.getItemCount(),Toast.LENGTH_SHORT).show();

    }
    /*public void onResume()
    {super.onResume();
    IntentFilter intentToReceiveFilter = new IntentFilter();
    intentToReceiveFilter.addAction(RSSService.class.getSimpleName());
    this.registerReceiver(mIntentReceiver, intentToReceiveFilter, null, progressBarHandler);
    mReceiversRegistered = true;
    }*/
    public void SaveProfile(View v){
    	SQLHelper sql=new SQLHelper(this, "DB", null, 1);
    	SQLiteDatabase db=sql.getReadableDatabase();
        Cursor response=db.rawQuery("select rowid from Profiles where Category='"+category+"' and Neighborhood='"+neighborhood+"'", null);
        if(response.getCount()==0){
        	db.close();
	        db=sql.getWritableDatabase();
	        db.execSQL("INSERT INTO Profiles (Category,Neighborhood) VALUES('"+category+"','"+neighborhood+"')");
        }
        db.close();
        Toast.makeText(v.getContext(), "Guardado exitoso", Toast.LENGTH_SHORT).show();
        ((Button)v).setEnabled(false);
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
    private Calendar convert(String d){
    	String[] time=d.split("-");
    	Calendar re=Calendar.getInstance();
    	re.set(Integer.parseInt(time[2]),Integer.parseInt(time[1]),Integer.parseInt(time[0]),0,0);
    	if(re.before(Calendar.getInstance())){
    		re=Calendar.getInstance();
        	re.set(Integer.parseInt(time[2]),Integer.parseInt(time[1]),Integer.parseInt(time[0]));
    	}
    	return re;
    }
    
    /*private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(RSSService.class.getSimpleName())) {
            	progressBar.setProgress(100);
            	if(progressBar.getProgress()>=100){
            		progressBar.dismiss();
            	}
            }
        }
    };

    
    private void crearProgress()
    {
    	
    	progressBar = new ProgressDialog(this);
    	
 		progressBar.setCancelable(true);
 		progressBar.setMessage("Buscando resultados ...");
 		progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
 		progressBar.setProgress(0);
 		progressBar.setMax(100);
 		progressBar.show();
 		new Thread(new Runnable() {
			  public void run() {
				while (progressBarStatus < 100) {

				  // process some tasks
				  progressBarStatus = busqueda();

				  // your computer is too fast, sleep 1 second
				  try {
					Thread.sleep(1000);
				  } catch (InterruptedException e) {
					e.printStackTrace();
				  }

				  // Update the progress bar
				  progressBarHandler.post(new Runnable() {
					public void run() {
					  progressBar.setProgress(progressBarStatus);
					}
				  });
				}

				// ok, file is downloaded,
				if (progressBarStatus >= 100) {

					// sleep 2 seconds, so that you can see the 100%
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					// close the progress bar dialog
					progressBar.dismiss();
				}
			  }
		       }).start();

	           }*/
  
    
    public int busqueda() {
    	 
    	
    	setContentView(R.layout.results);
        
        url="http://agendacultural.buenosaires.gob.ar/static/rssgen.php?";
        if(category!=""){
        	url+="categoria="+category+"&";
        }
        if(neighborhood!=""){
        	url+="barrio="+neighborhood+"&";
        }
        if(from!=""){
        	url+="fechaDesde="+from+"&";
        }
        if(to!=""){
        	url+="dias="+to+"&";
        }
        url+="limite=20&";
        url=url.substring(0, url.length()-1);
        ((Button)this.findViewById(R.id.save)).setEnabled(true);
        resultados= new RSSFeed();
        resultados=	this.getFeed(url);
        LinearLayout layout=((LinearLayout)this.findViewById(R.id.result));
       
        /*if (resultados==null)
        	
        	Toast.makeText(layout.getContext(),"Error en el sistema de agenda cultural. Por favor re intente mas tarde." ,Toast.LENGTH_SHORT).show();
        else*/
        if (resultados!=null)
        	if(resultados.getItemCount()==0){
        		TextView aux=new TextView(layout.getContext());
	            aux.setText("No se han obtenido resultados");
	            layout.addView(aux);
        	}
        	else
	        for (int i=0; i< resultados.getItemCount(); i++){
	            ButtonWithLink aux=new ButtonWithLink(layout.getContext());
	            aux.setText(resultados.getItem(i).getTitle());
	            aux.setLink(resultados.getItem(i).getLink());
	            aux.setOnClickListener(new Button.OnClickListener() 
	            {
	                public void onClick(View v) 
	                {
	                	Intent browserIntent=new Intent(Intent.ACTION_VIEW,Uri.parse(((ButtonWithLink)v).getLink()));
	                	startActivity(browserIntent);
	                }
	            });
	            layout.addView(aux); 

		
 
	}
    
        return 100;
    
	}
    }

              
    
    


