package com.geariot.platform.yi.dao;

import java.util.List;

import com.geariot.platform.yi.entities.Company;

public interface CompanyDao {

	public List<Company> find(Company c, int start, int number);

	public boolean add(Company c);

	public int getSize(Company c);
}
