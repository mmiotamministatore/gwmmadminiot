package it.mm.iot.gw.admin.service.model.asset;

import java.io.Serializable;

public class ModelDesignInfo implements Serializable {

	private Integer positionX;
	private Integer positionY;

	public Integer getPositionX() {
		return positionX;
	}

	public void setPositionX(Integer positionX) {
		this.positionX = positionX;
	}

	public Integer getPositionY() {
		return positionY;
	}

	public void setPositionY(Integer positionY) {
		this.positionY = positionY;
	}

}
