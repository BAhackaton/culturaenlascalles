package com.CulturaEnLasCalles;

import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

public class ChooseADateRange extends Activity {
    /** Called when the activity is first created. */
	String category;
	String neighborhood;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooserange);
        Bundle data=this.getIntent().getExtras();
        if(data!=null){
            category=(data.containsKey("category"))?data.getString("category"):"";
            neighborhood=(data.containsKey("neighborhood"))?data.getString("neighborhood"):"";
        }
        //LinearLayout layout=((LinearLayout)this.findViewById(R.layout.main));
        
    }
    
    private Calendar DatePickerToCalendar(DatePicker dp){
    	Calendar re = Calendar.getInstance();
    	re.set(Calendar.DAY_OF_MONTH,dp.getDayOfMonth());
    	re.set(Calendar.MONTH,dp.getMonth()); // 0-11 so 1 less
    	re.set(Calendar.YEAR, dp.getYear());
    	return re;
    }
    public void Search(View v){
    	DatePicker from=((DatePicker)this.findViewById(R.id.datePicker1));
    	DatePicker to=((DatePicker)this.findViewById(R.id.datePicker2));
    	long elapsedDays=(DatePickerToCalendar(to).getTimeInMillis()-DatePickerToCalendar(from).getTimeInMillis())/ (24 * 60 * 60 * 1000);

    	Intent Results=new Intent(this,Results.class);
    	Results.putExtra("neighborhood",neighborhood);
    	Results.putExtra("category",category);
    	Results.putExtra("from",from.getDayOfMonth()+"-"+(from.getMonth()+1)+"-"+from.getYear());
    	Results.putExtra("length",""+(elapsedDays+1));
    	startActivity(Results);
	}
}
