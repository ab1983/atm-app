package com.jnj.codingchallenge.exception;

public class AtmException extends Exception {

	public AtmException(String message) {
		super(message);
	}

	public AtmException(String message, Exception e) {
		super(message, e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5847916807910100625L;

}
