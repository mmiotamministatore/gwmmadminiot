package it.mm.iot.gw.admin.service.model.asset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Asset implements IAsset {

	private AssetInfo info;
	private ModelDesignInfo designInfo;
	private List<Asset> elements;
	private Map<String, Object> detail = new HashMap<>();

	public boolean hasInfo() {
		return info != null;
	}

	public AssetInfo getInfo() {
		if (!hasInfo()) {
			info = new AssetInfo();
		}
		return info;
	}

	public void setInfo(AssetInfo info) {
		this.info = info;
	}

	public boolean hasDesignInfo() {
		return designInfo != null;
	}

	public ModelDesignInfo getDesignInfo() {
		if (!hasDesignInfo()) {
			designInfo = new ModelDesignInfo();
		}
		return designInfo;
	}

	public void setDesignInfo(ModelDesignInfo designInfo) {
		this.designInfo = designInfo;
	}

	public Map<String, Object> getDetail() {
		return detail;
	}

	public void setDetail(Map<String, Object> detail) {
		this.detail = detail;
	}

	public List<Asset> getElements() {
		return elements;
	}

	public void setElements(List<Asset> elements) {
		this.elements = elements;
	}
	
	public void addAsset(Asset asset) {
		if(elements==null) {
			elements=new ArrayList<Asset>();
		}
		elements.add(asset);
	}
	

}
