package com.bit2017.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.bit2017.mysite.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public Object resolveArgument(
		MethodParameter parameter, 
		ModelAndViewContainer mavContainer, 
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory) 
		throws Exception {
		
		if( supportsParameter( parameter ) == false ){
			return WebArgumentResolver.UNRESOLVED;
		}
		
		// @AuthUser 붙어있고
		// 타입이 UserVo
		HttpServletRequest request = 
			(HttpServletRequest)webRequest.getNativeRequest();
		
		HttpSession session = request.getSession( false );
		if( session == null ){
			return WebArgumentResolver.UNRESOLVED;
		}
		
		return session.getAttribute( "authUser" );
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		AuthUser authUser = 
			parameter.getParameterAnnotation( AuthUser.class );
		
		// @AuthUser가 달려 있지 않음
		if( authUser == null ) {
			return false;
		}
		
		//파라미터 타입이 UserVo 인지 확인
		if( parameter.getParameterType().equals( UserVo.class ) == false ){
			return false;
		}
		
		return true;
	}

}
