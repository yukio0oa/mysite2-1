package com.bit2017.mysite.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bit2017.mysite.service.BoardService;
import com.bit2017.mysite.vo.BoardVo;
import com.bit2017.mysite.vo.UserVo;
import com.bit2017.web.WebUtil;

@Controller
@RequestMapping( "/board" )
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping( "" )
	public String list(
		@RequestParam( value="p", required=true, defaultValue="1") Integer page,
		@RequestParam( value="kwd", required=true, defaultValue="") String keyword,
		Model model ){
		
		Map<String, Object> map = boardService.getMessageList( page, keyword );
		
		model.addAttribute( "map", map );
		return "board/list";
	}
	
	@RequestMapping( "/view" )
	public String view(
		@RequestParam( value="no", required=true, defaultValue="0") Long no,
		@RequestParam( value="p", required=true, defaultValue="1") Integer page,
		@RequestParam( value="kwd", required=true, defaultValue="") String keyword,
		Model model ){
		
		BoardVo boardVo = boardService.getMessage(no);
		
		model.addAttribute( "boardVo", boardVo );
		model.addAttribute( "page", page );
		model.addAttribute( "keyword", keyword );

		return "board/view";
	}
	
	@RequestMapping( value="/write", method=RequestMethod.GET )
	public String write( HttpSession session ) {
		// 인증 체크
		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		if( authUser == null ){
			return "redirect:/user/loginform";
		}
		
		return "board/write";
	}
	
	@RequestMapping( value="/write", method=RequestMethod.POST )
	public String write(
		HttpSession session,
		@ModelAttribute BoardVo vo,
		@RequestParam( value="p", required=true, defaultValue="1") Integer page,
		@RequestParam( value="kwd", required=true, defaultValue="") String keyword ){

		// 인증 체크
		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		if( authUser == null ){
			return "redirect:/user/loginform";
		}
		
		vo.setUserNo( authUser.getNo() );
		boardService.writeMessage( vo );
		
		return
			"redirect:/board" +
			"?p=" + page + 
			"&kwd=" + WebUtil.encodeURL( keyword, "UTF-8");
	}
	
	@RequestMapping( value="/reply", method=RequestMethod.GET )
	public String reply(
		HttpSession session,	
		@RequestParam( value="no", required=true, defaultValue="0") Long no,
		@RequestParam( value="p", required=true, defaultValue="1") Integer page,
		@RequestParam( value="kwd", required=true, defaultValue="") String keyword,
		Model model ){
		
		// 인증 체크
		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		if( authUser == null ){
			return "redirect:/user/loginform";
		}
		
		BoardVo boardVo = boardService.getMessage(no);
			
		model.addAttribute( "boardVo", boardVo );
		model.addAttribute( "page", page );
		model.addAttribute( "keyword", keyword );

		return "board/reply";
	}
	
	@RequestMapping( value="/modify", method=RequestMethod.GET )
	public String modify(
		HttpSession session,
		@RequestParam( value="no", required=true, defaultValue="0") Long no,
		@RequestParam( value="p", required=true, defaultValue="1") Integer page,
		@RequestParam( value="kwd", required=true, defaultValue="") String keyword,
		Model model ){
		
		// 인증 체크
		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		if( authUser == null ){
			return "redirect:/user/loginform";
		}
		
		BoardVo boardVo = boardService.getMessage(no);
			
		model.addAttribute( "boardVo", boardVo );
		model.addAttribute( "page", page );
		model.addAttribute( "keyword", keyword );

		return "board/modify";
	}
	
	@RequestMapping( value="/modify", method=RequestMethod.POST )
	public String modify(
		HttpSession session,
		@ModelAttribute BoardVo boardVo,
		@RequestParam( value="p", required=true, defaultValue="1") Integer page,
		@RequestParam( value="kwd", required=true, defaultValue="") String keyword ){
		
		// 인증 체크
		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		if( authUser == null ){
			return "redirect:/user/loginform";
		}
		
		boardVo.setUserNo( authUser.getNo() );
		boardService.updateMessage(boardVo);
		
		return 
			"redirect:/board/view" +	
			"?no=" + boardVo.getNo() + 
			"&kwd=" + WebUtil.encodeURL( keyword, "UTF-8");
	}
	
	@RequestMapping( "/delete" )
	public String delete(
		HttpSession session,
		@ModelAttribute BoardVo vo,
		@RequestParam( value="p", required=true, defaultValue="1") Integer page,
		@RequestParam( value="kwd", required=true, defaultValue="") String keyword ){

		// 인증 체크
		UserVo authUser = (UserVo)session.getAttribute( "authUser" );
		if( authUser == null ){
			return "redirect:/user/loginform";
		}
		
		vo.setUserNo( authUser.getNo() );
		boardService.deleteMessage( vo );
		
		return 
			"redirect:/board" +
			"?p=" + page + 
			"&kwd=" + WebUtil.encodeURL( keyword, "UTF-8");
	}	
}
