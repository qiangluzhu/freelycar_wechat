package com.geariot.platform.yi.dao.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.geariot.platform.yi.dao.UserDao;
import com.geariot.platform.yi.entities.Admin;
import com.geariot.platform.yi.entities.Community;
import com.geariot.platform.yi.entities.Reservation;
import com.geariot.platform.yi.entities.Technician;
import com.geariot.platform.yi.entities.UserAddress;

@Repository
public class UserDaoImpl implements UserDao{
	
	private static final Logger log = Logger.getLogger(UserDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public boolean register(String openId, String nickName) {
		try{
			String sql= "insert ignore into user(openId, nickName) values('" + openId + "','"+nickName+"')";
			getSession().createSQLQuery(sql).executeUpdate();//注意,插入要加上executeUpdate,否则插入不成功  
			return true;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean add(UserAddress c) {
		try{
			String openId=c.getOpenId();
			UserAddress u = getAddById(openId);
			if(u==null){
				c.setIsSelected(1);
			}else{
				c.setIsSelected(0);
			}
			getSession().save(c);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
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
			log.error(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean modify(UserAddress c) {
		try{
			getSession().update(c);
			return true;
		}catch(Exception e){
			log.error(e.getMessage());
			return false;
		}
	}

	@Override
	public UserAddress getAddById(String id) {
		try {
			String hql = "from UserAddress where openId=:id and isSelected=1";
			UserAddress add= (UserAddress) getSession().createQuery(hql).setString("id", id).uniqueResult();
			return add;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return null;
		}
	}
	

	@Override
	public UserAddress getAddByAddId(String id) {
		try {
			String hql = "from UserAddress where id=:id";
			UserAddress add= (UserAddress) getSession().createQuery(hql).setString("id", id).uniqueResult();
			return add;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
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
			log.error(e.getMessage());
			return null;
		}
	}

	@Override
	public boolean reserve(Reservation r) {
		try{
			Query query0 = getSession().createQuery("from Technician");
			query0.setFirstResult(0); // 开始记录
			query0.setMaxResults(1); // 查询多少条
			Technician t = (Technician) query0.uniqueResult();
			r.setOrderOpenId(t.getOpenId());
			r.setOrderPerson(t.getName());
			r.setOrderPhone(t.getPhone());
			getSession().save(r);
			String tOpenid=r.getOrderOpenId();
//			String tOpenid="oBaSqs929zqFraeZy2YXWeqAQJ7o";
			JSONObject data = packJsonmsg(r.getRperson(),r.getPhone(),r.getOnTime(),"成功");
			String url="www.geariot.com/freelycar/mechanic.html";
			sendWechatmsgToUser(tOpenid,"L9Y9HHSN96_maQXSYUyYAbZf_fMeHB2EsR1hk2Eft0s",url,"",data);
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
			String hql = "from Reservation where ropenId=:openId  order by createTime desc";
			Query query0 = getSession().createQuery(hql).setString("openId", openId);
			query0.setFirstResult(start); // 开始记录
			query0.setMaxResults(number); // 查询多少条
			List<Reservation> addList=query0.list();
			return addList;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
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
	/**
     * @method packJsonmsg
     * @描述: TODO(封装微信模板:订单支付成功) 
     * @参数@param cname  客户姓名
     * @参数@param cphone  客户电话
     * @参数@param rtime  预约时间
     * @参数@param result  预约结果
     * @参数@return
     * @返回类型：JSONObject
     * @添加时间 2016-1-5下午03:38:54
     * @作者：***
     */
    public static JSONObject packJsonmsg(String cname, String cphone, Date rtime, String result){
        JSONObject json = new JSONObject();
        JSONObject jsonFirst = new JSONObject();
		jsonFirst.put("value", "客户预约通知");
		jsonFirst.put("color", "#173177");
		json.put("first", jsonFirst);
		
		JSONObject jsonName = new JSONObject();
		jsonName.put("value", cname);
		jsonName.put("color", "#173177");
		json.put("keyword1", jsonName);
		
		JSONObject jsonPhone = new JSONObject();
		jsonPhone.put("value", cphone);
		jsonPhone.put("color", "#173177");
		json.put("keyword2", jsonPhone);
		
		JSONObject jsonTime = new JSONObject();
		jsonTime.put("value", rtime);
		jsonTime.put("color", "#173177");
		json.put("keyword3", jsonTime);
		
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("value", result);
		jsonResult.put("color", "#173177");
		json.put("keyword4", jsonResult);
		
		JSONObject jsonRemark = new JSONObject();
		jsonRemark.put("value", "点击查看预约详情");
		jsonRemark.put("color", "#173177");
		json.put("remark", jsonRemark);
        return json;
    }
    
    /**
     * @method sendWechatmsgToUser
     * @描述: TODO(发送模板信息给用户) 
     * @参数@param touser  用户的openid
     * @参数@param templat_id  信息模板id
     * @参数@param url  用户点击详情时跳转的url
     * @参数@param topcolor  模板字体的颜色
     * @参数@param data  模板详情变量 Json格式
     * @参数@return
     * @返回类型：String
     * @添加时间 2016-1-5上午10:38:45
     * @作者：***
     */
    public static String sendWechatmsgToUser(String touser, String templat_id, String clickurl, String topcolor, JSONObject data){
        String tmpurl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
        String token = getAccess_token("wxfd188f8284ee297b","8e7a35d582d6b9fe771ac56462029321");  //微信凭证，access_token
        String url = tmpurl.replace("ACCESS_TOKEN", token);
        JSONObject json = new JSONObject();
        json.put("touser", touser);
		json.put("template_id", templat_id);
		json.put("url", clickurl);
		json.put("topcolor", topcolor);
		json.put("data", data);
        String result = httpsRequest(url, "POST", json.toString());
        try {
            JSONObject resultJson = new JSONObject(result);
            String errmsg = (String) resultJson.get("errmsg");
            if(!"ok".equals(errmsg)){  //如果为errmsg为ok，则代表发送成功，公众号推送信息给用户了。
                return "error";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "success";
    }
    
    public static String httpsRequest(String requestUrl, String requestMethod, String outputStr){
        try {
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }
            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            return buffer.toString();
        } catch (ConnectException ce) {
           log.error("连接超时：{}");
        } catch (Exception e) {
        	log.error("https请求异常：{}");
        }
        return null;
    }
    
    public  static String getAccess_token(String appId,String appSecret) {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + appId+ "&secret=" + appSecret;
        String accessToken = null;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet
                    .openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = new JSONObject(message);
            accessToken = demoJson.getString("access_token");
            log.info(accessToken);
            is.close();
        } catch (Exception e) {
        	log.error(e.getMessage());
            e.printStackTrace();
        }
        return accessToken;
    }

	@Override
	public boolean adminLogin(String username, String password) {
		try {
			String hql = "from Admin where username=:username and password=:password";
			Admin admin = (Admin) getSession().createQuery(hql).setCacheable(true).setString("username", username).setString("password", password).uniqueResult();
			if(admin!=null){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean setDefaultAddress(String addId,String userId) {
		try{
			String hql0 = "from UserAddress where openId=:userId";
			Query query0 = getSession().createQuery(hql0).setString("userId", userId);
			List<UserAddress> addList=query0.list();
			for(int i=0;i<addList.size();i++){
				addList.get(i).setIsSelected(0);
			}
			String hql = "from UserAddress where id=:addId";
			UserAddress add= (UserAddress) getSession().createQuery(hql).setString("addId", addId).uniqueResult();
			add.setIsSelected(1);
			return true;
		}catch(Exception e){
			log.error(e.getMessage());
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getrSize(String openId) {
		String hql = "from Reservation where ropenId=:openId";
		Query query0 = getSession().createQuery(hql).setString("openId", openId);
		List<Reservation> addList=query0.list();
		return addList.size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getcSize() {
		Query query0 = getSession().createQuery("from Community");
		List<Community> hList=query0.list();
		return hList.size();
	}
}
