package com.amolpc.stc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amolpc.stc.domain.product.Product;
import com.amolpc.stc.domain.tax.Tax;
import com.amolpc.stc.domain.tax.TaxFactory;

/**
 * This class decides the taxes applicable for a given {@linkplain Product}
 */
@Component
public class ProductTaxFactory {

	@Autowired
	private TaxFactory taxFactory;

	public List<Tax> getApplicableTaxes(Product product) {
		List<Tax> taxes = new ArrayList<>();
		switch (product.getType()) {
		case BOOK:
		case FOOD:
		case MEDICAL:
			addImportDutyIfApplicable(product, taxes);
			break;
		case OTHER:
			addBasicSalesTax(taxes);
			addImportDutyIfApplicable(product, taxes);
			break;
		default:
			break;
		}
		return taxes;
	}

	private void addBasicSalesTax(List<Tax> taxes) {
		taxes.add(taxFactory.getTax(Tax.Type.BASIC_SALES_TAX));
	}

	private void addImportDutyIfApplicable(Product product, List<Tax> taxes) {
		if (product.isImported()) {
			taxes.add(taxFactory.getTax(Tax.Type.IMPORT_DUTY));
		}
	}
}
