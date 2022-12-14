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

import it.mm.iot.gw.admin.service.SensorService;
import it.mm.iot.gw.admin.service.dto.DataListSensorRequest;
import it.mm.iot.gw.admin.service.dto.InfoListSensorOutput;
import it.mm.iot.gw.admin.service.dto.InfoListSensorRequest;
import it.mm.iot.gw.admin.service.dto.InfoSensorOutput;
import it.mm.iot.gw.admin.service.dto.InfoSensorRequest;
import it.mm.iot.gw.admin.web.message.OutputMessageFactory;
import it.mm.iot.gw.admin.web.rest.dto.InfoListSensorMessage;
import it.mm.iot.gw.admin.web.rest.dto.InfoSensorMessage;

@RestController
@RequestMapping("/api/iotgw/v1/sensor")
public class SensorResource extends AbstractRestService {

	@Autowired
	private SensorService sensorService;

	private final Logger log = LoggerFactory.getLogger(SensorResource.class);

	@PostMapping("/info")
	// @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
	public ResponseEntity<InfoSensorMessage<InfoSensorOutput>> getInfoSensor(
			@RequestBody InfoSensorRequest infoSensorRequest) {
		InfoSensorOutput out = sensorService.getInfoSensor(infoSensorRequest);

		return new ResponseEntity<>(
				OutputMessageFactory.create(InfoSensorMessage.class, out, getOperationOutcomeAndRemove()),
				HttpStatus.OK);
	}

	@PostMapping("/infos")
	// @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
	public ResponseEntity<InfoListSensorMessage<InfoListSensorOutput>> getInfosSensor(
			@RequestBody InfoListSensorRequest infoListSensorRequest) {
		InfoListSensorOutput out = sensorService.getListInfoSensor(infoListSensorRequest);

		return new ResponseEntity<>(
				OutputMessageFactory.create(InfoListSensorMessage.class, out, getOperationOutcomeAndRemove()),
				HttpStatus.OK);
	}

	@PostMapping("/data")
	// @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
	public ResponseEntity<InfoListSensorMessage<InfoListSensorOutput>> getDataSensors(
			@RequestBody DataListSensorRequest dataListSensorRequest) {
		InfoListSensorOutput out = sensorService.getListDataSensor(dataListSensorRequest);

		return new ResponseEntity<>(
				OutputMessageFactory.create(InfoListSensorMessage.class, out, getOperationOutcomeAndRemove()),
				HttpStatus.OK);
	}
}
