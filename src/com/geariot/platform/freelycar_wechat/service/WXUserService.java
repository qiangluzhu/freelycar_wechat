package com.geariot.platform.freelycar_wechat.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geariot.platform.freelycar_wechat.dao.CarDao;
import com.geariot.platform.freelycar_wechat.dao.CardDao;
import com.geariot.platform.freelycar_wechat.dao.ClientDao;
import com.geariot.platform.freelycar_wechat.dao.ConsumOrderDao;
import com.geariot.platform.freelycar_wechat.dao.PointDao;
import com.geariot.platform.freelycar_wechat.dao.WXUserDao;
import com.geariot.platform.freelycar_wechat.entities.Car;
import com.geariot.platform.freelycar_wechat.entities.Card;
import com.geariot.platform.freelycar_wechat.entities.Client;
import com.geariot.platform.freelycar_wechat.entities.ConsumOrder;
import com.geariot.platform.freelycar_wechat.entities.WXUser;
import com.geariot.platform.freelycar_wechat.model.RESCODE;
import com.geariot.platform.freelycar_wechat.utils.Constants;
import com.geariot.platform.freelycar_wechat.utils.JsonPropertyFilter;
import com.geariot.platform.freelycar_wechat.utils.JsonResFactory;
import com.geariot.platform.freelycar_wechat.utils.query.ClientBean;
import com.geariot.platform.freelycar_wechat.utils.query.PointBean;

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
	private PointDao pointDao;
	@Autowired
	private ConsumOrderDao consumOrderDao;
	@Autowired
	private CardDao cardDao;

	public double test() {
		return 0;
	}

	public String deletWXUser(String openId) {
		wxUserDao.deleteUser(openId);
		return JsonResFactory.buildOrg(RESCODE.SUCCESS).toString();
	}

	@SuppressWarnings("null")
	public String login(String openId) {
		WXUser wxUser = wxUserDao.findUserByOpenId(openId);
		if (wxUser == null) {
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND_WXUSER).toString();
		}
		Client client = clientDao.findByPhone(wxUser.getPhone());
		if (client == null) {
			client.setPhone(wxUser.getPhone());
			client.setBirthday(wxUser.getBirthday());
			client.setGender(wxUser.getGender());
			if (wxUser.getName() == null)
				client.setName(wxUser.getNickName());
			else
				client.setName(wxUser.getName());
			clientDao.save(client);
		}
		JSONObject obj = new JSONObject();
		obj.put(Constants.RESPONSE_CLIENT_KEY, client);
		return JsonResFactory.buildNet(RESCODE.SUCCESS,
				Constants.RESPONSE_CLIENT_KEY, client.getId()).toString();
		// return JsonResFactory.buildOrg(RESCODE.SUCCESS).toString();
	}

	public String listDiscount(int clientId) {
		Client client = clientDao.findById(clientId);
		if (client == null) {
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		JsonConfig config = JsonResFactory.dateConfig();
		JsonPropertyFilter filter = new JsonPropertyFilter(Client.class);
		config.setJsonPropertyFilter(filter);
		JSONObject obj = JsonResFactory.buildNet(RESCODE.SUCCESS,
				Constants.RESPONSE_CLIENT_KEY,
				JSONObject.fromObject(client, config));
		return obj.toString();
	}

	public String setDefaultCar(int carId) {
		Car car = carDao.findById(carId);
		if (car == null)
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		for (Car cars : car.getClient().getCars()) {
			cars.setDefaultCar(false);
		}
		car.setDefaultCar(true);
		carDao.update(car);
		return JsonResFactory.buildOrg(RESCODE.SUCCESS).toString();
	}

	public String addCar(Car car) {
		System.out.println("<<<" + car);
		Client client = clientDao.findById((car.getClient()).getId());
		if (client == null) {
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		Car exist = carDao.findByLicense(car.getLicensePlate());
		if (exist != null) {
			return JsonResFactory.buildOrg(RESCODE.CAR_LICENSE_EXIST)
					.toString();
		}
		car.setCreateDate(new Date());
		client.getCars().add(car);
		return JsonResFactory.buildOrg(RESCODE.SUCCESS).toString();
	}

	public String deleteCar(int carId) {
		carDao.deleteById(carId);
		;
		return JsonResFactory.buildOrg(RESCODE.SUCCESS).toString();
	}

	// 返回微信用户信息,client card
	public String detail(int clientId) {
		Client client = clientDao.findById(clientId);
		if (client == null) {
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		JsonConfig config = JsonResFactory.dateConfig();
		JsonPropertyFilter filter = new JsonPropertyFilter(Client.class);
		config.setJsonPropertyFilter(filter);
		JSONObject obj = JsonResFactory.buildNet(RESCODE.SUCCESS,
				Constants.RESPONSE_CLIENT_KEY,
				JSONObject.fromObject(client, config));
		List<ConsumOrder> consumOrders = this.consumOrderDao
				.findWithClientId(clientId);
		if (consumOrders != null) {
			obj.put(Constants.RESPONSE_CONSUMORDER_KEY,
					JSONArray.fromObject(consumOrders, config));
		}
		return obj.toString();
	}

	public String modifyCar(int clientId, int id, String insuranceCity, String insuranceCompany, String insuranceEndtime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Client client = clientDao.findById(clientId);
		if (client == null) {
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		Car modify = this.carDao.findById(id);
		modify.setInsuranceCity(insuranceCity);
		modify.setInsuranceCompany(insuranceCompany);
		modify.setInsuranceEndtime(sdf.parse(insuranceEndtime));
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(insuranceEndtime));
		cal.add(Calendar.YEAR, -1);
		modify.setInsuranceStarttime(cal.getTime());
		carDao.update(modify);
		JsonConfig config = JsonResFactory.dateConfig();
		config.registerPropertyExclusions(Car.class, new String[] { "client" });
		JSONObject obj = JsonResFactory.buildNetWithData(RESCODE.SUCCESS, JSONObject.fromObject(modify,config));
		obj.put("clientName",client.getName());
		obj.put("idNumber",client.getIdNumber());
		return obj.toString();
	}

	public String getPoint(int clientId) {
		Client client = clientDao.findById(clientId);
		if (client == null) {
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		List<Object[]> exists = pointDao.getPoint(client.getId());
		JsonConfig config = JsonResFactory.dateConfig();
		if (exists == null || exists.isEmpty()) {
			return JsonResFactory.buildOrg(RESCODE.NO_RECORD).toString();
		} else {
			List<PointBean> pointBeans = new ArrayList<>();
			for (Object[] exist : exists) {
				pointBeans.add(new PointBean(
						(int) Math.rint((Double) exist[1]), (Date) exist[0]));
			}
			return JsonResFactory.buildOrg(RESCODE.SUCCESS,
					Constants.RESPONSE_POINT_KEY,
					JSONArray.fromObject(pointBeans, config)).toString();
		}
	}

	// point、wxuser、discount,复用性极差
	public String getWXUser(String openId) {
		WXUser wxUser = wxUserDao.findUserByOpenId(openId);
		if (wxUser == null) {
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		Client client = clientDao.findByPhone(wxUser.getPhone());
		if (client == null) {
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		// Object favour =
		// favourRemainingsDao.getCountByClientId(client.getId());
		Object point = pointDao.getSumPoint(client.getId());
		JSONObject obj = new JSONObject();
		// if(favour==null){
		// favour=0;
		// }
		if (point == null)
			point = 0;
		else
			point = (int) Math.rint((double) (point));
		// obj.put(Constants.RESPONSE_FAVOUR_KEY, favour);
		obj.put("point", point);
		obj.put(Constants.RESPONSE_WXUSER_KEY, wxUser);

		return JsonResFactory.buildNetWithData(RESCODE.SUCCESS, obj).toString();
	}

	public String listCard(int clientId) {
		List<Card> Cards = cardDao.listCardByClientId(clientId);
		JsonConfig config = JsonResFactory.dateConfig();
		JSONArray array = JSONArray.fromObject(Cards, config);
		return JsonResFactory.buildNetWithData(RESCODE.SUCCESS, array)
				.toString();
	}

	public String listCar(int clientId) {
		List<Car> cars = carDao.findByClientId(clientId);
		JsonConfig config = JsonResFactory.dateConfig();
		config.registerPropertyExclusions(Car.class, new String[] { "client" });
		// JsonPropertyFilter filter = new JsonPropertyFilter();
		// config.setJsonPropertyFilter(filter);
		List<Object> list = new ArrayList<Object>();

		for (Car car : cars) {

			JSONObject obj = new JSONObject();
			Date today = new Date();
			long result = 0;
			if (car.getInsuranceStarttime() == null) {
				result = 365;
			} else {
				long intervalMilli = today.getTime()
						- car.getInsuranceStarttime().getTime();
				result = 365 - (intervalMilli / (24 * 60 * 60 * 1000));
				System.out.println(">>>>>" + result);
			}
			obj.put("car", JSONObject.fromObject(car, config));
			obj.put("time", result);
			list.add(obj);
		}
		JSONArray.fromObject(list, config);
		return JsonResFactory.buildNetWithData(RESCODE.SUCCESS, list)
				.toString();
	}
	
	public String carDetail(int carId){
		Car exist = carDao.findById(carId);
		if(exist == null){
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND).toString();
		}
		else{
			Client client = exist.getClient();
			JsonConfig config = JsonResFactory.dateConfig();
			config.registerPropertyExclusions(Car.class, new String[] { "client" });
			JSONObject obj = JsonResFactory.buildNetWithData(RESCODE.SUCCESS, JSONObject.fromObject(exist,config));
			obj.put("clientName",client.getName());
			obj.put("idNumber",client.getIdNumber());
			return obj.toString();
		}
	}

	public String addWXUser(WXUser wxUser) {
		WXUser oldWXUser = wxUserDao.findUserByPhone(wxUser.getPhone());
		if(oldWXUser!=null){
			if(wxUser.getName()!=null)
				oldWXUser.setName(wxUser.getName());
			oldWXUser.setBirthday(wxUser.getBirthday());
			oldWXUser.setGender(wxUser.getGender());
			wxUserDao.save(oldWXUser);
		}
		else
			wxUserDao.updateUser(oldWXUser);
		return JsonResFactory.buildOrg(RESCODE.SUCCESS).toString();
	}
}
