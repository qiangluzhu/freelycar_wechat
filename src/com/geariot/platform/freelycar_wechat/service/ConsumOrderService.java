package com.geariot.platform.freelycar_wechat.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geariot.platform.freelycar_wechat.utils.Constants;
import com.geariot.platform.freelycar_wechat.utils.DateJsonValueProcessor;
import com.geariot.platform.freelycar_wechat.utils.JsonResFactory;
import com.geariot.platform.freelycar_wechat.dao.ConsumOrderDao;
import com.geariot.platform.freelycar_wechat.entities.ConsumOrder;
import com.geariot.platform.freelycar_wechat.model.RESCODE;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


@Service
@Transactional
public class ConsumOrderService {
	@Autowired
	private ConsumOrderDao consumOrderDao;
	public String detail(String consumOrderId){
		JSONObject obj = new JSONObject();
		obj.put(Constants.RESPONSE_DATA_KEY, consumOrderDao.findById(consumOrderId));
		return JsonResFactory.buildNetWithData(RESCODE.SUCCESS, obj).toString();
	}
	public String listConsumOrder(int clientId, int page, int number) {
		int from = (page-1)*number;
		List<ConsumOrder> exist = consumOrderDao.listByClientId(clientId,from,number);
		if (exist == null || exist.isEmpty()) {
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		long realSize = consumOrderDao.getCountByClientId(clientId);
		int size = (int)Math.ceil(realSize/number);
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(exist, config);
		net.sf.json.JSONObject obj= JsonResFactory.buildNetWithData(RESCODE.SUCCESS, jsonArray);
		obj.put(Constants.RESPONSE_SIZE_KEY, size);
		obj.put(Constants.RESPONSE_REAL_SIZE_KEY, realSize);
		return obj.toString();
	}
}
