package com.data.generator.controller;

import javax.validation.constraints.NotEmpty;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.data.generator.profiler.Profiler;
import com.data.generator.service.DataGeneratorService;
import com.data.generator.templates.TemplateGenerator;

@RestController
@RequestMapping("/generate")
public class DataGeneratorController extends ExceptionThrower{
	
	@Autowired
	private DataGeneratorService dataGeneratorService;
	
	@GetMapping
	@RequestMapping(method=RequestMethod.GET ,path = "/template", produces = "text/plain", consumes = "application/json")
	public ResponseEntity<String> getTemplate(
			@NonNull
			@NotEmpty(message = "Request Body cannot be empty")
			@RequestBody String object) throws Exception{
		if(object == null || (object != null && object.isEmpty()))
				throw new Exception("Request Body cannot be empty");
		else 	return new ResponseEntity<String>(dataGeneratorService.generateTemplate(object),HttpStatus.CREATED);
	}
	
	/**Generates an ordered data set of Random Data. The method has a few options to customise the data set generated.
	 * <br>
	 * Following are the options that this method supports: 
	 * <ul>
	 * <li>It utilises {@code count} to determine the number of records to be generated;</li>
	 * <li>The {@code uniqueCount} determines the number of unique records to be generated; &</li>
	 * <li>The {@code object} describes the requestBody, which carries the information of the template and any profiles that can be used.</li>
	 * </ul>
	 * 
	 * @param count the total number of records to be generated
	 * @param uniqueCount number of unique records to be generated, based on the profiles given or generated.
	 * @param object requestBody, which carries the information of the template and any profiles that can be used.
	 * 
	 * @return A wrapped object of ResponseEntity, carrying a String representation of a {@link JSONArray}.
	 * @throws Exception for any issues arisen, while generating the data set.
	 * @see Profiler
	 * @see TemplateGenerator
	 * @see JSONArray
	 * */
	@GetMapping(path = "/randomise", produces = "application/json", consumes = "application/json")
	public ResponseEntity<String> getRandomisedData(
			@RequestHeader(name = "count",required = false,defaultValue = "1")
			Integer count,
			@RequestHeader(name = "uniqueCount",required = false,defaultValue = "0")
			Integer uniqueCount,
			@NonNull
			@NotEmpty(message = "Request Body cannot be empty")
			@RequestBody String object) throws Exception{
		if(object == null || (object != null && object.isEmpty()))
				throw new Exception("Request Body cannot be empty"); 
		else 	return new ResponseEntity<String>(dataGeneratorService.generateRandomData(count, uniqueCount, object),HttpStatus.CREATED);
	}

}
