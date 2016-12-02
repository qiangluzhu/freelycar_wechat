package com.geariot.platform.yi.dao;

import java.util.HashMap;
import java.util.List;

import com.geariot.platform.yi.entities.Reservation;

public interface ReservationDao {

	public List<Reservation> getByPerson(Reservation r, int start, int number);

	public HashMap<String, Integer> statistic();

}
