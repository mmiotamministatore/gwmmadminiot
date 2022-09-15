package it.mm.iot.gw.admin.web.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
//import org.springframework.web.util.HtmlUtils;

@Controller
public class WSController {
	private final SimpMessagingTemplate simpMessagingTemplate;

	public WSController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
	public void sendSensor(String sensor) {
        simpMessagingTemplate.convertAndSend("/topic/data", sensor);
    }

}