package com.CulturaEnLasCalles;

import android.content.Context;

public class EventButton extends android.widget.Button {

	private String title;
	private String description;
	private long milliseconds;
	
	public EventButton(Context context) {
		super(context);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getMilliseconds() {
		return milliseconds;
	}

	public void setMilliseconds(long milliseconds) {
		this.milliseconds = milliseconds;
	}

}
