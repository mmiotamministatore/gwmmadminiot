package it.mm.iot.gw.admin.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.ObjectMapper;

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
import it.mm.iot.gw.admin.service.model.event.SensorMeasure;

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

		LocalDateTime[] dts = convertPeriodFilter(powerUsageRequest.getPeriodFilter());

		IoTPlatformOutputMessage ritorno = iotPlatformService.getPowerUsage(tenantId, dts[0], dts[1]);
		if (ritorno != null && ritorno.getResult() == StatusResponseIoTEnum.SUCCESS) {

			if (ritorno.getCount() > 0) {
				powerUsage=loadHistoricalPowerUsage(ritorno.getRows(),
						powerUsageRequest.getPeriodFilter().getReferenceDate(), new PeriodFilterTypeEnum[]{PeriodFilterTypeEnum.USERDEFINED});
			}
		}

		PowerUsageOutput powerUsageOutput = new PowerUsageOutput();
		powerUsageOutput.setPowerUsage(powerUsage);
		return powerUsageOutput;
	}

	public PowerUsageOutput getHistoricalPowerUsage(PowerUsageRequest powerUsageRequest) {

		Map<PeriodFilterTypeEnum, PowerUsagePeriod> powerUsage = new HashMap<>();

		LocalDateTime[] dts = convertPeriodFilter(powerUsageRequest.getPeriodFilter());

		IoTPlatformOutputMessage ritorno = iotPlatformService.getPowerUsage(tenantId, dts[0], dts[1]);
		if (ritorno != null && ritorno.getResult() == StatusResponseIoTEnum.SUCCESS) {

			if (ritorno.getCount() > 0) {
				powerUsage=loadHistoricalPowerUsage(ritorno.getRows(),
						powerUsageRequest.getPeriodFilter().getReferenceDate(), new PeriodFilterTypeEnum[]{
								PeriodFilterTypeEnum.DAY,
								PeriodFilterTypeEnum.WEEK,
								PeriodFilterTypeEnum.MONTLY,
								PeriodFilterTypeEnum.QUARTERLY});
			}
		}

		PowerUsageOutput powerUsageOutput = new PowerUsageOutput();
		powerUsageOutput.setPowerUsage(powerUsage);
		return powerUsageOutput;
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

	private Map<PeriodFilterTypeEnum,PowerUsagePeriod> loadHistoricalPowerUsage(List<SensorData> rows, LocalDateTime referenceDate,
			PeriodFilterTypeEnum[] tipi) {

		Map<PeriodFilterTypeEnum,PowerUsagePeriod> mapPowUsage=new HashMap<>();
		for (PeriodFilterTypeEnum tipo : tipi) {
			mapPowUsage.put(tipo, initializePup(tipo));
		}

		LocalDate refData = referenceDate.toLocalDate();

		for (SensorData row : rows) {
			for (PeriodFilterTypeEnum tipo : tipi) {
				if(isPeriodo(tipo, refData, row.getDataEvento())) {
					addToPowerUsage(mapPowUsage.get(tipo), row);
				}
			}

		}
		return mapPowUsage;
	}
	private PowerUsagePeriod initializePup(PeriodFilterTypeEnum periodFilterType) {
		PowerUsagePeriod pup = new PowerUsagePeriod();
		pup.setPeriodType(periodFilterType);
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
	
		Map<String, BigDecimal>  detailData = sensorDataFactory.convertToSensorPowerMeasures(sensorData);
		BigDecimal pue=detailData.get("PUE");
		BigDecimal peakLoad=detailData.get("MM_E_Meter_SB_Power_Distribution");
		BigDecimal itLoad=detailData.get("MM_E_Meter_SB_IT_Load");
		if(pup.getMaxValue()==null || pue.compareTo(pup.getMaxValue())>0) {
			pup.setMaxValue(pue);
		}
		if(pup.getMinValue()==null || pue.compareTo(pup.getMinValue())<0) {
			pup.setMinValue(pue);
		}
		
		pup.setSumPue(pup.getSumPue().add(pue));
		pup.setRowCount(pup.getRowCount()+1); 
		pup.setAvgValue(pup.getSumPue().divide(BigDecimal.valueOf(pup.getRowCount()),6,RoundingMode.HALF_UP));
		pup.setPeakLoadSum(peakLoad.add(pup.getPeakLoadSum()));
		pup.setItLoadSum(itLoad.add(pup.getItLoadSum()));
		//{MM_E_Meter_SB_Power_Distribution=2.8735, MM_E_Meter_SB_IT_Load=10.91325, MM_E_Meter_SB_Gen=7.6535, MM_E_Meter_SB_Solar=8.36675, MM_E_Meter_SB_Lighting=4.26025, MM_E_Meter_SB_UPS=11.49875, MM_E_Meter_SB_Cooling_Chiller=7.56625, MM_E_Meter_SB_Cooling_Crac=4.42175, MM_E_Meter_SB_Cooling_Fan=9.626, MM_E_Meter_SB_Cooling_Gen=7.86225, PUE=0.26330378209974115}
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

	private LocalDateTime[] convertPeriodFilter(PeriodFilter periodFilter) {
		LocalDateTime[] dts = new LocalDateTime[2];

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
			dts[0] = LocalDateTime.of(refDate.minusWeeks(1), LocalTime.MIN);
			dts[1] = LocalDateTime.of(refDate, LocalTime.MAX);

			break;
		case MONTLY:
			dts[0] = LocalDateTime.of(refDate.minusMonths(1), LocalTime.MIN);
			dts[1] = LocalDateTime.of(refDate, LocalTime.MAX);
			break;
		case QUARTERLY:
			dts[0] = LocalDateTime.of(refDate.minusMonths(4), LocalTime.MIN);
			dts[1] = LocalDateTime.of(refDate, LocalTime.MAX);
			break;
		case YEAR:
			dts[0] = LocalDateTime.of(refDate.minusYears(1), LocalTime.MIN);
			dts[1] = LocalDateTime.of(refDate, LocalTime.MAX);
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

		return dts;
	}

}
