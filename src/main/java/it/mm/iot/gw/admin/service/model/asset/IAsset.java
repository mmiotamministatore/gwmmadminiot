package it.mm.iot.gw.admin.service.model.asset;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IAsset extends Serializable {

	AssetInfo getInfo();

	ModelDesignInfo getDesignInfo();

	Map<String, Object> getDetail();

	void setInfo(AssetInfo info);

	void setDesignInfo(ModelDesignInfo info);

	void setDetail(Map<String, Object> detail);

	List<Asset> getElements();

	void setElements(List<Asset> elements);

}