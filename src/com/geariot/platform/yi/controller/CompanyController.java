package com.geariot.platform.yi.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geariot.platform.yi.entities.Company;
import com.geariot.platform.yi.service.CompanyService;

@RestController
@RequestMapping(value="/company")
public class CompanyController {
	@Autowired
	CompanyService service;
	
	@RequestMapping(value = "/getByName", method = RequestMethod.GET)
	public String getByName(Company c,int page,int number) {
		return service.getByName(c,page,number);
	}
	
	@RequestMapping(value = "/makeReserve", method = RequestMethod.POST)
	public String makeReserve(Company c) {
//		Company cc=new Company();
//		cc.setCreateTime(new Date());
//		cc.setName("企业内购");
//		cc.setOpenId("bbb123");
//		cc.setPerson("odie");
//		cc.setPhone("3456");
//		cc.setUserRequire("团购洗车");
//		service.makeReserve(cc);
		c.setCreateTime(new Date());
		return service.makeReserve(c);
//		return null;
	}
	
}
