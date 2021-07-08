package com.pecaja.app.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "marca")
public class Marca implements Serializable {

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

	@ManyToOne
	@JoinColumn(name = "categoria_id")
	@JsonIgnore
	private Categoria categoria;

	@Transient
	private String categoria_id;

	@ManyToOne
	@JoinColumn(name = "revendedor_id")
	@JsonIgnore
	private User revendedor;
	
	public Marca() {
		super();
	}

	public Marca(Long id,
			@NotNull @NotEmpty(message = "O nome é um campo obrigatório.") @Size(min = 3, message = "O nome deve ter pelo menos 3 letras") String nome,
			Categoria categoria) {
		super();
		this.id = id;
		this.nome = nome;
		this.categoria = categoria;
	}

	public Marca(
			@NotNull @NotEmpty(message = "O nome é um campo obrigatório.") @Size(min = 3, message = "O nome deve ter pelo menos 3 letras") String nome,
			String categoria_id) {
		super();
		this.nome = nome;
		this.categoria_id = categoria_id;
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getCategoria_id() {
		return categoria_id;
	}

	public void setCategoria_id(String categoria_id) {
		this.categoria_id = categoria_id;
	}

	public User getRevendedor() {
		return revendedor;
	}

	public void setRevendedor(User revendedor) {
		this.revendedor = revendedor;
	}
	
	
}
