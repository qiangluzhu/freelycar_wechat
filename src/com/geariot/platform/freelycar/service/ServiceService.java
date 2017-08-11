package com.geariot.platform.freelycar.service;


import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.geariot.platform.freelycar.dao.ServiceDao;
import com.geariot.platform.freelycar.entities.Service;
import com.geariot.platform.freelycar.model.RESCODE;
import com.geariot.platform.freelycar.utils.Constants;
import com.geariot.platform.freelycar.utils.DateJsonValueProcessor;
import com.geariot.platform.freelycar.utils.JsonResFactory;
import com.geariot.platform.freelycar.utils.query.ServiceAndQueryCreator;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;



@org.springframework.stereotype.Service
@Transactional
public class ServiceService {

	@Autowired
	private ServiceDao serviceDao;
	
	public String addService(Service service){
		service.setCreateDate(new Date());
		serviceDao.save(service);
		JsonConfig config = JsonResFactory.dateConfig();
		return JsonResFactory.buildNetWithData(RESCODE.SUCCESS,net.sf.json.JSONObject.fromObject(service, config)).toString();
	}
	
	public String deleteService(Integer... serviceIds){
		int count = 0;
		for(int serviceId : serviceIds){
			com.geariot.platform.freelycar.entities.Service exist = serviceDao.findServiceById(serviceId);
			if(exist == null){
				count++;
			}
			else{
				//删除service只将deleted字段设为true，不在数据库中删除此条字段
				exist.setDeleted(true);
			}
		}
		if(count !=0){
			String tips = "共"+count+"条未在数据库中存在记录";
			net.sf.json.JSONObject obj = JsonResFactory.buildNetWithData(RESCODE.PART_SUCCESS , tips);
			long realSize = serviceDao.getCount();
			obj.put(Constants.RESPONSE_REAL_SIZE_KEY,realSize);
			return obj.toString();
		}
		else{
			JSONObject obj = JsonResFactory.buildOrg(RESCODE.SUCCESS);
			long realSize = serviceDao.getCount();
			obj.put(Constants.RESPONSE_REAL_SIZE_KEY,realSize);
			return obj.toString();
		}
}
	
	public String modifyService(Service service){
		Service exist = serviceDao.findServiceById(service.getId());
		JSONObject obj = null;
		if(exist == null){
			obj = JsonResFactory.buildOrg(RESCODE.NOT_FOUND);
		}
		else{
			exist.setComment(service.getComment());
			exist.setName(service.getName());
			exist.setValidTime(service.getValidTime());
			exist.setPrice(service.getPrice());
			obj = JsonResFactory.buildOrg(RESCODE.SUCCESS);
		}
		return obj.toString();
	}
	
	/*public String getServiceList(int page , int number){
		String andCondition = new ServiceAndQueryCreator(name).createStatement();
		int from = (page - 1) * number;
		List<Service> list = serviceDao.listServices(from, number);
		if(list == null || list.isEmpty()){
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		long realSize = serviceDao.getCount();
		int size=(int) Math.ceil(realSize/(double)number);
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(list, config);
		net.sf.json.JSONObject obj = JsonResFactory.buildNetWithData(RESCODE.SUCCESS, jsonArray);
		obj.put(Constants.RESPONSE_SIZE_KEY, size);
		return obj.toString();
	}*/
	
	public String getSelectService(String name , int page , int number){
		String andCondition = new ServiceAndQueryCreator(name).createStatement();
		int from = (page - 1) * number;
		List<Service> list = serviceDao.listServices(andCondition, from, number);
		if(list == null || list.isEmpty()){
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		long realSize = serviceDao.getConditionCount(andCondition);
		int size = (int) Math.ceil(realSize/(double)number);
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(list, config);
		net.sf.json.JSONObject obj =  JsonResFactory.buildNetWithData(RESCODE.SUCCESS, jsonArray);
		obj.put(Constants.RESPONSE_SIZE_KEY, size);
		obj.put(Constants.RESPONSE_REAL_SIZE_KEY, realSize);
		return obj.toString();
	}
	
	public String getAllName(){
		List<Object> list = serviceDao.listName();
		JSONArray jsonArray = JSONArray.fromObject(list);
		net.sf.json.JSONObject obj = JsonResFactory.buildNetWithData(RESCODE.SUCCESS, jsonArray);
		return obj.toString();
	}
}
