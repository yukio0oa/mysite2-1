package com.bit2017.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( "/gallery" )
public class GalleryController {

	@RequestMapping( value={ "", "/list" } )
	public String list() {
		return "gallery/list";
	}
}
