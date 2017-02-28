package com.bit2017.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bit2017.mysite.service.GalleryService;
import com.bit2017.mysite.vo.GalleryVo;
import com.bit2017.mysite.vo.UserVo;
import com.bit2017.security.Auth;
import com.bit2017.security.AuthUser;

@Controller
@RequestMapping( "/gallery" )
public class GalleryController {

	@Autowired
	private GalleryService galleryService;  
	
	@RequestMapping( "" )
	public String index( Model model ){
		List<GalleryVo> list= galleryService.getImageList();
		model.addAttribute( "list", list );
		model.addAttribute( "baseURL", GalleryService.BASE_URL );
		return "gallery/list";
	}

	@Auth
	@RequestMapping( "/delete/{no}" )
	public String delete(
		@AuthUser UserVo authUser,
		@PathVariable( "no" ) Long no ){
		return "redirect:/gallery";
	}
	
	@RequestMapping( value="/upload", method=RequestMethod.POST )
	public String upload(
		@ModelAttribute GalleryVo galleryVo,
		@RequestParam( "file" ) MultipartFile multipartFile ){
		galleryService.restore( galleryVo, multipartFile );
		return "redirect:/gallery";
	}
}