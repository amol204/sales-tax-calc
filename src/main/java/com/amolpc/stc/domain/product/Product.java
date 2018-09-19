package com.amolpc.stc.domain.product;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * This is a simple POJO which will act as an input for the calculation
 */
public class Product implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private long quantity = 1;
	private Type type;
	private boolean isImported = false;
	private String description;
	private BigDecimal price;

	public static enum Type {
		BOOK, FOOD, MEDICAL, OTHER
	}

	/**
	 * Calculates total price of product.
	 * <br> Total Price = (no. of units) * (price per unit)
	 * @return
	 */
	public BigDecimal calculateTotalPrice(){
		return (price != null ? price.multiply(BigDecimal.valueOf(quantity)) : BigDecimal.ZERO);
	}

	public Type getType() {
		return type;
	}

	public boolean isImported() {
		return isImported;
	}


	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setImported(boolean isImported) {
		this.isImported = isImported;
	}
}
