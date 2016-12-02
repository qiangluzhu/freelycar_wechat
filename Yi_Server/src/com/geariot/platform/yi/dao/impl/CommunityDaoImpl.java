package com.geariot.platform.yi.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.geariot.platform.yi.dao.CommunityDao;
import com.geariot.platform.yi.entities.Community;
import com.geariot.platform.yi.utils.Constants;
import com.geariot.platform.yi.utils.query.CommunityAndQueryCreator;
import com.geariot.platform.yi.utils.query.QueryUtils;

@Repository
public class CommunityDaoImpl implements CommunityDao{

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public boolean add(Community c) {
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
			String hql = "delete from Community where id = :id";
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
	public boolean modify(Community c) {
		try{
			getSession().update(c);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Community> find(Community c,int start,int number) {
		StringBuffer basicQueryStr;
		basicQueryStr = new StringBuffer("from Community");
		CommunityAndQueryCreator creator = new CommunityAndQueryCreator(c);
		String creatorStr = creator.createStatement();
		Query query = QueryUtils.createLocalQuery(getSession(), basicQueryStr,
				creatorStr);
		query.setCacheable(Constants.SELECT_CACHE);
		query.setFirstResult(start);// 设置起始行
		query.setMaxResults(number);// 每页条数
		return query.list();
	}

}
