/**
 * 
 */
package com.geariot.platform.freelycar_wechat.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
	private Date finishDate;	//支付完时间
	private int payState;		//订单支付状态,0未支付，1支付完成
	private Service service;	//购买的卡类
	private Favour favour;		//购买的抵用券
	private int payMethod;		//支付方式，只有微信0
	public Date getFinishDate() {
		return finishDate;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
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
	@ManyToOne
	@JoinColumn(name="favourId", foreignKey=@ForeignKey(name="none"))
	public Favour getFavour() {
		return favour;
	}
	public void setFavour(Favour favour) {
		this.favour = favour;
	}
	public int getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(int payMethod) {
		this.payMethod = payMethod;
	}
	
}
