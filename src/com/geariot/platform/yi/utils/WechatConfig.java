package com.geariot.platform.yi.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.video.utils.HttpRequest;

/**
 * 微信相关的常量
 * @author javabean
 *
 */
public class WechatConfig {

	
	///http://www.dayiya.com.cn/album/api/user/wechatLogin/userzone
	//https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx3d0a8de6b9ec1dcf&redirect_uri=http%3a%2f%2fwww.geariot.com%2falbum%2fapi%2fuser%2fwechatLogin%2fuserzone&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect
	private static Logger log = Logger.getLogger(WechatConfig.class);
	
	//自定义的token
	public static String token = "album8264";
	
	public static String appId = "wx3d0a8de6b9ec1dcf";
	//public static String mchId = "1380202002";    //商户号，微信商户平台里面获取
	//public static String secret = "da188683eaeacf8cde241aee8df3cb30";
	public static String secret = "ab533e50cb12807e527382a2813d1010";

	//public static String key = "zimerwechatcreatetimeymd20160816"; //签名秘钥，在微信商户平台里面设置

	//public static String orderUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	//设置token 和 jsapi_ticket的过期时间 为一个半小时
	private final static int TIME_OUT = 5400*1000;
	
	private final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";

	private final static String WECHAT_USER_INFO = "https://api.weixin.qq.com/sns/userinfo";

	
	
	/**
	 * 
	 * 
	 * 
	 * 获得 token的方法 (这个用户获取用户信息的token ，和下面的普通token不同)
	 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842&token=&lang=zh_CN
	 */
	
	//用户缓存itoken openid 之类的变量
	public static Map<String ,JSONObject> cacheVariable = new ConcurrentHashMap<String, JSONObject>();
	
	private static String getAccessTokenUrl(String code) {
		String ret = ACCESS_TOKEN_URL + "?appid=" + appId + "&secret=" + secret
				+ "&code=" + code + "&grant_type=authorization_code";
		return ret;
	}
	/**
	 * 
	 * @param code
	 * @return
	 * { "access_token":"ACCESS_TOKEN",    
		 "expires_in":7200,    
		 "refresh_token":"REFRESH_TOKEN",    
		 "openid":"OPENID",    
		 "scope":"SCOPE" } 
	 */
	public static JSONObject getAccessToken(String code){
		//每次code不一样，即使是同一个用户，因此没必须缓存这个access_token
		String tokenUrl = getAccessTokenUrl(code);
		String call = HttpRequest.getCall(tokenUrl, null, null);
		JSONObject obj;
		try {
			obj = new JSONObject(call);
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException("WechatConfig#获取token的json字符串解析失败",e);
		}
		
		log.info("新获取的token:"+obj.getString("access_token"));
		
		return obj;
		
	}
	
	
	/**
	 * 
	 * 
	 * 
	 * 获得userInfo的方法
	 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842&token=&lang=zh_CN
	 */

	private static String getWehatUserInfoUrl(String accessToken, String openId) {
		return WECHAT_USER_INFO + "?access_token=" + accessToken + "&openid="
				+ openId + "&lang=zh_CN";
	}
	
	/**
	 * 
	 * @param accessToken 是上面的获取用户信息的token，和下面的调用接口的token不同
	 * @param openId
	 * @return
	 * 
	 * {    "openid":" OPENID",  
			 " nickname": NICKNAME,   
			 "sex":"1",   
			 "province":"PROVINCE"   
			 "city":"CITY",   
			 "country":"COUNTRY",    
			 "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ
			  4eMsv84eavHiaiceqxibJxCfHe/46",  
			 "privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],    
			 "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL" 
			} 
	 */
	public static JSONObject getWXUserInfo(String accessToken, String openId){
		String wxUserInfoUrl = getWehatUserInfoUrl(accessToken, openId);
		String userInfo = HttpRequest.getCall(wxUserInfoUrl, null, null);
		
		JSONObject obj;
		try {
			obj = new JSONObject(userInfo);
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException("WechatConfig#获取userInfo的json字符串解析失败",e);
		}
		
		return obj;
	}
	
	
	
	/**
	 * 微信普通的access_token 调用各接口时都需使用access_token
	 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183&token=&lang=zh_CN
	 * 
	 * {"access_token":"ACCESS_TOKEN","expires_in":7200}
	 * 
	 * 
	 */
	
	public static JSONObject getAccessTokenForInteface(){
		//检查是否过期 我这里设置了1个半小时过期
		JSONObject tokenJSON = cacheVariable.get("itoken");
		if(tokenJSON!=null && (System.currentTimeMillis()-tokenJSON.getLong("get_time"))<TIME_OUT){
			log.info("从缓存中拿的itoken:"+tokenJSON.getString("access_token"));
			return tokenJSON;
		}
		
		
		String  tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+secret;
		String call = HttpRequest.getCall(tokenUrl,null,null);
		JSONObject obj = null;
		try {
			obj = new JSONObject(call);
			obj.put("get_time", System.currentTimeMillis());//此处设置获取时间，用于比对过期时间
		} catch (JSONException e) {
			e.printStackTrace();
		}
		cacheVariable.put("itoken", obj);
		log.info("新获取的itoken:"+obj.getString("access_token"));
		return obj;
	}
	
	
	/**
	 * 微信JS接口的临时票据
	 * http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115&token=683615784&lang=zh_CN
	 * 
	 * {
		"errcode":0,
		"errmsg":"ok",
		"ticket":"bxLdikRXVbTPdHSM05e5u5sUoXNKd8-41ZO3MhKoyN5OfkWITDGgnr2fwJ0m9E8NYzWKVZvdVtaUgWvsdshFKA",
		"expires_in":7200
		}
	 * 
	 * 
	 */
	private static final String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi";
	public static JSONObject getJsApiTicketByWX() {
			JSONObject accessToken = getAccessTokenForInteface();
			String token = accessToken.getString("access_token");
		
			//检查是否过期 我这里设置了1个半小时过期
			JSONObject ticketJSON = cacheVariable.get("jsapi_ticket");
			if(ticketJSON!=null && (System.currentTimeMillis()-ticketJSON.getLong("get_time"))<TIME_OUT){
				log.info("从缓存中拿的jsapi_ticket:"+ticketJSON.getString("ticket"));
				return ticketJSON;
			}
		
			String jsAPITicktUrl = JSAPI_TICKET_URL + "&access_token=" + token;
			JSONObject jsonObject;
	        try {
	            String sTotalString = HttpRequest.getCall(jsAPITicktUrl,null,null);
	            jsonObject = new JSONObject(sTotalString);
	            jsonObject.put("get_time", System.currentTimeMillis());//此处设置获取时间，用于比对过期时间
	        } catch (Exception e) {
	        	throw new RuntimeException(e.getMessage(), e);
	        }
	        
	        log.info("新获取的jsapi_ticket:"+jsonObject);
	        cacheVariable.put("jsapi_ticket", jsonObject);
	        return jsonObject;
	 
	    }
	
	
	
	/**
	 * 下载多媒体文件
	 * https://mp.weixin.qq.com/wiki/12/58bfcfabbd501c7cd77c19bd9cfa8354.html
	 * 
	 * 正确情况下的返回HTTP头如下：
	 * HTTP/1.1 200 OK
		Connection: close
		Content-Type: image/jpeg 
		Content-disposition: attachment; filename="MEDIA_ID.jpg"
		Date: Sun, 06 Jan 2013 10:20:18 GMT
		Cache-Control: no-cache, must-revalidate
		Content-Length: 339721
		curl -G "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID"
	 * 
	 * 
	 */
	/**
	 * 
	 * @param access_token 调用接口的token
	 * @param media_id
	 * @return
	 */
	public static String WXDownMedia(String userId,String media_id){
		JSONObject accessToken = getAccessTokenForInteface();
		String access_token = accessToken.getString("access_token");
		
		String downUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="+access_token+"&media_id="+media_id;
		HttpRequest.userId = userId;
		String head = HttpRequest.getCall(downUrl, null, null);
		
		return head;
	}
	
}
