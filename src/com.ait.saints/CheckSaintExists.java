package com.ait.saints;

import java.util.List;

import com.ait.validation.ErrorMessages;
import com.ait.validation.SaintValidationException;


public class CheckSaintExists {
	Saints saint;
	List<Saints> saints;

	public void checkSaintExists(Saints saint, List<Saints> saints) throws SaintValidationException {
		this.saint = saint;
		this.saints = saints;

		for (Saints s : saints) {
			if ((s.getName()).equals(saint.getName())) {
				throw new SaintValidationException(ErrorMessages.ALREADY_EXISTS.getMsg());
			}
		}
	}
}
