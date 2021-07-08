package com.pecaja.app.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pecaja.app.enums.Status;

@Entity
@Table(name = "cliente")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Cliente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Column(name = "nome")
	private String nome;

	@Column(unique = true, name = "cpf")
	private String cpf;
	
	@Column(name = "telefone")
	private String telefone;

	@Column(unique = true, name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(unique = true, name = "email")
	private String email;

	@ManyToOne
	@JoinColumn(name = "endereco_id")
	private Endereco endereco;

	@JsonIgnore
	@Enumerated(EnumType.STRING)
	private Status status;
	
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getTelefone() {
		return telefone;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Override
    public String toString() {
        return "\n  Cliente {" +
                "\n     id: " + this.id +
                "\n     nome: " + this.nome +
                "\n     cpf: " + this.cpf +
                "\n     username: " + this.username +
                "\n     password: " + this.password +
                "\n     email: " + this.email +
                "\n         endereco: {" +
                "\n             rua: " + this.endereco.getRua() +
                "\n             bairro: " + this.endereco.getBairro() +
                "\n             numero: " + this.endereco.getNumero() +
                "\n             latitude: " + this.endereco.getLatitude() +
                "\n             longitude: " + this.endereco.getLongitude() +
                "\n             estado: {" +
                "\n                 uf: " + this.endereco.getEstado().getUf() +
                "\n             }" +
                "\n             cidade: {" +
                "\n                 nome: " + this.endereco.getCidade().getNome() +
                "\n             }" +
                "\n         }" +
                "\n }";
    }

}
