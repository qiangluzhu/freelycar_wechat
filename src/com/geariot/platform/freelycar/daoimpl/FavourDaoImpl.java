package com.geariot.platform.freelycar.daoimpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.geariot.platform.freelycar.dao.FavourDao;
import com.geariot.platform.freelycar.entities.Favour;
@Repository
public class FavourDaoImpl implements FavourDao{
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session getSession(){
		return this.sessionFactory.getCurrentSession();
	}
	
	@Override
	public Favour findById(int id) {
		String hql = "from Favour where id= :id";
		return (Favour) this.getSession().createQuery(hql).setInteger("id",id)
				.uniqueResult();
	}

}
