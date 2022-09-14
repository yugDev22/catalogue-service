package com.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.bean.InventoryItem;
import com.inventory.persistence.InventoryDao;

@Service
public class InventoryServiceImpl implements InventoryService {
	
	@Autowired
	private InventoryDao inventoryDao;

	@Override
	public InventoryItem searchItemByProductCode(String code) {
		 List<InventoryItem> list = inventoryDao.findByProductCode(code);
		 if(list.isEmpty()) {
			 return null;
		 }
		 return list.get(0);
	}

	@Override
	public InventoryItem updateQuantity(String code, Integer quantity) {
		if(quantity<0) {
			return null;
		}
		InventoryItem item = searchItemByProductCode(code);
		if(item==null) {
			return null;
		}
		int available = item.getAvailableQuantity();
		int rows = inventoryDao.updateInventory(available+quantity, code);
		if(rows>0) {
			item.setAvailableQuantity(available+quantity);
			return item;
		}
		return null;
	}

}
