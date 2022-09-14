package com.inventory.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.inventory.bean.InventoryItem;
import java.lang.String;
import java.util.List;

import javax.transaction.Transactional;

@Repository
public interface InventoryDao extends JpaRepository<InventoryItem,Long> {
	
	List<InventoryItem> findByProductCode(String productcode);
	
	@Modifying
	@Transactional
	@Query(nativeQuery = true,value = "UPDATE InventoryItem SET availableQuantity=:quantity WHERE productCode=:code")
	public InventoryItem updateInventory(@Param("quantity") Integer quantity,@Param("code") String code);

}
