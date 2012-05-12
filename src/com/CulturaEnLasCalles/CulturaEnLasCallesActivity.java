package com.CulturaEnLasCalles;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class CulturaEnLasCallesActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        LinearLayout layout=((LinearLayout)this.findViewById(R.id.listacategorias));
        for(int x=0;x<globalData.categories.length;x++){
        	ButtonWithID aux=new ButtonWithID(this);
        	aux.setText(globalData.categories[x].getDescription());
        	aux.setresultID(globalData.categories[x].getId()+"");
        	aux.setInternalName(globalData.categories[x].getInternalCategoryValue());
        	aux.setOnClickListener(new Button.OnClickListener() 
            {
                public void onClick(View v) 
                {
                	String category=(String) ((ButtonWithID)v).getInternalName();
                	Intent NeighborhoodChooser=new Intent(v.getContext(),NeighborhoodChooser.class);
                	NeighborhoodChooser.putExtra("category",category.equalsIgnoreCase("todos")?"":category);
                	startActivity(NeighborhoodChooser);
                }
            });
        	layout.addView(aux);
        }
        
    }
    
    public void getCategory(View v){
    	String category=(String) ((Button)v).getText();
    	category=category.replace(' ', '-').toLowerCase();
    	Intent NeighborhoodChooser=new Intent(this,NeighborhoodChooser.class);
    	NeighborhoodChooser.putExtra("category",category.equalsIgnoreCase("todos")?"":category);
    	startActivity(NeighborhoodChooser);
    }
}