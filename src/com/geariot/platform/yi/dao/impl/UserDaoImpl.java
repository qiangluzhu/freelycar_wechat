package com.geariot.platform.yi.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.geariot.platform.yi.dao.UserDao;
import com.geariot.platform.yi.entities.Community;
import com.geariot.platform.yi.entities.Reservation;
import com.geariot.platform.yi.entities.User;
import com.geariot.platform.yi.entities.UserAddress;

@Repository
public class UserDaoImpl implements UserDao{

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public boolean register(String openId, String nickName) {
		try{
			User u=new User();
			u.setNickName(nickName);
			u.setOpenId(openId);
			getSession().save(u);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean add(UserAddress c) {
		try{
			getSession().save(c);
			String openId=c.getOpenId();
			String hql = "from User where openId=:id";
			List<User> user= getSession().createQuery(hql).setString("id", openId).list();
			if(user.size()>0){
				User u=user.get(0);
				u.setAddressId(c.getId());
				return true;
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(String addressId) {
		try {
			String hql = "delete from UserAddress where id = :id";
			int ex = getSession().createQuery(hql).setString("id", addressId)
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
	public boolean modify(UserAddress c) {
		try{
			getSession().update(c);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserAddress getAddById(String id) {
		try {
			String hql = "from User where openId=:id";
			List<User> user= getSession().createQuery(hql).setString("id", id).list();
			if(user.size()>0){
				User u=user.get(0);
				String hql1="from UserAddress where id=:addId";
				List<UserAddress> addList= getSession().createQuery(hql1).setString("addId", u.getAddressId()).list();
				if(addList.size()>0){
					return addList.get(0);
				}else{
					return null;
				}
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserAddress> getAll(String openId) {
		try {
			String hql = "from UserAddress where openId=:openId";
			List<UserAddress> addList= getSession().createQuery(hql).setString("openId", openId).list();
			return addList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean reserve(Reservation r) {
		try{
			getSession().save(r);
			String tOpenid=r.getOrderOpenId();
			
			//TODO 将预约推送给技师
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reservation> getRecord(String openId, int start, int number) {
		try {
			String hql = "from Reservation where ropenId=:openId";
			Query query0 = getSession().createQuery(hql).setString("openId", openId);
			query0.setFirstResult(start); // 开始记录
			query0.setMaxResults(10); // 查询多少条
			List<Reservation> addList=query0.list();
			return addList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Community> getCode(int start, int number) {
		Query query0 = getSession().createQuery("from Community");
		query0.setFirstResult(start); // 开始记录
		query0.setMaxResults(number); // 查询多少条
		List<Community> hList=query0.list();
		return hList;
	}

}
