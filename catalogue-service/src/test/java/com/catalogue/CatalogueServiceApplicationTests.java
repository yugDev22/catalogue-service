package com.catalogue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.catalogue.bean.Product;
import com.catalogue.dao.ProductDao;
import com.catalogue.service.ProductServiceImpl;

@SpringBootTest
class CatalogueServiceApplicationTests {
	
	@Autowired
	private ProductDao productDao; 
	
	@AfterEach
	public void tearDown() {
		productDao.deleteAll();
		Product prod1 = new Product(101L,"P001","Poha","Ready to cook poha 100gm",25.00);
		Product prod2 = new Product(102L,"I001","Idli Rava","Ready to cook idli 100gm",125.00);
		productDao.save(prod1);
		productDao.save(prod2);
	}
	
	@Nested
	class ProductDaoLayerTests{
		@Test
		void testForFindAllMethodPositiveCase() {
			productDao.deleteAll();
			Product prod1 = new Product(101L,"P001","Poha","Ready to cook poha 100gm",25.00);
			Product prod2 = new Product(102L,"I001","Idli Rava","Ready to cook idli 100gm",125.00);
			productDao.save(prod1);
			productDao.save(prod2);
			List<Product> expectedList = new ArrayList<Product>();
			expectedList.add(prod1);
			expectedList.add(prod2);
			assertIterableEquals(expectedList,productDao.findAll());
		}
		
		@Test
		void testForFindAllMethodNegativeCase() {
			productDao.deleteAll();
			List<Product> expectedList = new ArrayList<Product>();
			assertIterableEquals(expectedList,productDao.findAll());
		}
		
		@Test
		void testForFindByCodeMethodPositiveCase() {
			productDao.deleteAll();
			Product prod1 = new Product(101L,"P001","Poha","Ready to cook poha 100gm",25.00);
			Product prod2 = new Product(102L,"I001","Idli Rava","Ready to cook idli 100gm",125.00);
			productDao.save(prod1);
			productDao.save(prod2);
			assertEquals(prod1,productDao.findByCode("P001").get(0));
		}	
		
		@Test
		void testForFindByCodeMethodNegativeCase() {
			productDao.deleteAll();
			Product prod1 = new Product(101L,"P001","Poha","Ready to cook poha 100gm",25.00);
			productDao.save(prod1);
			assertIterableEquals(new ArrayList<Product>(),productDao.findByCode("I001"));
		}	
	}
	
	@Nested
	@ExtendWith(MockitoExtension.class)
	class ProductServiceLayerTests{
		
		@Mock
		private ProductDao productDao1;
		
		@InjectMocks
		private ProductServiceImpl productService;

		
		@BeforeEach
		public void setUp() {
			//MockitoAnnotations.openMocks(this);
			//productDao1 = Mockito.mock(ProductDao.class);
		}
		
		@Test
		public void testForGetAllProductsMethodPositive() {
			Product prod1 = new Product(101L,"P001","Poha","Ready to cook poha 100gm",25.00);
			Product prod2 = new Product(102L,"I001","Idli Rava","Ready to cook idli 100gm",125.00);
			List<Product> expectedList = new ArrayList<Product>();
			expectedList.add(prod1);
			expectedList.add(prod2);
			Mockito.when(productDao1.findAll()).thenReturn(expectedList);
			
			assertIterableEquals(expectedList, productService.getAllProducts());
			
		}
		
		@Test
		public void testForGetAllProductsMethodNegative() {
			Mockito.when(productDao1.findAll()).thenReturn(new ArrayList<Product>());

			assertIterableEquals(new ArrayList<Product>(), productService.getAllProducts());
		}
		
		@Test
		public void testForSearchProductByCodeMethodPositive() {
			Product prod1 = new Product(101L,"P001","Poha","Ready to cook poha 100gm",25.00);
			Product prod2 = new Product(102L,"I001","Idli Rava","Ready to cook idli 100gm",125.00);
			ArrayList<Product> prod1List = new ArrayList<Product>();
			prod1List.add(prod1);
			ArrayList<Product> prod2List = new ArrayList<Product>();
			prod1List.add(prod2);
			Mockito.when(productDao1.findByCode("P001")).thenReturn(prod1List);
			
			assertEquals(prod1, productService.searchProductByCode("P001"));
		}
		
		@Test
		public void testForSearchProductByCodeMethodNegative() {
			ArrayList<Product> prod2List = new ArrayList<Product>();
			Mockito.when(productDao1.findByCode("I001")).thenReturn(prod2List);
			
			assertEquals(null, productService.searchProductByCode("I001"));
		}
		
	}

}
