package com.pecaja.app.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "sugestao")
public class Sugestao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@NotEmpty(message = "A sugestão é um campo obrigatório.")
	@Lob
	@Column(name = "sugestao")
	private String sugestao;

	@Temporal(TemporalType.DATE)
	@Column(name = "data", nullable = true)
	private Date data;

	@OneToOne
	@JsonIgnore
	private User user;

	public Sugestao() {
		super();
	}

	public Sugestao(Long id, @NotNull @NotEmpty(message = "A sugestão é um campo obrigatório.") String sugestao,
			Date data, User user) {
		super();
		this.id = id;
		this.sugestao = sugestao;
		this.data = data;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSugestao() {
		return sugestao;
	}

	public void setSugestao(String sugestao) {
		this.sugestao = sugestao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
