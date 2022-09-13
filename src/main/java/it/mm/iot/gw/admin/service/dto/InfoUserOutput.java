package it.mm.iot.gw.admin.service.dto;

import it.mm.iot.gw.admin.web.message.OutputPayload;

public class InfoUserOutput implements OutputPayload {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6851860782236725145L;
	
	private String tenant;

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

}
