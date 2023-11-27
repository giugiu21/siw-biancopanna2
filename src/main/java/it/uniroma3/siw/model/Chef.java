package it.uniroma3.siw.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class Chef {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String name;
	@NotBlank
	private String lastname;
	/*@OneToMany(mappedBy = "chef")
	private List<Recipe> recipes;*/
	//immagine(?)
	private String email;


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

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
