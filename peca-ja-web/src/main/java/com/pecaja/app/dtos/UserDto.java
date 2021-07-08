package com.pecaja.app.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pecaja.app.models.Produto;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class UserDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private long id;
	private String nome;
	private String telefone;
	private String email;
	private String fantasia;
	private String cnpj;
	private int estimativa_entrega;
	private List<Produto> produtos = new ArrayList<Produto>(); 
	
	public UserDto(long id,String nome, String telefone, String email, String fantasia, String cnpj,List<Produto> produtos,int tempo) {
		super();
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.fantasia = fantasia;
		this.cnpj = cnpj;
		this.produtos = produtos;
		this.estimativa_entrega = tempo;
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
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFantasia() {
		return fantasia;
	}
	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}	
	
	public List<Produto> getProdutos() {
		return produtos;
	}
	
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	public int getEstimativa_entrega() {
		return estimativa_entrega;
	}
	
	public void setEstimativa_entrega(int estimativa_entrega) {
		this.estimativa_entrega = estimativa_entrega;
	}
}
