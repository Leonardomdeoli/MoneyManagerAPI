package br.com.developer.system.api.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class ResourceCreateEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	private HttpServletResponse responde;
	private Long code;
	
	public ResourceCreateEvent(Object source, HttpServletResponse responde, Long code) {
		super(source);
		this.responde = responde;
		this.code = code;
	}

	public HttpServletResponse getResponde() {
		return responde;
	}

	public Long getCode() {
		return code;
	}
}
