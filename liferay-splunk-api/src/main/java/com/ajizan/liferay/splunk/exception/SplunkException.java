package com.ajizan.liferay.splunk.exception;

import com.liferay.portal.kernel.exception.PortalException;

public class SplunkException extends PortalException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int _code;
	private String _description;

	public SplunkException(int code, String description) {
		super(description);
		_description = description;
		_code = code;
	}

	public String getMessage() {
		return "[" + _code + "]: " + _description;
	}

	public String getLocalizedMessage() {
		return this.getMessage();
	}

}
