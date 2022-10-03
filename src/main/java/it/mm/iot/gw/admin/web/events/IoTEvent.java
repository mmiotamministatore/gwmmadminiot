package it.mm.iot.gw.admin.web.events;

import org.springframework.context.ApplicationEvent;

public class IoTEvent<T> extends ApplicationEvent {
	private T data;

	public IoTEvent(final Object source, final T data) {
		super(source);
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
