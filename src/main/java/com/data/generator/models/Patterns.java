package com.data.generator.models;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;

public class Patterns {

	private Map<String, String> patterns;
	
	private JSONArray profiles;
	
	public Patterns() {
		patterns =new LinkedHashMap<>();
		profiles =new JSONArray();
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


	/**
	 * @return the profiles
	 */
	public final JSONArray getProfiles() {
		return profiles;
	}


	/**
	 * @param profiles the profiles to set
	 */
	public final void setProfiles(JSONArray profiles) {
		this.profiles = profiles;
	}
	
	public final void setProfiles(String profiles) {
		this.profiles=new JSONArray(profiles);
	}
}
