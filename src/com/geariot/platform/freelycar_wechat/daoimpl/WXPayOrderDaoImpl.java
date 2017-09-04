package com.geariot.platform.freelycar_wechat.daoimpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.geariot.platform.freelycar_wechat.dao.WXPayOrderDao;
import com.geariot.platform.freelycar_wechat.entities.WXPayOrder;

@Repository
public class WXPayOrderDaoImpl implements WXPayOrderDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Session getSession(){
		return this.sessionFactory.getCurrentSession();
	}
	
	@Override
	public void saveWXPayOrder(WXPayOrder wxPayOrder) {
		this.getSession().saveOrUpdate(wxPayOrder);
	}

	@Override
	public WXPayOrder findById(String wxPayOrderId) {
		String hql = "from WXPayOrder where wxPayOrderId = :wxPayOrderId";
		return (WXPayOrder) this.getSession().createQuery(hql).setString("wxPayOrderId", wxPayOrderId).uniqueResult();
	}

}
