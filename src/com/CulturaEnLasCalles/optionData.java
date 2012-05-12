package com.CulturaEnLasCalles;

public class optionData {
	private int id;
	private String description;
	private String internalCategoryValue;
	public optionData(){
		
	}
	public optionData(int id,String description,String internalCategoryValue){
		this.id=id;
		this.description=description;
		this.internalCategoryValue=internalCategoryValue;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getInternalCategoryValue() {
		return internalCategoryValue;
	}
	public void setInternalCategoryValue(String internalCategoryValue) {
		this.internalCategoryValue = internalCategoryValue;
	}
}
