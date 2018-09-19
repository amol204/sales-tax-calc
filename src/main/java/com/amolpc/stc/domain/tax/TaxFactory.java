package com.amolpc.stc.domain.tax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TaxFactory {
	@Autowired
	private ApplicationContext ctx;

	public Tax getTax(Tax.Type type) {
		if(type == null){
			throw new IllegalArgumentException("Type can not be null");
		}
		return (Tax) ctx.getBean(type.name());
	}
}
