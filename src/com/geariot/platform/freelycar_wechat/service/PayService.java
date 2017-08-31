package com.geariot.platform.freelycar_wechat.service;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONObject;

import com.geariot.platform.freelycar_wechat.utils.JsonResFactory;
import com.geariot.platform.freelycar_wechat.dao.CardDao;
import com.geariot.platform.freelycar_wechat.dao.FavourDao;
import com.geariot.platform.freelycar_wechat.dao.FavourProjectInfosDao;
import com.geariot.platform.freelycar_wechat.dao.ProjectDao;
import com.geariot.platform.freelycar_wechat.dao.ServiceDao;
import com.geariot.platform.freelycar_wechat.dao.WXUserDao;
import com.geariot.platform.freelycar_wechat.entities.Card;
import com.geariot.platform.freelycar_wechat.entities.Favour;
import com.geariot.platform.freelycar_wechat.entities.FavourProjectInfos;
import com.geariot.platform.freelycar_wechat.entities.FavourRemainings;
import com.geariot.platform.freelycar_wechat.entities.WXUser;
import com.geariot.platform.freelycar_wechat.entities.Service;
import com.geariot.platform.freelycar_wechat.entities.WXPayOrder;
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
	
	
	//创建card订单	
	public String createCardOrder(String openId,double totalPrice,
			Service service){
		log.debug("create new order");
		WXPayOrder wxPayOrder = buildBasivOrders(openId, totalPrice);
		log.debug("id" + wxPayOrder.getId() + "总金额" + wxPayOrder.getTotalPrice() + "openId" + wxPayOrder.getOpenId() +"Date" + wxPayOrder.getCreateDate());
		WXUser wxUser =  wxUserDao.findUserByOpenId(openId);
		wxPayOrder.setService(service);
		JSONObject obj = new JSONObject();
		obj.put(Constants.RESPONSE_WXUSER_KEY, wxUser);
		obj.put(Constants.RESPONSE_WXORDER_KEY,wxPayOrder);
		
		
		return JsonResFactory.buildNetWithData(RESCODE.SUCCESS, obj).toString();
	}
	
	//create favour order
	public String createFavourOrder(String openId,double totalPrice,
			FavourRemainings favour){
		WXPayOrder wxPayOrder = buildBasivOrders(openId, totalPrice);
		WXUser wxUser =  wxUserDao.findUserByOpenId(openId);
		JSONObject obj = new JSONObject();
		obj.put(Constants.RESPONSE_WXUSER_KEY, wxUser);
		obj.put(Constants.RESPONSE_WXORDER_KEY,wxPayOrder);
		
		
		return JsonResFactory.buildNetWithData(RESCODE.SUCCESS, obj).toString();
		
	}
	
	
	private WXPayOrder buildBasivOrders(String openId,double totalPrice){
		WXPayOrder wxPayOrder = new WXPayOrder();
		wxPayOrder.setId(IDGenerator.generate(IDGenerator.WX_CONSUM));
		wxPayOrder.setCreateDate(new Date());
		wxPayOrder.setOpenId(openId);
		wxPayOrder.setPayMethod(Constants.PAY_BY_WX);
		wxPayOrder.setPayState(Constants.PAY_UNPAY);
		wxPayOrder.setTotalPrice(totalPrice);
		return wxPayOrder;
	}
}