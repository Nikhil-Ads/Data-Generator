package com.data.generator.randomiser;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.data.generator.models.Patterns;

public interface Randomiser {
	
	default boolean isDateFormat(String format) {
		try {new SimpleDateFormat(format);
			 return true;}
		catch(IllegalArgumentException ex) {
			 System.err.println(ex.getMessage());
			 return false;
		}
	}
	
	default boolean isNumberFormat(String pattern) {
		try {NumberFormat.getNumberInstance().parse(pattern);
			 return true;
		}catch(ParseException e) {
			System.err.println(e.getMessage());
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
			System.err.println(e.getMessage());
			return false;
		}
	}
	
	default String formatDate(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	String randomiseData(int count) throws Exception;
	
	public void setPattern(Patterns patterns);

}
