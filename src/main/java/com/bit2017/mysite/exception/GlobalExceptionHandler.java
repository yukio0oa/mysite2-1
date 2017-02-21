package com.bit2017.mysite.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler( Exception.class )
	public String handleException( Exception e ) {
		// 1. log 처리
		e.printStackTrace();
		
		// 2. 화면처리( 사용자한테 사과... )
		return "error/exception";
	}
}
