/**
 * 
 */
package com.data.generator.profiler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author LPT-2492
 *
 */
@Component
public class JSONProfiler implements Profiler {
	
	private static final Logger logger=LoggerFactory.getLogger(JSONProfiler.class);
	
	private JSONArray profiles;
	private String    template;

	/**
	 * 
	 */
	public JSONProfiler() {
		template=new String();
		profiles=new JSONArray();
	}

	@Override
	public void profile(String requestBody) {
		logger.debug("Entering profile() method");
		logger.debug("Request Body passed: "+requestBody);
		JSONObject json=new JSONObject(requestBody);
		logger.debug("Parsed RequestBody: "+json.toString());
				
		if(json.has("profiles"))
			profiles=json.getJSONArray("profiles");
		if(json.has("template"))
				template=json.getJSONObject("template").toString();
		else	template=json.toString();
		
		logger.debug("template: "+template);
		logger.debug("profiles: "+profiles.toString());
		logger.debug("Exiting profile() method");
	}

	@Override
	public String getTemplate() {
		return template;
	}

	@Override
	public String getProfiles() {
		return profiles.toString();
	}
}
