package com.geariot.platform.yi.service;

import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geariot.platform.yi.dao.TechnicianDao;
import com.geariot.platform.yi.entities.Reservation;
import com.geariot.platform.yi.entities.Technician;
import com.geariot.platform.yi.model.RESCODE;
import com.geariot.platform.yi.utils.Constants;
import com.geariot.platform.yi.utils.MD5;

@Transactional
@Service
public class TechnicianService {
	
	@Autowired
	TechnicianDao dao;
	

	public String add(Technician c) {
		JSONObject resultObj=new JSONObject();
		c.setPassword(MD5.compute(c.getPassword()));
		c.setCreateTime(new Date());
		boolean b = dao.add(c);
		if (!b) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.DELETE_ERROR);
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

	public String modify(Technician c) {
		JSONObject resultObj=new JSONObject();
		c.setPassword(MD5.compute(c.getPassword()));
		boolean b = dao.modify(c);
		if (!b) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.DELETE_ERROR);
			resultObj.put(Constants.RESPONSE_MSG_KEY,
					RESCODE.UPDATE_ERROR.getMsg());
			return resultObj.toString();
		}
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
		resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
		return resultObj.toString();
	}

	public String find(Technician c, int page, int number) {
		int start = (page-1)*number;
		JSONObject resultObj=new JSONObject();
		List<Technician> list = dao.find(c,start,number);
		if (list!=null&&list.size()!=0) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
			resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
			resultObj.put(Constants.RESPONSE_DATA_KEY, list);
			double size=Math.ceil(list.size()/(double)number);
			resultObj.put("size", size);
			return resultObj.toString();
		}
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.DELETE_ERROR);
		resultObj.put(Constants.RESPONSE_MSG_KEY,
				RESCODE.NOT_FOUND.getMsg());
		return resultObj.toString();
	}

	public String login(String phone, String password) {
		JSONObject resultObj=new JSONObject();
		password=MD5.compute(password);
		Technician b = dao.login(phone,password);
		if (b==null) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.DELETE_ERROR);
			resultObj.put(Constants.RESPONSE_MSG_KEY,
					RESCODE.NOT_FOUND.getMsg());
			return resultObj.toString();
		}
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
		resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
		resultObj.put(Constants.RESPONSE_DATA_KEY, new JSONObject(b));
		return resultObj.toString();
	}

	public String getReservation(String tid, int page, int number) {
		int start = (page-1)*number;
		JSONObject resultObj=new JSONObject();
		List<Reservation> list = dao.getReservation(tid,start,number);
		if (list!=null&&list.size()!=0) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
			resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
			resultObj.put(Constants.RESPONSE_DATA_KEY, list);
			double size=Math.ceil(list.size()/(double)number);
			resultObj.put("size", size);
			return resultObj.toString();
		}
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.DELETE_ERROR);
		resultObj.put(Constants.RESPONSE_MSG_KEY,
				RESCODE.NOT_FOUND.getMsg());
		return resultObj.toString();
	}

	public String getById(String id) {
		JSONObject resultObj=new JSONObject();
		Technician s = dao.getById(id);
		if (s!=null) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
			resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
			resultObj.put(Constants.RESPONSE_DATA_KEY, new JSONObject(s));
			return resultObj.toString();
		}
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.DELETE_ERROR);
		resultObj.put(Constants.RESPONSE_MSG_KEY,
				RESCODE.NOT_FOUND.getMsg());
		return resultObj.toString();
	}

}
