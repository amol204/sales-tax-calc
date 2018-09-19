package com.amolpc.stc.config;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.amolpc.stc.domain.tax.SimpleTaxImpl;
import com.amolpc.stc.domain.tax.Tax;

@SpringBootApplication(scanBasePackages = {"com.amolpc"})
@PropertySources({
	@PropertySource(value = { "classpath:taxConfig.properties" }),
	@PropertySource(value = { "classpath:keywords.properties" })
})
public class AppConfig {
	@Autowired
	private Environment env;
	
	@Bean(name = "BASIC_SALES_TAX")
	public Tax basicSalesTax(){
		String property = env.getProperty("sales.tax.multiplier");
		SimpleTaxImpl taxImpl = new SimpleTaxImpl(Tax.Type.BASIC_SALES_TAX, new BigDecimal(property));
		return taxImpl;
	}
	
	@Bean(name = "IMPORT_DUTY")
	public Tax importDutyTax(){
		String property = env.getProperty("import.tax.multiplier");
		SimpleTaxImpl taxImpl = new SimpleTaxImpl(Tax.Type.IMPORT_DUTY, new BigDecimal(property));
		return taxImpl;
	}


	@Bean
	public MappingJackson2HttpMessageConverter jacksonMessageChanger() {
		MappingJackson2HttpMessageConverter obj = new MappingJackson2HttpMessageConverter();
		return obj;
	}
	
	@Bean
	public RequestMappingHandlerAdapter annotationMethodHandlerAdapter() {
		RequestMappingHandlerAdapter obj = new RequestMappingHandlerAdapter();
		obj.getMessageConverters().add(0, jacksonMessageChanger());
		return obj;
	}
}
