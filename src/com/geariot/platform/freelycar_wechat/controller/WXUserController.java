package com.geariot.platform.freelycar_wechat.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geariot.platform.freelycar_wechat.entities.Car;
import com.geariot.platform.freelycar_wechat.service.WXUserService;

@RestController
@RequestMapping("/user")
public class WXUserController {
	private static final Logger log = LogManager.getLogger(WXUserController.class);
	@Autowired
	private WXUserService wxUserService;
//	@RequestMapping(value = "/addWxUser",method = RequestMethod.POST)
//	public String addWxUser(String openId,String nickName,String headimgurl,String phone){
//		
//	}
//	@RequestMapping(value = "/login",method = RequestMethod.POST)
//	public String login(String openId){
//		
//	}
//	@RequestMapping(value = "/logout",method = RequestMethod.POST)
//	public String logout(String openId){
//		
//	}
	@RequestMapping(value = "/myDiscount",method = RequestMethod.POST)
	public String myDiscount(String openId){
		return wxUserService.listDiscount(openId);
	}
	//要显示积分记录查project
	@RequestMapping(value = "/points",method = RequestMethod.POST)
	public String points(String openId){
		return wxUserService.getPoint(openId);
	}
	//设置时间
	@RequestMapping(value = "/defaultCar",method = RequestMethod.POST)
	public String defaultCar(int carId){
		return wxUserService.setDefaultCar(carId);
	}
	@RequestMapping(value = "/carInfo",method = RequestMethod.POST)
	public String carInfo(int carId,Car car){
		return wxUserService.modifyCar(carId,car);
	}
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public void test(){
		wxUserService.test();
	}
	@RequestMapping(value = "/detail",method = RequestMethod.GET)
	public String detail(String openId){
		return wxUserService.detail(openId);
	}
	@RequestMapping(value = "/addcar",method = RequestMethod.POST)
	public String addcar(String openId,Car car){
		return wxUserService.addCar(openId,car);
	}
	@RequestMapping(value = "/delCar",method = RequestMethod.POST)
	public String delCar(int carId){
		return wxUserService.deleteCar(carId);
	}

}
