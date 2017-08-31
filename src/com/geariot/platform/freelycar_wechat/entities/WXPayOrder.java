/**
 * 
 */
package com.geariot.platform.freelycar_wechat.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.geariot.platform.freelycar_wechat.utils.JsonDateDeserialize;

/**
 * @author mxy940127
 *
 */

@Entity
public class WXPayOrder {
	private String id;
	private String openId;		//微信openId
	private double totalPrice;		//支付金额
	@JsonDeserialize(using=JsonDateDeserialize.class)
	private Date createDate;	//订单产生时间
	private Date finishDate;	//完成时间
	private String productName; //产品名称
	private int payState;		//订单支付状态
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	private Service service;	//购买的卡类
	private int payMethod;		//支付方式
	private Set<FavourToOrder> favours;//购买券
	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinColumn(name="wxOrderId", foreignKey=@ForeignKey(name="none"))
	public Set<FavourToOrder> getFavours() {
		return favours;
	}
	public void setFavours(Set<FavourToOrder> favours) {
		this.favours = favours;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getPayState() {
		return payState;
	}
	public void setPayState(int payState) {
		this.payState = payState;
	}
	@ManyToOne
	@JoinColumn(name="serviceId", foreignKey=@ForeignKey(name="none"))
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	public int getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(int payMethod) {
		this.payMethod = payMethod;
	}
	
}
