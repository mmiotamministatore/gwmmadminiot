package it.mm.iot.gw.admin.service.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import it.mm.iot.gw.admin.service.model.asset.AssetInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class TenantInfo extends AssetInfo {

	private String tenant;

	private String nomeAzienda;
	private String logo;

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public String getNomeAzienda() {
		return nomeAzienda;
	}

	public void setNomeAzienda(String nomeAzienda) {
		this.nomeAzienda = nomeAzienda;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

}
