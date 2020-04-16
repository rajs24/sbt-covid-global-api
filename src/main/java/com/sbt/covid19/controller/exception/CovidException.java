package com.sbt.covid19.controller.exception;

public class CovidException extends Exception {

	private static final long serialVersionUID = 7718828512143293558L;

	public CovidException() {
		super();
	}

	public CovidException(String message) {
		super(message);
	}

	public CovidException(Throwable cause) {
		super(cause);
	}

	public CovidException(String message, Throwable cause) {
		super(message, cause);
	}

	public CovidException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}