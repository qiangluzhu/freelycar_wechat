package com.geariot.platform.freelycar.dao;

import java.util.List;

import com.geariot.platform.freelycar.entities.InventoryType;

public interface InventoryTypeDao {
	
	long getCount();
	
	InventoryType findById(int inventoryTypeId);
	
	void add(InventoryType inventoryType);
	
	void delete(int typeId);
	
	List<InventoryType> list(int from, int pageSize);
	
	List<InventoryType> query(String andCondition, int from, int pageSize);
	
	long getQueryCount(String andCondition);

	InventoryType findByName(String typeName);
}
