package com.pecaja.app.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;


	@NotNull
	@NotEmpty(message = "O nome é um campo obrigatório.")
	@Size(min = 3, message = "O nome deve ter pelo menos 3 caracteres")
	@Column(name = "nome", length = 45)
	private String nome;

	@ManyToMany(mappedBy = "roles")
	private List<User> users;

	public Role() {
		super();
	}

	public Role(Long id, String nome, List<User> users) {
		super();
		this.id = id;
		this.nome = nome;
		this.users = users;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String getAuthority() {
		return this.nome;
	}

}
