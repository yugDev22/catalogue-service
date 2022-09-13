package com.catalogue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catalogue.bean.Product;
import com.catalogue.dao.ProductDao;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDao productDao;

	@Override
	public List<Product> getAllProducts() {
		return productDao.findAll();
	}

	@Override
	public Product searchProductByCode(String prodCode) {
		List<Product> list = productDao.findByCode(prodCode);
		if(list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

}
