package it.mm.iot.gw.admin.service.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class IssueOperationOutcome {
	
	private SeverityEnum severity;
	private String code;
	private String message;
	private Object[] additionalParam;
	private String details;
	private String applicationName;
	private String traceId;
	private String spanId;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Object[] getAdditionalParam() {
		return additionalParam;
	}
	public void setAdditionalParam(Object[] additionalParam) {
		this.additionalParam = additionalParam;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public SeverityEnum getSeverity() {
		return severity;
	}
	public void setSeverity(SeverityEnum severity) {
		this.severity = severity;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTraceId() {
		return traceId;
	}
	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}
	public String getSpanId() {
		return spanId;
	}
	public void setSpanId(String spanId) {
		this.spanId = spanId;
	}
	public String getApplicationName() {
		return applicationName;
	}
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
}
