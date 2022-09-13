package it.mm.iot.gw.admin.web.message;

import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OutputMessageFactory {
	private static final Logger log = LoggerFactory.getLogger(OutputMessageFactory.class);

	private OutputMessageFactory() {
		throw new IllegalStateException("Utility class");
	}

	private static final ObjectMapper mapper = new ObjectMapper();

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

	public static void putOperationOutcome(OperationOutcome outcome, String id) {
		try {
			MDC.put("OPERATION-OUTCOME_" + id, mapper.writeValueAsString(outcome));
		} catch (IllegalArgumentException | JsonProcessingException e) {
			log.error(e.getMessage());
		}
	}

	public static <T extends OutputMessage<K>, K extends OutputPayload> T create(Class<T> clazz, K payload, String id) {
		T message = null;
		try {
			message = clazz.getDeclaredConstructor().newInstance();
			message.setPayload(payload);
			OperationOutcome outcome = getOperationOutcomeNull(id);
			message.setOutcome(outcome);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			log.error(e.getMessage());
		}
		return message;
	}

	public static <T extends OutputMessage<K>, K extends OutputPayload> T create(Class<T> clazz, K payload,
			OperationOutcome outcome) {
		T message = null;
		try {
			message = clazz.getDeclaredConstructor().newInstance();
			message.setPayload(payload);
			message.setOutcome(outcome);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			log.error(e.getMessage());
		}
		return message;
	}
}