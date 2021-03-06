package com.geariot.platform.freelycar_wechat.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.geariot.platform.freelycar_wechat.entities.Car;
import com.geariot.platform.freelycar_wechat.model.InfoBean;
import com.geariot.platform.freelycar_wechat.model.RESCODE;
import com.geariot.platform.freelycar_wechat.service.WXUserService;
import com.geariot.platform.freelycar_wechat.utils.JsonResFactory;
import com.geariot.platform.freelycar_wechat.wxutils.WechatLoginUse;

@Controller
@RequestMapping("/user")
public class WXUserController {
	private static final Logger log = LogManager
			.getLogger(WXUserController.class);
	@Autowired
	private WXUserService wxUserService;

	
	private static String BASEURL = "http://www.freelycar.com/freelycar_wechat/index.html#/";
	
	
	/*微信菜单转发*/
	
	//个人中心
	@RequestMapping(value = "/wechatlogin")
	public String wechatLogin(String htmlPage, String code,boolean isAuth) {
			return getWechatInfo(htmlPage, code, isAuth);
	}
	
	//直接内部跳转
	@RequestMapping(value = "/menuRedirect")
	public String menuRedirect(String htmlPage, String code) {
		String ret = BASEURL+htmlPage;
		return "redirect:"+ret;
	}
	
	
	
	
	public String getWechatInfo(String htmlPage, String code, boolean isAuth) {
		String wechatInfo = WechatLoginUse.wechatInfo(code);
		JSONObject resultJson;
		try {
			resultJson = new JSONObject(wechatInfo);
			if(resultJson.get("message").equals("success")){
				String openid = resultJson.getString("openid");
				String nickname = resultJson.getString("nickname");
				String headimgurl = resultJson.getString("headimgurl");
				nickname = URLEncoder.encode(nickname,"utf-8");
				headimgurl = URLEncoder.encode(headimgurl,"utf-8");
				
				String ret = BASEURL+htmlPage;
				log.error("-------------------");
				log.error("ordertrack".equals(htmlPage));
				
				//是否重新授权
				if(isAuth){
					ret = BASEURL+"login/" + openid+"/"+nickname+"/"+headimgurl+"/"+htmlPage;
				}else if("center".equals(htmlPage) 
						|| "serviceCard".equals(htmlPage) 
						|| "inquiry".equals(htmlPage)
						|| "personalInfo".equals(htmlPage)
						|| "ordertrack".equals(htmlPage)){
					boolean wxUser = wxUserService.isExistUserOpenId(openid);
					if(!wxUser){
						ret = BASEURL+"login/" + openid+"/"+nickname+"/"+headimgurl+"/"+htmlPage;
					}else{
						if("personalInfo".equals(htmlPage)){
							ret = BASEURL+"center";
						}
					}
				}
				log.error(ret);
				return "redirect:"+ret;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "redirect:../../fail.html";    //重定向到失败页面
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/updateWXUser",method = RequestMethod.POST)
	public String addWxUser(@RequestBody InfoBean infoBean){
		try{
			return wxUserService.addWXUser(infoBean.getPhone(),infoBean.getName(),infoBean.getBirthday(),infoBean.getGender());
		}catch(ParseException e){
			return JsonResFactory.buildOrg(RESCODE.DATE_FORMAT_ERROR).toString();
		}
	}

	// @RequestMapping(value = "/login",method = RequestMethod.GET)
	// public String login(String openId){
	// return wxUserService.login(openId);
	// }
	
	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(String openId) {
		return wxUserService.deletWXUser(openId);
	}

	
	@ResponseBody
	@RequestMapping(value = "/myDiscount", method = RequestMethod.GET)
	public String myDiscount(int clientId) {
		return wxUserService.listDiscount(clientId);
	}

	// 要显示积分记录查project
	@ResponseBody
	@RequestMapping(value = "/points", method = RequestMethod.GET)
	public String points(int clientId) {
		return wxUserService.getPoint(clientId);
	}

	// 设置时间
	@ResponseBody
	@RequestMapping(value = "/defaultCar", method = RequestMethod.GET)
	public String defaultCar(int carId) {
		System.out.println(carId);
		return wxUserService.setDefaultCar(carId);
	}

	@ResponseBody
	@RequestMapping(value = "/carInfo", method = RequestMethod.GET)
	public String carInfo(int clientId, int id, String insuranceCity,
			String insuranceCompany, String insuranceEndtime, String name, String idNumber)
			throws ParseException {
		return wxUserService.modifyCar(clientId, id, insuranceCity,
				insuranceCompany, insuranceEndtime, name, idNumber);
	}

	@ResponseBody
	@RequestMapping(value = "/annualCheck", method = RequestMethod.GET)
	public String annualCheck(int clientId, int id, String licenseDate)throws ParseException{
		return wxUserService.annualCheck(clientId, id, licenseDate);
	}
	/*@ResponseBody
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(String phone,String openId,String headimgurl,String nickName) {
		//System.out.print(JsonResFactory.buildNetWithData(RESCODE.SUCCESS, wxUserService.login(phone,openId,headimgurl,nickName)).toString());
	net.sf.json.JSONObject obj = wxUserService.login(phone,openId,headimgurl,nickName);
		//return obj.toString();
		return JsonResFactory.buildOrg(RESCODE.SUCCESS, Constants.RESPONSE_CLIENT_KEY, obj).toString();
	}*/

	
	@ResponseBody
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(int clientId) {
		return wxUserService.detail(clientId);
	}

	@ResponseBody
	@RequestMapping(value = "/listCar", method = RequestMethod.GET)
	public String addcar(int clientId) {
		return wxUserService.listCar(clientId);
	}

	@ResponseBody
	@RequestMapping(value = "/addCar", method = RequestMethod.POST)
	public String addcar(@RequestBody Car car) {
		log.debug(">>>>>>>> " + car);
		return wxUserService.addCar(car);
	}

	
	@ResponseBody
	@RequestMapping(value = "/delCar", method = RequestMethod.GET)
	public String delCar(int carId) {
		return wxUserService.deleteCar(carId);
	}

	
	@ResponseBody
	@RequestMapping(value = "/wxInfo", method = RequestMethod.GET)
	public String wxInfo(String openId) {
		return wxUserService.getWXUser(openId);
	}

	
	@ResponseBody
	@RequestMapping(value = "/listCard", method = RequestMethod.GET)
	public String listCard(int clientId) {
		return wxUserService.listCard(clientId);
	}

	
	@ResponseBody
	@RequestMapping(value = "/carDetail", method = RequestMethod.GET)
	public String carDetail(int carId) { 
		return wxUserService.carDetail(carId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/quickOrder", method = RequestMethod.GET)
	public String quickOrder(int clientId){
		return wxUserService.quickOrder(clientId);
	}
	
	@ResponseBody
	@RequestMapping(value = "/insurance", method = RequestMethod.GET)
	public String insuranceRemind(int carId, boolean check){
		return wxUserService.insuranceRemind(carId, check);
	}
	
	@ResponseBody
	@RequestMapping(value = "/annual", method = RequestMethod.GET)
	public String annualRemind(int carId, boolean check){
		return wxUserService.annualRemind(carId, check);
	}
	
}
