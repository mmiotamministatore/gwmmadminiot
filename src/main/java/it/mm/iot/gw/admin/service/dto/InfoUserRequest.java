package it.mm.iot.gw.admin.service.dto;

import java.util.List;

public class InfoUserRequest {

	private String tenant;
	private String sensor;

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public String getSensor() {
		return sensor;
	}

	public void setSensor(String sensor) {
		this.sensor = sensor;
	}
}
