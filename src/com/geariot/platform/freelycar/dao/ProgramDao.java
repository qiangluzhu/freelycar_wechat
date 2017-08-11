package com.geariot.platform.freelycar.dao;

import java.util.List;

import com.geariot.platform.freelycar.entities.Program;

public interface ProgramDao {

	Program findProgramByProgramId(int programId);
	
	Program findProgramByName(String name);
	
	void save(Program program);
	
	void delete(Program program);
	
	void delete(int programId);
	
	List<Program> listPrograms(int from, int pageSize);
	
	long getCount();
	
	List<Program> listAll();
}
