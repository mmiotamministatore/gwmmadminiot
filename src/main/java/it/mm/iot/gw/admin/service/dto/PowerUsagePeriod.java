package it.mm.iot.gw.admin.service.dto;

import java.math.BigDecimal;

public class PowerUsagePeriod {

	private PeriodFilterTypeEnum periodType;
	private PeriodDecoded periodDecoded;

	private BigDecimal peakLoadSum;
	private BigDecimal itLoadSum;
	
	private BigDecimal minValue;
	private BigDecimal avgValue;
	private BigDecimal maxValue;
	
	private BigDecimal sumPue;
	private Integer rowCount;
	
	
	public PeriodFilterTypeEnum getPeriodType() {
		return periodType;
	}
	public void setPeriodType(PeriodFilterTypeEnum periodType) {
		this.periodType = periodType;
	}
	public BigDecimal getMinValue() {
		return minValue;
	}
	public void setMinValue(BigDecimal minValue) {
		this.minValue = minValue;
	}
	public BigDecimal getAvgValue() {
		return avgValue;
	}
	public void setAvgValue(BigDecimal avgValue) {
		this.avgValue = avgValue;
	}
	public BigDecimal getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue;
	}
	public BigDecimal getPeakLoadSum() {
		return peakLoadSum;
	}
	public void setPeakLoadSum(BigDecimal peakLoadSum) {
		this.peakLoadSum = peakLoadSum;
	}
	public BigDecimal getItLoadSum() {
		return itLoadSum;
	}
	public void setItLoadSum(BigDecimal itLoadSum) {
		this.itLoadSum = itLoadSum;
	}
	public Integer getRowCount() {
		return rowCount;
	}
	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}
	public BigDecimal getSumPue() {
		return sumPue;
	}
	public void setSumPue(BigDecimal sumPue) {
		this.sumPue = sumPue;
	}
	public PeriodDecoded getPeriodDecoded() {
		return periodDecoded;
	}
	public void setPeriodDecoded(PeriodDecoded periodDecoded) {
		this.periodDecoded = periodDecoded;
	}

}
