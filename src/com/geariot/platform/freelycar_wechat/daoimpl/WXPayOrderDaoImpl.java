package com.geariot.platform.freelycar_wechat.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.geariot.platform.freelycar_wechat.dao.WXPayOrderDao;
import com.geariot.platform.freelycar_wechat.entities.WXPayOrder;
import com.geariot.platform.freelycar_wechat.utils.Constants;

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
		String hql = "from WXPayOrder where id = :wxPayOrderId";
		return (WXPayOrder) this.getSession().createQuery(hql).setString("wxPayOrderId", wxPayOrderId).setCacheable(Constants.SELECT_CACHE).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WXPayOrder> listByOpenId(String openId, int from, int number) {
		String hql = "from WXPayOrder where openId = :openId order by createDate desc";
		return this.getSession().createQuery(hql).setString("openId", openId)
				.setCacheable(Constants.SELECT_CACHE).list();
	}

	@Override
	public long getCountByOpenId(String openId) {
		String hql = "select count(*) from WXPayOrder where openId = :openId";
		return (long) this.getSession().createQuery(hql).setString("openId", openId).setCacheable(Constants.SELECT_CACHE).uniqueResult();
	}

}
