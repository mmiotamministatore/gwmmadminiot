package it.mm.iot.gw.admin.web.events;

import java.time.LocalDateTime;

public class IoTSubscribeUser{

	private String userName;
	private LocalDateTime expirationDate;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}

}
