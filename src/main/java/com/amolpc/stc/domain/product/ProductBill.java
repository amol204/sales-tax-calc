package com.amolpc.stc.domain.product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amolpc.stc.domain.tax.Tax;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * Represents the bill corresponding to a given {@linkplain Product}
 * <br> Holds the taxes applicable to a given products
 */
public class ProductBill implements Serializable {

	private static final long serialVersionUID = 1L;

	private Product product;

	/**
	 * holds info about various types of taxes.
	 * <br>e.g.
	 * 		<br>Basic Sales Tax = 45
	 * 		<br>Import Duty = 60
	 */
	private Map<Tax.Type, BigDecimal> taxAmountMap = new HashMap<>();

	public ProductBill(Product product) {
		super();
		this.product = product;
	}

	public void applyTaxes(List<Tax> taxes) {
		if(taxes==null || taxes.isEmpty()){
			return;
		}
		for (Tax tax : taxes) {
			BigDecimal taxAmt = tax.calculate(product.calculateTotalPrice());
			taxAmountMap.put(tax.getType(), taxAmt);
		}
	}

	public BigDecimal calculateTotalTax() {
		BigDecimal totalTax = BigDecimal.ZERO;
		Collection<BigDecimal> taxAmounts = taxAmountMap.values();
		for (BigDecimal taxAmt : taxAmounts) {
			totalTax = totalTax.add(taxAmt);
		}
		return totalTax;
	}

	public String getProductDetail() {
		return product.getQuantity() + " " + product.getDescription() + " : "
				+ product.calculateTotalPrice().add(calculateTotalTax());
	}

	@JsonIgnore
	public Product getProduct() {
		return product;
	}
}
