/**
 * 
 */
package com.data.generator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.data.generator.exceptions.GeneratorException;
import com.data.generator.utility.ErrorMessagesUtil;


/**
 * @author LPT-2492
 *
 */
public class ExceptionThrower {
	
	@Autowired
	ErrorMessagesUtil errorMessageUtil;

	public void throwPropertiesNotFoundException() throws GeneratorException {
		throwGeneratorException(GeneratorErrorConstants.PREFIX + GeneratorErrorConstants.PROPERTY_NOT_FOUND,
				HttpStatus.BAD_REQUEST);
	}

	public void throwGeneratorException(String code, HttpStatus status) throws GeneratorException {
		throw new GeneratorException(Integer.valueOf(code), errorMessageUtil.getErrorMessage(code) , status);
	}
}
