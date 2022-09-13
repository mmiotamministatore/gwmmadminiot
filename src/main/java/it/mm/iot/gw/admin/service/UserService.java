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

		Organizzazione org = assetFactory.initOrganizzazione();
		org.getInfo().setDescrizione("Data Center Inc.");
		assetTree.addAsset(org);
		
		Sito sito = assetFactory.initSito();
		sito.getInfo().setDescrizione("Main Power");
		org.addAsset(sito);
		
		Contenitore room11=new Contenitore();
		Sensore s1=new Sensore();
		room11.addAsset(s1);
		Sensore s2=new Sensore();
		room11.addAsset(s2);
		
		sito.addAsset(room11);
		Contenitore room12=new Contenitore();
		sito.addAsset(room12);
		
		Sito sito2 = assetFactory.initSito();
		sito2.getInfo().setDescrizione("UPS");
		org.addAsset(sito2);
		Contenitore room21=new Contenitore();
		sito2.addAsset(room21);
		Sensore s211=new Sensore();
		room21.addAsset(s211);
		Sensore s212=new Sensore();
		room21.addAsset(s212);
		

		Sito sito3 = assetFactory.initSito();
		sito3.getInfo().setDescrizione("Generator");
		org.addAsset(sito3);

		Sito sito4 = assetFactory.initSito();
		sito4.getInfo().setDescrizione("HVAC");
		org.addAsset(sito4);
		
		return infoUser;
	}

}
