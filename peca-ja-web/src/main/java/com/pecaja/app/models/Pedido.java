package com.pecaja.app.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pecaja.app.enums.StatusPedido;
import com.pecaja.app.enums.Status;

@Entity
@Table(name = "pedido")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Temporal(TemporalType.DATE)
	@Column(name = "data")
	private Date data;

	@Column(name = "valor")
	private double valor;
	
	@Column(name= "dicaEntregador")
	private String dicaEntregador;

	@ManyToOne 
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	@Column
	private int estimativa_entrega;

	@Enumerated(EnumType.STRING)
    public StatusPedido statusPedido = StatusPedido.AGUARDANDO;
	
	@Column
	private int quantidade;
	
	@Enumerated(EnumType.STRING)
	private Status status;

	@ManyToOne
	@JoinColumn(name = "revendedor_id")
	private User revendedor;
	
	@ManyToOne
	@JoinColumn(name="historico_id")
	private Historico historico;
	
	@OneToOne
	private Produto produto;

	public Pedido(Long id, @NotNull Date data, @NotNull int quantidade, @NotNull double valor, Produto produto) {
		super();
		this.id = id;
		this.data = data;
		this.valor = valor;
		this.produto = produto;
	}

	public Pedido() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public StatusPedido getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(StatusPedido statusPedido) {
		this.statusPedido = statusPedido;
	}

	public Historico getHistorico() {
		return historico;
	}

	public void setHistorico(Historico historico) {
		this.historico = historico;
	}

	public String getDicaEntregador() {
		return dicaEntregador;
	}

	public void setDicaEntregador(String dicaEntregador) {
		this.dicaEntregador = dicaEntregador;
	}

	public int getEstimativa_entrega() {
		return estimativa_entrega;
	}

	public void setEstimativa_entrega(int estimativa_entrega) {
		this.estimativa_entrega = estimativa_entrega;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public User getRevendedor() {
		return revendedor;
	}
	
	public void setRevendedor(User revendedor) {
		this.revendedor = revendedor;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
