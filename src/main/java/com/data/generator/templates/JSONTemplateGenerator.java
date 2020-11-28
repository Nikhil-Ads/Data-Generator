package com.data.generator.templates;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.data.generator.models.Patterns;

@Component
public class JSONTemplateGenerator implements TemplateGenerator {
	
	private JSONObject json;
	
	private Patterns patterns;
	
	private String properties;
	
	private static final String 	   SEPARATOR = new String(",");
	private static final String	   	   DELIMITER = new String(":");
	
	public JSONTemplateGenerator() {
		patterns=new Patterns();
	}

	/**
	 * @return the json
	 */
	public final JSONObject getJson() {
		return json;
	}
	/**
	 * @return the separator
	 */
	public static final String getSeparator() {
		return SEPARATOR;
	}
	/**
	 * @return the delimiter
	 */
	public static final String getDelimiter() {
		return DELIMITER;
	}
	/**
	 * @param json the json to set
	 */
	public final void setJson(JSONObject json) {
		this.json = json;
	}

	@Override
	public String getPropertiesFromExample(String example, String propertiesSeparator, String delimiter) {
		if(patterns == null)
			patterns=new Patterns();
		
		patterns.getPatterns().clear();
		properties=new String("{\r\n");
		json = new JSONObject(example);
		json.toMap().forEach((K,V) -> { 
										patterns.getPatterns().put(K,V != null ? V.toString() : null);
										if(V == null)
											 properties+=String.format("\"%s\" : %s", K, null);
										else if(V != null && V.toString().matches("%GEN%.+"))
											{String value = V.toString().substring(5);
											 
											String type = new String();
											if(value.contains("%")) 
												type = value.split("%")[0];
											
											if(type.equalsIgnoreCase("time") || type.equalsIgnoreCase("number"))
													properties+=String.format("\"%s\" : {{%s}},\r\n", K, K);
											else	properties+=String.format("\"%s\" : \"{{%s}}\",\r\n", K,K);
											}									
										else	properties+=String.format("\"%s\" : \"{{%s}}\",\r\n", K,K);
									   });
		properties=properties.substring(0,properties.length()-3)+"\r\n}";
		return properties.toString();
	}

	/**
	 * @return the patterns
	 */
	public final Patterns getPatterns() {
		return patterns;
	}

	/**
	 * @param patterns the patterns to set
	 */
	public final void setPatterns(Map<String, String> patterns) {
		this.patterns.setPatterns(patterns);
	}
	
	
	
}
