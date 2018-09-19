package com.amolpc.stc.domain.product;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ProductBillConsolidatedTest {
	
	private ProductBill bill;
	
	@SuppressWarnings("serial")
	@Before
	public void setUp(){
		Product product = new Product(){

			@Override
			public BigDecimal calculateTotalPrice() {
				return BigDecimal.valueOf(200);
			}
			
		};
		bill = new ProductBill(product){

			@Override
			public BigDecimal calculateTotalTax() {
				return BigDecimal.TEN;
			}
			
		};
	}

	@Test(expected = IllegalArgumentException.class)
	public void testProductBillConsolidated_null_argument() {
		new ProductBillConsolidated(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testProductBillConsolidated_empty_argument() {
		List<ProductBill> bills = new ArrayList<>();
		new ProductBillConsolidated(bills);
	}

	@Test
	public void testGetSalesTax_single_product() {
		List<ProductBill> bills = new ArrayList<>();
		bills.add(bill);
		ProductBillConsolidated billConsolidated = new ProductBillConsolidated(bills);
		BigDecimal salesTax = billConsolidated.getSalesTax();
		assertEquals(BigDecimal.valueOf(10), salesTax);
	}
	
	@Test
	public void testGetSalesTax_two_products() {
		List<ProductBill> bills = new ArrayList<>();
		bills.add(bill);
		bills.add(bill);
		ProductBillConsolidated billConsolidated = new ProductBillConsolidated(bills);
		BigDecimal salesTax = billConsolidated.getSalesTax();
		assertEquals(BigDecimal.valueOf(20), salesTax);
	}

	@Test
	public void testGetTotal_single_product() {
		List<ProductBill> bills = new ArrayList<>();
		bills.add(bill);
		ProductBillConsolidated billConsolidated = new ProductBillConsolidated(bills);
		BigDecimal total = billConsolidated.getTotal();
		assertEquals(BigDecimal.valueOf(210), total);
	}

	@Test
	public void testGetTotal_two_products() {
		List<ProductBill> bills = new ArrayList<>();
		bills.add(bill);
		bills.add(bill);
		ProductBillConsolidated billConsolidated = new ProductBillConsolidated(bills);
		BigDecimal total = billConsolidated.getTotal();
		assertEquals(BigDecimal.valueOf(420), total);
	}
}
