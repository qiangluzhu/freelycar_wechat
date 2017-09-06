/**
 * 
 */
package com.geariot.platform.freelycar_wechat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
