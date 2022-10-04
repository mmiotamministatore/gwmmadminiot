package it.mm.iot.gw.admin.service.dto;

import it.mm.iot.gw.admin.service.model.event.SensorData;
import it.mm.iot.gw.admin.web.message.OutputPayload;

public class InfoSensorOutput implements OutputPayload {

	private SensorData sensorData;

	public SensorData getSensorData() {
		return sensorData;
	}

	public void setSensorData(SensorData sensorData) {
		this.sensorData = sensorData;
	}

}
