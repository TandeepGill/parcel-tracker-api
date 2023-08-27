package com.fdmgroup.parceltracker.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customergen")
	@SequenceGenerator(name = "customergen", sequenceName = "CUSTOMER_ID_SEQ", allocationSize = 1)
	private Long id;

	@NotBlank(message = "Name cannot be blank. Please input a name.")
	@Size(min = 3, max = 60, message = "Name must be 3 to 60 characters long.")
	private String name;

	@NotBlank(message = "Email cannot be blank. Please input an email address.")
	@Size(min = 3, max = 50, message = "Email address must be 3 to 50 characters long.")
	private String email;

	@NotBlank(message = "Address cannot be blank. Please input an address.")
	@Size(min = 3, max = 100, message = "Address must be 3 to 100 characters long.")
	private String address;

	public Customer() {
	}

	public Customer(String name, String email, String address) {
		this.name = name;
		this.email = email;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
