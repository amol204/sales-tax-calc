package com.amolpc.stc.domain.tax;

import java.math.BigDecimal;

/**
 * Interface which defines the contract for tax classes
 * 
 *
 */
public interface Tax {
	
	Type getType();
	
	BigDecimal calculate(BigDecimal amount);

	public static enum Type{
		BASIC_SALES_TAX, IMPORT_DUTY
	}
}
