package it.mm.iot.gw.admin.service.dto;

import java.math.BigDecimal;

public class PowerUsagePeriod {

	private PeriodFilterTypeEnum periodType;
	private PeriodDecoded periodDecoded;

	private BigDecimal peakLoadSum;
	private Integer rowCountPeakLoad;
	private BigDecimal avgValuePeakLoad;
	
	private BigDecimal itLoadSum;
	private Integer rowCountItLoad;
	private BigDecimal avgValueItLoad;
	
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
	public PeriodDecoded getPeriodDecoded() {
		return periodDecoded;
	}
	public void setPeriodDecoded(PeriodDecoded periodDecoded) {
		this.periodDecoded = periodDecoded;
	}
	public BigDecimal getPeakLoadSum() {
		return peakLoadSum;
	}
	public void setPeakLoadSum(BigDecimal peakLoadSum) {
		this.peakLoadSum = peakLoadSum;
	}
	public Integer getRowCountPeakLoad() {
		return rowCountPeakLoad;
	}
	public void setRowCountPeakLoad(Integer rowCountPeakLoad) {
		this.rowCountPeakLoad = rowCountPeakLoad;
	}
	public BigDecimal getAvgValuePeakLoad() {
		return avgValuePeakLoad;
	}
	public void setAvgValuePeakLoad(BigDecimal avgValuePeakLoad) {
		this.avgValuePeakLoad = avgValuePeakLoad;
	}
	public BigDecimal getItLoadSum() {
		return itLoadSum;
	}
	public void setItLoadSum(BigDecimal itLoadSum) {
		this.itLoadSum = itLoadSum;
	}
	public Integer getRowCountItLoad() {
		return rowCountItLoad;
	}
	public void setRowCountItLoad(Integer rowCountItLoad) {
		this.rowCountItLoad = rowCountItLoad;
	}
	public BigDecimal getAvgValueItLoad() {
		return avgValueItLoad;
	}
	public void setAvgValueItLoad(BigDecimal avgValueItLoad) {
		this.avgValueItLoad = avgValueItLoad;
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
	public BigDecimal getSumPue() {
		return sumPue;
	}
	public void setSumPue(BigDecimal sumPue) {
		this.sumPue = sumPue;
	}
	public Integer getRowCount() {
		return rowCount;
	}
	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}
	
	
	
	

}
