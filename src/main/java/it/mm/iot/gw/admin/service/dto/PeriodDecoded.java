package it.mm.iot.gw.admin.service.dto;

import java.time.LocalDateTime;

public class PeriodDecoded {

	private PeriodFilterTypeEnum tipoFiltro;
	private LocalDateTime ref;
	private LocalDateTime from;
	private LocalDateTime to;
	private String decodedPeriod;
	
	public PeriodFilterTypeEnum getTipoFiltro() {
		return tipoFiltro;
	}
	public void setTipoFiltro(PeriodFilterTypeEnum tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
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
	public String getDecodedPeriod() {
		return decodedPeriod;
	}
	public void setDecodedPeriod(String decodedPeriod) {
		this.decodedPeriod = decodedPeriod;
	}
	public LocalDateTime getRef() {
		return ref;
	}
	public void setRef(LocalDateTime ref) {
		this.ref = ref;
	}

}
