package com.geariot.platform.yi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geariot.platform.yi.entities.Store;
import com.geariot.platform.yi.service.StoreService;

@RestController
@RequestMapping(value="/store")
public class StoreController {
	@Autowired
	StoreService service;
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(Store c) {
//		Store s=new Store();
//		s.setAddress("青岛路22号");
//		s.setCity("南京");
//		s.setProvince("江苏");
//		s.setRegion("玄武");
//		s.setInfo("这是一个爱车的地方");
//		s.setName("小一爱车");
//		s.setPhone("123456788");
//		s.setUrl("123.jpg");
//		service.add(s);
		
		return service.add(c);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(String id) {
		return service.delete(id);
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(Store c) {
		return service.modify(c);
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public String find(Store c,int start,int number) {
//		Store s=new Store();
//		s.setCity("南京");
		return service.find(c,start,number);
	}
	
	@RequestMapping(value = "/getById", method = RequestMethod.GET)
	public String getById(String id){
		return service.getById(id);
	}
}
