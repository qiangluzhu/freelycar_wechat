package com.geariot.platform.freelycar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geariot.platform.freelycar.entities.Project;
import com.geariot.platform.freelycar.service.ProjectService;
import com.geariot.platform.freelycar.shiro.PermissionRequire;

@RestController
@RequestMapping(value = "/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@RequestMapping(value = "/add" , method = RequestMethod.POST)
	@PermissionRequire("project:add")
	public String addProject(@RequestBody Project project){
		return projectService.addProject(project);
	}
	
	@RequestMapping(value = "/modify" , method = RequestMethod.POST)
	@PermissionRequire("project:modify")
	public String modifyProject(Project project){
		return projectService.modifyProject(project);
	}
	
	@RequestMapping(value = "/delete" , method = RequestMethod.POST)
	@PermissionRequire("project:delete")
	public String deleteProject(Integer... projectIds){
		return projectService.deleteProject(projectIds);
	}
	
	@RequestMapping(value = "/query" , method = RequestMethod.GET)
	@PermissionRequire("project:query")
	public String getSelectProject(String name , String programId , int page , int number){
		return projectService.getSelectProject(name, programId, page, number);
	}
	
	@RequestMapping(value = "/name" , method = RequestMethod.GET)
	public String getProjectName(){
		return projectService.getProjectName();
	}
	
	@RequestMapping(value = "/getbyid" , method = RequestMethod.GET)
	public String getProject(int projectId){
		return projectService.getProject(projectId);
	}
}
