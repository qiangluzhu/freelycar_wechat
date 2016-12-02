package com.geariot.platform.yi.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.geariot.platform.yi.dao.CompanyDao;
import com.geariot.platform.yi.entities.Company;
import com.geariot.platform.yi.utils.Constants;
import com.geariot.platform.yi.utils.query.CompanyAndQueryCreator;
import com.geariot.platform.yi.utils.query.QueryUtils;

@Repository
public class CompanyDaoImpl implements CompanyDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Company> find(Company c,int start,int number) {
		StringBuffer basicQueryStr;
		basicQueryStr = new StringBuffer("from Company");
		CompanyAndQueryCreator creator = new CompanyAndQueryCreator(c);
		String creatorStr = creator.createStatement();
		Query query = QueryUtils.createLocalQuery(getSession(), basicQueryStr,
				creatorStr);
		query.setCacheable(Constants.SELECT_CACHE);
		query.setFirstResult(start);// 设置起始行
		query.setMaxResults(number);// 每页条数
		return query.list();
	}

	@Override
	public boolean add(Company c) {
		try{
			getSession().save(c);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

}
