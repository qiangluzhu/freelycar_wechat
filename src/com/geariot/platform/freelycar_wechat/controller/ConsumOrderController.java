package com.geariot.platform.freelycar_wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.geariot.platform.freelycar_wechat.service.ConsumOrderService;
@RestController
@RequestMapping(value = "/orders")
public class ConsumOrderController {
	@Autowired
	private ConsumOrderService consumOrderService;
	@RequestMapping(value = "/detail",method = RequestMethod.GET)
	public String detail(String consumOrderId){
		return consumOrderService.detail(consumOrderId);
	}
	@RequestMapping(value = "/listConsumOrder",method = RequestMethod.GET)
	public String listConsumOrder(int clientId , int page , int number){
		return consumOrderService.listConsumOrder(clientId,page,number);
	}
	@RequestMapping(value = "/detailWXPayOrder",method = RequestMethod.GET)
	public String detailWXPayOrder(String wxPayOrderId){
		return consumOrderService.detailWXPayOrder(wxPayOrderId);
	}
}
