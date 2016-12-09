package com.geariot.platform.yi.dao;

import java.util.List;

import com.geariot.platform.yi.entities.Community;
import com.geariot.platform.yi.entities.Reservation;
import com.geariot.platform.yi.entities.UserAddress;

public interface UserDao {

	boolean register(String openId, String nickName);

	boolean add(UserAddress c);

	boolean delete(String addressId);

	boolean modify(UserAddress c);

	UserAddress getAddById(String id);

	List<UserAddress> getAll(String openId);

	boolean reserve(Reservation r);

	List<Reservation> getRecord(String openId, int start, int number);

	List<Community> getCode(int start, int number);

	boolean adminLogin(String username, String password);

	boolean setDefaultAddress(String addId,String userId);
	
	public int getrSize(String openId);
	
	public int getcSize();

}
