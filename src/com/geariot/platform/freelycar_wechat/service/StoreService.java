package com.geariot.platform.freelycar_wechat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geariot.platform.freelycar_wechat.dao.StoreDao;

/**
 * @author mxy940127
 *
 */

@Service
@Transactional
public class StoreService {

	@Autowired
	private StoreDao storeDao;
}
