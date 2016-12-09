package com.geariot.platform.yi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.geariot.platform.yi.entities.Community;
import com.geariot.platform.yi.service.CommunityService;

@RestController
@RequestMapping(value="/community")
public class CommunityController {
	@Autowired
	CommunityService service;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(Community c,MultipartFile file) {
		return service.add(c,file);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(String id) {
		return service.delete(id);
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(Community c,MultipartFile file) {
		return service.modify(c,file);
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String find(Community c,int page,int number) {
		return service.find(c,page,number);
	}
	
	@RequestMapping(value = "/getById", method = RequestMethod.GET)
	public String getById(String id) {
		return service.getById(id);
	}
	
}
