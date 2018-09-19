package com.amolpc.stc.ws;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amolpc.stc.domain.product.Product;
import com.amolpc.stc.domain.product.ProductBill;
import com.amolpc.stc.domain.product.ProductBillConsolidated;
import com.amolpc.stc.exceptions.BadRequestException;
import com.amolpc.stc.exceptions.ParserException;
import com.amolpc.stc.parser.Parser;
import com.amolpc.stc.service.TaxCalculationService;

@RestController
public class CalculationService {
	@Autowired
	private TaxCalculationService calculationService;

	@Autowired
	private Parser parser;

	@RequestMapping(value ="/calculate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE )
	public String[] calculate(@RequestBody String [] strArr) throws ParserException, BadRequestException{
		if(strArr == null || strArr.length == 0){
			throw new BadRequestException("Request can not be null or empty");
		}
		List<Product> products = parser.parse(strArr);
		ProductBillConsolidated productBillConsolidated = getConsolidatedBill(products);
		return convertBillToStrArray(productBillConsolidated);
	}

	private ProductBillConsolidated getConsolidatedBill(List<Product> products) {
		List<ProductBill> bills = new ArrayList<>();
		for (Product product: products) {
			ProductBill bill = calculationService.calculate(product);
			bills.add(bill);
		}
		ProductBillConsolidated productBillConsolidated = new ProductBillConsolidated(bills);
		return productBillConsolidated;
	}
	
	private String[] convertBillToStrArray(ProductBillConsolidated productBillConsolidated){
		List<ProductBill> productBills = productBillConsolidated.getProductBills();
		String[] arr = new String[productBills.size() + 2];
		int i = 0;
		for (;i<productBills.size();i++){
			ProductBill bill = productBills.get(i);
			arr[i] = bill.getProductDetail();
		}
		arr[i] = "Sales Taxes : "+productBillConsolidated.getSalesTax();
		arr[++i] = "Total : "+productBillConsolidated.getTotal();
		return arr;
	}
}
