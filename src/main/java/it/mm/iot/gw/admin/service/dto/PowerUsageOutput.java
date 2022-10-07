package it.mm.iot.gw.admin.service.dto;

import java.util.Map;

import it.mm.iot.gw.admin.web.message.OutputPayload;

public class PowerUsageOutput implements OutputPayload {

	Map<PeriodFilterTypeEnum, PowerUsagePeriod> powerUsage;

	public Map<PeriodFilterTypeEnum, PowerUsagePeriod> getPowerUsage() {
		return powerUsage;
	}

	public void setPowerUsage(Map<PeriodFilterTypeEnum, PowerUsagePeriod> powerUsage) {
		this.powerUsage = powerUsage;
	}

}
