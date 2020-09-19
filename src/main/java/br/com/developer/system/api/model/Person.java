package br.com.developer.system.api.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PERSON")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long code;

	@Size(min = 3, max = 50, message = "{validation.name.size}")
	@NotEmpty(message = "{validation.name.notEmpty}")
	private String name;

	@NotEmpty
	private Boolean active;
	
	@Embedded
	private Address address;

	public Person(Long code, String name, Boolean active, Address address) {
		this.code = code;
		this.name = name;
		this.active = active;
		this.address = address;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
