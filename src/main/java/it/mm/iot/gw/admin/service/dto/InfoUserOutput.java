package it.mm.iot.gw.admin.service.dto;

import it.mm.iot.gw.admin.service.model.AssetTree;
import it.mm.iot.gw.admin.service.model.TenantInfo;
import it.mm.iot.gw.admin.web.message.OutputPayload;

public class InfoUserOutput implements OutputPayload {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6851860782236725145L;

	private TenantInfo tenantInfo;
	private AssetTree assetTree;

	public TenantInfo getTenantInfo() {
		return tenantInfo;
	}

	public void setTenantInfo(TenantInfo tenantInfo) {
		this.tenantInfo = tenantInfo;
	}

	public AssetTree getAssetTree() {
		return assetTree;
	}

	public void setAssetTree(AssetTree assetTree) {
		this.assetTree = assetTree;
	}

}
