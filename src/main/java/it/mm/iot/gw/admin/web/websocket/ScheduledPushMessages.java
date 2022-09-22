package it.mm.iot.gw.admin.web.websocket;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledPushMessages {

    @Autowired
    private IoTService ioTService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    public ScheduledPushMessages(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Scheduled(fixedRate = 5000)
    public void sendMessage() {
        final String time = new SimpleDateFormat("HH:mm").format(new Date());
        //ioTService.sendMessage();
    }
}
