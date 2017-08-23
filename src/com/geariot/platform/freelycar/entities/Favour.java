/**
 * 
 */
package com.geariot.platform.freelycar.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.geariot.platform.freelycar.utils.JsonDateDeserialize;

/**
 * @author mxy940127
 *
 */

@Entity
public class Favour {
	private int id;					
	private String name;			//优惠券名称
	private int type;				//优惠券类型 1=抵用券 2=代金券
	private int validTime;			//券类有效时间
	private String content;			//内容说明
	@JsonDeserialize(using=JsonDateDeserialize.class)
	private Date buyDeadline;		//限时购买截止日期
	private Set<FavourProjectInfos> set;		//优惠项目
	private boolean deleted;		//是否删除
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getValidTime() {
		return validTime;
	}
	public void setValidTime(int validTime) {
		this.validTime = validTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getBuyDeadline() {
		return buyDeadline;
	}
	public void setBuyDeadline(Date buyDeadline) {
		this.buyDeadline = buyDeadline;
	}
	@OneToMany
	@JoinColumn(name="favourId", foreignKey=@ForeignKey(name="none"))
	public Set<FavourProjectInfos> getList() {
		return set;
	}
	public void setList(Set<FavourProjectInfos> set) {
		this.set = set;
	}
	public boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
}
