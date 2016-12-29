package com.geariot.platform.yi.service;

import java.util.Date;
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
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.CREATE_ERROR);
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
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.CREATE_ERROR);
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
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.NOT_FOUND);
		resultObj.put(Constants.RESPONSE_MSG_KEY,
				RESCODE.NOT_FOUND.getMsg());
		return resultObj.toString();
	}

	
	/**
	 * @javebean
	 * 返回三个int 作为 标示
	 * 1：技师为空
	 * 2：技师openid为空 未激活
	 * 3. 技师 已经激活
	 */
	public String reserve(Reservation r) {
		JSONObject resultObj=new JSONObject();
		r.setCreateTime(new Date());
		int b = dao.reserve(r);
		
		if (b==2) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.TECHNICIAN_INACTIVATED);
			resultObj.put(Constants.RESPONSE_MSG_KEY,
					RESCODE.TECHNICIAN_INACTIVATED.getMsg());
			return resultObj.toString();
		}else if(b==1){
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.TECHNICIAN_ERROR);
			resultObj.put(Constants.RESPONSE_MSG_KEY,
					RESCODE.TECHNICIAN_ERROR.getMsg());
			return resultObj.toString();
		}
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
		resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
		//resultObj.put(Constants.RESPONSE_DATA_KEY, b);
		return resultObj.toString();
	}

	public String getRecord(String openId, int page, int number) {
		int start = (page-1)*number;
		JSONObject resultObj=new JSONObject();
		List<Reservation> list = dao.getRecord(openId,start,number);
		if (list!=null&&list.size()!=0) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
			resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
			resultObj.put(Constants.RESPONSE_DATA_KEY, list);
			int realSize=dao.getrSize(openId);
			double size=Math.ceil(realSize/(double)number);
			resultObj.put("size", size);
			resultObj.put("num", realSize);
			return resultObj.toString();
		}
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.NOT_FOUND);
		resultObj.put(Constants.RESPONSE_MSG_KEY,
				RESCODE.NOT_FOUND.getMsg());
		return resultObj.toString();
	}

	public String getCode(int page, int number) {
		int start = (page-1)*number;
		JSONObject resultObj=new JSONObject();
		List<Community> list = dao.getCode(start,number);
		if (list!=null&&list.size()!=0) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
			resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
			resultObj.put(Constants.RESPONSE_DATA_KEY, list);
			int realSize=dao.getcSize();
			double size=Math.ceil(realSize/(double)number);
			resultObj.put("size", size);
			resultObj.put("num", realSize);
			return resultObj.toString();
		}
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.NOT_FOUND);
		resultObj.put(Constants.RESPONSE_MSG_KEY,
				RESCODE.NOT_FOUND.getMsg());
		return resultObj.toString();
	}

	public String adminLogin(String username, String password) {
		JSONObject resultObj=new JSONObject();
		boolean b = dao.adminLogin(username,password);
		if (!b) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.NOT_FOUND);
			resultObj.put(Constants.RESPONSE_MSG_KEY,
					RESCODE.NOT_FOUND.getMsg());
			return resultObj.toString();
		}
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
		resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
		resultObj.put(Constants.RESPONSE_DATA_KEY, b);
		return resultObj.toString();
	}

	public String setDefaultAddress(String addId,String userId) {
		JSONObject resultObj=new JSONObject();
		boolean b = dao.setDefaultAddress(addId,userId);
		if (!b) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.CREATE_ERROR);
			resultObj.put(Constants.RESPONSE_MSG_KEY,
					RESCODE.CREATE_ERROR.getMsg());
			return resultObj.toString();
		}
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
		resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
		resultObj.put(Constants.RESPONSE_DATA_KEY, b);
		return resultObj.toString();
	}

	public String getAddByAddId(String id) {
		JSONObject resultObj=new JSONObject();
		UserAddress b = dao.getAddByAddId(id);
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

}
