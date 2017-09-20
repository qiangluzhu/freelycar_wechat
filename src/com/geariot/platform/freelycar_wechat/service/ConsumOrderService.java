package com.geariot.platform.freelycar_wechat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geariot.platform.freelycar_wechat.utils.Constants;
import com.geariot.platform.freelycar_wechat.utils.JsonResFactory;
import com.geariot.platform.freelycar_wechat.utils.query.OrderBean;
import com.geariot.platform.freelycar_wechat.dao.*;
import com.geariot.platform.freelycar_wechat.entities.*;
import com.geariot.platform.freelycar_wechat.model.RESCODE;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


@Service
@Transactional
public class ConsumOrderService {	
	@Autowired
	private ConsumOrderDao consumOrderDao;
	@Autowired
	private WXPayOrderDao wxPayOrderDao;
	@Autowired
	private WXUserDao wxUserDao;
	@Autowired
	private ClientDao clientDao;
	@Autowired
	private CardDao cardDao;
	//根据前端之前获取的单据ID调用接口，不需要验证单据不存在
	public String detail(String consumOrderId){
		ConsumOrder consumOrder = consumOrderDao.findById(consumOrderId);
		Set<ProjectInfo> projects = consumOrder.getProjects();
		List<OrderBean> projectsForRemaining = new ArrayList<OrderBean>();
		for(ProjectInfo project : projects){
			CardProjectRemainingInfo cardProjectRemainingInfo = cardDao.getProjectRemainingInfo(Integer.parseInt(project.getCardId()), project.getProjectId());
			int remaining = 0;
			if(cardProjectRemainingInfo != null){
				remaining = cardProjectRemainingInfo.getRemaining();
			}
			OrderBean orderBean = new OrderBean();
			orderBean.setRemaining(remaining);
			orderBean.setProjectInfo(project);
			projectsForRemaining.add(orderBean);
		}
		System.out.println("<<<<<"+consumOrder);
		JsonConfig config = JsonResFactory.dateConfig();
		config.registerPropertyExclusions(ConsumOrder.class,new String[]{"projects"});
		JSONObject obj = JSONObject.fromObject(consumOrderDao.findById(consumOrderId),config);	
		obj.put("projects", projectsForRemaining);
		System.out.println("<<<<<"+obj);
		return JsonResFactory.buildOrg(RESCODE.SUCCESS, Constants.RESPONSE_CONSUMORDER_KEY, obj).toString();

	}
	
	
	
	
	public String listConsumOrder(int clientId, int page, int number) {
		int from = (page-1)*number;
		List<ConsumOrder> exist = consumOrderDao.listByClientId(clientId,from,number);
		if (exist == null || exist.isEmpty()) {
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		long realSize = consumOrderDao.getCountByClientId(clientId);
		int size = (int)Math.ceil(realSize/number);
		//JsonConfig config = new JsonConfig();
		//config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
		JsonConfig config = JsonResFactory.dateConfig();
		JSONArray jsonArray = JSONArray.fromObject(exist,config);
		net.sf.json.JSONObject obj= JsonResFactory.buildNetWithData(RESCODE.SUCCESS, jsonArray);
		obj.put(Constants.RESPONSE_SIZE_KEY, size);
		obj.put(Constants.RESPONSE_REAL_SIZE_KEY, realSize);
		return obj.toString();
	}




	public String detailWXPayOrder(String wxPayOrderId) {
		JsonConfig config = JsonResFactory.dateConfig();
		JSONObject obj = JSONObject.fromObject(wxPayOrderDao.findById(wxPayOrderId),config);
		return JsonResFactory.buildOrg(RESCODE.SUCCESS, Constants.RESPONSE_WXORDER_KEY, obj).toString();
	}




	public String listWXPayOrder(int clientId, int page, int number) {
		WXUser wxUser = wxUserDao.findUserByPhone(clientDao.findById(clientId).getPhone());
		String openId = wxUser.getOpenId();
		int from = (page-1)*number;
		List<WXPayOrder> exist = wxPayOrderDao.listByOpenId(openId,from,number);
		if (exist == null || exist.isEmpty()) {
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		long realSize = wxPayOrderDao.getCountByOpenId(openId);
		int size = (int)Math.ceil(realSize/number);
		//JsonConfig config = new JsonConfig();
		//config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
		JsonConfig config = JsonResFactory.dateConfig();
		JSONArray jsonArray = JSONArray.fromObject(exist,config);
		net.sf.json.JSONObject obj= JsonResFactory.buildNetWithData(RESCODE.SUCCESS, jsonArray);
		obj.put(Constants.RESPONSE_SIZE_KEY, size);
		obj.put(Constants.RESPONSE_REAL_SIZE_KEY, realSize);
		return obj.toString();
	}




	public String comment(String consumOrderId, String comment, int stars) {
		ConsumOrder consumOrder = consumOrderDao.findById(consumOrderId);
		consumOrder.setComment(comment);
		consumOrder.setStars(stars);
		consumOrder.setCommentDate(new Date());
		consumOrderDao.update(consumOrder);
		return JsonResFactory.buildNet(RESCODE.SUCCESS).toString();

	}
	
	
}
