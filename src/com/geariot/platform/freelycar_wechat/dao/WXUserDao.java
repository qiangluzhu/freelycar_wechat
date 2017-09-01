package com.geariot.platform.freelycar_wechat.dao;

import com.geariot.platform.freelycar_wechat.entities.WXUser;

public interface WXUserDao {
	WXUser findUserByOpenId(String openId);
	WXUser findUserByPhone(String phone);
	void saveOrUpdateUser(WXUser wxUser);
	void deleteUser(String openId);
}
