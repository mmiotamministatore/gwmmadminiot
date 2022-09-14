package it.mm.iot.gw.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import it.mm.iot.gw.admin.service.dto.InfoUserOutput;
import it.mm.iot.gw.admin.service.dto.InfoUserRequest;
import it.mm.iot.gw.admin.service.exception.SeverityEnum;
import it.mm.iot.gw.admin.service.model.AssetFactory;
import it.mm.iot.gw.admin.service.model.AssetTree;
import it.mm.iot.gw.admin.service.model.TenantInfo;
import it.mm.iot.gw.admin.service.model.asset.Contenitore;
import it.mm.iot.gw.admin.service.model.asset.Organizzazione;
import it.mm.iot.gw.admin.service.model.asset.Sensore;
import it.mm.iot.gw.admin.service.model.asset.SensoreAmbienteParete;
import it.mm.iot.gw.admin.service.model.asset.SensoreArmadioRack;
import it.mm.iot.gw.admin.service.model.asset.SensoreEnergia;
import it.mm.iot.gw.admin.service.model.asset.SensoreStazioneMeteo;
import it.mm.iot.gw.admin.service.model.asset.Sito;

@Service
@Validated
public class UserService extends AbstractService {

	@Autowired
	private AssetFactory assetFactory;

	public InfoUserOutput getInfoUser(InfoUserRequest infoUserRequest) {
		issueOperationFactory.addIssue(SeverityEnum.INFORMATION, "IOT-I0001", "Prova");

		/** Lettura info */
		InfoUserOutput infoUser = new InfoUserOutput();
		infoUser.setTenantInfo(new TenantInfo());
		infoUser.getTenantInfo().setNomeAzienda("Azienda Demo");
		infoUser.getTenantInfo().setTenant(infoUserRequest.getTenant());
		AssetTree assetTree = assetFactory.initAssetTree();
		infoUser.setAssetTree(assetTree);

		Organizzazione org = assetFactory.newInstance(Organizzazione.class);
		org.getInfo().setDescrizione("Data Center Inc.");
		assetTree.addAsset(org);
		
		Sito sito = assetFactory.newInstance(Sito.class);
		sito.getInfo().setDescrizione("Main Power");
		org.addAsset(sito);
		
		Contenitore room11=assetFactory.newInstance(Contenitore.class);	
		room11.getInfo().setDescrizione("Main Room");
		sito.addAsset(room11);
		
		SensoreEnergia se=assetFactory.newInstance(SensoreEnergia.class);
		se.getInfo().setDescrizione("ANALIZZATORE RETE");
		se.setIdDispositivo("MM_E_Meter-SB_XXX");
		room11.addAsset(se);	
		SensoreAmbienteParete sap=assetFactory.newInstance(SensoreAmbienteParete.class);
		sap.getInfo().setDescrizione("SONDA AMBIENTE DA PARETE");
		sap.setIdDispositivo("MM_E_SENSORE_BELIMO_XXX_XXX");
		room11.addAsset(sap);	
		SensoreArmadioRack sarh=assetFactory.newInstance(SensoreArmadioRack.class);
		sarh.getInfo().setDescrizione("SONDA ARMADIO RACK HIGH");
		sarh.setIdDispositivo("MM_E_SENSORE-Rack_XX-XX-High");
		room11.addAsset(sarh);	
		SensoreArmadioRack sarm=assetFactory.newInstance(SensoreArmadioRack.class);
		sarm.getInfo().setDescrizione("SONDA ARMADIO RACK MID");
		sarm.setIdDispositivo("MM_E_SENSORE-Rack_XX-XX-Mid");
		room11.addAsset(sarm);	
		SensoreArmadioRack sarl=assetFactory.newInstance(SensoreArmadioRack.class);
		sarl.getInfo().setDescrizione("SONDA ARMADIO RACK LOW");
		sarl.setIdDispositivo("MM_E_SENSORE-Rack_XX-XX-Low");
		room11.addAsset(sarl);	
		SensoreStazioneMeteo ssm=assetFactory.newInstance(SensoreStazioneMeteo.class);
		ssm.getInfo().setDescrizione("STAZIONE METEO ESTERNA");
		ssm.setIdDispositivo("MM_E_Stazione-Meteo");
		room11.addAsset(ssm);	
		
		
		Contenitore room12=assetFactory.newInstance(Contenitore.class);
		room12.getInfo().setDescrizione("Room 2");
		sito.addAsset(room12);
		
		

//		Sensore s2=assetFactory.newInstance(Sensore.class);
//		s2.getInfo().setDescrizione("Sensore 1");
//		room11.addAsset(s2);
//		
//
//		Contenitore room12=assetFactory.newInstance(Contenitore.class);
//		room12.getInfo().setDescrizione("Room 2");
//		sito.addAsset(room12);
//		
//		Sito sito2 =  assetFactory.newInstance(Sito.class);
//		sito2.getInfo().setDescrizione("UPS");
//		org.addAsset(sito2);
//		Contenitore room21=assetFactory.newInstance(Contenitore.class);
//		room21.getInfo().setDescrizione("Room 1");
//		sito2.addAsset(room21);
//		Sensore s211=assetFactory.newInstance(Sensore.class);
//		s211.getInfo().setDescrizione("Sensore 1_2");
//		room21.addAsset(s211);
//		Sensore s212=assetFactory.newInstance(Sensore.class);
//		s212.getInfo().setDescrizione("Sensore 2_2");
//		room21.addAsset(s212);
//		

		Sito sito3 =  assetFactory.newInstance(Sito.class);
		sito3.getInfo().setDescrizione("Generator");
		org.addAsset(sito3);

		Sito sito4 =  assetFactory.newInstance(Sito.class);
		sito4.getInfo().setDescrizione("HVAC");
		org.addAsset(sito4);
		
		return infoUser;
	}

}
