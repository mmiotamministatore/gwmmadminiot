package it.mm.iot.gw.admin.service.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import it.mm.iot.gw.admin.service.model.asset.Asset;
import it.mm.iot.gw.admin.service.model.asset.AssetInfo;
import it.mm.iot.gw.admin.service.model.asset.Organizzazione;
import it.mm.iot.gw.admin.service.model.asset.Sito;

@Component
public class AssetFactory {
	public AssetTree initAssetTree() {
		AssetTree assetTree = new AssetTree();
		assetTree.setInfo(new AssetTreeInfo());
		assetTree.getInfo().setId(UUID.randomUUID().toString());
		assetTree.getInfo().setDataUltimaVersione(LocalDate.now());
		assetTree.setStructure(new AssetStructure());
		List<Asset> assets = new ArrayList<Asset>();
		assetTree.getStructure().setAssets(assets);
		return assetTree;
	}
	public Organizzazione initOrganizzazione() {
		Organizzazione organizzazione = new Organizzazione();
		organizzazione.setInfo(initAssetInfo());
		
		List<Asset> assets = new ArrayList<Asset>();
		organizzazione.setElements(assets);
		
		return organizzazione;
	}

	public Sito initSito() {
		Sito sito = new Sito();
		sito.setInfo(initAssetInfo());
		
		List<Asset> assets = new ArrayList<Asset>();
		sito.setElements(assets);
		
		return sito;
	}
	public AssetInfo initAssetInfo() {
		AssetInfo assetInfo = new AssetInfo();
		
		assetInfo.setId(UUID.randomUUID().toString());
		assetInfo.setDataUltimaVersione(LocalDate.now());

		
		return assetInfo;
	}
}
