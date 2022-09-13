package it.mm.iot.gw.admin.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import it.mm.iot.gw.admin.service.dto.InfoUserOutput;
import it.mm.iot.gw.admin.service.dto.InfoUserRequest;
import it.mm.iot.gw.admin.service.exception.SeverityEnum;
import it.mm.iot.gw.admin.web.rest.dto.InfoUserMessage;

@Service
@Validated
public class UserService extends AbstractService {
	public InfoUserOutput getInfoUser(InfoUserRequest infoUserRequest) {
		/** Lettura info */
		InfoUserOutput infoUser=new InfoUserOutput();
		issueOperationFactory.addIssue(SeverityEnum.INFORMATION, "IOT-I0001", "Prova");
		infoUser.setTenant(infoUserRequest.getTenant());
		return infoUser;
	}

}
