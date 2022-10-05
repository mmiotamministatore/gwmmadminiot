package it.mm.iot.gw.admin.service.dto;

import java.util.List;

public class InfoListSensorRequest {

	private List<String> sensorListId;

	public List<String> getSensorListId() {
		return sensorListId;
	}

	public void setSensorListId(List<String> sensorListId) {
		this.sensorListId = sensorListId;
	}

}
