package com.geariot.platform.yi.dao;

import java.util.List;

import com.geariot.platform.yi.entities.Reservation;
import com.geariot.platform.yi.entities.Technician;

public interface TechnicianDao {

	boolean add(Technician c);

	boolean delete(String id);

	boolean modify(Technician c);

	List<Technician> find(Technician c, int start, int number);

	Technician login(String phone, String password);

	List<Reservation> getReservation(String tid, int start, int number);

}
