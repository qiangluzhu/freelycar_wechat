package com.geariot.platform.yi.dao;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.geariot.platform.yi.entities.Community;

public interface CommunityDao {

	public boolean add(Community c,MultipartFile file);

	public boolean delete(String id);

	public boolean modify(Community c,MultipartFile file);

	public List<Community> find(Community c, int start, int number);

	public Community getById(String id);

}
