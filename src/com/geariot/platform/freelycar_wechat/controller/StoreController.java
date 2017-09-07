/**
 * 
 */
package com.geariot.platform.freelycar_wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.geariot.platform.freelycar_wechat.service.StoreService;

/**
 * @author mxy940127
 *
 */

@RestController
@RequestMapping(value = "/store")
public class StoreController {

	@Autowired
	private StoreService storeService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listStore(int page, int number){
		return null;
	}
	
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String storeDetail(int storeId){
		return null;
	}
	
	@RequestMapping(value = "/evaluation", method = RequestMethod.GET)
	public String storeComment(int storeId){
		return null;
	}

}
