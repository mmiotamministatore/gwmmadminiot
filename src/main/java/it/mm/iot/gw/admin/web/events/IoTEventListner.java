package it.mm.iot.gw.admin.web.events;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import it.mm.iot.gw.admin.web.websocket.IoTService;

@Component
public class IoTEventListner {

	private static final Logger log = LoggerFactory.getLogger(IoTEventListner.class);
	
	private Map<String, IoTSubscribeUser> userConn=new ConcurrentHashMap<String, IoTSubscribeUser>();
	
	@Autowired
	private IoTService iotService;

	// @EventListener(event.getData().getClass())
	// @EventListener
	@EventListener(condition = "#event.data.class.simpleName=='IoTSubscribeUser'")
	@Order(2)
	public void onApplicationEventIoTSubscribeUser(@NonNull IoTEvent event) {
		IoTSubscribeUser evtUser=(IoTSubscribeUser) event.getData();
		log.info("Subscribe USER"+evtUser.getUserName());
		userConn.put(evtUser.getUserName(), evtUser);
		System.out.println("Received spring generic event - " + event.getData().getClass().getSimpleName() + " "
				+ event.getData().getClass().getTypeName());
	}

	@EventListener(condition = "#event.data.class.simpleName=='IoTUnsubscribeUser'")
	@Order(1)
	public void onApplicationEventIoTUnsubscribeUser(@NonNull IoTEvent event) {
		IoTUnsubscribeUser evtUser=(IoTUnsubscribeUser) event.getData();
		log.info("UNSubscribe USER"+evtUser.getUserName());
		userConn.remove(evtUser.getUserName());
		System.out.println("Received spring generic event - " + event.getData().getClass().getSimpleName() + " "
				+ event.getData().getClass().getTypeName());
	}

	@EventListener(condition = "#event.data.class.simpleName=='IoTMeasureData'")
	@Order(3)
	public void onApplicationEventIoTMeasureData(@NonNull IoTEvent event) {
		
		log.info("onApplicationEventIoTMeasureData Evento ricevuto: da girare agli utenti");
		// this.messagingTemplate.convertAndSendToUser(
		// "biro", "/topic/iot/secure/data/user", args.get(0));
		//String user = "4a3e1029-7943-49f6-9c21-8a32e83cbfd0";
		LocalDateTime now = LocalDateTime.now();
		for (Entry<String, IoTSubscribeUser> entry : userConn.entrySet()) {
			IoTSubscribeUser userEvt=entry.getValue();
		
			log.info("onApplicationEventIoTMeasureData: Per ogni utente: "+userEvt);
			System.out.println("Received spring generic event - " + event.getData().getClass().getSimpleName() + " "
					+ event.getData().getClass().getTypeName());
			
		    if (!now.isAfter(userEvt.getExpirationDate())) {
		    	iotService.sendMessageToUser(userEvt.getUserName(), event.getData());
		    	
		    }

		}
		//iotService.sendMessageToUser(user, event.getData());

	}

}