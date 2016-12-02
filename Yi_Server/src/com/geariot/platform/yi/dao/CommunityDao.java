package com.geariot.platform.yi.dao;

import java.util.List;

import com.geariot.platform.yi.entities.Community;

public interface CommunityDao {

	public boolean add(Community c);

	public boolean delete(String id);

	public boolean modify(Community c);

	public List<Community> find(Community c, int start, int number);

}
