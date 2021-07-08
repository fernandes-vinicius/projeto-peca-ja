package com.pecaja.app.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pecaja.app.enums.Status;


@Entity
@Table(name = "produto")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Produto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "peso")
	private double peso;

	@Column(name = "preco")
	private double preco;

	@Column(name = "quantidade")
	private int quantidade;

	@ManyToOne()
	@JoinColumn(name = "revendedor_id")
	@JsonIgnore
	private User revendedor;

	@OneToOne
	@JoinColumn(name = "Marca_id")
	private Marca marca;

	@Transient
	private Long marca_id;

	@Transient
	private Long categoria_id;

	@ManyToOne
	@JoinColumn(name = "Categoria_id")
	private Categoria categoria;

	@OneToOne
	private Pedido pedido; 
	
	@Enumerated(EnumType.STRING)
	@JsonIgnore
	private Status status;

	public Produto() {
		super();
		marca = new Marca();
		categoria = new Categoria();
	}

	public Produto(int quantidade, double preco, double peso, Marca marca, Categoria categoria) {
		super();
		this.quantidade = quantidade;
		this.preco = preco;
		this.peso = peso;
		this.marca = marca;
		this.categoria = categoria;
	}

	public Produto(int quantidade, double preco, double peso) {
		super();
		this.quantidade = quantidade;
		this.preco = preco;
		this.peso = peso;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public User getUser() {
		return revendedor;
	}

	public void setUser(User user) {
		this.revendedor = user;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public Long getMarca_id() {
		return marca_id;
	}

	public void setMarca_id(Long marca_id) {
		this.marca_id = marca_id;
	}

	public Long getCategoria_id() {
		return categoria_id;
	}

	public void setCategoria_id(Long categoria_id) {
		this.categoria_id = categoria_id;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
