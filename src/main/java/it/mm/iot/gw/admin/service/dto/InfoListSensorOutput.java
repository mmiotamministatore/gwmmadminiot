package it.mm.iot.gw.admin.service.dto;

import java.util.List;

import it.mm.iot.gw.admin.service.model.event.SensorData;
import it.mm.iot.gw.admin.web.message.OutputPayload;

public class InfoListSensorOutput implements OutputPayload {

	private List<SensorData> sensorListData;

	public List<SensorData> getSensorListData() {
		return sensorListData;
	}

	public void setSensorListData(List<SensorData> sensorListData) {
		this.sensorListData = sensorListData;
	}

}
