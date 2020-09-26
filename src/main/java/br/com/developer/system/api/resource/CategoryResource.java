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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.developer.system.api.event.ResourceCreateEvent;
import br.com.developer.system.api.model.Category;
import br.com.developer.system.api.repository.CategoryRepository;

@RestController
@RequestMapping("/category")
public class CategoryResource {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Category> getCategories() {
		return categoryRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Category> newCategory(@Valid @RequestBody Category category, HttpServletResponse response) {
		Category categorySalved = categoryRepository.save(category);

		publisher.publishEvent(new ResourceCreateEvent(this, response, categorySalved.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(categorySalved);
	}
	
	@GetMapping("/{code}")
	public ResponseEntity<Category> findToCode(@PathVariable Long id) {
		Optional<Category> optional = categoryRepository.findById(id);
		
		return optional.isPresent() ? ResponseEntity.ok().body(optional.get()) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		categoryRepository.deleteById(id);
	}

}
