package com.geariot.platform.yi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geariot.platform.yi.entities.Technician;
import com.geariot.platform.yi.service.TechnicianService;
import com.geariot.platform.yi.utils.MD5;

@RestController
@RequestMapping(value="/technician")
public class TechnicianController {
	@Autowired
	TechnicianService service;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(Technician c) {
//		Technician t=new Technician();
//		t.setCreateTime(new Date());
//		t.setName("小王");
//		t.setOpenId("qwer1234");
//		t.setPassword("123");
//		t.setPhone("123456");
//		t.setSid("1");
//		t.setPassword(MD5.compute(t.getPassword()));
//		service.add(t);
		
		c.setPassword(MD5.compute(c.getPassword()));
		return service.add(c);
//		return null;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(String id) {
		return service.delete(id);
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(Technician c) {
		return service.modify(c);
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String find(Technician c,int start,int number) {
		return service.find(c,start,number);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(String phone,String password) {
		return service.login(phone,password);
	}
	
	@RequestMapping(value = "/getReservation", method = RequestMethod.GET)
	public String getReservation(String openId,int start,int number) {
		return service.getReservation(openId,start,number);
	}
}
