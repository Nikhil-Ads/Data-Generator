package com.data.generator.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ErrorMessagesUtil {

	@Autowired
	MessageSource messageSource;

	public String getErrorMessage(String messageKey) {
		try {
			return messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
		} catch (NoSuchMessageException ex) {
			return "There is some problem at the server end. Please contact administrator.";
		}
	}

	public String getErrorMessage(int errorCode) {
		String messageKey = "" + errorCode;
		try {
			return messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
		} catch (NoSuchMessageException ex) {
			return "There is some problem at the server end. Please contact administrator.";
		}
	}
}