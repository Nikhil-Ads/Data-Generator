package com.data.generator.controller;

import javax.validation.constraints.NotEmpty;

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

import com.data.generator.randomiser.Randomiser;
import com.data.generator.templates.TemplateGenerator;

@RestController
@RequestMapping("/generate")
public class DataGeneratorController {

	private TemplateGenerator templateGenerator;
	
	private Randomiser randomiser;
	
	public DataGeneratorController(@Autowired TemplateGenerator templateGenerator,@Autowired Randomiser randomiser) {
		this.randomiser=randomiser;
		this.templateGenerator=templateGenerator;
	}
	
	@GetMapping
	@RequestMapping(method=RequestMethod.GET ,path = "/template", produces = "text/plain", consumes = "application/json")
	public ResponseEntity<String> getTemplate(
			@NonNull
			@NotEmpty(message = "Request Body cannot be empty")
			@RequestBody String object){
		return new ResponseEntity<String>(templateGenerator.getPropertiesFromExample(object, null, null),HttpStatus.CREATED);
	}
	
	@GetMapping(path = "/randomise", produces = "application/json", consumes = "application/json")
	public ResponseEntity<String> getRandomisedData(
			@RequestHeader(name = "count",required = false,defaultValue = "1")
			Integer count,			
			@NonNull
			@NotEmpty(message = "Request Body cannot be empty")
			@RequestBody String object) throws Exception{
		if(object == null || (object != null && object.isEmpty()))
			throw new Exception("Request Body cannot be empty");
		templateGenerator.getPropertiesFromExample(object, null, null);
		randomiser.setPattern(templateGenerator.getPatterns());
		return new ResponseEntity<String>(randomiser.randomiseData(count),HttpStatus.CREATED);
	}

}
