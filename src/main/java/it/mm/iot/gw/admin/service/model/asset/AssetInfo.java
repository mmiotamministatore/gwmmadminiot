package it.mm.iot.gw.admin.service.model.asset;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import it.mm.iot.gw.admin.service.model.AssetStateEnum;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class AssetInfo implements Serializable {

	private String id;
	private String descrizione;
	private LocalDate dataUltimaVersione;
	private AssetStateEnum status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDate getDataUltimaVersione() {
		return dataUltimaVersione;
	}

	public void setDataUltimaVersione(LocalDate dataUltimaVersione) {
		this.dataUltimaVersione = dataUltimaVersione;
	}

	public AssetStateEnum getStatus() {
		return status;
	}

	public void setStatus(AssetStateEnum status) {
		this.status = status;
	}
}
