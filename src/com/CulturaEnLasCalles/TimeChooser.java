package com.CulturaEnLasCalles;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TimeChooser extends Activity {
    /** Called when the activity is first created. */
	String category;
	String neighborhood;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timechooser);
        Bundle data=this.getIntent().getExtras();
        if(data!=null){
            category=(data.containsKey("category"))?data.getString("category"):"";
            neighborhood=(data.containsKey("neighborhood"))?data.getString("neighborhood"):"";
        }
        //LinearLayout layout=((LinearLayout)this.findViewById(R.layout.main));
    	
    }
    

    public void ChooseADay(View v){
    	Intent ChooseADay=new Intent(this,ChooseADay.class);
    	ChooseADay.putExtra("neighborhood",neighborhood);
    	ChooseADay.putExtra("category",category);
    	startActivity(ChooseADay);
    }
    
    public void ChooseADateRange(View v){
    	Intent ChooseADateRange=new Intent(this,ChooseADateRange.class);
    	ChooseADateRange.putExtra("neighborhood",neighborhood);
    	ChooseADateRange.putExtra("category",category);
    	startActivity(ChooseADateRange);
    }
    
    public void Today(View v){
    	goToResult("1");
    }
	
    public void Someday(View v){
    	goToResult("365");
    }
    
    private void goToResult(String cant) {
		Calendar hoy= new GregorianCalendar();
    	hoy.setTime(new Date());
    	
    	Intent Results=new Intent(this,Results.class);
    	Results.putExtra("neighborhood",neighborhood);
    	Results.putExtra("category",category);
    	Results.putExtra("from",hoy.get(Calendar.DAY_OF_MONTH)+"-"+(hoy.get(Calendar.MONTH)+1)+"-"+hoy.get(Calendar.YEAR));
    	Results.putExtra("length",cant);
    	startActivity(Results);
	}

}
