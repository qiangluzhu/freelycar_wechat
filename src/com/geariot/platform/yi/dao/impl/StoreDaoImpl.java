package com.geariot.platform.yi.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.geariot.platform.house.entities.House;
import com.geariot.platform.yi.dao.StoreDao;
import com.geariot.platform.yi.entities.Store;
import com.geariot.platform.yi.utils.Constants;
import com.geariot.platform.yi.utils.query.QueryUtils;
import com.geariot.platform.yi.utils.query.StoreAndQueryCreator;

@Repository
public class StoreDaoImpl implements StoreDao{
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public boolean add(Store c) {
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
			String hql = "delete from Store where id = :id";
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
	public boolean modify(Store c) {
		try{
			getSession().update(c);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Store> find(Store c, int start, int number) {
		StringBuffer basicQueryStr;
		basicQueryStr = new StringBuffer("from Store");
		StoreAndQueryCreator creator = new StoreAndQueryCreator(c);
		String creatorStr = creator.createStatement();
		Query query = QueryUtils.createLocalQuery(getSession(), basicQueryStr,
				creatorStr);
		query.setCacheable(Constants.SELECT_CACHE);
		query.setFirstResult(start);// 设置起始行
		query.setMaxResults(number);// 每页条数
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Store getById(String id) {
		String hql = "from Store where id=:id";
		List<Store> list = getSession().createQuery(hql)
				.setString("id", id).setCacheable(Constants.SELECT_CACHE)
				.list();
		if(list.size()!=0){
			return list.get(0);
		}
		return null;
	}
	
	
}
