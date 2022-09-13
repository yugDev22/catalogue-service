package com.catalogue.service;

import java.util.List;

import com.catalogue.bean.Product;

public interface ProductService {

	public List<Product> getAllProducts();
	public Product searchProductByCode(String prodCode);
}
