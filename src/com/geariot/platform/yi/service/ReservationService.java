package com.geariot.platform.yi.service;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geariot.platform.yi.dao.ReservationDao;
import com.geariot.platform.yi.entities.Reservation;
import com.geariot.platform.yi.model.RESCODE;
import com.geariot.platform.yi.utils.Constants;

@Transactional
@Service
public class ReservationService {
	
	@Autowired
	ReservationDao reservationDao;
	

	public String getByPerson(Reservation r, int start, int number) {
		JSONObject resultObj=new JSONObject();
		List<Reservation> list = reservationDao.getByPerson(r,start,number);
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

	public String statistic() {
		JSONObject resultObj=new JSONObject();
		HashMap<String,Integer> map = reservationDao.statistic();
		if (map!=null&&map.size()!=0) {
			resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
			resultObj.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
			resultObj.put(Constants.RESPONSE_DATA_KEY, map);
			return resultObj.toString();
		}
		resultObj.put(Constants.RESPONSE_CODE_KEY, RESCODE.DELETE_ERROR);
		resultObj.put(Constants.RESPONSE_MSG_KEY,
				RESCODE.NOT_FOUND.getMsg());
		return resultObj.toString();
	}

}
