package com.CulturaEnLasCalles;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

public class ChooseADay extends Activity {
    /** Called when the activity is first created. */
	String category;
	String neighborhood;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        
        setContentView(R.layout.chooseaday);
        
        Bundle data=this.getIntent().getExtras();
        if(data!=null){
            category=(data.containsKey("category"))?data.getString("category"):"";
            neighborhood=(data.containsKey("neighborhood"))?data.getString("neighborhood"):"";
        }
        //LinearLayout layout=((LinearLayout)this.findViewById(R.layout.main));
        
    }
    
    
    public void Search(View v){
    	DatePicker picker=((DatePicker)this.findViewById(R.id.datePicker1));
    	Intent Results=new Intent(this,Results.class);
    	Results.putExtra("neighborhood",neighborhood);
    	Results.putExtra("category",category);
    	Results.putExtra("from",picker.getDayOfMonth()+"-"+(picker.getMonth()+1)+"-"+picker.getYear());
    	Results.putExtra("length","1");
    	startActivity(Results);
	}
}