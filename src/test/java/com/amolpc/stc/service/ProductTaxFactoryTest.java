package com.amolpc.stc.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.amolpc.stc.config.AppConfig;
import com.amolpc.stc.domain.product.Product;
import com.amolpc.stc.domain.product.Product.Type;
import com.amolpc.stc.domain.tax.Tax;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ProductTaxFactoryTest {

	@Autowired
	private ProductTaxFactory productTaxFactory;
	
	@Test
	public void testGetApplicableTaxes_imported_book_product() {
		Product product = new Product();
		product.setType(Type.BOOK);
		product.setImported(true);
		List<Tax> applicableTaxes = productTaxFactory.getApplicableTaxes(product);
		assertTrue(applicableTaxes.size() == 1);
		assertEquals(com.amolpc.stc.domain.tax.Tax.Type.IMPORT_DUTY, applicableTaxes.get(0).getType());
	}
	
	@Test
	public void testGetApplicableTaxes_imported_food_product() {
		Product product = new Product();
		product.setType(Type.FOOD);
		product.setImported(true);
		List<Tax> applicableTaxes = productTaxFactory.getApplicableTaxes(product);
		assertTrue(applicableTaxes.size() == 1);
		assertEquals(com.amolpc.stc.domain.tax.Tax.Type.IMPORT_DUTY, applicableTaxes.get(0).getType());
	}
	
	@Test
	public void testGetApplicableTaxes_imported_medical_product() {
		Product product = new Product();
		product.setType(Type.MEDICAL);
		product.setImported(true);
		List<Tax> applicableTaxes = productTaxFactory.getApplicableTaxes(product);
		assertTrue(applicableTaxes.size() == 1);
		assertEquals(com.amolpc.stc.domain.tax.Tax.Type.IMPORT_DUTY, applicableTaxes.get(0).getType());
	}
	
	@Test
	public void testGetApplicableTaxes_book_product() {
		Product product = new Product();
		product.setType(Type.BOOK);
		List<Tax> applicableTaxes = productTaxFactory.getApplicableTaxes(product);
		assertTrue(applicableTaxes.size() == 0);
	}
	
	@Test
	public void testGetApplicableTaxes_food_product() {
		Product product = new Product();
		product.setType(Type.FOOD);
		List<Tax> applicableTaxes = productTaxFactory.getApplicableTaxes(product);
		assertTrue(applicableTaxes.size() == 0);
	}
	
	@Test
	public void testGetApplicableTaxes_medical_product() {
		Product product = new Product();
		product.setType(Type.MEDICAL);
		List<Tax> applicableTaxes = productTaxFactory.getApplicableTaxes(product);
		assertTrue(applicableTaxes.size() == 0);
	}

	@Test
	public void testGetApplicableTaxes_other_product() {
		Product product = new Product();
		product.setType(Type.OTHER);
		List<Tax> applicableTaxes = productTaxFactory.getApplicableTaxes(product);
		assertTrue(applicableTaxes.size() == 1);
		assertEquals(com.amolpc.stc.domain.tax.Tax.Type.BASIC_SALES_TAX, applicableTaxes.get(0).getType());
	}
	
	@Test
	public void testGetApplicableTaxes_imported_other_product() {
		Product product = new Product();
		product.setImported(true);
		product.setType(Type.OTHER);
		List<Tax> applicableTaxes = productTaxFactory.getApplicableTaxes(product);
		assertTrue(applicableTaxes.size() == 2);
		boolean basicSalesTaxAdded = false;
		boolean importDutyAdded = false;
		for (Tax tax : applicableTaxes) {
			if(tax.getType().equals(Tax.Type.BASIC_SALES_TAX)){
				basicSalesTaxAdded = true;
			}
			if(tax.getType().equals(Tax.Type.IMPORT_DUTY)){
				importDutyAdded = true;
			}
		}
		assertTrue(importDutyAdded);
		assertTrue(basicSalesTaxAdded);
	}
}
