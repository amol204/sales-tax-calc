package com.amolpc.stc.domain.tax;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.amolpc.stc.config.AppConfig;
import com.amolpc.stc.domain.tax.Tax.Type;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class TaxFactoryTest {

	@Autowired
	private TaxFactory taxFactory;
	
	@Test
	public void testGetTax() {
		Tax tax = null;
		
		tax = taxFactory.getTax(Type.BASIC_SALES_TAX);
		assertNotNull(tax);
		assertEquals(Type.BASIC_SALES_TAX, tax.getType());
		
		tax = taxFactory.getTax(Type.IMPORT_DUTY);
		assertNotNull(tax);
		assertEquals(Type.IMPORT_DUTY, tax.getType());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetTax_null_argument() {
		taxFactory.getTax(null);
	}

}
