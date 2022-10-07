package it.mm.iot.gw.admin.service.model.event;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import it.mm.iot.gw.admin.web.events.IoTEvent;
import it.mm.iot.gw.admin.web.events.IoTMeasureData;
import it.mm.iot.gw.admin.web.events.IoTSubscribeUser;
import it.mm.iot.gw.admin.web.events.IoTUnsubscribeUser;

@Component
public class AssetEventManager {

	private static final Logger log = LoggerFactory.getLogger(AssetEventManager.class);

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	SensorDataFactory sensorDataFactory;

	private Map<String, AssetDataEvent> assets = new HashMap<>();

	public void addAsset(AssetDataEvent asset) {
		assets.put(asset.getRef().getIdDispositivo(), asset);
	}

	public void subscribe(String user, LocalDateTime expDate, String assetId) {
		IoTSubscribeUser newSub = new IoTSubscribeUser();
		newSub.setUserName(user);
		newSub.setExpirationDate(expDate);
		IoTEvent<IoTSubscribeUser> eventIot = new IoTEvent<IoTSubscribeUser>(this, newSub);
		publisher.publishEvent(eventIot);
	}

	public void unsubscribe(String user) {
		IoTUnsubscribeUser unsUser = new IoTUnsubscribeUser();
		unsUser.setUserName(user);
		IoTEvent<IoTUnsubscribeUser> eventIot = new IoTEvent<IoTUnsubscribeUser>(this, unsUser);
		publisher.publishEvent(eventIot);
	}

	public void sendMessageUserSubscribed(String data) {

		SensorData sensorData = sensorDataFactory.convertToSensorData(data);
		// log.info("Dati Corretti: " + sensorData);
		List<SensorMeasure> detailData = sensorDataFactory.convertToSensorMeasures(sensorData);
		sensorData.setDetailData(detailData);

		IoTMeasureData evtdata = new IoTMeasureData();
		evtdata.setData(data);
		evtdata.setDetailData(detailData);
		IoTEvent<SensorData> eventIot = new IoTEvent<SensorData>(this, sensorData);
		publisher.publishEvent(eventIot);

	}

	public void executeData(String data) {
		SensorData sensorData = sensorDataFactory.convertToSensorData(data);
		// log.info("Dati Corretti: " + sensorData);
		List<SensorMeasure> detailData = sensorDataFactory.convertToSensorMeasures(sensorData);
		sensorData.setDetailData(detailData);

		IoTMeasureData evtdata = new IoTMeasureData();
		evtdata.setDetailData(detailData);
		evtdata.setData(data);
		IoTEvent<SensorData> eventIot = new IoTEvent<SensorData>(this, sensorData);
		publisher.publishEvent(eventIot);

	}
}
