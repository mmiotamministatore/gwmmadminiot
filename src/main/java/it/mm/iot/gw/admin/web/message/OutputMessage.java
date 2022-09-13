package it.mm.iot.gw.admin.web.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class OutputMessage<T> {

	private T payload;
	private OperationOutcome outcome;

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	public OperationOutcome getOutcome() {
		return outcome;
	}

	public void setOutcome(OperationOutcome outcome) {
		this.outcome = outcome;
	}

}
