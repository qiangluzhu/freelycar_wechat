package com.geariot.platform.yi.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.geariot.platform.yi.dao.ServiceTypeDao;
import com.geariot.platform.yi.entities.ServiceType;
import com.geariot.platform.yi.entities.Store;
import com.geariot.platform.yi.utils.Constants;
import com.geariot.platform.yi.utils.query.QueryUtils;
import com.geariot.platform.yi.utils.query.TypeQueryCreator;

@Repository
public class ServiceTypeDaoImpl implements ServiceTypeDao{
	
	private static final Logger log = Logger.getLogger(StoreDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public boolean add(ServiceType c) {
		try{
			getSession().save(c);
			return true;
		}catch(Exception e){
			log.error(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean delete(String id) {
		try {
			String hql = "delete from ServiceType where id = :id";
			int ex = getSession().createQuery(hql).setString("id", id)
					.executeUpdate();
			if (ex > 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean modify(ServiceType c) {
		try{
			getSession().update(c);
			return true;
		}catch(Exception e){
			log.error(e.getMessage());
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Store> find(ServiceType s) {
		StringBuffer basicQueryStr;
		basicQueryStr = new StringBuffer("from ServiceType");
		TypeQueryCreator creator = new TypeQueryCreator(s);
		String creatorStr = creator.createStatement(0);
		Query query = QueryUtils.createLocalQuery(getSession(), basicQueryStr,
				creatorStr);
		query.setCacheable(Constants.SELECT_CACHE);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ServiceType getById(String id) {
		String hql = "from ServiceType where id=:id";
		List<ServiceType> list = getSession().createQuery(hql)
				.setString("id", id).setCacheable(Constants.SELECT_CACHE)
				.list();
		if(list.size()!=0){
			return list.get(0);
		}
		return null;
	}

}
