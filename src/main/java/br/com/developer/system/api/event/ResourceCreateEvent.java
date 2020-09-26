package br.com.developer.system.api.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class ResourceCreateEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	private HttpServletResponse responde;
	private Long id;
	
	public ResourceCreateEvent(Object source, HttpServletResponse responde, Long id) {
		super(source);
		this.responde = responde;
		this.id = id;
	}

	public HttpServletResponse getResponde() {
		return responde;
	}

	public Long getId() {
		return id;
	}
}
