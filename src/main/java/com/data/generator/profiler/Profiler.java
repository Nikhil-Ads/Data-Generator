package com.data.generator.profiler;

public interface Profiler {
	
	public void   profile(String requestBody);
	
	public String getTemplate();
	
	public String getProfiles();
}
