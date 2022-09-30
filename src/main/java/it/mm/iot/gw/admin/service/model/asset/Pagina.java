package it.mm.iot.gw.admin.service.model.asset;

public class Pagina extends Asset{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9141818329040269549L;
	
	public String uri;
	public String icon;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	

}
