package it.mm.iot.gw.admin.service.model.asset;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;

@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class AssetBuildingInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6849659087500891951L;

    private String bimModel;
    private String bimData;

    public String getBimModel() {
        return bimModel;
    }

    public void setBimModel(String bimModel) {
        this.bimModel = bimModel;
    }

    public String getBimData() {
        return bimData;
    }

    public void setBimData(String bimData) {
        this.bimData = bimData;
    }
}
