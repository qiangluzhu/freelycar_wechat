package com.geariot.platform.yi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geariot.platform.yi.entities.Reservation;
import com.geariot.platform.yi.service.ReservationService;

@RestController
@RequestMapping(value="/reservation")
public class ReservationController {
	@Autowired
	ReservationService service;
	
	@RequestMapping(value = "/getByPerson", method = RequestMethod.GET)
	public String getByPerson(Reservation r,int start,int number) {
//		Reservation rr=new Reservation();
//		rr.setNickName("gar");
		return service.getByPerson(r,start,number);
	}
	
	@RequestMapping(value = "/statistic", method = RequestMethod.GET)
	public String statistic() {
		return service.statistic();
	}
	
	@RequestMapping(value = "/getById", method = RequestMethod.GET)
	public String getById(String id) {
		return service.getById(id);
	}
	
}
