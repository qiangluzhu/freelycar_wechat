package com.geariot.platform.freelycar_wechat.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geariot.platform.freelycar_wechat.dao.WXUserDao;
import com.geariot.platform.freelycar_wechat.entities.WXUser;
import com.geariot.platform.freelycar_wechat.model.RESCODE;
import com.geariot.platform.freelycar_wechat.utils.Constants;
import com.geariot.platform.freelycar_wechat.utils.JsonResFactory;

import net.sf.json.JsonConfig;



@Service
@Transactional
public class SmsService {
	
	@Autowired
	private WXUserDao wxUserDao;
	
	private static final Logger log = LogManager.getLogger(SmsService.class);
	
	public String bindUser(String phone, String openId){
		log.debug("绑定用户的openId：" + openId);
		WXUser userOpenId = wxUserDao.findUserByOpenId(openId);
		JSONObject resultObj = new JSONObject();
		//本地数据库没有openId的记录，不太可能出现的情况。
		if(userOpenId == null){
			resultObj = JsonResFactory.buildOrg(RESCODE.NOT_FOUND);
			return resultObj.toString();
		}
		//本地根据openId找到一条数据，且这条数据的phone字段不为空。
		else{
			if(userOpenId.getPhone() != null && !userOpenId.getPhone().isEmpty()){
				//如果phone字段和传入的phone不一样，则这个openId已绑定的其他手机。
				if(!userOpenId.getPhone().equals(phone)){
					resultObj.put(Constants.RESPONSE_DATA_KEY,RESCODE.BINDED_WITH_OTHER_PHONE);
					resultObj.put(Constants.RESPONSE_MSG_KEY,RESCODE.BINDED_WITH_OTHER_PHONE.getMsg());
					return resultObj.toString();
				}
				return JsonResFactory.buildNet(RESCODE.SUCCESS).toString();
			}
			else {
				userOpenId.setPhone(phone);
				wxUserDao.saveOrUpdateUser(userOpenId);
				JsonConfig config = JsonResFactory.dateConfig();
				return JsonResFactory.buildNetWithData(RESCODE.SUCCESS, net.sf.json.JSONObject.fromObject(userOpenId,config)).toString();
			}
		}	
	}
}
