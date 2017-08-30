package com.geariot.platform.freelycar_wechat.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.geariot.platform.freelycar_wechat.dao.CardDao;
import com.geariot.platform.freelycar_wechat.dao.FavourDao;
import com.geariot.platform.freelycar_wechat.dao.FavourProjectInfosDao;
import com.geariot.platform.freelycar_wechat.dao.ProjectDao;
import com.geariot.platform.freelycar_wechat.dao.ServiceDao;
import com.geariot.platform.freelycar_wechat.dao.WXUserDao;
import com.geariot.platform.freelycar_wechat.entities.Favour;
import com.geariot.platform.freelycar_wechat.entities.FavourProjectInfos;
import com.geariot.platform.freelycar_wechat.entities.WXUser;
import com.geariot.platform.freelycar_wechat.entities.Service;
import com.geariot.platform.freelycar_wechat.model.RESCODE;
import com.geariot.platform.freelycar_wechat.utils.*;

@org.springframework.stereotype.Service
@Transactional
public class PayService {
	
	@Autowired
	private WXUserDao wxUserDao;
	@Autowired
	private ProjectDao projectDao;
	@Autowired
	private ServiceDao serviceDao;
	@Autowired
	private CardDao cardDao;
	@Autowired
	private FavourDao favourDao;
	@Autowired
	private FavourProjectInfosDao favourProjectInfosDao;
	
	private static final Logger log = LogManager.getLogger(PayService.class);
	
	
	//创建订单	
	public org.json.JSONObject createOrder(String openId,float totalPrice,
			int projectId, int waitlist,int type){
		log.debug("create new order");
		org.json.JSONObject obj = new org.json.JSONObject();
		WXUser wxUser =  wxUserDao.findUserByOpenId(openId);
		if(wxUser== null){
			obj.put(Constants.RESPONSE_CODE_KEY, RESCODE.NOT_FOUND.toString());
			obj.put(Constants.RESPONSE_MSG_KEY, RESCODE.NOT_FOUND.getMsg());
			return obj;
		}
		String productName = null;
		if(type == 0){
			FavourProjectInfos favourProjectInfos = favourProjectInfosDao.findById(projectId);
			//productName = projectDao.findProjectById(favourProjectInfos.getProject());
		}
		else{
			productName = serviceDao.findServiceById(projectId).getName();
			
		}
		return null;
	}
}
