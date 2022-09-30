package it.mm.iot.gw.admin.service.feign.dto;

import java.util.List;
import java.util.Map;

import it.mm.iot.gw.admin.service.model.event.SensorData;

public class IoTPlatformOutputMessage {

	private StatusResponseIoTEnum result;
	private String message;
	private Integer count;
	private List<SensorData> rows;

	public StatusResponseIoTEnum getResult() {
		return result;
	}

	public void setResult(StatusResponseIoTEnum result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<SensorData> getRows() {
		return rows;
	}

	public void setRows(List<SensorData> rows) {
		this.rows = rows;
	}

}
