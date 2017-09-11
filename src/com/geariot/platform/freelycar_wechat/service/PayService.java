package com.geariot.platform.freelycar_wechat.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.geariot.platform.freelycar_wechat.utils.query.FavourOrderBean;
import com.geariot.platform.freelycar_wechat.wxutils.IdentifyOrder;
import com.geariot.platform.freelycar_wechat.utils.JsonResFactory;
import com.geariot.platform.freelycar_wechat.utils.Constants;
import com.geariot.platform.freelycar_wechat.utils.DateHandler;
import com.geariot.platform.freelycar_wechat.dao.*;
import com.geariot.platform.freelycar_wechat.entities.*;
import com.geariot.platform.freelycar_wechat.model.RESCODE;
import com.geariot.platform.freelycar_wechat.utils.*;

@org.springframework.stereotype.Service
@Transactional
public class PayService {
	
	@Autowired
	private WXUserDao wxUserDao;
	@Autowired
	private ServiceDao serviceDao;
	@Autowired
	private WXPayOrderDao wxPayOrderDao;
	@Autowired
	private ConsumOrderDao consumOrderDao;
	@Autowired
	private ClientDao clientDao;
	@Autowired
	private IncomeOrderDao incomeOrderDao;
	@Autowired
	private CardDao cardDao;
	
	private static final Logger log = LogManager.getLogger(PayService.class);
	
	
	//创建card订单	
	public String createCardOrder(String openId,double totalPrice,
			int serviceId){
		log.debug("create new order");
		WXPayOrder wxPayOrder = buildBasivOrders(openId, totalPrice);
		log.debug("id" + wxPayOrder.getId() + "总金额" + wxPayOrder.getTotalPrice() + "openId" + wxPayOrder.getOpenId() +"Date" + wxPayOrder.getCreateDate());
		WXUser wxUser =  wxUserDao.findUserByOpenId(openId);
		Service service = serviceDao.findServiceById(serviceId);
		wxPayOrder.setService(service);
		wxPayOrder.setProductName(service.getName());
		Map<String, Object> obj = new HashMap<String, Object>();
		//JSONObject obj = new JSONObject();
		obj.put(Constants.RESPONSE_WXUSER_KEY, wxUser);
		obj.put(Constants.RESPONSE_WXORDER_KEY,wxPayOrder);
		wxPayOrderDao.saveWXPayOrder(wxPayOrder);
		JsonConfig config = JsonResFactory.dateConfig();
		return JsonResFactory.buildNetWithData(RESCODE.SUCCESS,
				net.sf.json.JSONObject.fromObject(obj, config)).toString();
	}
	
	//create favour order
	public String createFavourOrder(FavourOrderBean favourOrderBean){
		String openId = favourOrderBean.getOpenId();
		double totalPrice = favourOrderBean.getTotalPrice();
		Set<FavourToOrder> favours = favourOrderBean.getFavours();
		WXPayOrder wxPayOrder = buildBasivOrders(openId, totalPrice);
		WXUser wxUser =  wxUserDao.findUserByOpenId(openId);
		String productName = null;
		for(FavourToOrder favour : favours)
			productName += favour.getFavour().getName()+"*"+favour.getCount()+",";
		wxPayOrder.setProductName(productName);
		wxPayOrder.setFavours(favours);
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put(Constants.RESPONSE_WXUSER_KEY, wxUser);
		obj.put(Constants.RESPONSE_WXORDER_KEY,wxPayOrder);
		wxPayOrderDao.saveWXPayOrder(wxPayOrder);
		JsonConfig config = JsonResFactory.dateConfig();
		return JsonResFactory.buildNetWithData(RESCODE.SUCCESS,net.sf.json.JSONObject.fromObject(obj, config)).toString();
		
	}
	
	
	private WXPayOrder buildBasivOrders(String openId,double totalPrice){
		WXPayOrder wxPayOrder = new WXPayOrder();
		wxPayOrder.setId(IDGenerator.generate(IDGenerator.WX_CONSUM));
		System.out.println(">>>>"+IDGenerator.generate(IDGenerator.WX_CONSUM));
		wxPayOrder.setCreateDate(new Date());
		wxPayOrder.setOpenId(openId);
		wxPayOrder.setPayMethod(Constants.PAY_BY_WX);
		wxPayOrder.setPayState(Constants.PAY_UNPAY);
		wxPayOrder.setTotalPrice(totalPrice);
		return wxPayOrder;
	}

	public org.json.JSONObject paySuccess(String orderId) {
		log.debug("付款成功，进入paySuccess后续处理。");
		float amount;
		int clientId;
		String licensePlate = null;
		Date payDate = new Date();
		int payMethod = Constants.PAY_BY_WX;
		String programName;
		if(IdentifyOrder.identify(orderId)){
		ConsumOrder order = null;
			synchronized(PayService.class)
			{
			order = consumOrderDao.findById(orderId);
			if(order.getState() >= 1)
				{
				log.debug("已处理微信回调，订单已处理。直接返回成功。");
				org.json.JSONObject res = new org.json.JSONObject();
				res.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
				res.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
				return res;
				}
			order.setPayState(1); // 支付状态
			amount=(float) order.getTotalPrice();
			clientId = order.getClientId();
			licensePlate = order.getLicensePlate();
			programName = order.getProgramName();
			}
		
		}
		else{
			WXPayOrder order = null;
			synchronized(PayService.class)
			{
				order = wxPayOrderDao.findById(orderId);
				if(order.getPayState() >= 1)
					{
					log.debug("已处理微信回调，订单已处理。直接返回成功。");
					org.json.JSONObject res = new org.json.JSONObject();
					res.put(Constants.RESPONSE_CODE_KEY, RESCODE.SUCCESS);
					res.put(Constants.RESPONSE_MSG_KEY, RESCODE.SUCCESS.getMsg());
					return res;
					}
				order.setPayState(1); // 支付状态
				amount=(float) order.getTotalPrice();
				clientId = clientDao.findByPhone(wxUserDao.findUserByOpenId(order.getOpenId()).getPhone()).getId();
				programName = Constants.WX_CARDANDFAVOUR;
				//更新card表和favour表
				org.json.JSONObject result;
				Service service = order.getService();
				List<FavourToOrder> favourToOrders = new ArrayList<FavourToOrder>(order.getFavours());
				if(service!=null){
					Card card = new Card();
					card.setPayDate(payDate);
					card.setService(service);
					card.setExpirationDate(getExpiration(service,payDate));
					card.setPayMethod(payMethod);
					card.setCardNumber(IDGenerator.generate(IDGenerator.WX_CARD));
					result = buyCard(clientId,card);
				}
				else{
					for(FavourToOrder favourToOrder:favourToOrders){
						FavourProjectRemainingInfo favourProjectRemainingInfo = new FavourProjectRemainingInfo();
						favourToOrder.getFavour();
					}
				}
			IncomeOrder incomeOrder = new IncomeOrder();
			incomeOrder.setAmount(amount);
			incomeOrder.setClientId(clientId);
			incomeOrder.setLicensePlate(licensePlate);
			incomeOrder.setPayDate(payDate);
			incomeOrder.setPayMethod(payMethod);
			incomeOrder.setProgramName(programName);
			incomeOrderDao.save(incomeOrder);
			
			}
		
		}
		return null;
}	
	public Date getExpiration(Object object,Date payDate){
		Calendar curr = Calendar.getInstance();
		curr.setTime(payDate);
		if(object instanceof Service)
			curr.set(Calendar.YEAR,curr.get(Calendar.YEAR)+((Service)object).getValidTime());
		if(object instanceof Favour)
			curr.set(Calendar.YEAR,curr.get(Calendar.YEAR)+((Favour)object).getValidTime());
		Date date=curr.getTime(); 
		return date;
	}
	
	public	org.json.JSONObject buyTickit(int clientId,Ticket tickiet){
		Client client = clientDao.findById(clientId);
		return null;
	}
	
	public org.json.JSONObject buyCard(int clientId, Card card) {
		if(card.getCardNumber() == null || card.getCardNumber().isEmpty() || card.getCardNumber().trim().isEmpty()){
			String cardNumber = String.valueOf((int)((Math.random()*9+1)*100000));
			while(cardDao.findByCardNumber(cardNumber) != null){
				cardNumber = String.valueOf((int)((Math.random()*9+1)*100000));
			}
			card.setCardNumber(cardNumber);
		}
		else if(cardDao.findByCardNumber(card.getCardNumber()) != null){
			return JsonResFactory.buildOrg(RESCODE.CARDNUMBER_EXIST);
		}
		Client client = clientDao.findById(clientId);
		Service service = this.serviceDao.findServiceById(card.getService().getId());
		if(client == null || service == null){
			return JsonResFactory.buildOrg(RESCODE.NOT_FOUND);
		}
		//将优惠券信息添加到客户卡列表中
		Set<Ticket> tickets = new HashSet<>();
		for(FavourInfos favourInfos : service.getFavourInfos()){
			Set<FavourProjectRemainingInfo> remainingInfos = new HashSet<>();
			Ticket ticket = new Ticket();
			ticket.setFavour(favourInfos.getFavour());
			ticket.setExpirationDate(DateHandler.addValidMonth(DateHandler.toCalendar(new Date()), favourInfos.getFavour().getValidTime()).getTime());;
			FavourProjectRemainingInfo projectRemainingInfo = new FavourProjectRemainingInfo();
			for(FavourProjectInfos projectInfos : favourInfos.getFavour().getSet()){
				projectRemainingInfo.setProject(projectInfos.getProject());
				projectRemainingInfo.setRemaining(projectInfos.getTimes() * favourInfos.getCount());
				remainingInfos.add(projectRemainingInfo);
				log.debug("***************************"+projectRemainingInfo.toString());
			}
			ticket.setRemainingInfos(remainingInfos);
			tickets.add(ticket);
		}
		Set<Ticket> set = client.getTickets();
		if(set == null){
			set = new HashSet<>();
			client.setTickets(tickets);
		}
		for(Ticket add : tickets){
			set.add(add);
		}
		//将服务信息次数复制到卡中
		Set<CardProjectRemainingInfo> cardInfos = new HashSet<>();
		for(ServiceProjectInfo info : service.getProjectInfos()){
			CardProjectRemainingInfo cardInfo = new CardProjectRemainingInfo();
			cardInfo.setProject(info.getProject());
			cardInfo.setRemaining(info.getTimes());
			cardInfos.add(cardInfo);
			log.debug("***************************"+cardInfo.toString());
		}
		card.setProjectInfos(cardInfos);
		log.debug("***************************"+card.toString());
		//将新增卡增加到客户卡列表中
		Set<Card> cards = client.getCards();
		if(cards == null){
			cards = new HashSet<>();
			client.setCards(cards);
		}
		card.setPayDate(new Date());
		Calendar exp = Calendar.getInstance();
		exp.setTime(new Date());
		exp.add(Calendar.YEAR, service.getValidTime());
		card.setExpirationDate(exp.getTime());
		cards.add(card);
		//创建新的收入订单并保存
		IncomeOrder order = new IncomeOrder();
		order.setAmount(service.getPrice());
		order.setClientId(clientId);
		order.setLicensePlate(null);
		order.setMember(true);
		order.setPayDate(new Date());
		order.setProgramName(Constants.CARD_PROGRAM);
		order.setPayMethod(card.getPayMethod());
		/*Admin admin = this.adminDao.findAdminById(card.getOrderMaker().getId());
		order.setStaffNames(admin.getStaff().getName());*/
		this.incomeOrderDao.save(order);
		//更新客户的消费次数与消费情况信息。
		client.setConsumTimes(client.getConsumTimes() + 1);
		client.setConsumAmout(client.getConsumAmout() + order.getAmount());
		client.setLastVisit(new Date());
		client.setIsMember(true);
		return JsonResFactory.buildOrg(RESCODE.SUCCESS);
	}
}

