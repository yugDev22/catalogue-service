package com.catalogue.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalogue.bean.Product;
import java.lang.String;
import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product,Long> {
	
	List<Product> findByCode(String code);

}
