package com.data.generator.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class Patterns {

	private Map<String, String> patterns;
	
	public Patterns() {
		patterns =new LinkedHashMap<>();
	}

	/**
	 * @return the patterns
	 */
	public final Map<String, String> getPatterns() {
		return patterns;
	}

	/**
	 * @param patterns the patterns to set
	 */
	public final void setPatterns(Map<String, String> patterns) {
		this.patterns = patterns;
	}
}
