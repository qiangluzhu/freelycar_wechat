package com.geariot.platform.yi.dao;

import java.util.List;

import com.geariot.platform.yi.entities.Store;

public interface StoreDao {

	public boolean add(Store c);

	public boolean delete(String id);

	public boolean modify(Store c);

	public List<Store> find(Store c, int start, int number);

}
