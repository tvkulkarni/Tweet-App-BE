package com.app.exception;

public class InvalidUsernamePwdException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidUsernamePwdException(String e) {
		super(e);
	}

}
