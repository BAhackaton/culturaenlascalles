package com.CulturaEnLasCalles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends Activity{
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
	}
	
	public void NewSearch(View v)
	{
    	Intent newWindow=new Intent(this,CulturaEnLasCallesActivity.class);
    	startActivity(newWindow);
	}
	
	public void GetProfile(View v)
	{
    	Intent newWindow=new Intent(this,ChooseProfile.class);
    	startActivity(newWindow);
	}
	
}
