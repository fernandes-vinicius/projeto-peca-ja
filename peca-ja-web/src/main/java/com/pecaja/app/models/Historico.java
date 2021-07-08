package com.pecaja.app.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.pecaja.app.enums.Status;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "historico")
public class Historico implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	@JoinColumn(name = "usuario_id")
	@JsonIgnore
	private User user;

	@OneToMany
	private List<Pedido> pedidos;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data")
	private Date data; 
	

	@Enumerated(EnumType.STRING)
	private Status status;
	
	public Historico() {
		super();
	}

	public Historico(User user, List<Pedido> pedidos, Status status) {
		super();
		this.user = user;
		this.pedidos = pedidos;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Pedido> getPedido() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}

}
