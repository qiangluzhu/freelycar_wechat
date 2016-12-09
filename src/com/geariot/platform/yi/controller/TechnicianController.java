package com.geariot.platform.yi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geariot.platform.yi.entities.Technician;
import com.geariot.platform.yi.service.TechnicianService;

@RestController
@RequestMapping(value="/technician")
public class TechnicianController {
	@Autowired
	TechnicianService service;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(Technician c) {
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
	public String find(Technician c,int page,int number) {
		return service.find(c,page,number);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(String phone,String password) {
		return service.login(phone,password);
	}
	
	@RequestMapping(value = "/getReservation", method = RequestMethod.GET)
	public String getReservation(String openId,int page,int number) {
		return service.getReservation(openId,page,number);
	}
	
	@RequestMapping(value="/getById",method=RequestMethod.GET)
	public String getById(String id){
		return service.getById(id);
	}
}
