package com.geariot.platform.freelycar.daoimpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.geariot.platform.freelycar.dao.WXUserDao;
import com.geariot.platform.freelycar.entities.Client;
import com.geariot.platform.freelycar.entities.WXUser;
import com.geariot.platform.freelycar.utils.Constants;
@Repository
public class WXUserDaoImpl implements WXUserDao{
    @Autowired
    private SessionFactory sessionFactory;
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
    
	@Override
	public WXUser findUserByOpenId(String openId) {
    	String hql = "from WXUser where openid= :openid ";
    	//(Client) this.getSession().createQuery(hql).setString("phone", phone).uniqueResult()
		WXUser wxuser= (WXUser) getSession().createQuery(hql).setString("openid", openId).uniqueResult();
		return wxuser;
	}

	@Override
	public WXUser findUserByPhone(String phone) {
    	String hql = "from WXUser where phone = :phone";
		return (WXUser) getSession().createQuery(hql).setString("phone", phone).uniqueResult();
	}

	@Override
	public void saveOrUpdateUser(WXUser wxUser) {
		// update wxuser
		
	}

	@Override
	public boolean deleteUser(String openId) {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
//	public boolean deleteUser(String openId) {
//		String sql="update wxuser set openId=null,headimgurl=null where openId=:openId";
//		this.getSession().createSQLQuery(sql).setString("openId", openId).setCacheable(Constants.SELECT_CACHE).uniqueResult();
//	}

}
