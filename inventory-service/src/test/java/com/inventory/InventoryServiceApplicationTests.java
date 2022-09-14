package com.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.inventory.bean.InventoryItem;
import com.inventory.persistence.InventoryDao;
import com.inventory.service.InventoryServiceImpl;

@SpringBootTest
class InventoryServiceApplicationTests {


	@Nested
	class InventoryDaoLayerTests{
		
		@Autowired
		private InventoryDao inventoryDao;
		
		@BeforeEach
		public void setUp() {
			inventoryDao.deleteAll();
		}
		
		@Test
		public void testForFindByProductCodePositive() {
			InventoryItem item1 = new InventoryItem(101L,"P001",123);
			InventoryItem item2 = new InventoryItem(102L,"I001",125);
			List<InventoryItem> expectedList = new ArrayList<InventoryItem>();
			expectedList.add(item1);
			inventoryDao.save(item1);
			inventoryDao.save(item2);
			
			assertIterableEquals(expectedList, inventoryDao.findByProductCode("P001"));
		}
		
		@Test
		public void testForFindByProductCodeNegative() {
			InventoryItem item1 = new InventoryItem(101L,"P001",123);
			List<InventoryItem> expectedList = new ArrayList<InventoryItem>();
			
			inventoryDao.save(item1);
			
			assertIterableEquals(expectedList, inventoryDao.findByProductCode("I001"));
		}
		
		@Test
		public void testForUpdateInventoryPositive() {
			InventoryItem item1 = new InventoryItem(101L,"P001",123);
			InventoryItem item2 = new InventoryItem(102L,"I001",125);

			inventoryDao.save(item1);
			inventoryDao.save(item2);
			
			assertEquals(1, inventoryDao.updateInventory(133,"P001"));
		}
		
		@Test
		public void testForUpdateInventoryNegative() {
			InventoryItem item1 = new InventoryItem(101L,"P001",123);

			inventoryDao.save(item1);
			
			assertEquals(0, inventoryDao.updateInventory(135,"I001"));
		}
	}
	
	@Nested
	class InventoryServiceLayerTests{
		
		@Mock
		InventoryDao inventoryDao1;
		
		@InjectMocks
		private InventoryServiceImpl inventoryService;
		
		@Test
		public void testForSearchItemByProductCodePositive() {
			InventoryItem item1 = new InventoryItem(101L,"P001",123);
			List<InventoryItem> expectedList = new ArrayList<InventoryItem>();
			expectedList.add(item1);
			Mockito.when(inventoryDao1.findByProductCode("P001")).thenReturn(expectedList);
			
			assertEquals(item1, inventoryService.searchItemByProductCode("P001"));
		}
		
		@Test
		public void testForSearchItemByProductCodeNegative() {
			
			Mockito.when(inventoryDao1.findByProductCode("I001")).thenReturn(new ArrayList<InventoryItem>());
			
			assertEquals(null, inventoryService.searchItemByProductCode("P001"));
		}
		
		@Test
		public void testForUpdateQuantityPositive() {
			InventoryItem expectedItem = new InventoryItem(101L,"P001",133);
			
			InventoryItem item = new InventoryItem(101L,"P001",123);
			List<InventoryItem> expectedList = new ArrayList<InventoryItem>();
			expectedList.add(item);
			
			Mockito.when(inventoryDao1.findByProductCode("P001")).thenReturn(expectedList);
			Mockito.when(inventoryDao1.updateInventory(133,"P001")).thenReturn(1);
			
			assertEquals(expectedItem, inventoryService.updateQuantity("P001",10));
		}
		
		@Test
		public void testForUpdateQuantityNegative1() {
	
			Mockito.when(inventoryDao1.updateInventory(133,"I001")).thenReturn(0);
			
			assertEquals(null, inventoryService.updateQuantity("I001",10));
		}
		
		@Test
		public void testForUpdateQuantityNegative2() {
			
			assertEquals(null, inventoryService.updateQuantity("I001",-10));
		}
		
	}

}
