package com.geariot.platform.yi.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.geariot.platform.yi.dao.TechnicianDao;
import com.geariot.platform.yi.entities.Reservation;
import com.geariot.platform.yi.entities.Technician;
import com.geariot.platform.yi.utils.Constants;
import com.geariot.platform.yi.utils.query.QueryUtils;
import com.geariot.platform.yi.utils.query.TechnicianAndQueryCreator;

@Repository
public class TechnicianDaoImpl implements TechnicianDao{

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	
	@Override
	public boolean add(Technician c) {
		try{
			getSession().save(c);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(String id) {
		try {
			String hql = "delete from Technician where id = :id";
			int ex = getSession().createQuery(hql).setString("id", id)
					.executeUpdate();
			if (ex > 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean modify(Technician c) {
		try{
			getSession().update(c);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Technician> find(Technician c, int start, int number) {
		StringBuffer basicQueryStr;
		basicQueryStr = new StringBuffer("from Technician");
		TechnicianAndQueryCreator creator = new TechnicianAndQueryCreator(c);
		String creatorStr = creator.createStatement(0);
		Query query = QueryUtils.createLocalQuery(getSession(), basicQueryStr,
				creatorStr);
		query.setCacheable(Constants.SELECT_CACHE);
		query.setFirstResult(start);// 设置起始行
		query.setMaxResults(number);// 每页条数
		return query.list();
	}

	@Override
	public Technician login(String phone, String password) {
		try {
			
			String hql = "from Technician where phone=:phone and password=:psw";
			Technician user = (Technician) getSession().createQuery(hql).setCacheable(true).setString("phone", phone).setString("psw", password).uniqueResult();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reservation> getReservation(String tid, int start, int number) {
		String hql="from Reservation where orderOpenId=:tid";
		Query query0 = getSession().createQuery(hql).setString("tid", tid);
		query0.setFirstResult(start); // 开始记录
		query0.setMaxResults(number); // 查询多少条
		List<Reservation> rList=query0.list();
		return rList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Technician getById(String id) {
		String hql = "from Technician where id=:id";
		List<Technician> list = getSession().createQuery(hql)
				.setString("id", id).setCacheable(Constants.SELECT_CACHE)
				.list();
		if(list.size()!=0){
			return list.get(0);
		}
		return null;
	}


	@Override
	public int gettSize(Technician c) {
		StringBuffer basicQueryStr;
		basicQueryStr = new StringBuffer("from Technician");
		TechnicianAndQueryCreator creator = new TechnicianAndQueryCreator(c);
		String creatorStr = creator.createStatement(0);
		Query query = QueryUtils.createLocalQuery(getSession(), basicQueryStr,
				creatorStr);
		query.setCacheable(Constants.SELECT_CACHE);
		return query.list().size();
	}


	@SuppressWarnings("unchecked")
	@Override
	public int getrSize(String tid) {
		String hql="from Reservation where orderOpenId=:tid";
		Query query0 = getSession().createQuery(hql).setString("tid", tid);
		List<Reservation> rList=query0.list();
		return rList.size();
	}

}
