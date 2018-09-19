package com.amolpc.stc.parser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amolpc.stc.domain.product.Product;
import com.amolpc.stc.domain.product.Product.Type;
import com.amolpc.stc.exceptions.ParserException;

@Component
public class Parser {
	
	@Autowired
	private ParserConfig parserConfig;
	
	private String patternStr = "(\\d)\\s([\\w ]*)at\\s(\\d*\\.*\\d+)";
	
	private static final String EXPCTED_INPUT_FORMAT = "<Quantity> <Product Description> <Price>";
	
	private Pattern pattern;
	
	public Parser() {
		pattern = Pattern.compile(patternStr);
	}

	public List<Product> parse(String[] arr) throws ParserException{
		List<Product> products = new ArrayList<>();
		for (String str : arr) {
			Product product = convertToProduct(str);
			products.add(product);
		}
		return products;
	}

	Product convertToProduct(String str) throws ParserException{
		if(!str.matches(patternStr)){
			throw new ParserException("Invalid input format. input: "+str+" .Expected input format:"+EXPCTED_INPUT_FORMAT);
		}
		Matcher matcher = pattern.matcher(str);
		String qtyStr = null;
		String description = null;
		String priceStr = null;
		while(matcher.find()){
			qtyStr = matcher.group(1);
			description = matcher.group(2);
			priceStr = matcher.group(3);
		}
		long qty = Long.parseLong(qtyStr);
		double price = Double.parseDouble(priceStr);
		Product product = new Product();
		product.setQuantity(qty);
		product.setDescription(description.trim());
		product.setPrice(BigDecimal.valueOf(price));
		product.setType(deriveType(description));
		product.setImported(isImported(description));
		return product;
	}
	
	Type deriveType(String descr){
		// convert string to lower case for comparison
		descr = descr.toLowerCase();
		String[] books = parserConfig.getBooks();
		for (String str : books) {
			if(descr.matches(getPattern(str))){
				return Type.BOOK;
			}
		}
		String[] foods = parserConfig.getFoods();
		for (String str : foods) {
			if(descr.matches(getPattern(str))){
				return Type.FOOD;
			}
		}
		String[] medicals = parserConfig.getMedicals();
		for (String str : medicals) {
			if(descr.matches(getPattern(str))){
				return Type.MEDICAL;
			}
		}
		return Type.OTHER;
	}

	private String getPattern(String str) {
		return ".*\\b"+str+"\\b.*";
	}
	
	boolean isImported(String descr){
		// convert string to lower case for comparison
		descr = descr.toLowerCase();
		String[] importIndicators = parserConfig.getImportIndicators();
		for (String str : importIndicators) {
			if (descr.matches(getPattern(str))) {
				return true;
			}
		}
		return false;
	}
}
