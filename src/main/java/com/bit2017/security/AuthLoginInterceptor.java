package com.bit2017.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bit2017.mysite.service.UserService;
import com.bit2017.mysite.vo.UserVo;

public class AuthLoginInterceptor extends HandlerInterceptorAdapter {

	//@Autowired	
	//private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String email = request.getParameter( "email" );
		String password = request.getParameter( "password" );
		
		ApplicationContext ac = 
				WebApplicationContextUtils.getWebApplicationContext( request.getServletContext() );
		
		UserService userService = ac.getBean( UserService.class );
		
		UserVo userVo = userService.getUser(email, password);
		if( userVo == null ) {
			response.sendRedirect( 
				request.getContextPath() + 
				"/user/loginform?result=fail" );
			return false;
		}
		
		//인증 처리
		HttpSession session = request.getSession( true );
		session.setAttribute( "authUser", userVo );
		response.sendRedirect( 
				request.getContextPath() + "/main" );
		return false;
	}

}
