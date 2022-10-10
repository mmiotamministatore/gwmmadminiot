package it.mm.iot.gw.admin.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import it.mm.iot.gw.admin.service.dto.PeriodDecoded;
import it.mm.iot.gw.admin.service.dto.PeriodFilter;
import it.mm.iot.gw.admin.service.dto.PeriodFilterTypeEnum;
import it.mm.iot.gw.admin.service.dto.PowerUsageOutput;
import it.mm.iot.gw.admin.service.dto.PowerUsagePeriod;
import it.mm.iot.gw.admin.service.dto.PowerUsageRequest;
import it.mm.iot.gw.admin.service.feign.IotPlatformService;
import it.mm.iot.gw.admin.service.feign.dto.IoTPlatformOutputMessage;
import it.mm.iot.gw.admin.service.feign.dto.StatusResponseIoTEnum;
import it.mm.iot.gw.admin.service.model.event.SensorData;
import it.mm.iot.gw.admin.service.model.event.SensorDataFactory;

@Service
@Validated
public class PowerService extends AbstractService {

	@Value("${app.iotmm.tenant.id:maticmind}")
	private String tenantId;
	@Value("${app.iotmm.tenant.description:MATICMIND}")
	private String tenantDescription;

	@Autowired
	SensorDataFactory sensorDataFactory;

	@Autowired
	private IotPlatformService iotPlatformService;

	public PowerUsageOutput getPowerUsage(PowerUsageRequest powerUsageRequest) {
		Map<PeriodFilterTypeEnum, PowerUsagePeriod> powerUsage = new HashMap<>();

		powerUsage.putAll(getUserDefinedPowerUsage(powerUsageRequest));
		powerUsage.putAll(getHistoricalPowerUsage(powerUsageRequest));

		PowerUsageOutput powerUsageOutput = new PowerUsageOutput();
		powerUsageOutput.setPowerUsage(powerUsage);
		return powerUsageOutput;
	}

	private Map<PeriodFilterTypeEnum, PowerUsagePeriod> getUserDefinedPowerUsage(PowerUsageRequest powerUsageRequest) {

		Map<PeriodFilterTypeEnum, PowerUsagePeriod> powerUsage = new HashMap<>();

		PeriodDecoded dts = convertPeriodFilter(powerUsageRequest.getPeriodFilter());

		IoTPlatformOutputMessage ritorno = iotPlatformService.getPowerUsage(tenantId, dts.getFrom(), dts.getTo());
		if (ritorno != null && ritorno.getResult() == StatusResponseIoTEnum.SUCCESS) {

			if (ritorno.getCount() > 0) {
				powerUsage = loadPowerUsage(ritorno.getRows(), dts,
						powerUsageRequest.getPeriodFilter().getReferenceDate(),
						new PeriodFilterTypeEnum[] { PeriodFilterTypeEnum.USERDEFINED });
			}
		}

		return powerUsage;
	}

	private Map<PeriodFilterTypeEnum, PowerUsagePeriod> getHistoricalPowerUsage(PowerUsageRequest powerUsageRequest) {

		Map<PeriodFilterTypeEnum, PowerUsagePeriod> powerUsage = new HashMap<>();
		PeriodFilterTypeEnum[] periods = new PeriodFilterTypeEnum[] { PeriodFilterTypeEnum.DAY,
				PeriodFilterTypeEnum.WEEK, PeriodFilterTypeEnum.MONTLY, PeriodFilterTypeEnum.QUARTERLY };

		for (PeriodFilterTypeEnum period : periods) {
			PeriodFilter pf = new PeriodFilter();
			pf.setTipoFiltro(period);
			pf.setReferenceDate(powerUsageRequest.getPeriodFilter().getReferenceDate());
			PeriodDecoded dts = convertPeriodFilter(pf);
			IoTPlatformOutputMessage ritorno = iotPlatformService.getPowerUsageStat(dts.getDecodedPeriod(), tenantId,
					dts.getFrom(), dts.getTo());
			if (ritorno != null && ritorno.getResult() == StatusResponseIoTEnum.SUCCESS) {

				if (ritorno.getCount() > 0) {
					powerUsage
							.put(period,
									loadHistoricalPowerUsage(ritorno.getRows(), dts,
											powerUsageRequest.getPeriodFilter().getReferenceDate(), period)
													.get(period));
				}
			}
		}

		return powerUsage;
	}

//	private PowerUsagePeriod loadPowerUsage(List<SensorData> rows, LocalDateTime referenceDate,
//			PeriodFilterTypeEnum tipoFiltro) {
//
//		PowerUsagePeriod pup = initializePup(PeriodFilterTypeEnum.USERDEFINED);
//		LocalDate refData = referenceDate.toLocalDate();
//
//		for (SensorData row : rows) {
//			addToPowerUsage(pup, row);
//
//		}
//		return pup;
//	}

	private Map<PeriodFilterTypeEnum, PowerUsagePeriod> loadPowerUsage(List<SensorData> rows, PeriodDecoded dts,
			LocalDateTime referenceDate, PeriodFilterTypeEnum[] tipi) {

		Map<PeriodFilterTypeEnum, PowerUsagePeriod> mapPowUsage = new HashMap<>();
		for (PeriodFilterTypeEnum tipo : tipi) {
			PeriodFilter periodFilter = new PeriodFilter();
			periodFilter.setReferenceDate(referenceDate);
			periodFilter.setTipoFiltro(tipo);
			periodFilter.setUserDefFrom(dts.getFrom());
			periodFilter.setUserDefTo(dts.getTo());

			PeriodDecoded pded = convertPeriodFilter(periodFilter);
			mapPowUsage.put(tipo, initializePup(pded));
		}

		LocalDate refData = referenceDate.toLocalDate();

		for (SensorData row : rows) {
			for (PeriodFilterTypeEnum tipo : tipi) {
				if (isPeriodo(tipo, refData, row.getDataEvento())) {
					addToPowerUsage(mapPowUsage.get(tipo), row);
				}
			}

		}
		return mapPowUsage;
	}

	private Map<PeriodFilterTypeEnum, PowerUsagePeriod> loadHistoricalPowerUsage(List<SensorData> rows,
			PeriodDecoded dts, LocalDateTime referenceDate, PeriodFilterTypeEnum tipo) {

		Map<PeriodFilterTypeEnum, PowerUsagePeriod> mapPowUsage = new HashMap<>();
		PeriodFilter periodFilter = new PeriodFilter();
		periodFilter.setReferenceDate(referenceDate);
		periodFilter.setTipoFiltro(tipo);
		periodFilter.setUserDefFrom(dts.getFrom());
		periodFilter.setUserDefTo(dts.getTo());

		PeriodDecoded pded = convertPeriodFilter(periodFilter);
		mapPowUsage.put(tipo, initializePup(pded));

		LocalDate refData = referenceDate.toLocalDate();

		for (SensorData row : rows) {
			addToHistoricalPowerUsage(mapPowUsage.get(tipo), row);

		}
		return mapPowUsage;
	}

	private PowerUsagePeriod initializePup(PeriodDecoded pded) {
		PowerUsagePeriod pup = new PowerUsagePeriod();
		pup.setPeriodType(pded.getTipoFiltro());
		pup.setPeriodDecoded(pded);
		pup.setMinValue(null);
		pup.setAvgValue(null);
		pup.setMaxValue(null);

		pup.setItLoadSum(BigDecimal.ZERO);
		pup.setPeakLoadSum(BigDecimal.ZERO);
		pup.setSumPue(BigDecimal.ZERO);
		pup.setRowCount(0);
		return pup;

	}

	private void addToPowerUsage(PowerUsagePeriod pup, SensorData sensorData) {

		Map<String, BigDecimal> detailData = sensorDataFactory.convertToSensorPowerMeasures(sensorData);
		BigDecimal pue = detailData.get("PUE");
		BigDecimal peakLoad = detailData.get("MM_E_Meter_SB_Power_Distribution");
		BigDecimal itLoad = detailData.get("MM_E_Meter_SB_IT_Load");
		if (pue != null) {
			if (pup.getMaxValue() == null || pue.compareTo(pup.getMaxValue()) > 0) {
				pup.setMaxValue(pue);
			}
			if (pup.getMinValue() == null || pue.compareTo(pup.getMinValue()) < 0) {
				pup.setMinValue(pue);
			}

			pup.setSumPue(pup.getSumPue().add(pue));
			pup.setRowCount(pup.getRowCount() + 1);
			pup.setAvgValue(pup.getSumPue().divide(BigDecimal.valueOf(pup.getRowCount()), 6, RoundingMode.HALF_UP));
		}
		if (peakLoad != null) {
			pup.setPeakLoadSum(peakLoad.add(pup.getPeakLoadSum()));
		}
		if (itLoad != null) {
			pup.setItLoadSum(itLoad.add(pup.getItLoadSum()));
		}
		// {MM_E_Meter_SB_Power_Distribution=2.8735, MM_E_Meter_SB_IT_Load=10.91325,
		// MM_E_Meter_SB_Gen=7.6535, MM_E_Meter_SB_Solar=8.36675,
		// MM_E_Meter_SB_Lighting=4.26025, MM_E_Meter_SB_UPS=11.49875,
		// MM_E_Meter_SB_Cooling_Chiller=7.56625, MM_E_Meter_SB_Cooling_Crac=4.42175,
		// MM_E_Meter_SB_Cooling_Fan=9.626, MM_E_Meter_SB_Cooling_Gen=7.86225,
		// PUE=0.26330378209974115}
	}

	private void addToHistoricalPowerUsage(PowerUsagePeriod pup, SensorData sensorData) {

		Map<String, BigDecimal> detailData = sensorDataFactory.convertToSensorPowerMeasures(sensorData);
		BigDecimal pueMin = detailData.get("PUE_MIN");
		BigDecimal pueAvg = detailData.get("PUE_AVG");
		BigDecimal pueMax = detailData.get("PUE_MAX");

		pup.setMinValue(pueMin.setScale(3,RoundingMode.HALF_DOWN));
		pup.setAvgValue(pueAvg.setScale(3,RoundingMode.HALF_DOWN));
		pup.setMaxValue(pueMax.setScale(3,RoundingMode.HALF_DOWN));
	}

	private boolean isPeriodo(PeriodFilterTypeEnum tipoPeriodo, LocalDate refData, LocalDateTime dataEvento) {
		boolean isPeriodo = false;
		switch (tipoPeriodo) {
		case USERDEFINED:
			isPeriodo = true;
			break;
		case DAY:
			if (refData.compareTo(dataEvento.toLocalDate()) == 0) {
				isPeriodo = true;
			}
			break;
		case MONTLY:
			Long monthsBetween = ChronoUnit.MONTHS.between(refData, dataEvento.toLocalDate());
			if (monthsBetween <= 1) {
				isPeriodo = true;
			}
			break;
		case QUARTERLY:
			int quarter = refData.get(IsoFields.QUARTER_OF_YEAR)
					- dataEvento.toLocalDate().get(IsoFields.QUARTER_OF_YEAR);
			if (quarter == 0) {
				isPeriodo = true;
			}
			break;
		case WEEK:
			int week = refData.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
					- dataEvento.toLocalDate().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
			if (week == 0) {
				isPeriodo = true;
			}
			break;
		case YEAR:
			int year = refData.getYear() - dataEvento.toLocalDate().getYear();
			if (year == 0) {
				isPeriodo = true;
			}
			break;
		case NULL:
			break;
		default:
			break;

		}
		return isPeriodo;
	}

	private List<PeriodFilterTypeEnum> getPeriodsOfEvent(LocalDate refData, LocalDateTime dataEvento) {
		List<PeriodFilterTypeEnum> list = new ArrayList<>();
		if (refData.compareTo(dataEvento.toLocalDate()) == 0) {
			list.add(PeriodFilterTypeEnum.DAY);
		}
		int week = refData.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
				- dataEvento.toLocalDate().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
		if (week == 0) {
			list.add(PeriodFilterTypeEnum.WEEK);
		}
		Long monthsBetween = ChronoUnit.MONTHS.between(refData, dataEvento.toLocalDate());
		if (monthsBetween <= 1) {
			list.add(PeriodFilterTypeEnum.MONTLY);
		}
		int quarter = refData.get(IsoFields.QUARTER_OF_YEAR) - dataEvento.toLocalDate().get(IsoFields.QUARTER_OF_YEAR);
		if (quarter == 0) {
			list.add(PeriodFilterTypeEnum.QUARTERLY);
		}

		int year = refData.getYear() - dataEvento.toLocalDate().getYear();
		if (year == 0) {
			list.add(PeriodFilterTypeEnum.YEAR);
		}

		return list;
	}

	private PeriodDecoded convertPeriodFilter(PeriodFilter periodFilter) {
		PeriodDecoded pd = new PeriodDecoded();
		pd.setTipoFiltro(periodFilter.getTipoFiltro());
		pd.setRef(periodFilter.getReferenceDate());
		LocalDateTime[] dts = new LocalDateTime[2];
		String dsPeriodo = "";

		LocalDateTime refDateTime = periodFilter.getReferenceDate();
		LocalDate refDate = refDateTime.toLocalDate();

		// TODO Auto-generated method stub
		switch (periodFilter.getTipoFiltro()) {
		case USERDEFINED:
			dts[0] = periodFilter.getUserDefFrom();
			dts[1] = periodFilter.getUserDefTo();
			break;
		case DAY:
			dts[0] = LocalDateTime.of(refDate, LocalTime.MIN);
			dts[1] = LocalDateTime.of(refDate, LocalTime.MAX);
			break;
		case WEEK:
			// dts[0] = LocalDateTime.of(refDate.with(DayOfWeek.MONDAY), LocalTime.MIN);
			// dts[1] = LocalDateTime.of(refDate, LocalTime.MAX);
			dts[0] = LocalDateTime.of(refDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)), LocalTime.MIN);
			dts[1] = LocalDateTime.of(refDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)), LocalTime.MAX);

			break;
		case MONTLY:
			dts[0] = LocalDateTime.of(refDate.withDayOfMonth(1), LocalTime.MIN);
			dts[1] = LocalDateTime.of(refDate.withDayOfMonth(refDate.getMonth().length(refDate.isLeapYear())),
					LocalTime.MAX);
			break;
		case QUARTERLY:
			dts[0] = LocalDateTime.of(refDate.withDayOfMonth(1).minusMonths(2), LocalTime.MIN);
			dts[1] = LocalDateTime.of(refDate.withDayOfMonth(refDate.getMonth().length(refDate.isLeapYear())),
					LocalTime.MAX);
			break;
		case YEAR:
			dts[0] = LocalDateTime.of(refDate.minusYears(1), LocalTime.MIN);
			dts[1] = LocalDateTime.of(refDate.withMonth(12).withDayOfMonth(31), LocalTime.MAX);
			break;
		case NULL:
			dts[0] = refDateTime;
			dts[1] = refDateTime;
			break;
		default:
			dts[0] = refDateTime;
			dts[1] = refDateTime;
			break;

		}

		pd.setFrom(dts[0]);
		pd.setTo(dts[1]);

		return pd;
	}

}
