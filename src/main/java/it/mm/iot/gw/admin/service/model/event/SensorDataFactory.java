package it.mm.iot.gw.admin.service.model.event;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class SensorDataFactory {
	@Autowired
	private ObjectMapper mapper;

	public SensorData convertToSensorData(String data) {
		try {
			return mapper.readValue(data, SensorData.class);
		} catch (JsonMappingException e) {
		} catch (JsonProcessingException e) {
		}
		return null;
	}

	public List<SensorMeasure> convertToSensorMeasures(SensorData sensorData) {
		if (sensorData != null) {
			try {
				List<SensorMeasure> detailData = mapper.readValue(sensorData.getJsonMessage(),
						mapper.getTypeFactory().constructCollectionType(List.class, SensorMeasure.class));
				return detailData;
			} catch (JsonProcessingException e) {

			}
		}
		return null;
	}

	public Map<String, BigDecimal>  convertToSensorPowerMeasures(SensorData sensorData) {
		if (sensorData != null) {
			try {
				Map<String, BigDecimal> detailData = mapper.readValue(sensorData.getJsonMessage(),
						mapper.getTypeFactory().constructMapType(Map.class, String.class, BigDecimal.class));

				return detailData;
			} catch (JsonProcessingException e) {

			}
		}
		return null;
	}
}
