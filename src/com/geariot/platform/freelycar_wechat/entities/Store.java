/**
 * 
 */
package com.geariot.platform.freelycar_wechat.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * @author mxy940127
 *
 */

@Entity
public class Store {
	private int id;							//门店Id
	private String name;					//门店名称
	private String address;					//门店地址
	private double latitude;				//门店纬度
	private double longitude;				//门店经度
	private String openingTime;				//门店营业时间
	private String phone;					//门店联系方式
	private List<Project> projects;			//门店经营项目
	private List<Favour> favours;			//门店优惠活动
	private List<imgUrl> imgUrls;			//门店图片
	private Date createDate;				//门店创建时间
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getOpeningTime() {
		return openingTime;
	}
	public void setOpeningTime(String openingTime) {
		this.openingTime = openingTime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@OneToMany
	@JoinColumn(name="storeId", foreignKey=@ForeignKey(name="none"))
	public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	@OneToMany
	@JoinColumn(name="storeId", foreignKey=@ForeignKey(name="none"))
	public List<Favour> getFavours() {
		return favours;
	}
	public void setFavours(List<Favour> favours) {
		this.favours = favours;
	}
	@OneToMany
	@JoinColumn(name="storeId", foreignKey=@ForeignKey(name="none"))
	public List<imgUrl> getImgUrls() {
		return imgUrls;
	}
	public void setImgUrls(List<imgUrl> imgUrls) {
		this.imgUrls = imgUrls;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	} 
	
}
