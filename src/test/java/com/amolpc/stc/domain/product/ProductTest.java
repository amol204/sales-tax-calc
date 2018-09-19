package com.amolpc.stc.domain.product;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import org.junit.Test;

public class ProductTest {

	@Test
	public void testCalculateTotalPrice() {
		Product product = null;
		product = new Product();
		product.setPrice(new BigDecimal("12"));
		product.setQuantity(2);
		assertEquals(BigDecimal.valueOf(24), product.calculateTotalPrice());
		
		// no price set
		product = new Product();
		product.setQuantity(2);
		assertEquals(BigDecimal.ZERO, product.calculateTotalPrice());
	}

}
