package it.mm.iot.gw.admin.web.events;

import java.util.List;

import it.mm.iot.gw.admin.service.model.event.SensorMeasure;

public class IoTMeasureData{
	
	private String data;

	private List<SensorMeasure> detailData;

	public List<SensorMeasure> getDetailData() {
		return detailData;
	}

	public void setDetailData(List<SensorMeasure> detailData) {
		this.detailData = detailData;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
