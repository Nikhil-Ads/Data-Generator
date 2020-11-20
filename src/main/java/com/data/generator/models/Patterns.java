package com.data.generator.models;

import java.util.HashMap;
import java.util.Map;

public class Patterns {

	private Map<String, String> patterns;
	
	public Patterns() {
		patterns =new HashMap<>();
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
