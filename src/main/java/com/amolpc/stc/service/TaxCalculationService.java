package com.amolpc.stc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amolpc.stc.domain.product.Product;
import com.amolpc.stc.domain.product.ProductBill;
import com.amolpc.stc.domain.tax.Tax;

@Component
public class TaxCalculationService {
	
	@Autowired
	private ProductTaxFactory productTaxFactory;
	
	public ProductBill calculate(Product product){
		List<Tax> applicableTaxes = productTaxFactory.getApplicableTaxes(product);
		ProductBill bill = new ProductBill(product);
		bill.applyTaxes(applicableTaxes);
		return bill;
	}
}
