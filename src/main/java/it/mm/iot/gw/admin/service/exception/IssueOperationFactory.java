package it.mm.iot.gw.admin.service.exception;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import brave.Tracer;
import it.mm.iot.gw.admin.web.message.OperationOutcome;


@Component
public class IssueOperationFactory {
	
	private static final Logger log = LoggerFactory.getLogger(IssueOperationFactory.class);
	
	@Autowired
	private Tracer tracer;

	private static final ObjectMapper mapper = new ObjectMapper();

	public void addIssue(SeverityEnum severity, String code, String message) {
		IssueOperationOutcome ioo = new IssueOperationOutcome();
		ioo.setCode(code);
		ioo.setMessage(message);
		ioo.setSeverity(severity);
		addIssue(ioo);
	}

	public void addIssue(IssueOperationOutcome ioo) {
		if (SeverityEnum.FATAL.equals(ioo.getSeverity())) {
			throw new IotGenericException(ioo.getCode(), ioo.getMessage());
		}
		addIssueCommon(ioo);
	}

	private void addIssueCommon(IssueOperationOutcome ioo) {
		OperationOutcome oo = getOperationOutcome(tracer.currentSpan().context().traceIdString());
		if (oo.getIssue() == null) {
			oo.setIssue(new ArrayList<IssueOperationOutcome>());
		}
		oo.getIssue().add(ioo);
		putOperationOutcome(oo);
	}

	private void putOperationOutcome(OperationOutcome outcome) {
		String id = "OPERATION-OUTCOME_" + tracer.currentSpan().context().traceIdString();
		try {
			MDC.put(id, mapper.writeValueAsString(outcome));
		} catch (IllegalArgumentException | JsonProcessingException e) {
			log.error(e.getMessage());
		}
	}

	public static OperationOutcome getOperationOutcome(String id) {
		OperationOutcome outcome = getOperationOutcomeNull(id);
		if (outcome == null) {
			outcome = new OperationOutcome();
		}
		return outcome;
	}

	public static OperationOutcome getOperationOutcomeNull(String id) {
		OperationOutcome outcome = null;
		String outcomeString = MDC.get("OPERATION-OUTCOME_" + id);
		if (outcomeString != null) {
			try {
				outcome = mapper.readValue(outcomeString, OperationOutcome.class);
			} catch (JsonProcessingException | IllegalArgumentException e) {
				log.error(e.getMessage());
			}
		}
		return outcome;
	}

}
