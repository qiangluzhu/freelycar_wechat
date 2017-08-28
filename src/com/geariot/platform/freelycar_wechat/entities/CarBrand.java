package com.geariot.platform.freelycar_wechat.entities;
/*package com.geariot.platform.freelycar.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class CarBrand {
	private int id;
	private String brand;
	private char pinyin;
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public char getPinyin() {
		return pinyin;
	}
	public void setPinyin(char pinyin) {
		this.pinyin = pinyin;
	}
	private Set<CarType> types;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	@OneToMany(cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	@JoinColumn(name="brandId", foreignKey=@ForeignKey(name="none"))
	public Set<CarType> getTypes() {
		return types;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setTypes(Set<CarType> types) {
		this.types = types;
	}
	
	@Override
	public String toString() {
		return "CarBrand [id=" + id + ", name=" + name + ", carType=" + types + "]";
	}
}
*/