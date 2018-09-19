package com.amolpc.stc.parser;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.amolpc.stc.config.AppConfig;
import com.amolpc.stc.domain.product.Product;
import com.amolpc.stc.exceptions.ParserException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ParserTest {
	
	@Autowired
	Parser parser;
	
	@Test(expected = ParserException.class)
	public void testConvertToProduct_invalid_input() throws ParserException {
		parser.convertToProduct("invalid input String");
	}
	
	@Test
	public void testConvertToProduct() throws ParserException{
		String productStr1 = "1 music CD at 14.99";
		Product product1 = parser.convertToProduct(productStr1);
		assertNotNull(product1);
		assertEquals(1, product1.getQuantity());
		assertEquals("music CD", product1.getDescription());
		assertEquals(BigDecimal.valueOf(14.99), product1.getPrice());
		assertEquals(Product.Type.OTHER, product1.getType());
		assertFalse(product1.isImported());
		
		String productStr2 = "2 imported box of chocolates at 10.00";
		Product product2 = parser.convertToProduct(productStr2);
		assertNotNull(product2);
		assertEquals(2, product2.getQuantity());
		assertEquals("imported box of chocolates", product2.getDescription());
		assertEquals(BigDecimal.valueOf(10.00), product2.getPrice());
		assertEquals(Product.Type.FOOD, product2.getType());
		assertTrue(product2.isImported());
	}
	
	@Test
	public void testDeriveType(){
		assertEquals(Product.Type.FOOD, parser.deriveType("box of chocolates"));
		assertEquals(Product.Type.FOOD, parser.deriveType("box of chocolate"));
		assertEquals(Product.Type.FOOD, parser.deriveType("box of Chocolates"));
		assertEquals(Product.Type.FOOD, parser.deriveType("chocolates"));
		assertEquals(Product.Type.FOOD, parser.deriveType("chocolate"));
		
		assertEquals(Product.Type.BOOK, parser.deriveType("book"));
		assertEquals(Product.Type.BOOK, parser.deriveType("Harry potter Book"));
		assertEquals(Product.Type.BOOK, parser.deriveType("The Book of Eli"));
		assertEquals(Product.Type.BOOK, parser.deriveType("Some Books are kept in cupboard"));
		
		assertEquals(Product.Type.MEDICAL, parser.deriveType("headache pill"));
		assertEquals(Product.Type.MEDICAL, parser.deriveType("pill"));
		assertEquals(Product.Type.MEDICAL, parser.deriveType("PILLS"));
		
		assertEquals(Product.Type.OTHER, parser.deriveType("spill"));
		assertEquals(Product.Type.OTHER, parser.deriveType("some perfume"));
	}
	
	@Test
	public void testIsImported(){
		assertTrue(parser.isImported("it is imported stuff"));
		assertTrue(parser.isImported("it is not an imported stuff"));
		assertTrue(parser.isImported("it is IMPORTed stuff"));
		assertFalse(parser.isImported("it is just some stuff"));
	}
}
