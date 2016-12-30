package com.geariot.platform.yi.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.geariot.platform.yi.dao.ReservationDao;
import com.geariot.platform.yi.entities.Reservation;
import com.geariot.platform.yi.utils.Constants;
import com.geariot.platform.yi.utils.query.QueryUtils;
import com.geariot.platform.yi.utils.query.ReservationAndQueryCreator;

@Repository
public class ReservationDaoImpl implements ReservationDao {
	
	private static final Logger log = Logger.getLogger(ReservationDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Reservation> getByPerson(Reservation r, int start, int number) {
		StringBuffer basicQueryStr;
		basicQueryStr = new StringBuffer("from Reservation");
		ReservationAndQueryCreator creator = new ReservationAndQueryCreator(r);
		String creatorStr = creator.createStatement(0);
		Query query = QueryUtils.createLocalQuery(getSession(), basicQueryStr,
				creatorStr);
		query.setCacheable(Constants.SELECT_CACHE);
		query.setFirstResult(start);// 设置起始行
		query.setMaxResults(number);// 每页条数
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashMap<String, Integer> statistic() {
		
		int monthArr[]=new int[4];
		Date beginDate0 = new Date();
		Calendar date0 = Calendar.getInstance();
		date0.setTime(beginDate0);
		int month0=date0.get(Calendar.MONTH)+1;
		if(month0>4){
			for(int i=0;i<4;i++){
				monthArr[i]=month0-3+i;
			}
		}else{
			for(int i=0;i<4;i++){
				monthArr[i]=9+month0+i;
				if(monthArr[i]>12){
					monthArr[i]=monthArr[i]-12;
				}
			}
		}
		int[] countArr=new int[4];
		
		try {
			SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
			Date todayDate = new Date();
			Calendar date = Calendar.getInstance();
			date.setTime(todayDate);
			date.set(Calendar.MONTH, date.get(Calendar.MONTH) + 1);
			date.set(Calendar.DATE, 1);
			Date endDate = dft.parse(dft.format(date.getTime()));
			date.setTime(todayDate);
			int month = date.get(Calendar.MONTH);
			if (month < 4) {
				date.set(Calendar.MONTH, 9 + date.get(Calendar.MONTH));
				date.set(Calendar.YEAR, date.get(Calendar.YEAR - 1));
			} else {
				date.set(Calendar.MONTH, date.get(Calendar.MONTH) - 3);
				date.set(Calendar.YEAR, date.get(Calendar.YEAR));
			}
			date.set(Calendar.DATE, 1);
			Date startDate = dft.parse(dft.format(date.getTime()));
//			String startStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(startDate);
//			String endStr = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(endDate);
//			System.out.println(startStr+" "+endStr);
			
			String hql = "from Reservation where createTime >= :startTime and createTime < :endTime";
			List<Reservation> billArr=getSession().createQuery(hql)
					  .setTimestamp("startTime", startDate).setTimestamp("endTime", endDate).list();
			
			for(int i=0;i<billArr.size();i++){
				Reservation r=billArr.get(i);
				Date d=r.getCreateTime();
				date.setTime(d);
				int dMonth=date.get(Calendar.MONTH)+1;
				for(int j=0;j<4;j++){
					if(dMonth==monthArr[j]){
						countArr[j]++;
						break;
					}
				}
			}
			HashMap<String,Integer> map=new HashMap<String,Integer>();
			for(int i=0;i<4;i++){
				map.put(monthArr[i]+"月", countArr[i]);
			}
			return map;
		} catch (ParseException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Reservation getById(String id) {
		String hql = "from Reservation where id=:id";
		List<Reservation> list = getSession().createQuery(hql)
				.setString("id", id).setCacheable(Constants.SELECT_CACHE)
				.list();
		if(list.size()!=0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public int getSize(Reservation c) {
		StringBuffer basicQueryStr;
		basicQueryStr = new StringBuffer("from Reservation");
		ReservationAndQueryCreator creator = new ReservationAndQueryCreator(c);
		String creatorStr = creator.createStatement(0);
		Query query = QueryUtils.createLocalQuery(getSession(), basicQueryStr,
				creatorStr);
		query.setCacheable(Constants.SELECT_CACHE);
		return query.list().size();
	}

}
