package com.pecaja.app.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "endereco")
public class Endereco implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "rua", length = 100)
	private String rua;

	@Column(name = "bairro", length = 45)
	private String bairro;

	@Column(name = "numero")
	private int numero;

	@Column(name = "latitude")
	private long latitude;

	@Column(name = "longitude")
	private long longitude;

	@OneToOne
	@JoinColumn(name = "Estado_id")
	private Estado estado;

	@OneToOne
	@JoinColumn(name = "Cidade_id")
	private Cidade cidade;

	public Endereco(Long id, @NotNull String rua, @NotNull String bairro, @NotNull int numero, Estado estado,
			Cidade cidade) {
		super();
		this.id = id;
		this.rua = rua;
		this.bairro = bairro;
		this.numero = numero;
		this.estado = estado;
		this.cidade = cidade;
	}

	public Endereco() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public long getLatitude() {
		return latitude;
	}

	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}

	public long getLongitude() {
		return longitude;
	}

	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getShortEndereco() {
		return "" + getNumero() + " " + getRua() + "," + getBairro() + "," + getCidade().getNome() + ","
				+ getEstado().getUf();
	}
	
	@Override
	public String toString() {
		return "" + getNumero() + " " + getRua() + "," + getBairro() + "," + getCidade().getNome() + ","
				+ getEstado().getUf();
	}
}
