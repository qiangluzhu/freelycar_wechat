package com.geariot.platform.yi.entities;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "company_oder")
public class Company {
	private String id;
	private String name;
	private String person;	//报名人
	private String openId;	//报名人openid
	private String userRequire;	//需求
	private Date createTime;	//报名时间
	private String phone;	//联系电话
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	@Basic(fetch = FetchType.LAZY)   
	@Type(type="text")  
	@Column(name="userRequire", nullable=true)   
	public String getUserRequire() {
		return userRequire;
	}
	public void setUserRequire(String userRequire) {
		this.userRequire = userRequire;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

}
