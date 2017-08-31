package com.geariot.platform.freelycar_wechat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geariot.platform.freelycar_wechat.dao.CarDao;
import com.geariot.platform.freelycar_wechat.dao.ClientDao;
import com.geariot.platform.freelycar_wechat.dao.FavourRemainingsDao;
import com.geariot.platform.freelycar_wechat.dao.IncomeOrderDao;
import com.geariot.platform.freelycar_wechat.dao.PointDao;
import com.geariot.platform.freelycar_wechat.dao.WXUserDao;
import com.geariot.platform.freelycar_wechat.entities.Car;
import com.geariot.platform.freelycar_wechat.entities.Client;
import com.geariot.platform.freelycar_wechat.entities.FavourRemainings;
import com.geariot.platform.freelycar_wechat.entities.IncomeOrder;
import com.geariot.platform.freelycar_wechat.entities.WXUser;
import com.geariot.platform.freelycar_wechat.model.RESCODE;
import com.geariot.platform.freelycar_wechat.utils.Constants;
import com.geariot.platform.freelycar_wechat.utils.JsonPropertyFilter;
import com.geariot.platform.freelycar_wechat.utils.JsonResFactory;
import com.geariot.platform.freelycar_wechat.utils.query.PointBean;

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
	
	public double test(){
return 0;
	}
	
	public String deletWXUser(String openId){
		wxUserDao.deleteUser(openId);
		return JsonResFactory.buildOrg(RESCODE.SUCCESS).toString();
	}
	public String login(String openId){
		WXUser wxUser=wxUserDao.findUserByOpenId(openId);
		if(wxUser == null){
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND_WXUSER).toString();
		}
		Client client = new Client();
		if(clientDao.findByPhone(wxUser.getPhone())==null){
			client.setPhone(wxUser.getPhone());
			client.setBirthday(wxUser.getBirthday());
			client.setName(wxUser.getName());
			client.setGender(wxUser.getGender());
			clientDao.save(client);
		}
		return JsonResFactory.buildOrg(RESCODE.SUCCESS).toString();
	}
	//只做添加操作
	public String addWXUser(String openId,String nickName,String headimgurl,String phone){
		WXUser wxUser= new WXUser();
		wxUser.setPhone(phone);
		wxUser.setNickName(nickName);
		wxUser.setHeadimgurl(headimgurl);
		wxUser.setOpenId(openId);
		wxUserDao.saveOrUpdateUser(wxUser);
		return JsonResFactory.buildOrg(RESCODE.SUCCESS).toString();
	}
	
	public String listDiscount(String openId){
		WXUser wxUser=wxUserDao.findUserByOpenId(openId);
		if(wxUser == null){
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND_WXUSER).toString();
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
		carDao.update(car);
		return JsonResFactory.buildOrg(RESCODE.SUCCESS).toString();
	}
	
	public String addCar(String openId,Car car){
		WXUser wxUser=wxUserDao.findUserByOpenId(openId);
		if(wxUser == null){
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND_WXUSER).toString();
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
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND_WXUSER).toString();
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
				pointBeans.add(new PointBean((int)Math.rint((Double)exist[1]),(Date)exist[0]));
			}
			return JsonResFactory.buildNetWithData(RESCODE.SUCCESS, JSONArray.fromObject(pointBeans,config)).toString();
		}
	}
	
	
	//point、wxuser、discount,复用性极差
	public String getWXUser(String openId){
		WXUser wxUser=wxUserDao.findUserByOpenId(openId);
		if(wxUser == null){
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		Client client = clientDao.findByPhone(wxUser.getPhone());
		if(client == null){
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		Object favour = favourRemainingsDao.getCountByClientId(client.getId());
		Object point =pointDao.getSumPoint(client.getId()) ;
		JSONObject obj = new JSONObject();
		if(favour==null){
			favour=0;
		}
		if(point == null)
			point=0;
		else
			point=(int)Math.rint((double)(point));
		obj.put(Constants.RESPONSE_FAVOUR_KEY, favour);
		obj.put("point", point);
		obj.put(Constants.RESPONSE_WXUSER_KEY, wxUser);
		
		return JsonResFactory.buildNetWithData(RESCODE.SUCCESS, obj).toString();
	}
}