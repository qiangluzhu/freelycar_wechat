package com.geariot.platform.freelycar.dao;

import com.geariot.platform.freelycar.entities.WXUser;

public interface WXUserDao {
	WXUser findUserByOpenId(String openId);
	WXUser findUserByPhone(String phone);
	void saveOrUpdateUser(WXUser wxUser);
	boolean deleteUser(String openId);
}
