package com.geariot.platform.yi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geariot.platform.yi.entities.ServiceType;
import com.geariot.platform.yi.service.ServiceTypeService;

@RestController
@RequestMapping(value="/stype")
public class ServiceTypeController {
	@Autowired
	ServiceTypeService service;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(ServiceType c) {
		return service.add(c);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(String id) {
		return service.delete(id);
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(ServiceType c) {
		return service.modify(c);
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String find(ServiceType s) {
		return service.find(s);
	}
	
	@RequestMapping(value = "/getById", method = RequestMethod.GET)
	public String getById(String id){
		return service.getById(id);
	}
	
}
