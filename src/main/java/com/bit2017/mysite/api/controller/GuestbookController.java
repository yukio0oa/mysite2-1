package com.bit2017.mysite.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit2017.mysite.dto.JSONResult;
import com.bit2017.mysite.service.GuestbookService;
import com.bit2017.mysite.vo.GuestbookVo;

@Controller( "guestbookAPIController" )
@RequestMapping( "/api/guestbook" )
public class GuestbookController {

	@Autowired
	private GuestbookService guestbookService;
	
	@ResponseBody
	@RequestMapping( "/list/{page}" )
	public JSONResult list(
		@PathVariable( "page" ) Integer page ){
		
		List<GuestbookVo> list = 
				guestbookService.getMessageList( page );
		
		return JSONResult.success( list );
	}
	
	@ResponseBody
	@RequestMapping( "/add" )
	public JSONResult add( @ModelAttribute GuestbookVo vo ){
		guestbookService.addMessage( vo );
		vo = guestbookService.getMessage( vo.getNo() );
		return JSONResult.success( vo );
	}	
}
