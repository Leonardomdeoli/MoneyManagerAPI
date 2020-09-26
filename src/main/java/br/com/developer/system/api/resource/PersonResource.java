package br.com.developer.system.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.developer.system.api.event.ResourceCreateEvent;
import br.com.developer.system.api.model.Person;
import br.com.developer.system.api.repository.PersonRepository;
import br.com.developer.system.api.service.PersonService;

@RestController
@RequestMapping("/person")
public class PersonResource {
	
	@Autowired
	private PersonService personService;	

	@GetMapping
	public List<Person> getCategories() {
		return personService.findAll();
	}

	@PostMapping
	public ResponseEntity<Person> newPerson(@Valid @RequestBody Person person, HttpServletResponse response) {
		return ResponseEntity.status(HttpStatus.CREATED).body(personService.save(person, response));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Person> findById(@PathVariable Long id) {
		return ResponseEntity.ok().body(personService.findById(id));
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		personService.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Person> update(@PathVariable Long id, @Valid @RequestBody Person person) {

		Person personSalved = personService.update(id, person);
		
		return ResponseEntity.ok(personSalved);
	}
	
	@PutMapping("/{id}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateActive(@PathVariable Long id, @Valid @RequestBody Boolean active) {
		
		personService.updateActive(id, active);
	}
}
