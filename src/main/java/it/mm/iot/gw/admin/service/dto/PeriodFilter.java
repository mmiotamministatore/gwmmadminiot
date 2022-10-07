package it.mm.iot.gw.admin.service.dto;

import java.time.LocalDateTime;

public class PeriodFilter {

	private LocalDateTime referenceDate;
	private PeriodFilterTypeEnum tipoFiltro;
	
	private LocalDateTime userDefFrom;
	private LocalDateTime userDefTo;
	
	public LocalDateTime getReferenceDate() {
		return referenceDate;
	}
	public void setReferenceDate(LocalDateTime referenceDate) {
		this.referenceDate = referenceDate;
	}
	public PeriodFilterTypeEnum getTipoFiltro() {
		return tipoFiltro;
	}
	public void setTipoFiltro(PeriodFilterTypeEnum tipoFiltro) {
		this.tipoFiltro = tipoFiltro;
	}
	public LocalDateTime getUserDefFrom() {
		return userDefFrom;
	}
	public void setUserDefFrom(LocalDateTime userDefFrom) {
		this.userDefFrom = userDefFrom;
	}
	public LocalDateTime getUserDefTo() {
		return userDefTo;
	}
	public void setUserDefTo(LocalDateTime userDefTo) {
		this.userDefTo = userDefTo;
	}
}
