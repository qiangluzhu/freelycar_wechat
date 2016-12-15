package com.geariot.platform.yi.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geariot.platform.yi.utils.WechatLoginUse;


@RestController
@RequestMapping(value="/wechat")
public class WeChatController {
	
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
	
	public String getWechatInfo(String htmlPage, String code, String state){
		String wechatInfo = WechatLoginUse.wechatInfo(code);
		JSONObject resultJson;
		try {
			log.info("用户信息:"+wechatInfo);
			resultJson = new JSONObject(wechatInfo);
			if(resultJson.get("message").equals("success")){
				String openid = resultJson.getString("openid");
				String nickname = resultJson.getString("nickname");
				String headimgurl = resultJson.getString("headimgurl");
				
				return "redirect:../../../"+htmlPage+".html?openid=" + openid+"&nickname="+nickname+"&headimg="+headimgurl;
				
			}else{
				return null;	
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return null;	
		} 
		
	}
}
