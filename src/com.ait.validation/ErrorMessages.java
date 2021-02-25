package com.ait.validation;

public enum ErrorMessages {
	EMPTY_FIELDS("One or more empty fields"),
	ALREADY_EXISTS("The saint with given name already exists"),
	INVALID_CENTURY("Type a valid century between 0 and 21");
	
	
	private String errorMessage;
	
	ErrorMessages(String errMsg){
		this.errorMessage=errMsg;
	}
	
	public String getMsg(){
		return errorMessage;
	}
}
