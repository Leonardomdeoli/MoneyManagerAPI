package br.com.developer.system.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.developer.system.api.model.Category;
import br.com.developer.system.api.repository.CategoryRepository;

@RestController
@RequestMapping("/category")
public class CategoryResource {

	@Autowired
	private CategoryRepository categoryRepository;

	@GetMapping
	public List<Category> getCategories() {
		return categoryRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Category> newCategory(@Valid @RequestBody Category category, HttpServletResponse response) {
		Category categorySalved = categoryRepository.save(category);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{code}")
				.buildAndExpand(categorySalved.getCode()).toUri();

		return ResponseEntity.created(uri).body(categorySalved);
	}
	
	@GetMapping("/{code}")
	public ResponseEntity<Category> findToCode(@PathVariable Long code) {
		Optional<Category> optional = categoryRepository.findById(code);
		
		return optional.isPresent() ? ResponseEntity.ok().body(optional.get()) : ResponseEntity.notFound().build();
	}

}
