package com.geariot.platform.freelycar_wechat.dao;

import com.geariot.platform.freelycar_wechat.entities.WXPayOrder;

public interface WXPayOrderDao {
	public void saveWXPayOrder(WXPayOrder wxPayOrder);
	public WXPayOrder findById(String wxPayOrderId);
}
