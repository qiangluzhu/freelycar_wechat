
package com.geariot.platform.freelycar_wechat.controller;

import java.text.ParseException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.geariot.platform.freelycar_wechat.entities.Car;
import com.geariot.platform.freelycar_wechat.service.WXUserService;

@RestController
@RequestMapping("/user")
public class WXUserController {
	private static final Logger log = LogManager.getLogger(WXUserController.class);
	@Autowired
	private WXUserService wxUserService;
	@RequestMapping(value = "/addWxUser",method = RequestMethod.POST)
	public String addWxUser(String openId,String nickName,String headimgurl){
		return wxUserService.addWXUser(openId,nickName,headimgurl);
	}
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public String login(String openId){
		return wxUserService.login(openId);
	}
	@RequestMapping(value = "/logout",method = RequestMethod.POST)
	public String logout(String openId){
		return wxUserService.deletWXUser(openId);
	}
	@RequestMapping(value = "/myDiscount",method = RequestMethod.GET)
	public String myDiscount(int clientId){
		return wxUserService.listDiscount(clientId);
	}
	//要显示积分记录查project
	@RequestMapping(value = "/points",method = RequestMethod.GET)
	public String points(int clientId){
		return wxUserService.getPoint(clientId);
	}
	//设置时间
	@RequestMapping(value = "/defaultCar",method = RequestMethod.GET)
	public String defaultCar(int carId){
		System.out.println(carId);
		return wxUserService.setDefaultCar(carId);
	}
	@RequestMapping(value = "/carInfo",method = RequestMethod.GET)
	public String carInfo(int clientId, int id, String insuranceCity, String insuranceCompany, String insuranceEndtime) throws ParseException{
		return wxUserService.modifyCar(clientId, id, insuranceCity, insuranceCompany, insuranceEndtime);
	}
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public void test(){
		log.info("test   "+wxUserService.test());
	}
	
	@RequestMapping(value = "/detail",method = RequestMethod.GET)
	public String detail(int clientId){
		return wxUserService.detail(clientId);
	}
	@RequestMapping(value = "/listCar",method = RequestMethod.GET)
	public String addcar(int clientId){
		return wxUserService.listCar(clientId);
	}
	@RequestMapping(value = "/addCar",method = RequestMethod.POST)
	public String addcar(@RequestBody Car car){
		log.debug(">>>>>>>> "+car);
		return wxUserService.addCar(car);
	}
	@RequestMapping(value = "/delCar",method = RequestMethod.POST)
	public String delCar(int carId){
		return wxUserService.deleteCar(carId);
	}
	@RequestMapping(value = "/wxInfo",method = RequestMethod.GET)
	public String wxInfo(String openId){
		return wxUserService.getWXUser(openId);
	}
	@RequestMapping(value = "/listCard",method = RequestMethod.GET)
	public String listCard(int clientId){
		return wxUserService.listCard(clientId);
	}
	
	@RequestMapping(value = "/carDetail", method = RequestMethod.GET)
	public String carDetail(int carId){
		return wxUserService.carDetail(carId);
	}
}

