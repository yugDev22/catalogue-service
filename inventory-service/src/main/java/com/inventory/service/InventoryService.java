package com.inventory.service;

import com.inventory.bean.InventoryItem;

public interface InventoryService {

	public InventoryItem searchItemByProductCode(String code);
	public InventoryItem updateQuantity(String code, Integer quantity);
	
}
