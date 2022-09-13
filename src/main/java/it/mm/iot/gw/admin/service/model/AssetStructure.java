package it.mm.iot.gw.admin.service.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import it.mm.iot.gw.admin.service.model.asset.Asset;

@JsonTypeInfo(use=JsonTypeInfo.Id.MINIMAL_CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public class AssetStructure implements Serializable{

	private List<Asset> assets;

	public List<Asset> getAssets() {
		return assets;
	}

	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}

	public void addAssets(Asset asset) {
		if(assets==null) {
			assets=new ArrayList<>();
		}
		
		assets.add(asset);
	}
}
