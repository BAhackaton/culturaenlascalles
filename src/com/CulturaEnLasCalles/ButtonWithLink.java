package com.CulturaEnLasCalles;

import android.content.Context;

public class ButtonWithLink extends android.widget.Button {

	private String link;
	
	public ButtonWithLink(Context context) {
		super(context);
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
