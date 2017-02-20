package com.bit2017.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( "/user" )
public class UserController {
	
	@RequestMapping( "/joinform" )
	public String joinform(){
		return "user/joinform";
	}

	@RequestMapping( "/join" )
	public String join(){
		return "";
	}
	
}
