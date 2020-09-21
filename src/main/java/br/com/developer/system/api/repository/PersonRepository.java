package br.com.developer.system.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.developer.system.api.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
