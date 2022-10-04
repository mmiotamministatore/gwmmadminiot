package it.mm.iot.gw.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import it.mm.iot.gw.admin.service.dto.InfoSensorOutput;
import it.mm.iot.gw.admin.service.dto.InfoSensorRequest;
import it.mm.iot.gw.admin.service.dto.InfoUserOutput;
import it.mm.iot.gw.admin.service.dto.InfoUserRequest;
import it.mm.iot.gw.admin.service.exception.SeverityEnum;
import it.mm.iot.gw.admin.service.feign.IotPlatformService;
import it.mm.iot.gw.admin.service.feign.dto.IoTPlatformOutputMessage;
import it.mm.iot.gw.admin.service.feign.dto.StatusResponseIoTEnum;
import it.mm.iot.gw.admin.service.model.AssetFactory;
import it.mm.iot.gw.admin.service.model.AssetTree;
import it.mm.iot.gw.admin.service.model.TenantInfo;
import it.mm.iot.gw.admin.service.model.asset.Contenitore;
import it.mm.iot.gw.admin.service.model.asset.Organizzazione;
import it.mm.iot.gw.admin.service.model.asset.Pagina;
import it.mm.iot.gw.admin.service.model.asset.Sensore;
import it.mm.iot.gw.admin.service.model.asset.SensoreAmbienteParete;
import it.mm.iot.gw.admin.service.model.asset.SensoreArmadioRack;
import it.mm.iot.gw.admin.service.model.asset.SensoreEnergia;
import it.mm.iot.gw.admin.service.model.asset.SensoreStazioneMeteo;
import it.mm.iot.gw.admin.service.model.asset.Sito;
import it.mm.iot.gw.admin.service.model.event.SensorData;

@Service
@Validated
public class SensorService extends AbstractService {
	
    @Value("${app.iotmm.tenant.id:maticmind}")
    private String tenantId;
    @Value("${app.iotmm.tenant.description:MATICMIND}")
    private String tenantDescription;

    @Autowired
    private AssetFactory assetFactory;
    
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
}
