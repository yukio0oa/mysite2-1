package com.bit2017.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit2017.mysite.dto.JSONResult;
import com.bit2017.mysite.service.UserService;
import com.bit2017.mysite.vo.UserVo;

@Controller
@RequestMapping( "/user" )
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ResponseBody
	@RequestMapping( "/checkemail" )
	public JSONResult checkEmail(
		@RequestParam( value="email", required=true, defaultValue="") 
		String email ){
		
		boolean isExists = userService.exists( email );
		
		JSONResult json = new JSONResult();
		json.setResult( "success" );
		json.setData( isExists ? "exist" : "not exist" );
		
		return json;
	}
	
	@RequestMapping( "/joinform" )
	public String joinform(){
		return "user/joinform";
	}


	@RequestMapping( "/join" )
	public String join( @ModelAttribute UserVo userVo ){
		userService.join( userVo );
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping( "/joinsuccess" )
	public String joinSuccess() {
		return "user/joinsuccess";
	}
	
	@RequestMapping( "/loginform" )
	public String loginform(){
		return "user/loginform";
	}
	
	@RequestMapping( "/modifyform" )
	public String modifyform(
		HttpSession session,
		Model model ){
		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		if( authUser == null ) {
			return "redirect:/main";
		}
		
		UserVo userVo = userService.getUser( authUser.getNo() );
		model.addAttribute( "userVo", userVo );
		return "user/modifyform";
	}

	@RequestMapping( "/modify" )
	public String modify(
		HttpSession session,
		@ModelAttribute UserVo userVo){
		
		// 인증 여부
		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		if( authUser == null ) {
			return "redirect:/main";
		}
		
		userVo.setNo( authUser.getNo() );
		
		userService.updateUser( userVo );

		authUser.setName( userVo.getName() );
		return "redirect:/user/modifyform";
	}
	
	/*
	@Auth
	@RequestMapping( "/modifyform" )
	public String modifyform(
		@AuthUser UserVo authUser,
		Model model ){
		UserVo userVo = userService.getUser( authUser.getNo() );
		model.addAttribute( "userVo", userVo );
		return "user/modifyform";
	}
	*/	
}
