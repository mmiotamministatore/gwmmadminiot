package it.mm.iot.gw.admin.service.model.asset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class AssetBuildingInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6849659087500891951L;

    private List<String> bimModel;
    private List<String> bimData;
	public List<String> getBimModel() {
		return bimModel;
	}
	public void setBimModel(List<String> bimModel) {
		this.bimModel = bimModel;
	}
	public List<String> getBimData() {
		return bimData;
	}
	public void setBimData(List<String> bimData) {
		this.bimData = bimData;
	}
	
	public void addBimModel(String bimModel) {
		if(this.bimModel==null) {
			this.bimModel=new ArrayList<>();
		}
		this.bimModel.add(bimModel);
	}
	
	public void addBimData(String bimData) {
		if(this.bimData==null) {
			this.bimData=new ArrayList<>();
		}
		this.bimData.add(bimData);
	}
  
}
