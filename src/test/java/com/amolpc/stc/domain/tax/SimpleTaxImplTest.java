package com.amolpc.stc.domain.tax;

import java.math.BigDecimal;

import org.junit.Test;

import com.amolpc.stc.domain.tax.Tax.Type;

import static org.junit.Assert.*;

public class SimpleTaxImplTest {

	@Test
	public void testCalculate_rounding() {
		// 10 % tax
		SimpleTaxImpl tax = new SimpleTaxImpl(Type.BASIC_SALES_TAX, BigDecimal.valueOf(0.1));
		BigDecimal taxAmt = null;
		
		taxAmt = tax.calculate(BigDecimal.valueOf(100));
		assertEquals(BigDecimal.valueOf(10.0), taxAmt);
		
		taxAmt = tax.calculate(BigDecimal.valueOf(71.25));
		assertEquals(BigDecimal.valueOf(7.15), taxAmt);
		
		taxAmt = tax.calculate(BigDecimal.valueOf(5.625));
		assertEquals(BigDecimal.valueOf(0.6), taxAmt);
	}

}
