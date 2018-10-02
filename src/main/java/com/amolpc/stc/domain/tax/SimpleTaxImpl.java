package com.amolpc.stc.domain.tax;

import java.math.BigDecimal;

public class SimpleTaxImpl implements Tax {
	private Type type;
	private BigDecimal multiplier;

	public SimpleTaxImpl(Type type, BigDecimal multiplier) {
		super();
		this.type = type;
		this.multiplier = multiplier;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public BigDecimal calculate(BigDecimal amount) {
		if (amount != null) {
			BigDecimal tax = amount.multiply(multiplier);
			
			// round up to nearest 0.05
			double roundedTax = Math.ceil(tax.doubleValue() * 20.0) / 20.0;
			BigDecimal roundedTaxBigDecimal = BigDecimal.valueOf(roundedTax);
            System.out.println("amount = " + amount + " tax multiplier = " + multiplier + " tax = " + tax
                    + " roundedTax = " + roundedTax + " roundedTaxBigDecimal = " + roundedTaxBigDecimal);
            return roundedTaxBigDecimal;
		}
		return null;
	}

}
