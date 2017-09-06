package com.geariot.platform.freelycar_wechat.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.geariot.platform.freelycar_wechat.utils.query.FavourOrderBean;
import com.geariot.platform.freelycar_wechat.utils.JsonResFactory;
import com.geariot.platform.freelycar_wechat.dao.CardDao;
import com.geariot.platform.freelycar_wechat.dao.FavourDao;
import com.geariot.platform.freelycar_wechat.dao.FavourProjectInfosDao;
import com.geariot.platform.freelycar_wechat.dao.ProjectDao;
import com.geariot.platform.freelycar_wechat.dao.ServiceDao;
import com.geariot.platform.freelycar_wechat.dao.WXPayOrderDao;
import com.geariot.platform.freelycar_wechat.dao.WXUserDao;
import com.geariot.platform.freelycar_wechat.entities.FavourToOrder;
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
	private ServiceDao serviceDao;
	@Autowired
	private WXPayOrderDao wxPayOrderDao;
	
	private static final Logger log = LogManager.getLogger(PayService.class);
	
	
	//创建card订单	
	public String createCardOrder(String openId,double totalPrice,
			int serviceId){
		log.debug("create new order");
		WXPayOrder wxPayOrder = buildBasivOrders(openId, totalPrice);
		log.debug("id" + wxPayOrder.getId() + "总金额" + wxPayOrder.getTotalPrice() + "openId" + wxPayOrder.getOpenId() +"Date" + wxPayOrder.getCreateDate());
		WXUser wxUser =  wxUserDao.findUserByOpenId(openId);
		Service service = serviceDao.findServiceById(serviceId);
		wxPayOrder.setService(service);
		wxPayOrder.setProductName(service.getName());
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put(Constants.RESPONSE_WXUSER_KEY, wxUser);
		obj.put(Constants.RESPONSE_WXORDER_KEY,wxPayOrder);
		wxPayOrderDao.saveWXPayOrder(wxPayOrder);
		JsonConfig config = JsonResFactory.dateConfig();
		return JsonResFactory.buildNetWithData(RESCODE.SUCCESS,
				net.sf.json.JSONObject.fromObject(obj, config)).toString();
	}
	
	//create favour order
	public String createFavourOrder(FavourOrderBean favourOrderBean){
		String openId = favourOrderBean.getOpenId();
		double totalPrice = favourOrderBean.getTotalPrice();
		Set<FavourToOrder> favours = favourOrderBean.getFavours();
		WXPayOrder wxPayOrder = buildBasivOrders(openId, totalPrice);
		WXUser wxUser =  wxUserDao.findUserByOpenId(openId);
		String productName = null;
		for(FavourToOrder favour : favours)
			productName += favour.getFavour().getName()+"*"+favour.getCount()+",";
		wxPayOrder.setProductName(productName);
		wxPayOrder.setFavours(favours);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put(Constants.RESPONSE_WXUSER_KEY, wxUser);
		obj.put(Constants.RESPONSE_WXORDER_KEY,wxPayOrder);
		wxPayOrderDao.saveWXPayOrder(wxPayOrder);
		JsonConfig config = JsonResFactory.dateConfig();
		return JsonResFactory.buildNetWithData(RESCODE.SUCCESS,net.sf.json.JSONObject.fromObject(obj, config)).toString();
		
	}
	
	
	private WXPayOrder buildBasivOrders(String openId,double totalPrice){
		WXPayOrder wxPayOrder = new WXPayOrder();
		wxPayOrder.setId(IDGenerator.generate(IDGenerator.WX_CONSUM));
		System.out.println(">>>>"+IDGenerator.generate(IDGenerator.WX_CONSUM));
		wxPayOrder.setCreateDate(new Date());
		wxPayOrder.setOpenId(openId);
		wxPayOrder.setPayMethod(Constants.PAY_BY_WX);
		wxPayOrder.setPayState(Constants.PAY_UNPAY);
		wxPayOrder.setTotalPrice(totalPrice);
		return wxPayOrder;
	}

	public JSONObject paySuccess(String orderId) {
		// TODO Auto-generated method stub
		return null;
	}
}

