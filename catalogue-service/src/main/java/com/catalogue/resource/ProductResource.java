package com.catalogue.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.catalogue.bean.Product;
import com.catalogue.bean.ProductList;
import com.catalogue.service.ProductService;

@RestController
public class ProductResource {

	@Autowired
	private ProductService productService;
	
	@GetMapping(path = "/products")
	public ResponseEntity<ProductList> listAllProducts(){
		List<Product> prodList = productService.getAllProducts();
		if(prodList.isEmpty()) {
			return new ResponseEntity<ProductList>(new ProductList(),HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<ProductList>(new ProductList(prodList),HttpStatus.FOUND);
	}
	
	@GetMapping(path = "/products/code/{productCode}")
	public ResponseEntity<Product> getProductByCode(@PathVariable("productCode") String productCode){
		Product product = productService.searchProductByCode(productCode);
		if(product!=null) {
			return new ResponseEntity<Product>(product,HttpStatus.FOUND);
		}
		return new ResponseEntity<Product>(product,HttpStatus.NO_CONTENT);
	}

}
