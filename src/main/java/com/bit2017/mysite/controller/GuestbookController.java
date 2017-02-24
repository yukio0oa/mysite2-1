package com.bit2017.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bit2017.mysite.service.GuestbookService;
import com.bit2017.mysite.vo.GuestbookVo;

@Controller
@RequestMapping( "/guestbook" )
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping( value={"/list", ""} )
	public String list( Model model ) {
		List<GuestbookVo> list = 
				guestbookService.getMessageList();
		
		model.addAttribute( "list", list );
		return "guestbook/list";
	}
	
	@RequestMapping( "/list-ajax" )
	public String list() {
		return "guestbook/list-ajax";
	}
	
	@RequestMapping( "/deleteform/{no}" )
	public String deleteform( @PathVariable("no") Long no, Model model ){
		model.addAttribute( "no", no );
		return "guestbook/deleteform";
	}
	
	@RequestMapping( "/delete" )
	public String delete(@ModelAttribute GuestbookVo guestbookVo){
		guestbookService.removeMessage(guestbookVo);
		return "redirect:/guestbook/list";
	}
	
	@RequestMapping( "/add" )
	public String add(@ModelAttribute GuestbookVo guestbookVo){
		guestbookService.addMessage( guestbookVo );
		return "redirect:/guestbook/list";
	}	
}