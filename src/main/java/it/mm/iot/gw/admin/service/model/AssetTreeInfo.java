package it.mm.iot.gw.admin.service.model;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import it.mm.iot.gw.admin.service.model.asset.AssetInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.MINIMAL_CLASS, include=JsonTypeInfo.As.PROPERTY, property="@class")
public class AssetTreeInfo extends AssetInfo {


}
