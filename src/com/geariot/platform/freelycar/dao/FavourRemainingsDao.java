package com.geariot.platform.freelycar.dao;

import com.geariot.platform.freelycar.entities.FavourRemainings;
import java.util.List;
public interface FavourRemainingsDao {
	public List<FavourRemainings> favourtByClientId(int clientId);

}
