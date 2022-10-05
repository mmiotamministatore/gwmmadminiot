package it.mm.iot.gw.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import it.mm.iot.gw.admin.service.dto.InfoListSensorOutput;
import it.mm.iot.gw.admin.service.dto.InfoListSensorRequest;
import it.mm.iot.gw.admin.service.dto.InfoSensorOutput;
import it.mm.iot.gw.admin.service.dto.InfoSensorRequest;
import it.mm.iot.gw.admin.service.feign.IotPlatformService;
import it.mm.iot.gw.admin.service.feign.dto.IoTPlatformOutputMessage;
import it.mm.iot.gw.admin.service.feign.dto.StatusResponseIoTEnum;
import it.mm.iot.gw.admin.service.model.AssetFactory;
import it.mm.iot.gw.admin.service.model.event.SensorData;

@Service
@Validated
public class SensorService extends AbstractService {
	
    @Value("${app.iotmm.tenant.id:maticmind}")
    private String tenantId;
    @Value("${app.iotmm.tenant.description:MATICMIND}")
    private String tenantDescription;

    @Autowired
	private IotPlatformService iotPlatformService;

    public InfoSensorOutput getInfoSensor(InfoSensorRequest infoSensorRequest) {

        /** Lettura info */
        InfoSensorOutput infoSensor = new InfoSensorOutput();

        
        IoTPlatformOutputMessage ritorno=iotPlatformService.getSensors(tenantId, infoSensorRequest.getSensorId());
        if(ritorno.getResult()==StatusResponseIoTEnum.SUCCESS) {

			for (SensorData row : ritorno.getRows()) {
				infoSensor.setSensorData(row);
			}
        }
       

        return infoSensor;
    }
    
    public InfoListSensorOutput getListInfoSensor(InfoListSensorRequest infoListSensorRequest) {
    	List<SensorData>lista=new ArrayList<SensorData>();
    	for (String sensorId : infoListSensorRequest.getSensorListId()) {
            IoTPlatformOutputMessage ritorno=iotPlatformService.getSensors(tenantId, sensorId);
            if(ritorno.getResult()==StatusResponseIoTEnum.SUCCESS && ritorno.getCount()>0) {

    			for (SensorData row : ritorno.getRows()) {
    				lista.add(row);
    			}
            }
           
			
		}
    	InfoListSensorOutput out=new InfoListSensorOutput();
    	out.setSensorListData(lista);

        return out;
    }
}
