package it.mm.iot.gw.admin.service.model.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.mm.iot.gw.admin.web.events.IoTEvent;
import it.mm.iot.gw.admin.web.events.IoTMeasureData;
import it.mm.iot.gw.admin.web.events.IoTSubscribeUser;

@Component
public class AssetEventManager {

	private static final Logger log = LoggerFactory.getLogger(AssetEventManager.class);

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private ObjectMapper mapper;

	private Map<String, AssetDataEvent> assets = new HashMap<>();

	public void addAsset(AssetDataEvent asset) {
		assets.put(asset.getRef().getIdDispositivo(), asset);
	}

	public void subscribe(String user, String assetId) {
		IoTSubscribeUser newSub= new IoTSubscribeUser();
		newSub.setUserName(user);
		IoTEvent<IoTSubscribeUser> eventIot = new IoTEvent<IoTSubscribeUser>(this, newSub);
		publisher.publishEvent(eventIot);
	}

	public void executeData(String data) {
		try {
			SensorData sensorData = mapper.readValue(data, SensorData.class);
			// log.info("Dati Corretti: " + sensorData);
			List<SensorMeasure> detailData = mapper.readValue(sensorData.getJsonMessage(),
					mapper.getTypeFactory().constructCollectionType(List.class, SensorMeasure.class));
			sensorData.setDetailData(detailData);

			IoTMeasureData evtdata= new IoTMeasureData();
			evtdata.setDetailData(detailData);
			IoTEvent<IoTMeasureData> eventIot = new IoTEvent<IoTMeasureData>(this, evtdata);
			publisher.publishEvent(eventIot);
			
			//publisher.publishCustomEvent(data);
			// log.info("Dati Corretti: " + detailData);
			// if(detailData instanceof List) {
			//
			// List<Map> lista=(List) detailData;
			// for (Map message : lista) {
			// log.info("Dati Corretti: "+message);
			// }
			// }
			// else {
			//
			// }

		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			log.info("Dati non corretti: " + data);
			e.printStackTrace();
		}
	}
}
