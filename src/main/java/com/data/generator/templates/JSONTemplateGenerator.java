package com.data.generator.templates;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.data.generator.models.Patterns;

@Component
public class JSONTemplateGenerator implements TemplateGenerator {
	
	private JSONObject json;
	
	private Patterns patterns;
	
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
	public Map<String, String> getPropertiesFromExample(String example, String propertiesSeparator, String delimiter) {
		Map<String, String> properties=new HashMap<>();
		json = new JSONObject(example);
		json.toMap().forEach((K,V) -> { patterns.getPatterns().put(K,V.toString());
										properties.put(K, String.format("{{%s}}", K));
									   });
		return properties;
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
