package com.geariot.platform.yi.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geariot.platform.yi.entities.Reservation;
import com.geariot.platform.yi.entities.UserAddress;
import com.geariot.platform.yi.service.UserService;

@RestController
@RequestMapping(value="/user")
public class UserController {
	@Autowired
	UserService service;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(String openId,String nickName) {
		return service.register(openId,nickName);
	}
	
	@RequestMapping(value = "/addAddress", method = RequestMethod.POST)
	public String addAddress(UserAddress  c) {
		return service.addAddress(c);
//		return null;
	}
	
	@RequestMapping(value = "/delAddress", method = RequestMethod.GET)
	public String delAddress(String addressId ) {
		return service.delAddress(addressId);
	}
	
	@RequestMapping(value = "/modAddress", method = RequestMethod.POST)
	public String modAddress(UserAddress c) {
		return service.modAddress(c);
	}
	
	@RequestMapping(value = "/getAddById", method = RequestMethod.GET)
	public String getAddById(String id) {
		return service.getAddById(id);
	}
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public String getAll(String openId) {
		return service.getAll(openId);
	}
	
	
	@RequestMapping(value = "/reserve", method = RequestMethod.POST)
	public String reserve(Reservation r) {
//		Reservation rr=new Reservation();
//		rr.setAddress("汉口路22号");
//		rr.setCity("南京");
//		rr.setCreateTime(new Date());
//		rr.setLicense("BC4234");
//		rr.setNickName("garfield");
//		rr.setOnTime(new Date());
//		rr.setOrderOpenId("asdf1234");
//		rr.setOrderPerson("小张");
//		rr.setOrderPhone("654321");
//		rr.setPhone("152342363345");
//		rr.setProvince("江苏");
//		rr.setRegion("鼓楼");
//		rr.setRemark("这里需要预约一个修车");
//		rr.setRopenId("aaa123");
//		rr.setRperson("王小明");
//		service.reserve(rr);
//		return null;
		return service.reserve(r);
	}
	
	@RequestMapping(value = "/getRecord", method = RequestMethod.GET)
	public String getRecord(String openId,int start,int number) {
		return service.getRecord(openId,start,number);
	}
	
	@RequestMapping(value = "/getCode", method = RequestMethod.GET)
	public String getCode(int start,int number) {
		return service.getCode(start,number);
	}
}

