package com.geariot.platform.freelycar.dao;

import java.util.List;

public interface PointDao {
	public List<Object[]> getPoint(int clientId);
}
