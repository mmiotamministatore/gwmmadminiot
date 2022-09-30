package it.mm.iot.gw.admin.service.feign.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusResponseIoTEnum {

	SUCCESS,

	ERROR, NULL;

	public static StatusResponseIoTEnum fromCode(String codeString) {
		if (codeString == null || "".equals(codeString))
			return null;
		if ("success".equals(codeString))
			return SUCCESS;
		if ("error".equals(codeString))
			return ERROR;
		return NULL;
	}

	@JsonValue
	public String toCode() {
		switch (this) {
		case SUCCESS:
			return "success";
		case ERROR:
			return "error";
		default:
			return "";
		}
	}
}