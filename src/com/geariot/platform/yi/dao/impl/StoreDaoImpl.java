package com.geariot.platform.yi.dao.impl;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.geariot.platform.yi.dao.StoreDao;
import com.geariot.platform.yi.entities.Store;
import com.geariot.platform.yi.utils.Constants;
import com.geariot.platform.yi.utils.query.QueryUtils;
import com.geariot.platform.yi.utils.query.StoreAndQueryCreator;

@Repository
public class StoreDaoImpl implements StoreDao{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ServletContext context;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public boolean add(Store c,MultipartFile mf) {
		try{
			getSession().save(c);
			if (null != mf) {
				String baseUrl="D:\\upload\\store\\";
//				String baseUrl = context.getRealPath("") + "\\upload\\store\\";
				
				Path path = Paths.get(baseUrl);
				if(Files.notExists(path)){
					Files.createDirectories(path);
				}
				
				String fileName = mf.getOriginalFilename();
				if (!"".equals(fileName)) {
					// 获取后缀
					String suffix = fileName.substring(fileName.indexOf("."),
							fileName.length());
					String newName=c.getId()+suffix;
					c.setUrl(newName);
					try {
						mf.transferTo(new File(baseUrl + newName));
						return true;
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			return false;
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
	public boolean modify(Store c,MultipartFile mf) {
		try{
			getSession().update(c);
			if (null != mf) {
				String baseUrl="D:\\upload\\store\\";
//				String baseUrl = context.getRealPath("") + "\\upload\\store\\";
				Path path = Paths.get(baseUrl);
				if(Files.notExists(path)){
					Files.createDirectories(path);
				}
				
				
				String fileName = mf.getOriginalFilename();
				if (!"".equals(fileName)) {
					// 获取后缀
					String suffix = fileName.substring(fileName.indexOf("."),
							fileName.length());
					String newName=c.getId()+suffix;
					c.setUrl(newName);
					
					try {
						mf.transferTo(new File(baseUrl + newName));
					}catch(Exception e){
						e.printStackTrace();
						return false;
					}
				}
			}
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

	@Override
	public int getSize(Store c) {
		StringBuffer basicQueryStr;
		basicQueryStr = new StringBuffer("from Store");
		StoreAndQueryCreator creator = new StoreAndQueryCreator(c);
		String creatorStr = creator.createStatement(0);
		Query query = QueryUtils.createLocalQuery(getSession(), basicQueryStr,
				creatorStr);
		query.setCacheable(Constants.SELECT_CACHE);
		return query.list().size();
	}
	
	
}
