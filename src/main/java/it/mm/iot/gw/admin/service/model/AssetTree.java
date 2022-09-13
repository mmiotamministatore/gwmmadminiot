package it.mm.iot.gw.admin.service.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import it.mm.iot.gw.admin.service.model.asset.Asset;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class AssetTree implements Serializable {

	private AssetTreeInfo info;
	private AssetStructure structure;

	public boolean hasInfo() {
		return info != null;
	}

	public AssetTreeInfo getInfo() {
		if (!hasInfo()) {
			info = new AssetTreeInfo();
		}
		return info;
	}

	public void setInfo(AssetTreeInfo info) {
		this.info = info;
	}

	public boolean hasStructure() {
		return structure != null;
	}

	public AssetStructure getStructure() {
		if (!hasStructure())
			structure = new AssetStructure();
		return structure;
	}

	public void setStructure(AssetStructure structure) {
		this.structure = structure;
	}

	public void addAsset(Asset asset) {
		if(structure==null) {
			structure=new AssetStructure();
		}
		
		structure.addAssets(asset);
	}
}
