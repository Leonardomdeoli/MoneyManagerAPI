package br.com.developer.system.api.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.developer.system.api.event.ResourceCreateEvent;
import br.com.developer.system.api.model.Person;
import br.com.developer.system.api.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	public Person update(Long id, Person person) {
		Person personSalved = getPerson(id);

		BeanUtils.copyProperties(person, personSalved, "id");

		return personRepository.save(personSalved);
	}


	public void updateActive(Long id, @Valid Boolean active) {
		Person personSalved = getPerson(id);
		personSalved.setActive(active);
		
		personRepository.save(personSalved);
	}

	public List<Person> findAll() {
		return personRepository.findAll();
	}


	public void deleteById(Long id) {
		personRepository.deleteById(id);
	}

	public Person findById(Long id) {
		return getPerson(id);
	}
	
	private Person getPerson(Long id) {
		Optional<Person> personOptional = personRepository.findById(id);
		
		if (!personOptional.isPresent()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		Person personSalved = personOptional.get();
		return personSalved;
	}


	public Person save(@Valid Person person, HttpServletResponse response) {
		Person personSalved = personRepository.save(person);
		
		publisher.publishEvent(new ResourceCreateEvent(this, response, personSalved.getId()));
		
		return personSalved;
	}
}
