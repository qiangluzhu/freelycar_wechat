/*package com.geariot.platform.freelycar_wechat.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.geariot.platform.freelycar_wechat.model.RESCODE;
import com.geariot.platform.freelycar_wechat.utils.Constants;
import com.geariot.platform.freelycar_wechat.utils.HttpRequest;

@RestController
@RequestMapping("/sms")
public class SmsController {
	
	private String appid = "YPVPvcghD0yT1CtQKUOpOUGI-gzGzoHsz";
    private String appkey = "AnrwmLo01qL7RuKNbV0NwWR4";
    private String ContentType =  "application/json";

    private String leancouldUrlRes = "https://leancloud.cn/1.1/requestSmsCode";
    private String leancouldUrlVer = "https://leancloud.cn/1.1/verifySmsCode";
    
//	private Logger log = Logger.getLogger(SmsController.class);
	private Logger log = LogManager.getLogger(SmsController.class);
	//发送验证码请求
	@RequestMapping(value = "/verification",method=RequestMethod.POST)
	public String getVerification(String phone) {
		JSONObject param = new JSONObject();
		param.put("mobilePhoneNumber", phone);
		HttpEntity entity = HttpRequest.getEntity(param);
		Map<String,Object> head = setLeancloudHead();
		String result = HttpRequest.postCall(leancouldUrlRes, entity, head);
		log.debug("leancloud的返回码："+result);
		
		JSONObject json = null;
		try {
			json = new JSONObject(result);
		} catch (JSONException e) {
			e.printStackTrace();
			log.error("解析发送验证码返回结果错误");
		}
		if(json.getString("error") != null){
			json.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
            json.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
		}else{
			json.put(Constants.RESPONSE_CODE_KEY, json.getInt("code"));
			json.put(Constants.RESPONSE_MSG_KEY, json.getString("error"));
		}
        return json.toString();
	}

	//注册验证结果请求
	@RequestMapping(value = "/bind",method=RequestMethod.POST)
	public String verifySmsCode(String phone, String smscode, String openId) {
		JSONObject json = this.verifySmscode(phone, smscode);
		if(json.getString("error") != null) {
			log.debug(phone + ";code:" + smscode + " 验证失败。。。");
			json.put(Constants.RESPONSE_CODE_KEY, json.getInt("code"));
			json.put(Constants.RESPONSE_MSG_KEY, json.getString("error"));
			return json.toString();
		}
		else {
			String res = userService.bindUser(phone, openId);
			log.debug("绑定用户结果：" + res);
			return res;
		}
	}
	
	//绑定验证结果请求
	@RequestMapping(value = "/register",method=RequestMethod.POST)
	public String verifyRegister(String phone, String smscode, String openId) {
		JSONObject json = this.verifySmscode(phone, smscode);
		if(json.getString("error") != null) {
			log.debug(phone + ";code:" + smscode + " 验证失败。。。");
			json.put(Constants.RESPONSE_CODE_KEY, json.getInt("code"));
			json.put(Constants.RESPONSE_MSG_KEY, json.getString("error"));
			return json.toString();
		}
		else {
			return userService.phoneCheck(phone,openId);
			//查询本地数据phone，如果有openid也一样，则 提示已经注册过并绑定
			// 如果openid不一样，则提示该手机号已被其他账号绑定
			//如果本地没有，查询mb系统，有一样的手机号，则提示该手机号已注册，请进行绑定
			//如果本地没有，mb也没有，则成功进入下一步
		}
	}
	
	//帮同伴注册验证结果请求
	@RequestMapping(value = "/registerPal",method=RequestMethod.POST)
	public String veriftRegisterPal(User user, String smscode){
		String phone = user.getPhone();
		JSONObject json = this.verifySmscode(phone, smscode);
		if(json.getString("error") != null) {
			log.debug(phone + ";code:" + smscode + " 验证失败。。。");
			json.put(Constants.RESPONSE_CODE_KEY, json.getInt("code"));
			json.put(Constants.RESPONSE_MSG_KEY, json.getString("error"));
			return json.toString();
		}
		else {
			String res = userService.registerPal(user);
			log.debug("RegisterPal结果：" + res);
			return res;
		}
	}
	
	private JSONObject verifySmscode(String phone, String smscode){
		Map<String,Object> head = setLeancloudHead();
		String result = HttpRequest.postCall(leancouldUrlVer+"/"+smscode+"?mobilePhoneNumber="+phone, null, head);
		log.debug("绑定手机短信验证, phone:" + phone + ", smscode:" + smscode + "。 短信验证结果：" + result);
		JSONObject json = null;
		try {
			json = new JSONObject(result);
		} catch (JSONException e) {
			e.printStackTrace();
			log.error("解析验证验证码返回结果错误");
		}
		log.debug("解析后结果：" + json.toString());
		return json;
	}
	
	private Map<String,Object> setLeancloudHead(){
		Map<String,Object> head = new HashMap<String, Object>();
		head.put("X-LC-Id", appid);
		head.put("X-LC-Key", appkey);
		head.put("Content-Type", ContentType);
		return head;
	}
	
}
*/