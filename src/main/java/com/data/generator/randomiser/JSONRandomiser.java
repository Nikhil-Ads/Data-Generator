/**
 * 
 */
package com.data.generator.randomiser;

import java.util.Date;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.data.generator.controller.ExceptionThrower;
import com.data.generator.models.Patterns;
import com.data.generator.utility.KeyGenerator;

/**
 * @author LPT-2492
 *
 */
@Component
public class JSONRandomiser extends ExceptionThrower implements Randomiser {

	private JSONObject json;
	
	private Patterns pattern;
	
	@Autowired
	private KeyGenerator generator;
	
	public JSONRandomiser() {}
	/**
	 * 
	 */
	public JSONRandomiser(String data) {
		json =new JSONObject(data);
	}
	
	public JSONRandomiser(Map<String, String> tokens) {
		json = new JSONObject(tokens);
	}
	/**
	 * @return the json
	 */
	public final JSONObject getJson() {
		return json;
	}
	/**
	 * @return the pattern
	 */
	public final Patterns getPattern() {
		return pattern;
	}
	/**
	 * @param json the json to set
	 */
	public final void setJson(JSONObject json) {
		this.json = json;
	}
	/**
	 * @param pattern the pattern to set
	 */
	public final void setPattern(Patterns pattern) {
		this.pattern = pattern;
	}
	
	@Override
	public String randomiseData(int count) throws Exception {
		JSONArray jsonArr= new JSONArray();
		if(pattern != null)
			for(int i=1; i <= count; i++) {
				JSONObject jsonObject=new JSONObject();
				for(Map.Entry<String, String> propertyPattern : pattern.getPatterns().entrySet()) {
					Object V=null;
					String value= propertyPattern.getValue();
					if(value.matches("%GEN%.+")) {
						value = value.substring(5);
						
						String type = new String();
						if(value.contains("%")) {
							String[] typePair = value.split("%");
							type = typePair[0];
							value = typePair[1];
						}
							
						JSONObject json = new JSONObject(value);
						System.out.println(json.toString());
						if(type.equalsIgnoreCase("date") || json.has("format")){
								String format = json.getString("format");
								if(isDateFormat(format)) {							
									long lowerLimit = json.has("min") ? json.getLong("min") : 0;
									long upperLimit = new Date().getTime();
									long difference = upperLimit - lowerLimit; 	        								
						            long random = (long) (Math.random() * difference);
					        	    V=formatDate(new Date(lowerLimit + random), json.getString("format"));				        	    
								}
						}else if(type.equalsIgnoreCase("time") || (json.has("example") && isTime(Long.valueOf(json.get("example").toString())))) {						
							V=json.has("format") ?  formatDate(new Date(), json.getString("format")) : new Date().getTime();
						}else if(type.equalsIgnoreCase("number") || (json.has("example") && isNumberFormat(json.get("example").toString()))) {
							if(json.has("min") && json.has("max")) {
								int min = json.getInt("min");
								int max = json.getInt("max");									
								V=generator.generateKey(min, max, new int[] {KeyGenerator.NUMERIC});
							}else if(json.has("length")) {
								int length = json.getInt("length");
								V=generator.generateKey(length, new int[] {KeyGenerator.NUMERIC});
							}else {
								int length = json.get("example").toString().length();
								V=generator.generateKey(length, new int[] {KeyGenerator.NUMERIC});
							}
							V = Long.parseLong(V.toString());
						}else if(type.equalsIgnoreCase("options") || (json.has("values"))) {
							//Array for Values
							JSONArray array=json.getJSONArray("values");
							V=array.getString((int)(Math.random() * array.length()));												
						}else throwPropertiesNotFoundException();						
					}else if(value.equalsIgnoreCase("%GEN%")) {
						V=generator.generateKey(10, 20, new int[] {KeyGenerator.ALPHA_NUMERIC});
					}					
					jsonObject.put(propertyPattern.getKey(), V);	
				}
				jsonArr.put(jsonObject);
			}
		else throw new Exception("Patterns must be set for Randomisation of data");
		return jsonArr.toString();
	}

	
	
}
