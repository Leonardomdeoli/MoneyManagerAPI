package br.com.developer.system.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.developer.system.api.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
