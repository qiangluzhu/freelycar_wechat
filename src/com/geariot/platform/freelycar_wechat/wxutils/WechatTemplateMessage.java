package com.geariot.platform.freelycar_wechat.wxutils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.entity.StringEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.geariot.platform.freelycar_wechat.entities.*;

public class WechatTemplateMessage {
	
	private static final String PAY_SUCCESS_TEMPLATE_ID = "k2_jq21hHH1bMYDuiCwyW9w2-YEM0jJRRuLEDxJlwhQ";
	private static final String PAY_FAIL_ID = "L-MhZftW-sTPMCKBR3kNK_l9oZnVfwdYq_RyxLwueFo";
	
//	private static final Logger log = Logger.getLogger(WechatTemplateMessage.class);
	private static final Logger log = LogManager.getLogger(WechatTemplateMessage.class);
			
	private static final String PAY_ERROR_DATABASE_FAIL = "服务异常";
	
	private static String invokeTemplateMessage(JSONObject params){
		StringEntity entity = new StringEntity(params.toString(),"utf-8"); //解决中文乱码问题   
		String result = HttpRequest.postCall(WechatConfig.WECHAT_TEMPLATE_MESSAGE_URL + 
				WechatConfig.getAccessTokenForInteface().getString("access_token"),
				entity, null);
		log.debug("微信模版消息结果：" + result);
		return result;
	}
	


//{{first.DATA}}
//类型：{{keyword1.DATA}}
//金额：{{keyword2.DATA}}
//状态：{{keyword3.DATA}}
//时间：{{keyword4.DATA}}
//备注：{{keyword5.DATA}}
//{{remark.DATA}}
	public static void payWXSuccess(WXPayOrder wxPayOrder){
		log.debug("准备支付成功模版消息。。。");
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		JSONObject params = new JSONObject();
		JSONObject data = new JSONObject();
		params.put("touser", wxPayOrder.getOpenId());
		params.put("template_id", PAY_SUCCESS_TEMPLATE_ID);
//		params.put("url", "http://www.geariot.com/fitness/class.html");
		data.put("first", keywordFactory("支付成功", "#173177"));
		data.put("keyword1", keywordFactory(wxPayOrder.getProductName(), "#173177"));
		data.put("keyword2", keywordFactory((float)(Math.round(wxPayOrder.getTotalPrice()*100))/100 + "元", "#173177"));
		data.put("keyword3", keywordFactory("成功", "#173177"));
		data.put("keyword4", keywordFactory(df.format(wxPayOrder.getFinishDate()), "#173177"));
		data.put("keyword5", keywordFactory(""));
		data.put("remark", keywordFactory(""));
		params.put("data", data);
		String result = invokeTemplateMessage(params);
		log.debug("微信支付成功模版消息结果：" + result);
//		return result;
	}
	public static void paySuccess(ConsumOrder consumOrder,String openId){
		log.debug("准备支付成功模版消息。。。");
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		JSONObject params = new JSONObject();
		JSONObject data = new JSONObject();
		params.put("touser", openId);
		params.put("template_id", PAY_SUCCESS_TEMPLATE_ID);
//		params.put("url", "http://www.geariot.com/fitness/class.html");
		data.put("first", keywordFactory("支付成功", "#173177"));
		String productName = "";
		for(ProjectInfo projectInfo : consumOrder.getProjects())
			productName+=projectInfo.getName();
		data.put("keyword1", keywordFactory(productName, "#173177"));
		data.put("keyword2", keywordFactory((float)(Math.round(consumOrder.getTotalPrice()*100))/100 + "元", "#173177"));
		data.put("keyword3", keywordFactory("成功", "#173177"));
		data.put("keyword4", keywordFactory(df.format(consumOrder.getFinishTime()), "#173177"));
		data.put("keyword5", keywordFactory(""));
		data.put("remark", keywordFactory(""));
		params.put("data", data);
		String result = invokeTemplateMessage(params);
		log.debug("微信支付成功模版消息结果：" + result);
//		return result;
	}
	
	
//	{{first.DATA}}
//	支付金额：{{keyword1.DATA}}
//	商品信息：{{keyword2.DATA}}
//	失败原因：{{keyword3.DATA}}
//	{{remark.DATA}}
	public static void errorCancel(String error){

	}
	
	
	private static JSONObject keywordFactory(String value){
		JSONObject keyword = new JSONObject();
		keyword.put("value", value);
		return keyword;
	}
	
	private static JSONObject keywordFactory(String value, String color){
		JSONObject keyword = keywordFactory(value);
		keyword.put("color", color);
		return keyword;
	}
	
}
