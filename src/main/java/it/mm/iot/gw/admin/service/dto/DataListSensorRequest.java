package it.mm.iot.gw.admin.service.dto;

import java.time.LocalDateTime;
import java.util.List;

public class DataListSensorRequest {

	private List<String> sensorListId;
	private LocalDateTime from;
	private LocalDateTime to;

	public List<String> getSensorListId() {
		return sensorListId;
	}

	public void setSensorListId(List<String> sensorListId) {
		this.sensorListId = sensorListId;
	}

	public LocalDateTime getFrom() {
		return from;
	}

	public void setFrom(LocalDateTime from) {
		this.from = from;
	}

	public LocalDateTime getTo() {
		return to;
	}

	public void setTo(LocalDateTime to) {
		this.to = to;
	}

}
