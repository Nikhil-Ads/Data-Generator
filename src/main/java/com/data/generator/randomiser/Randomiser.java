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

	default boolean isNumber(String number) {
		try { Double.parseDouble(number);
			  return true;			
		}catch(NumberFormatException e) {
			  LOGGER.debug(e.getMessage());
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
	
	default boolean isRefExpression(String key) {
		int difference = key.length() - key.replaceAll("[$]", "").length();
		LOGGER.debug("Key passed to check reference: "+key);
		LOGGER.debug("Difference: "+difference);
		if((difference / 2 > 0) && (difference % 2 == 0))
				return true;
		else	return false;
	}
	
	default boolean isReference(String key){
		if(key.startsWith("$") && key.endsWith("$"))
		      return true;
		else  return false; 
	}
	
	default String formatDate(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}
	
	default long  getDate(String date) throws ParseException {
		return new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse(date).getTime();
	}
	
	default long getDate(String date,String format) throws ParseException{
		return new SimpleDateFormat(format).parse(date).getTime();
	}

	default <K,V> Map.Entry<K, V> getMapEntry(Map<K,V> map, K key){
		for(Map.Entry<K, V> entry: map.entrySet())
			if(entry.getKey().equals(key))
				return entry;
		return null;
	}
	
	String randomiseData(int count) throws Exception;
	
	public void setPattern(Patterns patterns);
}
