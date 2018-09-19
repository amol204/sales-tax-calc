package com.amolpc.stc.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ParserConfig {

	private String[] books;
	
	private String[] foods;
	
	private String[] medicals;
	
	private String[] importIndicators;
	
	@Autowired
	public ParserConfig(Environment env) {
		books = env.getProperty("books").split(",");
		foods = env.getProperty("foods").split(",");
		medicals = env.getProperty("medicals").split(",");
		importIndicators = env.getProperty("importIndicator").split(",");
	}

	public String[] getBooks() {
		return books;
	}

	public String[] getFoods() {
		return foods;
	}

	public String[] getMedicals() {
		return medicals;
	}

	public String[] getImportIndicators() {
		return importIndicators;
	}
	
	
}
