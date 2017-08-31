package com.geariot.platform.freelycar_wechat.controller;



import org.apache.logging.log4j.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.geariot.platform.freelycar_wechat.entities.Service;
import com.geariot.platform.freelycar_wechat.service.PayService;

@RestController
@RequestMapping(value = "/pay")
public class PayController {
	private static Logger log = LogManager.getLogger();
	@Autowired
	PayService payService;
	
//	@RequestMapping(value="favour")
//	public String wechatFavour(String openId,float totalPrice,int projectId, int waitlist,
//			HttpServletRequest request){
//			log.info("购买券");
//			JSONObject res = payService.createOrder(openId,totalPrice,projectId,waitlist,ORDERS_TYPE.FAVOUR.getValue());
//			
//			
//			
//			String orderId = res.getString(Constants.RESPONSE_DATA_KEY);
//		return wechatPay(orderId, openId, totalPrice, request);
//	}
//
	@RequestMapping(value="membershipCard")
	public String wechatCard(String openId,float totalPrice,Service service){
			log.info("购买卡");
			return payService.createCardOrder(openId,totalPrice,service);
		
	}

}
