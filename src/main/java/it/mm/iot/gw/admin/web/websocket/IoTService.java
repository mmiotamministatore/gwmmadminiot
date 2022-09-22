package it.mm.iot.gw.admin.web.websocket;

import it.mm.iot.gw.admin.security.AuthenticationFacade;
import it.mm.iot.gw.admin.service.dto.InfoUserOutput;
import it.mm.iot.gw.admin.web.message.OutputPayload;
import it.mm.iot.gw.admin.web.rest.AbstractRestService;
import it.mm.iot.gw.admin.web.websocket.dto.ActivityDTO;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
public class IoTService extends AbstractRestService implements ApplicationListener<SessionDisconnectEvent> {

    private static final Logger log = LoggerFactory.getLogger(IoTService.class);

    @Autowired
    private AuthenticationFacade authentication;

    private final SimpMessageSendingOperations messagingTemplate;

    public IoTService(SimpMessageSendingOperations messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /*
	@MessageMapping("/topic/iot/secure/activity")
	@SendTo("/topic/iot/secure/data")
	public ActivityDTO sendActivity(@Payload ActivityDTO activityDTO, StompHeaderAccessor stompHeaderAccessor,
			Principal principal) {
		activityDTO.setUserLogin(principal.getName());
		activityDTO.setSessionId(stompHeaderAccessor.getSessionId());
		activityDTO.setIpAddress(stompHeaderAccessor.getSessionAttributes().get(IP_ADDRESS).toString());
		activityDTO.setTime(Instant.now());
		log.debug("Sending user tracking data {}", activityDTO);
		return activityDTO;
	}
	*/

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        log.info("ECCOLO onApplicationEvent" + ToStringBuilder.reflectionToString(event));
        ActivityDTO activityDTO = new ActivityDTO();
        activityDTO.setSessionId(event.getSessionId());
        activityDTO.setPage("logout");
        messagingTemplate.convertAndSend("/topic/iot/secure/data", activityDTO);
    }

    public void sendMessage(Object jsonMsg) {
        messagingTemplate.convertAndSend("/topic/iot/secure/data", jsonMsg);
    }

    @MessageMapping("/topic/iot/secure/activity/user")
    @SendToUser("/topic/iot/secure/data/user")
    public Object sendDataToUser(@Payload Object jsonMsg, Principal principal) {
        log.info("ECCOLO sendDataToUser:" + authentication.getAuthentication().getName());
        return jsonMsg;
    }

    @MessageMapping("/queue/iot/secure/datas")
    //@SendToUser("/queue/iot/secure/data")
    public OutputPayload sendMessage2() {
        final String time = new SimpleDateFormat("HH:mm").format(new Date());
        InfoUserOutput out = new InfoUserOutput();
        // OutputMessageFactory.create(InfoUserMessage.class, out,
        // getOperationOutcomeAndRemove(), HttpStatus.OK);
        String user = "4a3e1029-7943-49f6-9c21-8a32e83cbfd0";
        messagingTemplate.convertAndSendToUser(user, "/queue/iot/secure/data", out);
        return out;
    }

    @MessageMapping("/queue/iot/secure/data")
    //@SendToUser("/queue/iot/secure/data")
    public OutputPayload sendMessageToUser(String user, Object message) {
        final String time = new SimpleDateFormat("HH:mm").format(new Date());
        InfoUserOutput out = new InfoUserOutput();
        // OutputMessageFactory.create(InfoUserMessage.class, out,
        // getOperationOutcomeAndRemove(), HttpStatus.OK);
        //String user="4a3e1029-7943-49f6-9c21-8a32e83cbfd0";
        messagingTemplate.convertAndSendToUser(user, "/queue/iot/secure/data", message);
        return out;
    }
}
