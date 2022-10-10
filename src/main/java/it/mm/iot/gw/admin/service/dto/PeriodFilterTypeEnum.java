package it.mm.iot.gw.admin.service.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PeriodFilterTypeEnum {
	USERDEFINED, DAY, WEEK, MONTLY,QUARTERLY, YEAR, NULL;

	public static PeriodFilterTypeEnum fromCode(String codeString) {
		if (codeString == null || "".equals(codeString))
			return null;
		if ("U".equals(codeString))
			return USERDEFINED;
		if ("D".equals(codeString))
			return DAY;
		if ("W".equals(codeString))
			return WEEK;
		if ("M".equals(codeString))
			return MONTLY;
		if ("Q".equals(codeString))
			return QUARTERLY;
		if ("Y".equals(codeString))
			return YEAR;
		return NULL;
	}

	@JsonValue
	public String toCode() {
		switch (this) {
		case USERDEFINED:
			return "U";
		case DAY:
			return "D";
		case WEEK:
			return "W";
		case MONTLY:
			return "M";
		case QUARTERLY:
			return "Q";
		case YEAR:
			return "Y";
		default:
			return "";
		}
	}
	
	public String decodedValue(){
		//millennium | century | decade | year | quarter | month | week | day | hour | minute | second | milliseconds | microseconds
		switch (this) {
		case USERDEFINED:
			return "user";
		case DAY:
			return "day";
		case WEEK:
			return "week";
		case MONTLY:
			return "month";
		case QUARTERLY:
			return "quarter";
		case YEAR:
			return "year";
		default:
			return "";
		}
	}
	
}
