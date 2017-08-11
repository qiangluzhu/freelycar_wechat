package com.geariot.platform.freelycar.controller;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geariot.platform.freelycar.entities.Admin;
import com.geariot.platform.freelycar.model.RESCODE;
import com.geariot.platform.freelycar.service.AdminService;
import com.geariot.platform.freelycar.shiro.PermissionRequire;
import com.geariot.platform.freelycar.utils.JsonResFactory;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	private static final Logger log = LogManager.getLogger(AdminController.class);
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(String account, String password, boolean rememberMe){
		return adminService.login(account, password, rememberMe);
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(){
		return adminService.logout();
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@PermissionRequire("admin:add")
	public String add(@RequestBody Admin admin){
		return adminService.addAdmin(admin);
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	@PermissionRequire("admin:modify")
	public String modify(@RequestBody Admin admin){
		return adminService.modify(admin);
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	@PermissionRequire("admin:delete")
	public String delete(String... accounts){
		log.info(Arrays.toString(accounts));
		return adminService.delete(accounts);
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	@PermissionRequire("admin:query")
	public String list(int page, int number){
		return adminService.list(page, number);
	}
	
	@RequestMapping(value="/query", method={RequestMethod.GET, RequestMethod.POST})
	@PermissionRequire("admin:query")
	public String query(String account, String name, int page, int number){
		return adminService.query(account, name, page, number);
	}
	
	@RequestMapping(value="/getaccount", method=RequestMethod.GET)
	public String queryByAccount(String account){
		return adminService.getAdminByAccount(account);
	}
	
	@RequestMapping(value="/changestate", method=RequestMethod.POST)
	@PermissionRequire("admin:modify")
	public String changeAdminCurrent(String account, int type){
		switch(type){
		case 0: return this.adminService.disable(account);
		case 1: return this.adminService.enable(account);
		default: return JsonResFactory.buildOrg(RESCODE.UNSUPPORT_TYPE).toString();
		}
	}
	
	@RequestMapping(value="/cur", method=RequestMethod.GET)
	public String cur(){
		Subject curUser = SecurityUtils.getSubject();
		System.out.println(curUser);
		System.out.println(curUser.getPrincipal());
		org.json.JSONObject obj = JsonResFactory.buildOrg(RESCODE.SUCCESS);
		obj.put("user", curUser.getPrincipal());
		return obj.toString();
	}
	
}
