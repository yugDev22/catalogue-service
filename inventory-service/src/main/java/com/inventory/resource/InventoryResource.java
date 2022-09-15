package com.inventory.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.bean.InventoryItem;
import com.inventory.service.InventoryService;

@RestController
public class InventoryResource {
	
	@Autowired
	private InventoryService inventoryService;
	
	@GetMapping(path = "/code/{productCode}",produces=MediaType.APPLICATION_JSON_VALUE)
	public InventoryItem getInventoryItemByProductCode(@PathVariable("productCode") String productCode) {
		return inventoryService.searchItemByProductCode(productCode);
	}

	@PutMapping(path = "/code/{productCode}/{availableQuantity}",produces=MediaType.APPLICATION_JSON_VALUE)
	public InventoryItem updateInventoryItemQuantityByProductCode(@PathVariable("productCode") String productCode,@PathVariable("availableQuantity") int availableQuantity) {
		return inventoryService.updateQuantity(productCode, availableQuantity);
	}

}
