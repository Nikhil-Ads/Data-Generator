package com.data.generator.utility;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class KeyGenerator {
	
	private final Random random;
	
	private static final int CATEGORIES=5;
	
	public static final String HEX=new String("0123456789ABCDEF");
	public static final String SPECIAL_CHAR=new String("@#$%^&*()_.,:");
	public static final String WHTIESPACE_CHAR=new String(" ");
				
	private static final Map<String, List<Character>> charsets=new HashMap<>();	
	
	public static final int ALPHA_NUMERIC=0;
	public static final int NUMERIC		 =1;
	public static final int HEXADECIMAL	 =2;
	public static final int SPECIALCHARS =3;
	public static final int SPACES		 =4;
	
	static {
		try {
			@SuppressWarnings("unchecked")
			Class<KeyGenerator> className=(Class<KeyGenerator>) Class.forName("com.data.generator.utility.KeyGenerator");
			for(Field field : className.getDeclaredFields()) 
				if(field.getModifiers() == 26 && field.getType().getName().contains("String")) {
					List<Character> chars=new ArrayList<>();
					for(Character ch : ((String)field.get(null)).toCharArray())
						chars.add(ch);
					charsets.put(field.getName(), chars);
				}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
		
	public KeyGenerator() {
		this.random=new Random();
	}
	
	public String generateKey(int length) {
		String result=new String();
		for(int ch=1; ch <=length; ch++) 
			switch(random.nextInt(CATEGORIES)) {
				//Case for AlphaNumeric
				case 0: {switch(random.nextInt(3)) {
								//Case for numeric 
								case 0: {	result+=(char)(48 + random.nextInt(10));	
											break;
										}
								//Case for Capital Letters
								case 1: {	result+=(char)(65 + random.nextInt(26));
											break;
										}
								//Case for Small Letters
								case 2: {	result+=(char)(97 + random.nextInt(26));
											break;
										}
						}								
						 break;	}
				//Case for Numeric Data
				case 1: {result+=(char)(48 + random.nextInt(10));					
					 	 break;	}
				//Case for Hexadecimal Data
				case 2: {result+=(char)(charsets.get("HEX").get(random.nextInt(charsets.get("HEX").size())));				
					 	 break;	}
				//Case for Special Characters
				case 3: {result+=(char)(charsets.get("SPECIAL_CHAR").get(random.nextInt(charsets.get("SPECIAL_CHAR").size())));					
					 	 break;	}
				//Case for White Spaces
				case 4: {result+=(char)(charsets.get("WHTIESPACE_CHAR").get(random.nextInt(charsets.get("WHTIESPACE_CHAR").size())));					
					 	 break;	}
			}		
		return result;
	}
	
	public String generateKey(int minLength, int maxLength) {		
		String result=new String();
		
		int difference=maxLength - minLength;
		int length=minLength + random.nextInt(difference);
		for(int ch=1; ch <= length; ch++) 
			switch(random.nextInt(CATEGORIES)) {
				//Case for AlphaNumeric
				case 0: {switch(random.nextInt(3)) {
								//Case for numeric 
								case 0: {	result+=(char)(48 + random.nextInt(10));	
											break;
										}
								//Case for Capital Letters
								case 1: {	result+=(char)(65 + random.nextInt(26));
											break;
										}
								//Case for Small Letters
								case 2: {	result+=(char)(97 + random.nextInt(26));
											break;
										}
						}								
						 break;	}
				//Case for Numeric Data
				case 1: {result+=(char)(48 + random.nextInt(10));					
					 	 break;	}
				//Case for Hexadecimal Data
				case 2: {result+=(char)(charsets.get("HEX").get(random.nextInt(charsets.get("HEX").size())));				
					 	 break;	}
				//Case for Special Characters
				case 3: {result+=(char)(charsets.get("SPECIAL_CHAR").get(random.nextInt(charsets.get("SPECIAL_CHAR").size())));					
					 	 break;	}
				//Case for White Spaces
				case 4: {result+=(char)(charsets.get("WHTIESPACE_CHAR").get(random.nextInt(charsets.get("WHTIESPACE_CHAR").size())));					
					 	 break;	}
			}		
		return result;				
	}
	
	public String generateKey(int length, int... categories) {		
		String result=new String();
		
		for(int ch=1; ch <= length; ch++) 
			switch(categories[random.nextInt(categories.length)]) {
				//Case for AlphaNumeric
				case 0: {switch(random.nextInt(3)) {
								//Case for numeric 
								case 0: {	result+=(char)(48 + random.nextInt(10));	
											break;
										}
								//Case for Capital Letters
								case 1: {	result+=(char)(65 + random.nextInt(26));
											break;
										}
								//Case for Small Letters
								case 2: {	result+=(char)(97 + random.nextInt(26));
											break;
										}
						}								
						 break;	}
				//Case for Numeric Data
				case 1: {result+=(char)(48 + random.nextInt(10));					
					 	 break;	}
				//Case for Hexadecimal Data
				case 2: {result+=(char)(charsets.get("HEX").get(random.nextInt(charsets.get("HEX").size())));				
					 	 break;	}
				//Case for Special Characters
				case 3: {result+=(char)(charsets.get("SPECIAL_CHAR").get(random.nextInt(charsets.get("SPECIAL_CHAR").size())));					
					 	 break;	}
				//Case for White Spaces
				case 4: {result+=(char)(charsets.get("WHTIESPACE_CHAR").get(random.nextInt(charsets.get("WHTIESPACE_CHAR").size())));					
					 	 break;	}
			}		
		return result;				
	}
	
	public String generateKey(int minLength, int maxLength, int... categories) {		
		String result=new String();
		
		int difference=maxLength - minLength;
		int length=minLength + random.nextInt(difference);
		for(int ch=1; ch <= length; ch++) 
			switch(categories[random.nextInt(categories.length)]) {
				//Case for AlphaNumeric
				case 0: {switch(random.nextInt(3)) {
								//Case for numeric 
								case 0: {	result+=(char)(48 + random.nextInt(10));	
											break;
										}
								//Case for Capital Letters
								case 1: {	result+=(char)(65 + random.nextInt(26));
											break;
										}
								//Case for Small Letters
								case 2: {	result+=(char)(97 + random.nextInt(26));
											break;
										}
						}								
						 break;	}
				//Case for Numeric Data
				case 1: {result+=(char)(48 + random.nextInt(10));					
					 	 break;	}
				//Case for Hexadecimal Data
				case 2: {result+=(char)(charsets.get("HEX").get(random.nextInt(charsets.get("HEX").size())));				
					 	 break;	}
				//Case for Special Characters
				case 3: {result+=(char)(charsets.get("SPECIAL_CHAR").get(random.nextInt(charsets.get("SPECIAL_CHAR").size())));					
					 	 break;	}
				//Case for White Spaces
				case 4: {result+=(char)(charsets.get("WHTIESPACE_CHAR").get(random.nextInt(charsets.get("WHTIESPACE_CHAR").size())));					
					 	 break;	}
			}		
		return result;				
	}
	
	public boolean isAlphaNumeric(String ch) {
		return ch.matches("\\w+") ? true: false;		
	}
	
	public boolean isDigit(String ch) {
		return ch.matches("\\d+") ? true: false;				
	}
	
	public boolean isHexadecimal(String ch) {
		for(char c : ch.toCharArray())
			if(!charsets.get("HEX").contains(c))
				return false;
		return true;
	}
	
	
	
}
