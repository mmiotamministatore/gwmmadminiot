package it.mm.iot.gw.admin.service.dto;

public class PowerUsageRequest {

	private PeriodFilter periodFilter;

	public PeriodFilter getPeriodFilter() {
		return periodFilter;
	}

	public void setPeriodFilter(PeriodFilter periodFilter) {
		this.periodFilter = periodFilter;
	}

}
