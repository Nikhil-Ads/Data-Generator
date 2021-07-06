/**
 * 
 */
package com.data.generator.randomiser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.data.generator.controller.ExceptionThrower;
import com.data.generator.exceptions.GeneratorException;
import com.data.generator.models.Patterns;
import com.data.generator.utility.KeyGenerator;

/**
 * @author LPT-2492
 *
 */
@Component
public class JSONRandomiser extends ExceptionThrower implements Randomiser {

	private JSONObject json, selectedProfile;
	
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
	public String randomiseData(int count,Integer uniqueCount) throws Exception {
		JSONArray jsonArr= new JSONArray();
		if(pattern != null) {
				JSONArray profiles = pattern.getProfiles();
				LOGGER.debug("Found Profiles: "+profiles.toString());
				LOGGER.debug("Unique Count passed: "+uniqueCount);

				for(int i=1; i <= count; i++) {
					if(uniqueCount > 0  && profiles.length() == uniqueCount)
						selectedProfile = profiles.getJSONObject((int)(Math.random() * profiles.length()));	
					JSONObject jsonObject=new JSONObject();
					for(Map.Entry<String, String> propertyPattern : pattern.getPatterns().entrySet()) {
						Object V=getRandomData(propertyPattern, jsonObject);				
						jsonObject.put(propertyPattern.getKey(), V);	
					}
					jsonArr.put(jsonObject);
					if(profiles.length() < uniqueCount)
						profiles.put(jsonObject);
					selectedProfile=null;
				}
		}else 	throw new Exception("Patterns must be set for Randomisation of data");
		return 	jsonArr.toString();
	}
	
	@SuppressWarnings("deprecation")
	private Long getDate(String key, JSONObject json, long defaultValue, JSONObject container) throws JSONException, GeneratorException, ParseException {
		Long result=null;
		if(json.has(key)) {
				if(isRefExpression(json.get(key).toString())) {
//						System.out.println("Evaluate");
						Object V = evaluateExpression(json.get(key).toString(), container);
						result =  V instanceof String ? Date.parse(V.toString()) : (Long) V;
				}else if(isReference(json.get(key).toString())) {
//						System.out.println("In reference");
						String value= json.getString(key);
						value=value.substring(1, value.length()-1);
						JSONObject parameters=null;
						if(!container.has(value))
							{ Map.Entry<String, String> entry = getMapEntry(pattern.getPatterns(), value);
							  if(entry != null) {
								  String object=entry.getValue();
								  object = object.substring(5);
								  if(object.contains("%")) {
										String[] typePair = object.split("%");
										object= typePair.length ==2 ? typePair[1] : "{}";
								  }
								  parameters=new JSONObject(object);
								  Object V = getRandomData(entry, container);
								  container.put(value, V);	
							  }
							 }
						if(parameters != null && parameters.has("format")) {
						 if(isDateFormat(parameters.getString("format")))	
								result = getDate(container.getString(value),parameters.getString("format"));
						}else if(container.get(value) instanceof  Date)	
							    result = ((Date)container.get(value)).getTime();
						else  if(isNumber(container.get(value).toString()))
								result = container.getLong(value);
				}else if(isDate(json.get(key).toString()))
						result=getDate(json.getString(key));
				 else	result=json.getLong(key); 
		}else 	result=defaultValue;
		return result;
	}
	
	private Object getRandomData(Map.Entry<String, String> propertyPattern, JSONObject jsonObject) throws GeneratorException, JSONException, ParseException {
		Object V=null;
		String value= propertyPattern.getValue();
//		System.out.println(value);
		if(value != null) {
				if(value.matches("%GEN%.+")) {
						String type = new String();
						value = value.substring(5);
						if(value.contains("%")) {
							String[] typePair = value.split("%");
							type = typePair[0];
							value= typePair.length ==2 ? typePair[1] : "{}";
						}
							
						JSONObject json = new JSONObject(value);
						
						if(json.has("alwaysNew") && !json.getBoolean("alwaysNew") && selectedProfile != null) {
							V = selectedProfile.has(propertyPattern.getKey()) ? selectedProfile.get(propertyPattern.getKey()) : null;
						}else {
							if(type.equalsIgnoreCase("date") && json.has("format"))
								V = generateDateString(json, jsonObject);
							else if(type.equalsIgnoreCase("date")) 
								V = generateDate(json, jsonObject);
							else if(type.equalsIgnoreCase("time") || (json.has("example") && isTime(Long.valueOf(json.get("example").toString()))))
								V = generateTime(json, jsonObject);
							else if(type.equalsIgnoreCase("now")) 
								V = generateNow(json);
							else if(type.equalsIgnoreCase("number") || (json.has("example") && isNumberFormat(json.get("example").toString()))) 
								V = generateNumber(json);
							else if(type.equalsIgnoreCase("string") || (json.has("example") && isNumberFormat(json.get("example").toString()))) 
								V = generateString(json);
							else if(type.equalsIgnoreCase("email") || (json.has("example") && isNumberFormat(json.get("example").toString()))) 
								V = generateEmail(json);
							else if((json.has("values"))) 
								V = generateFromValues(json);												
							else if(type.equalsIgnoreCase("expression") && (json.has("expression") && isRefExpression(json.getString("expression"))))
								V = evaluateExpression(json.getString("expression"), jsonObject);
							else throwPropertiesNotFoundException();
						}
				}else if(value.equalsIgnoreCase("%GEN%")) { 
						V=generator.generateKey(25, 30, new int[] {KeyGenerator.ALPHA_NUMERIC});}
				 else 	V=value;}
		return V;
	}
	
	private Object evaluateExpression(String key, JSONObject container) throws JSONException, GeneratorException, ParseException {
			Object V=null;
			if(key.contains(" + ")) {
				String[] keys = key.split(" [+] ");
				String leftOp = keys[0].substring(1,keys[0].length()-1);
				String rightOp= keys[1].substring(1,keys[1].length()-1);
				if(container.has(leftOp) && container.has(rightOp)) 
					V=container.getLong(leftOp) + container.getLong(rightOp);						
			}else if(key.contains(" - ")) {
				String[] keys = key.split(" [-] ");
				String leftOp = keys[0].substring(1,keys[0].length()-1);
				String rightOp= keys[1].substring(1,keys[1].length()-1);
				if(container.has(leftOp) && container.has(rightOp)) 
					V=container.getLong(leftOp) - container.getLong(rightOp);
			}else if(key.contains(" * ")) {
				String[] keys = key.split(" [*] ");
				String leftOp = keys[0].substring(1,keys[0].length()-1);
				String rightOp= keys[1].substring(1,keys[1].length()-1);
				if(container.has(leftOp) && container.has(rightOp)) 
					V=container.getLong(leftOp) * container.getLong(rightOp);
			}else if(key.contains(" / ")) {
				String[] keys = key.split(" [/] ");
				String leftOp = keys[0].substring(1,keys[0].length()-1);
				String rightOp= keys[1].substring(1,keys[1].length()-1);
				if(container.has(leftOp) && container.has(rightOp)) 
					V=container.getLong(leftOp) / container.getLong(rightOp);
			}else if(key.contains(" % ")) {
				String[] keys = key.split(" [%] ");
				String leftOp = keys[0].substring(1,keys[0].length()-1);
				String rightOp= keys[1].substring(1,keys[1].length()-1);
				if(container.has(leftOp) && container.has(rightOp)) 
					V=container.getLong(leftOp) % container.getLong(rightOp);
			}else if(key.contains(" ^ ")) {
				String[] keys = key.split(" [^] ");
				String leftOp = keys[0].substring(1,keys[0].length()-1);
				String rightOp= keys[1].substring(1,keys[1].length()-1);
				if(container.has(leftOp) && container.has(rightOp)) 
					V=container.getLong(leftOp) ^ container.getLong(rightOp);
			}else {
//				System.out.println("Key passed: "+key);
				String value=key.substring(1, key.length()-1);							
				if(!container.has(value))
					{ Map.Entry<String, String> entry = getMapEntry(pattern.getPatterns(), value);
//					  System.out.println("Entry found for value: "+value+":- "+entry);
					  if(entry != null) {
						  V = getRandomData(entry, container);
						  container.put(value, V);}	
					}
				V = container.get(value) instanceof String ? container.getString(value) : container.getLong(value);
			}
		return V;
	}
	
	
	private List<String> getDomains(JSONObject json, String domain){
		List<String> domains = new ArrayList<>();
		if(json.has(domain)) {
			Object V = json.get(domain);
			if(V instanceof JSONArray) {
				json.getJSONArray(domain).forEach(d -> {
					String dom =d.toString();
					if(dom.startsWith("@"))
						dom=dom.substring(1);
					domains.add(dom);});
			}else if(V instanceof String)
				domains.add(json.getString(domain));
		}else domains.add("@email.com");
		return domains;
	}
	
	private String generateDateString(JSONObject json, JSONObject jsonObject) throws JSONException, GeneratorException, ParseException {
		String format = json.getString("format");
		if(isDateFormat(format)) {	
				long lowerLimit = getDate("min", json, 0, jsonObject);
				long upperLimit = getDate("max", json, new Date().getTime(), jsonObject);
				long difference = getDate("maxDiff", json, upperLimit - lowerLimit, jsonObject); 	        								
	            long random = (long) (Math.random() * difference);
	    	    return formatDate(new Date(lowerLimit + random), format);				        	    
		}else 	return null;
	}
	
	@SuppressWarnings("deprecation")
	private Object generateDate(JSONObject json, JSONObject jsonObject) throws JSONException, GeneratorException, ParseException {
		long lowerLimit = getDate("min", json, 0, jsonObject);
		long upperLimit = getDate("max", json, new Date().getTime(), jsonObject);
		long difference = getDate("maxDiff", json, upperLimit - lowerLimit, jsonObject); 	        								
        long random = (long) (Math.random() * difference);
	    return new Date(lowerLimit + random).toGMTString();
	} 
	
	private Object generateTime(JSONObject json, JSONObject jsonObject) throws JSONException, GeneratorException, ParseException {
		long lowerLimit = getDate("min", json, 0, jsonObject);
		long upperLimit = getDate("max", json, new Date().getTime(), jsonObject);
		long difference = getDate("maxDiff", json, upperLimit - lowerLimit, jsonObject); 	        								
        long random = (long) (Math.random() * difference);				            
		return json.has("format") ?  formatDate(new Date(random), json.getString("format")) : new Date(random).getTime();
	}
	
	@SuppressWarnings("deprecation")
	private Object generateNow(JSONObject json) {
		return json.has("format") ?  (json.getString("format").equals("S") ? new Date().getTime() : formatDate(new Date(), json.getString("format"))) : new Date().toGMTString();
	}
	
	private Long generateNumber(JSONObject json) {
		String V=null;
		if(json.has("min") && json.has("max")) {
			int min = json.getInt("min");
			int max = json.getInt("max");									
			V=generator.generateKey(min, max, new int[] {KeyGenerator.NUMERIC});
		}else if(json.has("length")) {
			int length = json.getInt("length");
			V=generator.generateKey(length, new int[] {KeyGenerator.NUMERIC});
		}else {
			int length = json.has("example") ? json.get("example").toString().length() : 5;
			V=generator.generateKey(length, new int[] {KeyGenerator.NUMERIC});
		}
		return Long.parseLong(V.toString());
	}
	
	private String generateString(JSONObject json) {
		String V=null;
		if(json.has("min") && json.has("max")) {
			int min = json.getInt("min");
			int max = json.getInt("max");									
			V=generator.generateKey(min, max, new int[] {KeyGenerator.ALPHA_NUMERIC});
		}else if(json.has("length")) {
			int length = json.getInt("length");
			V=generator.generateKey(length, new int[] {KeyGenerator.ALPHA_NUMERIC});
		}else {
			int length = json.has("example") ? json.get("example").toString().length() : 5;
			V=generator.generateKey(length, new int[] {KeyGenerator.ALPHA_NUMERIC});
		}
		return V;
	}

	private String generateEmail(JSONObject json) {
		String V= null;
		List<String> domains = getDomains(json, "domain");
		if(json.has("min") && json.has("max")) {
			int min = json.getInt("min");
			int max = json.getInt("max");									
			V=generator.generateKey(min, max, new int[] {KeyGenerator.ALPHA_NUMERIC}) +"@"+ domains.get((int)(Math.random() * domains.size()));
		}else if(json.has("length")) {
			int length = json.getInt("length");
			V=generator.generateKey(length, new int[] {KeyGenerator.ALPHA_NUMERIC});
		}else {
			int length = json.has("example") ? json.get("example").toString().length() : 5;
			V=generator.generateKey(length, new int[] {KeyGenerator.ALPHA_NUMERIC});
		}
		return V;
	}
	
	private Object generateFromValues(JSONObject json) {
		//Array for Values
		JSONArray array=json.getJSONArray("values");
		return array.get((int)(Math.random() * array.length()));
	}
}
