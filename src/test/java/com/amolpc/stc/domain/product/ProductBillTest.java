package com.amolpc.stc.domain.product;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.amolpc.stc.domain.tax.Tax;

public class ProductBillTest {
	
	private Tax tax1;
	
	private Tax tax2;
	
	@Before
	public void setUp(){
		tax1 = new Tax() {
			
			@Override
			public Type getType() {
				return Type.BASIC_SALES_TAX;
			}
			
			@Override
			public BigDecimal calculate(BigDecimal amount) {
				return BigDecimal.valueOf(200);
			}
		};
		
		tax2 = new Tax() {
			
			@Override
			public Type getType() {
				return Type.IMPORT_DUTY;
			}
			
			@Override
			public BigDecimal calculate(BigDecimal amount) {
				return BigDecimal.valueOf(100);
			}
		};
	}

	@Test
	public void testApplyTaxes_single_tax() {
		Product product = createProduct();
		
		ProductBill bill = new ProductBill(product);
		
		List<Tax> taxes = new ArrayList<>();
		taxes.add(tax1);
		
		bill.applyTaxes(taxes);
		BigDecimal totalTax = bill.calculateTotalTax();
		assertEquals(BigDecimal.valueOf(200), totalTax);
	}
	
	@Test
	public void testApplyTaxes_multiple_tax() {
		Product product = createProduct();
		
		ProductBill bill = new ProductBill(product);
		
		List<Tax> taxes = new ArrayList<>();
		taxes.add(tax1);
		taxes.add(tax2);
		
		bill.applyTaxes(taxes);
		BigDecimal totalTax = bill.calculateTotalTax();
		assertEquals(BigDecimal.valueOf(300), totalTax);
	}
	
	@Test
	public void testApplyTaxes_no_tax() {
		Product product = createProduct();
		
		ProductBill bill = new ProductBill(product);
		
		bill.applyTaxes(null);
		BigDecimal totalTax = bill.calculateTotalTax();
		assertEquals(BigDecimal.ZERO, totalTax);
	}

	private Product createProduct() {
		Product product = new Product();
		product.setDescription("product description");
		product.setPrice(BigDecimal.valueOf(120));
		product.setQuantity(2);
		return product;
	}

	@Test
	public void testGetProductDetail() {
		Product product = createProduct();
		
		ProductBill bill = new ProductBill(product);
		
		List<Tax> taxes = new ArrayList<>();
		taxes.add(tax1);
		taxes.add(tax2);
		
		bill.applyTaxes(taxes);
		
		String productDetail = bill.getProductDetail();
		assertEquals("2 product description : 540", productDetail);
	}

}
