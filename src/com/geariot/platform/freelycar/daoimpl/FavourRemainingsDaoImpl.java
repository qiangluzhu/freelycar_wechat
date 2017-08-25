package com.geariot.platform.freelycar.daoimpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.geariot.platform.freelycar.dao.FavourRemainingsDao;
import com.geariot.platform.freelycar.entities.FavourRemainings;
import com.geariot.platform.freelycar.utils.Constants;
import java.util.List;
@Repository
public class FavourRemainingsDaoImpl implements FavourRemainingsDao{
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return this.sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FavourRemainings> favourtByClientId(int clientId) {
		String hql = "from FavourRemainings where clientId = :clientId";
		return this.getSession().createQuery(hql).setInteger("clientId",clientId).list();
	}

}
