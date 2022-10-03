package it.mm.iot.gw.admin.web.events;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class IoTEventListner {

	//@EventListener(event.getData().getClass())
	//@EventListener
	@EventListener(condition = "#event.data.class.simpleName=='IoTSubscribeUser'")
	@Order(1)
	public void onApplicationEventIoTSubscribeUser(@NonNull IoTEvent event) {
		System.out.println("Received spring generic event - " + event.getData().getClass().getSimpleName()+" "+event.getData().getClass().getTypeName());
		
		
	}
	@EventListener(condition = "#event.data.class.simpleName=='IoTMeasureData'")
	@Order(1)
	public void onApplicationEventIoTMeasureData(@NonNull IoTEvent event) {
		System.out.println("Received spring generic event - " + event.getData().getClass().getSimpleName()+" "+event.getData().getClass().getTypeName());
		
		
	}

}