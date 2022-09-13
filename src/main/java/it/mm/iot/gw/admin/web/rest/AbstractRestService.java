package it.mm.iot.gw.admin.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import brave.Tracer;
import it.mm.iot.gw.admin.web.message.OperationOutcome;

public abstract class AbstractRestService {

	private static final Logger log = LoggerFactory.getLogger(AbstractRestService.class);

	@Autowired
	private Tracer tracer;

	private static final ObjectMapper mapper = new ObjectMapper();

	protected synchronized OperationOutcome getOperationOutcomeAndRemove() {
		OperationOutcome outcome = null;
		String id = "OPERATION-OUTCOME_" + tracer.currentSpan().context().traceIdString();
		String outcomeString = MDC.get(id);
		MDC.remove(id);
		/**
		 * System.out.println("****************************************");
		 * System.out.println("* *"); System.out.println("* <" + outcomeString + ">");
		 * System.out.println("* <" + tracer.currentSpan().context().traceIdString() +
		 * "> "); System.out.println("* *");
		 * System.out.println("****************************************");
		 */
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