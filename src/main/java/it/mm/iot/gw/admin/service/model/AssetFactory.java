package it.mm.iot.gw.admin.service.model;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import it.mm.iot.gw.admin.service.model.asset.Asset;
import it.mm.iot.gw.admin.service.model.asset.AssetInfo;
import it.mm.iot.gw.admin.service.model.asset.Contenitore;
import it.mm.iot.gw.admin.service.model.asset.Organizzazione;
import it.mm.iot.gw.admin.service.model.asset.Sito;

@Component
public class AssetFactory {
	public <T extends Asset> T newInstance(Class<T> clazz) {
		try {
			T element =clazz.getDeclaredConstructor().newInstance();

			element.setInfo(initAssetInfo());
			element.getInfo().setStatus(AssetStateEnum.Unknown);
			List<Asset> assets = new ArrayList<Asset>();
			element.setElements(assets);
			
			return element;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
	}

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
	
	public AssetInfo initAssetInfo() {
		AssetInfo assetInfo = new AssetInfo();
		
		assetInfo.setId(UUID.randomUUID().toString());
		assetInfo.setDataUltimaVersione(LocalDate.now());

		
		return assetInfo;
	}
}
