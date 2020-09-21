package br.com.developer.system.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.developer.system.api.event.ResourceCreateEvent;
import br.com.developer.system.api.model.Person;
import br.com.developer.system.api.repository.PersonRepository;

@RestController
@RequestMapping("/person")
public class PersonResource {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Person> getCategories() {
		return personRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Person> newperson(@Valid @RequestBody Person person, HttpServletResponse response) {
		Person personSalved = personRepository.save(person);
		
		publisher.publishEvent(new ResourceCreateEvent(this, response, personSalved.getCode()));

		return ResponseEntity.status(HttpStatus.CREATED).body(personSalved);
	}
	
	@GetMapping("/{code}")
	public ResponseEntity<Person> findToCode(@PathVariable Long code) {
		Optional<Person> optional = personRepository.findById(code);
		
		return optional.isPresent() ? ResponseEntity.ok().body(optional.get()) : ResponseEntity.notFound().build();
	}

}
