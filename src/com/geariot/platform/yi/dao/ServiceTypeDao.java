package com.geariot.platform.yi.dao;

import java.util.List;

import com.geariot.platform.yi.entities.ServiceType;
import com.geariot.platform.yi.entities.Store;

public interface ServiceTypeDao {

	boolean add(ServiceType c);

	boolean delete(String id);

	boolean modify(ServiceType c);

	List<Store> find(ServiceType s);

	ServiceType getById(String id);

}
