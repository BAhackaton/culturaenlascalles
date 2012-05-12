package com.CulturaEnLasCalles;

import android.content.Context;
import android.widget.Button;

public class ProfileButton extends Button {

	private String category;
	private String neighborhood;
	
	public ProfileButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}
	
}
