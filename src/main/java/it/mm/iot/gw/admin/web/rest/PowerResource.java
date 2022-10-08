package it.mm.iot.gw.admin.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.mm.iot.gw.admin.service.PowerService;
import it.mm.iot.gw.admin.service.dto.PowerUsageOutput;
import it.mm.iot.gw.admin.service.dto.PowerUsageRequest;
import it.mm.iot.gw.admin.web.message.OutputMessageFactory;
import it.mm.iot.gw.admin.web.rest.dto.PowerUsageMessage;

@RestController
@RequestMapping("/api/iotgw/v1/power")
public class PowerResource extends AbstractRestService {

	@Autowired
	private PowerService powerService;

	private final Logger log = LoggerFactory.getLogger(PowerResource.class);

	@PostMapping("/usage")
	// @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
	public ResponseEntity<PowerUsageMessage<PowerUsageOutput>> getPowerUsage(
			@RequestBody PowerUsageRequest powerUsageRequest) {
		PowerUsageOutput out = powerService.getPowerUsage(powerUsageRequest);

		return new ResponseEntity<>(
				OutputMessageFactory.create(PowerUsageMessage.class, out, getOperationOutcomeAndRemove()),
				HttpStatus.OK);
	}

}
