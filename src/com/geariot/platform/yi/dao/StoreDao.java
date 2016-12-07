package com.geariot.platform.yi.dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.geariot.platform.yi.entities.Store;

public interface StoreDao {

	public boolean add(Store c,MultipartFile file);

	public boolean delete(String id);

	public boolean modify(Store c,MultipartFile file);

	public List<Store> find(Store c, int start, int number);

	public Store getById(String id);

}
