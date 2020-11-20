/**
 * 
 */
package com.data.generator.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

/**
 * @author LPT-2492
 *
 */
@Getter
@Setter
public class GeneratorException extends Exception {
	
	private Integer code;
	private HttpStatus httpStatus;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public GeneratorException() {

	}
	
	public GeneratorException(Integer code, String message, HttpStatus httpStatus) {
		this(message);
		this.code=code;
		this.httpStatus=httpStatus;
	}

	/**
	 * @param message
	 */
	public GeneratorException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public GeneratorException(Throwable cause) {
		super(cause);

	}

	/**
	 * @param message
	 * @param cause
	 */
	public GeneratorException(String message, Throwable cause) {
		super(message, cause);

	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public GeneratorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
