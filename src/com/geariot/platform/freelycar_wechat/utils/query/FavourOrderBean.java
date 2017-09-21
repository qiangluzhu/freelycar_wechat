package com.geariot.platform.freelycar_wechat.utils.query;

import java.util.Set;

import com.geariot.platform.freelycar_wechat.entities.FavourToOrder;

public class FavourOrderBean {
	String openId;
	float totalPrice;
	Set<FavourToOrder> favours;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Set<FavourToOrder> getFavours() {
		return favours;
	}
	public void setFavours(Set<FavourToOrder> favours) {
		this.favours = favours;
	}
}
