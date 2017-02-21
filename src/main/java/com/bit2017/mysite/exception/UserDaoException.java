package com.bit2017.mysite.exception;

public class UserDaoException extends RuntimeException {
	private static final long serialVersionUID = 990183795028594110L;

	public UserDaoException( String message ) {
		super( message );
	}
	
	public UserDaoException() {
		super( "UserDao Excepption Occurs" );
	}
}
