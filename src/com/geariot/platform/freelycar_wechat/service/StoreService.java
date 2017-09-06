package com.geariot.platform.freelycar_wechat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geariot.platform.freelycar_wechat.entities.Store;
import com.geariot.platform.freelycar_wechat.model.RESCODE;
import com.geariot.platform.freelycar_wechat.utils.JsonResFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.geariot.platform.freelycar_wechat.utils.Constants;
import com.geariot.platform.freelycar_wechat.dao.StoreDao;

/**
 * @author mxy940127
 *
 */

@Service
@Transactional
public class StoreService {

	@Autowired
	private StoreDao storeDao;
	
	public String listStore(int page, int number){
		int from = (page - 1) * number;
		List<Store> list = storeDao.listStore(from , number);
		if(list == null || list.isEmpty()){
			return JsonResFactory.buildOrg(RESCODE.NO_RECORD).toString();
		}
		else{
			long realSize = (long) storeDao.getCount();
			int size=(int) Math.ceil(realSize/(double)number);
			JsonConfig config = JsonResFactory.dateConfig();
			JSONArray jsonArray = JSONArray.fromObject(list , config);
			net.sf.json.JSONObject obj = JsonResFactory.buildNetWithData(RESCODE.SUCCESS, jsonArray);
			obj.put(Constants.RESPONSE_SIZE_KEY, size);
			obj.put(Constants.RESPONSE_REAL_SIZE_KEY,realSize);
			return obj.toString();
		}
	}
	
	public String storeDetail(int storeId){
		Store store = this.storeDao.findStoreById(storeId);
		if(store == null){
			return JsonResFactory.buildOrg(RESCODE.NO_RECORD).toString();
		}
		else{
			JsonConfig config = JsonResFactory.dateConfig();
			return JsonResFactory.buildNetWithData(RESCODE.SUCCESS, JSONObject.fromObject(store,config)).toString();
		}
	}
}
