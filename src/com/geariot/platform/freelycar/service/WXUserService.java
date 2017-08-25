package com.geariot.platform.freelycar.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geariot.platform.freelycar.dao.CarDao;
import com.geariot.platform.freelycar.dao.ClientDao;
import com.geariot.platform.freelycar.dao.IncomeOrderDao;
import com.geariot.platform.freelycar.dao.PointDao;
import com.geariot.platform.freelycar.dao.WXUserDao;
import com.geariot.platform.freelycar.dao.FavourRemainingsDao;
import com.geariot.platform.freelycar.entities.Car;
import com.geariot.platform.freelycar.entities.Client;
import com.geariot.platform.freelycar.entities.IncomeOrder;
import com.geariot.platform.freelycar.entities.WXUser;
import com.geariot.platform.freelycar.model.RESCODE;
import com.geariot.platform.freelycar.utils.Constants;
import com.geariot.platform.freelycar.utils.JsonPropertyFilter;
import com.geariot.platform.freelycar.utils.JsonResFactory;
import com.geariot.platform.freelycar.utils.query.PointBean;
import com.geariot.platform.freelycar.entities.FavourRemainings;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
@Service
@Transactional
public class WXUserService {
	@Autowired
	private CarDao carDao;
	@Autowired
	private WXUserDao wxUserDao;
	@Autowired
	private ClientDao clientDao;
	@Autowired
	private IncomeOrderDao incomeOrderDao;
	@Autowired
	private FavourRemainingsDao favourRemainingsDao;
	@Autowired
	private PointDao pointDao;
	
	public Client test(){
		return clientDao.findById(10);
	}
	
	public String listDiscount(String openId){
		WXUser wxUser=wxUserDao.findUserByOpenId(openId);
		if(wxUser == null){
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		Client client = clientDao.findByPhone(wxUser.getPhone());
		if(client == null){
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		List<FavourRemainings> list = favourRemainingsDao.favourtByClientId(client.getId());
		JsonConfig config = JsonResFactory.dateConfig(FavourRemainings.class);
		net.sf.json.JSONArray array = net.sf.json.JSONArray.fromObject(list, config);
		return JsonResFactory.buildNetWithData(RESCODE.SUCCESS, array).toString();
	}
	
	public String setDefaultCar(int carId){
		Car car=carDao.findById(carId);
		if(car == null)
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		car.setDefaultDate(new Date());
		return JsonResFactory.buildOrg(RESCODE.SUCCESS).toString();
	}
	
	public String addCar(String openId,Car car){
		WXUser wxUser=wxUserDao.findUserByOpenId(openId);
		if(wxUser == null){
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		Client client = clientDao.findByPhone(wxUser.getPhone());
		if(client == null){
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		Car exist = carDao.findByLicense(car.getLicensePlate());
		if(exist != null){
			return JsonResFactory.buildOrg(RESCODE.CAR_LICENSE_EXIST).toString();
		}
		car.setCreateDate(new Date());
		client.getCars().add(car);
		return JsonResFactory.buildOrg(RESCODE.SUCCESS).toString();
	}
	
	public String deleteCar(int carId){
		carDao.deleteById(carId);;
		return JsonResFactory.buildOrg(RESCODE.SUCCESS).toString();
	}
	//返回微信用户信息,client card
	public String detail(String openId){
		WXUser wxUser=wxUserDao.findUserByOpenId(openId);
		if(wxUser == null){
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		Client client = clientDao.findByPhone(wxUser.getPhone());
		if(client == null){
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		JsonConfig config = JsonResFactory.dateConfig();
		JsonPropertyFilter filter = new JsonPropertyFilter(Client.class);
		config.setJsonPropertyFilter(filter);
		JSONObject obj = JsonResFactory.buildNet(RESCODE.SUCCESS, 
				Constants.RESPONSE_CLIENT_KEY, JSONObject.fromObject(client, config));
		List<IncomeOrder> consumHist = this.incomeOrderDao.findByClientId(client.getId());
		if(consumHist != null){
			obj.put(Constants.RESPONSE_DATA_KEY, JSONArray.fromObject(consumHist, config));
		}
		return obj.toString();
	}
	public String modifyCar(int carId,Car car){
		Car exist=carDao.findById(carId);
		if(exist==null){
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		carDao.update(car);
		return JsonResFactory.buildOrg(RESCODE.SUCCESS).toString();
	}
	public String getPoint(String openId){
		WXUser wxUser=wxUserDao.findUserByOpenId(openId);
		if(wxUser == null){
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		Client client = clientDao.findByPhone(wxUser.getPhone());
		if(client == null){
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		List<Object[]> exists = pointDao.getPoint(client.getId());
		JsonConfig config = JsonResFactory.dateConfig();
		if (exists == null || exists.isEmpty()) {
			return JsonResFactory.buildOrg(RESCODE.NO_RECORD).toString();
		} else {
			List<PointBean> pointBeans = new ArrayList<>();
			for (Object[] exist : exists) {
				pointBeans.add(new PointBean((Double)exist[1],(Date)exist[0]));
			}
			return JsonResFactory.buildNetWithData(RESCODE.SUCCESS, JSONArray.fromObject(pointBeans,config)).toString();
		}
	}
}
