package br.com.developer.system.api.event.listerner;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.developer.system.api.event.ResourceCreateEvent;

@Component
public class ResourceCreateListerner implements ApplicationListener<ResourceCreateEvent> {

	@Override
	public void onApplicationEvent(ResourceCreateEvent event) {
		HttpServletResponse response = event.getResponde();
		Long id = event.getId();

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(id).toUri();

		response.setHeader("Location", uri.toASCIIString());
	}
}
