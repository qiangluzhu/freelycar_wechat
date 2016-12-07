package com.geariot.platform.yi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geariot.platform.yi.entities.Community;
import com.geariot.platform.yi.service.CommunityService;

@RestController
@RequestMapping(value="/community")
public class CommunityController {
	@Autowired
	CommunityService service;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(Community c) {
		return service.add(c);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(String id) {
		return service.delete(id);
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(Community c) {
		return service.modify(c);
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String find(Community c,int start,int number) {
		return service.find(c,start,number);
	}
	
	@RequestMapping(value = "/getById", method = RequestMethod.GET)
	public String getById(String id) {
		return service.getById(id);
	}
	
}
