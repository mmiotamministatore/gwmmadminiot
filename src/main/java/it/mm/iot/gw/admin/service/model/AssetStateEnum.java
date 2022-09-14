package it.mm.iot.gw.admin.service.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AssetStateEnum {
	Active("active"), Available("available"), Unknown("unknown");

	private final String text;

	/**
	 * @param text
	 */
	AssetStateEnum(final String text) {
		this.text = text;
	}

	public static AssetStateEnum fromCode(String codeString) {
		if (codeString == null || "".equals(codeString))
			return null;
		if ("active".equals(codeString))
			return Active;
		if ("available".equals(codeString))
			return Available;
		if ("unknown".equals(codeString))
			return Unknown;

		return Unknown;
	}

	@JsonValue
	public String toCode() {
		return this.text;
	}
}