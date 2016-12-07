package com.geariot.platform.yi.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geariot.platform.yi.dao.UserDao;
import com.geariot.platform.yi.entities.Community;
import com.geariot.platform.yi.entities.Reservation;
import com.geariot.platform.yi.entities.UserAddress;
import com.geariot.platform.yi.model.RESCODE;
import com.geariot.platform.yi.utils.Constants;

@Transactional
@Service
public class UserService {

	@Autowired
	UserDao dao;
	
	public String register(String openId, String nickName) {
		JSONObject resultObj=new JSONObject();
		boolean b = dao.register(openId,nickName);
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

	public String addAddress(UserAddress c) {
		JSONObject resultObj=new JSONObject();
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

	public String delAddress(String addressId) {
		JSONObject resultObj=new JSONObject();
		boolean b = dao.delete(addressId);
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

	public String modAddress(UserAddress c) {
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

	public String getAddById(String id) {
		JSONObject resultObj=new JSONObject();
		UserAddress b = dao.getAddById(id);
		if (b!=null) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
			resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
			resultObj.put(Constants.RESPONSE_DATA_KEY, new JSONObject(b));
			return resultObj.toString();
		}
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.NOT_FOUND);
		resultObj.put(Constants.RESPONSE_MSG_KEY,
				RESCODE.NOT_FOUND.getMsg());
		return resultObj.toString();
	}

	public String getAll(String openId) {
		JSONObject resultObj=new JSONObject();
		List<UserAddress> list = dao.getAll(openId);
		if (list!=null&&list.size()!=0) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
			resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
			resultObj.put(Constants.RESPONSE_DATA_KEY, list);
			return resultObj.toString();
		}
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.DELETE_ERROR);
		resultObj.put(Constants.RESPONSE_MSG_KEY,
				RESCODE.NOT_FOUND.getMsg());
		return resultObj.toString();
	}

	public String reserve(Reservation r) {
		JSONObject resultObj=new JSONObject();
		boolean b = dao.reserve(r);
		if (!b) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.DELETE_ERROR);
			resultObj.put(Constants.RESPONSE_MSG_KEY,
					RESCODE.CREATE_ERROR.getMsg());
			return resultObj.toString();
		}
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
		resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
		resultObj.put(Constants.RESPONSE_DATA_KEY, b);
		return resultObj.toString();
	}

	public String getRecord(String openId, int start, int number) {
		JSONObject resultObj=new JSONObject();
		List<Reservation> list = dao.getRecord(openId,start,number);
		if (list!=null&&list.size()!=0) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
			resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
			resultObj.put(Constants.RESPONSE_DATA_KEY, list);
			return resultObj.toString();
		}
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.DELETE_ERROR);
		resultObj.put(Constants.RESPONSE_MSG_KEY,
				RESCODE.NOT_FOUND.getMsg());
		return resultObj.toString();
	}

	public String getCode(int start, int number) {
		JSONObject resultObj=new JSONObject();
		List<Community> list = dao.getCode(start,number);
		if (list!=null&&list.size()!=0) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
			resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
			resultObj.put(Constants.RESPONSE_DATA_KEY, list);
			return resultObj.toString();
		}
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.DELETE_ERROR);
		resultObj.put(Constants.RESPONSE_MSG_KEY,
				RESCODE.NOT_FOUND.getMsg());
		return resultObj.toString();
	}

	public String adminLogin(String username, String password) {
		JSONObject resultObj=new JSONObject();
		boolean b = dao.adminLogin(username,password);
		if (!b) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.DELETE_ERROR);
			resultObj.put(Constants.RESPONSE_MSG_KEY,
					RESCODE.NOT_FOUND.getMsg());
			return resultObj.toString();
		}
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
		resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
		resultObj.put(Constants.RESPONSE_DATA_KEY, b);
		return resultObj.toString();
	}

}
