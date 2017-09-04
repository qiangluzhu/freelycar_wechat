package com.geariot.platform.freelycar_wechat.dao;

import java.util.List;

import com.geariot.platform.freelycar_wechat.entities.WXPayOrder;

public interface WXPayOrderDao {
	public void saveWXPayOrder(WXPayOrder wxPayOrder);
	public WXPayOrder findById(String wxPayOrderId);
	public List<WXPayOrder> listByOpenId(String openId, int from, int number);
	public long getCountByOpenId(String openId);
}
