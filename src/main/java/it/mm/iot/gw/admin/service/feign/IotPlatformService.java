package it.mm.iot.gw.admin.service.feign;

import java.time.LocalDateTime;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.mm.iot.gw.admin.config.OAuthFeignConfigIoTPlatform;

@FeignClient(name = "iotmm-platformservice", url = "${app.iot.platform.urlbase:http://10.92.1.2:8090}", configuration = OAuthFeignConfigIoTPlatform.class)
public interface IotPlatformService {
	@GetMapping(value = "/api/v1/analysis/events", consumes = "application/json")
	public String getEvents(@RequestParam(name = "t", required = true) String tenant,
			@RequestParam(name = "from", required = false) LocalDateTime from,
			@RequestParam(name = "to", required = false) LocalDateTime to,
			@RequestParam(name = "s", required = false) String sensore,
			@RequestParam(name = "et", required = false) String eventType);

	@GetMapping(value = "/api/v1/analysis/sensors", consumes = "application/json")
	public String getSensors(@RequestParam(name = "t", required = true) String tenant,
			@RequestParam(name = "s", required = false) String sensore);

}
