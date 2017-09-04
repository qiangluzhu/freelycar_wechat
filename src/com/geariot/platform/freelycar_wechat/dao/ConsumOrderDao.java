
package com.geariot.platform.freelycar_wechat.dao;

import java.util.Date;
import java.util.List;

import com.geariot.platform.freelycar_wechat.entities.ConsumOrder;

public interface ConsumOrderDao {
	
	void save(ConsumOrder consumOrder);

	List<ConsumOrder> list(int from, int pageSize);

	long getCount();
	
	long getCountByClientId(int clientId);
	
	List<ConsumOrder> listByClientId(int clientId,int from, int pageSize);

	ConsumOrder findById(String consumOrderId);

	List<ConsumOrder> query(String andCondition, int from, int pageSize);
	
	long getQueryCount(String andCondition);
	
	List<String> getConsumOrderIdsByStaffId(int staffId);
	
	List<ConsumOrder> findWithClientId(int clientId);
	
	List<ConsumOrder> findByMakerAccount(String account);
	
	List<ConsumOrder> findByPickCarStaffId(int staffId);
	
	long countInventoryInfoByIds(List<String> inventoryIds);
	
	void removeStaffInConsumOrderStaffs(int staffId);
	
	List<Object[]> programNameToday();
	
	List<Object[]> programNameMonth();
	
	List<Object[]> programNameRange(Date startTime , Date endTime);

	void update(ConsumOrder consumOrder);
}

