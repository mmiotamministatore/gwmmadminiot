package it.mm.iot.gw.admin.service.exception;

import java.io.Serializable;
import java.text.MessageFormat;

public class IotGenericException extends RuntimeException {
	private final String objectName;
	private final String code;
	private final String message;
	private final Serializable[] params;

	public IotGenericException(String code, String message) {
		this(message, code, message);
	}

	public IotGenericException(String objectName, String code, String message, Serializable... params) {
		super(code);
		this.objectName = objectName;
		this.code = code;
		this.message = message;
		this.params = params;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		if(params!=null && params.length>0) {
		return MessageFormat.format(message, (Object[])params);
		}
		else{
			return message;
		}
	}

	public String getObjectName() {
		return objectName;
	}

	public Object[] getParams() {
		return params;
	}

}
