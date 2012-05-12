package com.CulturaEnLasCalles;

import android.content.Context;

public class ButtonWithID extends android.widget.Button {

	private String resultID;

	private String internalName;
	
	
	public ButtonWithID(Context context) {
		super(context);
	}

	public String getresultID() {
		return resultID;
	}

	public void setresultID(String resultID) {
		this.resultID = resultID;
	}

	public String getInternalName() {
		return internalName;
	}

	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}
	
	

}
