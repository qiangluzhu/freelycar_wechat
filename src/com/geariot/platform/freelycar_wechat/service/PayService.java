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
import com.geariot.platform.freelycar_wechat.wxutils.IdentifyOrder;
import com.geariot.platform.freelycar_wechat.utils.JsonResFactory;
import com.geariot.platform.freelycar_wechat.dao.*;
import com.geariot.platform.freelycar_wechat.entities.*;
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
	@Autowired
	private ConsumOrderDao consumOrderDao;
	@Autowired
	private ClientDao clientDao;
	@Autowired
	private IncomeOrderDao incomeOrderDao;
	
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
		//JSONObject obj = new JSONObject();
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

	public org.json.JSONObject paySuccess(String orderId) {
		log.debug("付款成功，进入paySuccess后续处理。");
		float amount;
		int clientId;
		String licensePlate = null;
		Date payDate = new Date();
		int payMethod = Constants.PAY_BY_WX;
		String programName;
		if(IdentifyOrder.identify(orderId)){
		ConsumOrder order = null;
			synchronized(PayService.class)
			{
			order = consumOrderDao.findById(orderId);
			if(order.getState() >= 1)
				{
				log.debug("已处理微信回调，订单已处理。直接返回成功。");
				org.json.JSONObject res = new org.json.JSONObject();
				res.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
				res.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
				return res;
				}
			order.setState(1); // 支付状态
			amount=(float) order.getTotalPrice();
			clientId = order.getClientId();
			licensePlate = order.getLicensePlate();
			programName = order.getProgramName();
			}
		
		}
		else{
			WXPayOrder order = null;
			synchronized(PayService.class)
			{
			order = wxPayOrderDao.findById(orderId);
			if(order.getPayState() >= 1)
				{
				log.debug("已处理微信回调，订单已处理。直接返回成功。");
				org.json.JSONObject res = new org.json.JSONObject();
				res.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
				res.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
				return res;
				}
			order.setPayState(1); // 支付状态
			amount=(float) order.getTotalPrice();
			clientId = clientDao.findByPhone(wxUserDao.findUserByOpenId(order.getOpenId()).getPhone()).getId();
			programName = Constants.WX_CARDANDFAVOUR;
			}
			IncomeOrder incomeOrder = new IncomeOrder();
			incomeOrder.setAmount(amount);
			incomeOrder.setClientId(clientId);
			incomeOrder.setLicensePlate(licensePlate);
			incomeOrder.setPayDate(payDate);
			incomeOrder.setPayMethod(payMethod);
			incomeOrder.setProgramName(programName);
			incomeOrderDao.save(incomeOrder);
			
		}
		return null;
	}
}

