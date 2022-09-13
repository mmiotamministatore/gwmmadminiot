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

import it.mm.iot.gw.admin.service.UserService;
import it.mm.iot.gw.admin.service.dto.InfoUserOutput;
import it.mm.iot.gw.admin.service.dto.InfoUserRequest;
import it.mm.iot.gw.admin.web.message.OutputMessageFactory;
import it.mm.iot.gw.admin.web.rest.dto.InfoUserMessage;

@RestController
@RequestMapping("/api/iotgw/v1/user")
public class UserTenantResource extends AbstractRestService {

	@Autowired
	private UserService userService;

	private final Logger log = LoggerFactory.getLogger(UserTenantResource.class);

	@PostMapping("/info")
	public ResponseEntity<InfoUserMessage<InfoUserOutput>> getUserInfoComplete(
			@RequestBody InfoUserRequest infoUserRequest) {

		InfoUserOutput out = userService.getInfoUser(infoUserRequest);

		return new ResponseEntity<>(
				OutputMessageFactory.create(InfoUserMessage.class, out, getOperationOutcomeAndRemove()), HttpStatus.OK);
	}
}
