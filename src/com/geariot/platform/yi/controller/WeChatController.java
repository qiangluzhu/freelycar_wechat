package com.geariot.platform.yi.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.geariot.platform.yi.service.UserService;
import com.geariot.platform.yi.utils.WechatLoginUse;


@Controller
@RequestMapping(value="/wechat")
public class WeChatController {
	
	@Autowired
	UserService service;
	
	private Logger log = Logger.getLogger(UserController.class);

	@RequestMapping(value = "/center")
	public String toCenter(String code, String state) throws IOException {
		String htmlPage="center";     
		String redir = getWechatInfo(htmlPage, code, state);
		return redir;
	}
	
	@RequestMapping(value = "/service")
	public String toService(String code, String state) throws IOException {
		String htmlPage="service";    
		String redir = getWechatInfo(htmlPage, code, state);
		return redir;
	}
	
	public String getWechatInfo(String htmlPage, String code, String state) throws UnsupportedEncodingException{
		String wechatInfo = WechatLoginUse.wechatInfo(code);
		JSONObject resultJson;
		try {
			log.info("用户信息:"+wechatInfo);
			resultJson = new JSONObject(wechatInfo);
			if(resultJson.get("message").equals("success")){
				String openid = resultJson.getString("openid");
				String nickname = resultJson.getString("nickname");
				String headimgurl = resultJson.getString("headimgurl");
				service.register(openid,nickname);
				nickname = URLEncoder.encode(nickname,"utf-8");
				return "redirect:../../"+htmlPage+".html?openid=" + openid+"&nickname="+nickname+"&headimg="+headimgurl;
				
			}else{
				return null;	
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;	
		} 
		
	}
}
