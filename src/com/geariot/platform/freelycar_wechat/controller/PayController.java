package com.geariot.platform.freelycar_wechat.controller;



import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.logging.log4j.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geariot.platform.freelycar_wechat.utils.RandomStringGenerator;
import com.geariot.platform.freelycar_wechat.wxutils.WechatConfig;
import com.geariot.platform.freelycar_wechat.utils.HttpRequest;
import com.geariot.platform.freelycar_wechat.wxutils.WeChatSignatureUtil;
import com.geariot.platform.freelycar_wechat.wxutils.XMLParser;
import com.geariot.platform.freelycar_wechat.entities.FavourToOrder;
import com.geariot.platform.freelycar_wechat.entities.Service;
import com.geariot.platform.freelycar_wechat.entities.WXPayOrder;
import com.geariot.platform.freelycar_wechat.service.PayService;

import net.sf.json.JSONObject;

@RestController
@RequestMapping(value = "/pay")
public class PayController {
	private static Logger log = LogManager.getLogger();
	@Autowired
	PayService payService;
	
	@RequestMapping(value="favour")
	public String wechatFavour(String openId,float totalPrice,Set<FavourToOrder> favour){
			log.info("购买券");
			return payService.createFavourOrder(openId, totalPrice, favour);
	}

	@RequestMapping(value="membershipCard")
	public String wechatCard(String openId,float totalPrice,Service service){
			log.info("购买卡");
			return payService.createCardOrder(openId,totalPrice,service);
		
	}
	
//	public String wechatPay(String openId,WXPayOrder wxPayOrder,double totalPrice,HttpServletRequest request){
//		//微信支付
//		log.debug("微信支付");
//		JSONObject jsonObject = new JSONObject();
//		Map<String, Object> map = new HashMap<String, Object>();
//		//微信接口配置
//		String ip = request.getHeader("x-forwarded-for") == null ? request
//				.getRemoteAddr() : request.getHeader("x-forwarded-for");
//
//		log.debug("APP和网页支付提交用户端ip:" + ip);
//		map.put("appid", WechatConfig.APP_ID);
//		map.put("body", wxPayOrder.getProductName());
//		map.put("device_info", "WEB");
//		map.put("mch_id", WechatConfig.MCH_ID);// 商户号，微信商户平台里获取
////随机32位			
//		map.put("nonce_str", RandomStringGenerator.getRandomStringByLength(32));
//		
////返回结果	自己掉自己的接口		
//		map.put("notify_url", "http://" + WechatConfig.APP_DOMAIN + "/api/pay/wechatresult");
//		map.put("openid", openId);
//		map.put("out_trade_no", wxPayOrder.getId());
//		map.put("spbill_create_ip", ip);
//		map.put("total_fee", (int) (totalPrice * 100));
//		map.put("trade_type", "JSAPI");
//		// 签名
//		String sig = WeChatSignatureUtil.getSig(map);
//		map.put("sign", sig);
//		// 请求微信支付接口
//		HttpEntity entity = HttpRequest.getEntity(XMLParser.getXmlFromMap(map));
//		log.debug("entity: " + XMLParser.getXmlFromMap(map));
//		String result = HttpRequest.postCall(WechatConfig.ORDER_URL, entity,
//				null);
//
//		log.debug("请求微信支付结果：" + result);
//
//		Map<String, Object> resultMap = XMLParser.getMapFromXML(result);
//		// log.debug("微信统一下单结果：" + new JSONObject(resultMap).toString());
//	}
	
	@RequestMapping(value="wechatresult")
	public void wechatResult(HttpServletRequest request,
	HttpServletResponse response){
		log.info("callback wechatPay");
		//Map
	}
	
}
