package it.mm.iot.gw.admin.service.feign;

import java.time.LocalDateTime;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import it.mm.iot.gw.admin.config.OAuthFeignConfigIoTPlatform;
import it.mm.iot.gw.admin.service.feign.dto.IoTPlatformOutputMessage;

@FeignClient(name = "iotmm-platformservice", url = "${app.iot.platform.urlbase:http://10.92.1.2:8090}", configuration = OAuthFeignConfigIoTPlatform.class)
public interface IotPlatformService {
	@GetMapping(value = "/api/v1/analysis/events", consumes = "application/json")
	public IoTPlatformOutputMessage getEvents(@RequestParam(name = "t", required = true) String tenant,
			@RequestParam(name = "from", required = false) 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
			LocalDateTime from,
			
			@RequestParam(name = "to", required = false) 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
			LocalDateTime to,
			
			@RequestParam(name = "s", required = false) String sensore,
			@RequestParam(name = "et", required = false) String eventType);

	@GetMapping(value = "/api/v1/analysis/sensors", consumes = "application/json")
	public IoTPlatformOutputMessage getSensors(@RequestParam(name = "t", required = true) String tenant,
			@RequestParam(name = "s", required = false) String sensore);
	
	@GetMapping(value = "/api/v1/analysis/sensor/{s}/data", consumes = "application/json")
	public IoTPlatformOutputMessage getSensorData(
			@RequestParam(name = "t", required = true) String tenant,
			@PathVariable(name = "s", required = true) String sensore,
			@RequestParam(name = "from", required = true) 
	 		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
			LocalDateTime from,
			@RequestParam(name = "to", required = true) 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
			LocalDateTime to);

	@GetMapping(value = "/api/v1/analysis/power_usage", consumes = "application/json")
	public IoTPlatformOutputMessage getPowerUsage(@RequestParam(name = "t", required = true) String tenant,
			@RequestParam(name = "from", required = true) 
	 		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
			LocalDateTime from,
			@RequestParam(name = "to", required = true) 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
			LocalDateTime to);

	@GetMapping(value = "/api/v1/analysis/power_usage_stats/{period}", consumes = "application/json")
	public IoTPlatformOutputMessage getPowerUsageStat(
			@PathVariable(name = "period", required = true) String period,
			@RequestParam(name = "t", required = true) String tenant,
			@RequestParam(name = "from", required = true) 
	 		@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
			LocalDateTime from,
			@RequestParam(name = "to", required = true) 
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
			LocalDateTime to);

}
