package com.geariot.platform.yi.entities;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)  
@Table(name = "reservation")
public class Reservation {
	private String id;
	private String nickName;	//预约人微信昵称
	private String rperson;//预约人
	private String ropenId;	//预约人openid
	private String phone;
	private String province;//省
	private String city;	//市
	private String region;	//区
	private String address;	//具体地址
	private Date onTime;//上门时间
	private String license;	//车牌
	private String remark;	//备注
	private String orderPerson;	//接单人
	private String orderPhone;	//接单人电话
	private String orderOpenId;	//接单人id
	private Date createTime;	//订单创建时间
	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRperson() {
		return rperson;
	}
	public void setRperson(String rperson) {
		this.rperson = rperson;
	}
	public String getRopenId() {
		return ropenId;
	}
	public void setRopenId(String ropenId) {
		this.ropenId = ropenId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getOnTime() {
		return onTime;
	}
	public void setOnTime(Date onTime) {
		this.onTime = onTime;
	}
	public String getLicense() {
		return license;
	}
	public void setLicense(String license) {
		this.license = license;
	}
	@Basic(fetch = FetchType.LAZY)   
	@Type(type="text")  
	@Column(name="remark", nullable=true)   
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrderPerson() {
		return orderPerson;
	}
	public void setOrderPerson(String orderPerson) {
		this.orderPerson = orderPerson;
	}
	public String getOrderPhone() {
		return orderPhone;
	}
	public void setOrderPhone(String orderPhone) {
		this.orderPhone = orderPhone;
	}
	public String getOrderOpenId() {
		return orderOpenId;
	}
	public void setOrderOpenId(String orderOpenId) {
		this.orderOpenId = orderOpenId;
	}


}
