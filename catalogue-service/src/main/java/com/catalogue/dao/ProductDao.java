package com.catalogue.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.catalogue.bean.Product;

@Repository
public interface ProductDao extends JpaRepository<Product,String> {

}
