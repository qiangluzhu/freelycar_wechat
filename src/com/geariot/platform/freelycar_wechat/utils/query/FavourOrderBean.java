package com.geariot.platform.freelycar_wechat.utils.query;

import java.util.Set;

import com.geariot.platform.freelycar_wechat.entities.FavourToOrder;

public class FavourOrderBean {
	String openId;
	double totalPrice;
	Set<FavourToOrder> favours;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Set<FavourToOrder> getFavours() {
		return favours;
	}
	public void setFavours(Set<FavourToOrder> favours) {
		this.favours = favours;
	}
}
