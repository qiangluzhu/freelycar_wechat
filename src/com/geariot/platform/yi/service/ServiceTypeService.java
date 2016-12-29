package com.geariot.platform.yi.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geariot.platform.yi.dao.ServiceTypeDao;
import com.geariot.platform.yi.entities.ServiceType;
import com.geariot.platform.yi.entities.Store;
import com.geariot.platform.yi.model.RESCODE;
import com.geariot.platform.yi.utils.Constants;

@Transactional
@Service
public class ServiceTypeService {
	
	@Autowired
	ServiceTypeDao dao;

	public String add(ServiceType c) {
		JSONObject resultObj=new JSONObject();
		boolean b = dao.add(c);
		if (!b) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.CREATE_ERROR);
			resultObj.put(Constants.RESPONSE_MSG_KEY,
					RESCODE.CREATE_ERROR.getMsg());
			return resultObj.toString();
		}
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
		resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
		return resultObj.toString();
	}

	public String delete(String id) {
		JSONObject resultObj=new JSONObject();
		boolean b = dao.delete(id);
		if (!b) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.DELETE_ERROR);
			resultObj.put(Constants.RESPONSE_MSG_KEY,
					RESCODE.DELETE_ERROR.getMsg());
			return resultObj.toString();
		}
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
		resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
		return resultObj.toString();
	}

	public String modify(ServiceType c) {
		JSONObject resultObj=new JSONObject();
		boolean b = dao.modify(c);
		if (!b) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.UPDATE_ERROR);
			resultObj.put(Constants.RESPONSE_MSG_KEY,
					RESCODE.UPDATE_ERROR.getMsg());
			return resultObj.toString();
		}
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
		resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
		return resultObj.toString();
	}

	public String find(ServiceType s) {
		JSONObject resultObj=new JSONObject();
		List<Store> list = dao.find(s);
		if (list!=null&&list.size()!=0) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
			resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
			resultObj.put(Constants.RESPONSE_DATA_KEY, list);
			return resultObj.toString();
		}
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.NOT_FOUND);
		resultObj.put(Constants.RESPONSE_MSG_KEY,
				RESCODE.NOT_FOUND.getMsg());
		return resultObj.toString();
	}

	public String getById(String id) {
		JSONObject resultObj=new JSONObject();
		ServiceType s = dao.getById(id);
		if (s!=null) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
			resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
			resultObj.put(Constants.RESPONSE_DATA_KEY, new JSONObject(s));
			return resultObj.toString();
		}
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.NOT_FOUND);
		resultObj.put(Constants.RESPONSE_MSG_KEY,
				RESCODE.NOT_FOUND.getMsg());
		return resultObj.toString();
	}

}
