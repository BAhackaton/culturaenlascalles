package com.CulturaEnLasCalles;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ChooseProfile extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.chooseprofile);
        SQLHelper sql=new SQLHelper(this, "DB", null, 1);
        SQLiteDatabase db=sql.getReadableDatabase();
        Cursor response=db.rawQuery("select * from Profiles", null);
        response.moveToFirst();
        LinearLayout layout=((LinearLayout)this.findViewById(R.id.profilelist));
        do{
        	ProfileButton aux=new ProfileButton(this);
            aux.setText("Categoria: "+((response.getString(0).equals(""))?"Todos":response.getString(0).replace('-', ' '))+". Barrio: "+((response.getString(1).equals(""))?"Todos":response.getString(1).replace('-', ' ')));
            aux.setCategory(response.getString(0));
            aux.setNeighborhood(response.getString(1));
            aux.setOnClickListener(new Button.OnClickListener() 
            {
                public void onClick(View v) 
                {
                	String category=((ProfileButton)v).getCategory();
                	String neighborhood=((ProfileButton)v).getNeighborhood();
                	neighborhood=neighborhood.replace(' ', '-').toLowerCase();
                	Intent TimeChooser=new Intent(v.getContext(),TimeChooser.class);
                	TimeChooser.putExtra("neighborhood",neighborhood.equalsIgnoreCase("todos")?"":neighborhood);
                	TimeChooser.putExtra("category",category);
                	startActivity(TimeChooser);
                }
            });
            layout.addView(aux);
        	response.moveToNext();
        }while(!response.isAfterLast());
        db.close();
	}
}
