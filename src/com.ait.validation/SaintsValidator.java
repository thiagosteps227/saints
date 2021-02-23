package com.ait.validation;

import com.ait.saints.Saints;

public class SaintsValidator {
	Saints saint;
	
	public void validateSaint(Saints saint) throws SaintValidationException {
		this.saint = saint;
		checkEmptyFields(saint);
	}

	
	public void checkEmptyFields(Saints saint) throws SaintValidationException {
		
		if(saint.getName().length() == 0 || saint.getCountry().length() == 0 || saint.getCity().length() == 0 || 
		saint.getPicture().length() == 0 || saint.getDescription().length() == 0 || saint.getCentury() == 0) {
			throw new SaintValidationException(ErrorMessages.EMPTY_FIELDS.getMsg());
		}
	}
}
