package com.geariot.platform.freelycar_wechat.dao;

import java.util.List;

import com.geariot.platform.freelycar_wechat.entities.FavourRemainings;
public interface FavourRemainingsDao {
	public List<FavourRemainings> favourtByClientId(int clientId);

}
