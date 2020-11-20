package com.data.generator.templates;

import java.util.HashMap;
import java.util.Map;

import com.data.generator.models.Patterns;

public interface TemplateGenerator {
	
	default Map<String, String> getPropertiesFromExample(String example, String propertiesSeparator, String delimiter){
		Map<String, String> properties = new HashMap<>();

		for(String property : example.split(propertiesSeparator)){
			String[] data = property.split(delimiter);
			properties.put(data[0].trim(),data[1].trim());
		}
		return properties;
		}	
	
	public Patterns getPatterns();
}
