package com.geariot.platform.yi.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geariot.platform.yi.entities.Reservation;
import com.geariot.platform.yi.entities.UserAddress;
import com.geariot.platform.yi.service.UserService;
import com.geariot.platform.yi.utils.MD5;

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
	
	//id:用户的openid
	@RequestMapping(value = "/getAddById", method = RequestMethod.GET)
	public String getAddByOpenId(String id) {
		return service.getAddById(id);
	}
	
	@RequestMapping(value = "/getAddByAddId", method = RequestMethod.GET)
	public String getAddById(String id) {
		return service.getAddByAddId(id);
	}
	
	@RequestMapping(value = "/getAll", method = RequestMethod.GET)
	public String getAll(String openId) {
		return service.getAll(openId);
	}
	
	
	@RequestMapping(value = "/reserve", method = RequestMethod.POST)
	public String reserve(Reservation r) {
		Reservation rr=new Reservation();
		rr.setPhone("12345466787");
		rr.setRperson("cyl");
		rr.setAddress("Nanjing");
		rr.setOnTime(new Date());
		return service.reserve(rr);
	}
	
	@RequestMapping(value = "/getRecord", method = RequestMethod.GET)
	public String getRecord(String openId,int page,int number) {
		return service.getRecord(openId,page,number);
	}
	
	@RequestMapping(value = "/getCode", method = RequestMethod.GET)
	public String getCode(int page,int number) {
		return service.getCode(page,number);
	}
	
	@RequestMapping(value = "/adminLogin", method = RequestMethod.POST)
	public String adminLogin(String username,String password) {
		password=MD5.compute(password);
		return service.adminLogin(username,password);
	}
	
	@RequestMapping(value = "/setAddress", method = RequestMethod.GET)
	public String setDefaultAddress(String addId,String userId) {
		return service.setDefaultAddress(addId,userId);
	}
}

