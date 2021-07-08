package com.pecaja.app.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {

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

	@OneToMany(mappedBy = "categoria")
	@JsonIgnore
	private List<Marca> marcas;

	public Categoria() {
		super();
	}

	public Categoria(Long id,
			@NotNull @NotEmpty(message = "O nome é um campo obrigatório.") @Size(min = 3, message = "O nome deve ter pelo menos 3 letras") String nome,
			List<Marca> marcas) {
		super();
		this.id = id;
		this.nome = nome;
		this.marcas = marcas;
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

	public List<Marca> getMarcas() {
		return marcas;
	}

	public void setMarcas(List<Marca> marcas) {
		this.marcas = marcas;
	}

}
