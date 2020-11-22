package com.data.generator.randomiser;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.data.generator.models.Patterns;

public interface Randomiser {
	
	public final static Logger LOGGER = LoggerFactory.getLogger(Randomiser.class); 
	
	default boolean isDate(String date) {
		try {new Date(getDate(date));
			 return true;
		}catch(IllegalArgumentException | ParseException e) {
			LOGGER.debug(e.getMessage());
			return false;
		}
	}
	
	default boolean isDateFormat(String format) {
		try {new SimpleDateFormat(format);
			 return true;}
		catch(IllegalArgumentException ex) {
			LOGGER.debug(ex.getMessage());
			return false;
		}
	}
	
	default boolean isNumberFormat(String pattern) {
		try {NumberFormat.getNumberInstance().parse(pattern);
			 return true;
		}catch(ParseException e) {
			LOGGER.debug(e.getMessage());
			return false;
		}		
	}
	
	default boolean isTime(Long time) {
		try {
			if(time.toString().length() == 13) {
					new Date(time);
					return true;}
			else 	return false;
		}catch(NumberFormatException e) {
			LOGGER.debug(e.getMessage());
			return false;
		}
	}
	
	default String formatDate(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}
	
	default long  getDate(String date) throws ParseException {
		return new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse(date).getTime();
	}
	
	default boolean isReference(String key, Map<String, Object> map){
		if(key.startsWith("$") && key.endsWith("$") && map.containsKey(key.substring(1, key.length()-1)))
		      return true;
		else  return false; 
	}

	String randomiseData(int count) throws Exception;
	
	public void setPattern(Patterns patterns);

}
