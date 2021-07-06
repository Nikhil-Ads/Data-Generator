/**
 * 
 */
package com.data.generator.service;

import org.springframework.stereotype.Service;

import com.data.generator.profiler.Profiler;
import com.data.generator.randomiser.Randomiser;
import com.data.generator.templates.TemplateGenerator;

/**
 * @author LPT-2492
 *
 */
@Service
public class DataGeneratorService {
	
	private Profiler profiler;
	
	private TemplateGenerator templateGenerator;
	
	private Randomiser randomiser;

	/**
	 * @param profiler
	 * @param templateGenerator
	 * @param randomiser
	 */
	public DataGeneratorService(Profiler profiler, TemplateGenerator templateGenerator, Randomiser randomiser) {
		this.profiler = profiler;
		this.templateGenerator = templateGenerator;
		this.randomiser = randomiser;
	}

	/**
	 * 
	 */
	public String generateTemplate(String object) {
		//Profiling Data
		profiler.profile(object);
		//Generating Template and Returning it
		return templateGenerator.getPropertiesFromExample(profiler.getTemplate(), null, null);
	}
	
	/**
	 * */
	public String generateRandomData(Integer count, Integer uniqueCount, String object) throws Exception {
		//Profiling Data
		profiler.profile(object);
		//Generating Template
		templateGenerator.getPropertiesFromExample(profiler.getTemplate(), null, null);
		templateGenerator.getPatterns().setProfiles(profiler.getProfiles());
		randomiser.setPattern(templateGenerator.getPatterns());
		//Randomising Data and Returning Results 
		return randomiser.randomiseData(count,uniqueCount);
	}

}
