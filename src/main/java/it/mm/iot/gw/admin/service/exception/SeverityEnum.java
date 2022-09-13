package it.mm.iot.gw.admin.service.exception;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SeverityEnum {
	
	FATAL,ERROR,WARNING,INFORMATION;
	
	public static SeverityEnum fromCode(String codeString) {
		if (codeString == null || "".equals(codeString))
			return null;
		if ("FATAL".equals(codeString))
			return FATAL;
		if ("ERROR".equals(codeString))
			return ERROR;
		if ("WARNING".equals(codeString))
			return WARNING;
		if ("INFORMATION".equals(codeString))
			return INFORMATION;		
		return null;
	}
	
	@JsonValue
	public String toCode() {
		switch (this) {
		case FATAL:
			return "FATAL";
		case ERROR:
			return "ERROR";
		case WARNING:
			return "WARNING";
		case INFORMATION:
			return "INFORMATION";
		}
		return null;
	}
}