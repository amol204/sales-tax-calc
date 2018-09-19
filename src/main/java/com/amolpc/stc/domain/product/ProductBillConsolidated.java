package com.amolpc.stc.domain.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ProductBillConsolidated implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<ProductBill> productBills;

	public ProductBillConsolidated(List<ProductBill> productBills) {
		if(productBills == null || productBills.isEmpty()){
			throw new IllegalArgumentException("Product bills can not be null or empty");
		}
		this.productBills = productBills;
	}

	public List<ProductBill> getProductBills() {
		return productBills;
	}
	
	/**
	 * Calculates the total sales tax for all product bills
	 * @return
	 */
	public BigDecimal getSalesTax(){
		BigDecimal total = BigDecimal.ZERO;
		for (ProductBill productBill : productBills) {
			BigDecimal taxPerProduct = productBill.calculateTotalTax();
			total = total.add(taxPerProduct);
		}
		return total;
	}
	
	/**
	 * Calculates total price of product including sales taxes
	 * @return
	 */
	public BigDecimal getTotal(){
		BigDecimal totalPriceOfProducts = BigDecimal.ZERO;
		for (ProductBill productBill : productBills) {
			BigDecimal pricePerProduct = productBill.getProduct().calculateTotalPrice();
			totalPriceOfProducts = totalPriceOfProducts.add(pricePerProduct);
		}
		return totalPriceOfProducts.add(getSalesTax());
	}
}
