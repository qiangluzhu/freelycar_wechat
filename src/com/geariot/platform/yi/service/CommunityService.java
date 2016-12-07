package com.geariot.platform.yi.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geariot.platform.yi.dao.CommunityDao;
import com.geariot.platform.yi.entities.Community;
import com.geariot.platform.yi.model.RESCODE;
import com.geariot.platform.yi.utils.Constants;

@Transactional
@Service
public class CommunityService {
	
	@Autowired
	CommunityDao communityDao;
	

	public String add(Community c) {
		JSONObject resultObj=new JSONObject();
		boolean b = communityDao.add(c);
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
		boolean b = communityDao.delete(id);
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

	public String modify(Community c) {
		JSONObject resultObj=new JSONObject();
		boolean b = communityDao.modify(c);
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

	public String find(Community c, int start, int number) {
		JSONObject resultObj=new JSONObject();
		List<Community> list = communityDao.find(c,start,number);
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

	public String getById(String id) {
		JSONObject resultObj=new JSONObject();
		Community s = communityDao.getById(id);
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
