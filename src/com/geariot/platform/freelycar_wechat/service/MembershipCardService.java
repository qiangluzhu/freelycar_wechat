package com.geariot.platform.freelycar_wechat.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;

import com.geariot.platform.freelycar_wechat.model.RESCODE;
import com.geariot.platform.freelycar_wechat.utils.DateJsonValueProcessor;
import com.geariot.platform.freelycar_wechat.utils.JsonResFactory;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import com.geariot.platform.freelycar_wechat.utils.Constants;
import com.geariot.platform.freelycar_wechat.dao.ServiceDao;
import com.geariot.platform.freelycar_wechat.entities.Service;

@org.springframework.stereotype.Service
@Transactional
public class MembershipCardService {
	@Autowired
	private ServiceDao serviceDao;
	public String getMembershipCardList(int page , int number){
		int from = (page-1)*number;
		List<Service> exist = serviceDao.listOnWX(from,number);
		if (exist == null || exist.isEmpty()) {
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		long realSize = serviceDao.getCount();
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