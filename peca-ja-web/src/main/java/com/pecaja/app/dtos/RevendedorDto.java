package com.pecaja.app.dtos;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RevendedorDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@SerializedName("atividade_principal")
	@Expose
	private List<AtividadePrincipal> atividadePrincipal = null;
	@SerializedName("data_situacao")
	@Expose
	private String dataSituacao;
	@SerializedName("nome")
	@Expose
	private String nome;
	@SerializedName("uf")
	@Expose
	private String uf;
	@SerializedName("telefone")
	@Expose
	private String telefone;
	@SerializedName("situacao")
	@Expose
	private String situacao;
	@SerializedName("bairro")
	@Expose
	private String bairro;
	@SerializedName("logradouro")
	@Expose
	private String logradouro;
	@SerializedName("numero")
	@Expose
	private String numero;
	@SerializedName("cep")
	@Expose
	private String cep;
	@SerializedName("municipio")
	@Expose
	private String municipio;
	@SerializedName("abertura")
	@Expose
	private String abertura;
	@SerializedName("natureza_juridica")
	@Expose
	private String naturezaJuridica;
	@SerializedName("fantasia")
	@Expose
	private String fantasia;
	@SerializedName("cnpj")
	@Expose
	private String cnpj;
	@SerializedName("ultima_atualizacao")
	@Expose
	private String ultimaAtualizacao;
	@SerializedName("status")
	@Expose
	private String status;
	@SerializedName("tipo")
	@Expose
	private String tipo;
	@SerializedName("complemento")
	@Expose
	private String complemento;
	@SerializedName("email")
	@Expose
	private String email;
	@SerializedName("efr")
	@Expose
	private String efr;
	@SerializedName("motivo_situacao")
	@Expose
	private String motivoSituacao;
	@SerializedName("situacao_especial")
	@Expose
	private String situacaoEspecial;
	@SerializedName("data_situacao_especial")
	@Expose
	private String dataSituacaoEspecial;
	@SerializedName("capital_social")
	@Expose
	private String capitalSocial;

	public List<AtividadePrincipal> getAtividadePrincipal() {
		return atividadePrincipal;
	}

	public void setAtividadePrincipal(List<AtividadePrincipal> atividadePrincipal) {
		this.atividadePrincipal = atividadePrincipal;
	}

	public String getDataSituacao() {
		return dataSituacao;
	}

	public void setDataSituacao(String dataSituacao) {
		this.dataSituacao = dataSituacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getAbertura() {
		return abertura;
	}

	public void setAbertura(String abertura) {
		this.abertura = abertura;
	}

	public String getNaturezaJuridica() {
		return naturezaJuridica;
	}

	public void setNaturezaJuridica(String naturezaJuridica) {
		this.naturezaJuridica = naturezaJuridica;
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

	public String getUltimaAtualizacao() {
		return ultimaAtualizacao;
	}

	public void setUltimaAtualizacao(String ultimaAtualizacao) {
		this.ultimaAtualizacao = ultimaAtualizacao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEfr() {
		return efr;
	}

	public void setEfr(String efr) {
		this.efr = efr;
	}

	public String getMotivoSituacao() {
		return motivoSituacao;
	}

	public void setMotivoSituacao(String motivoSituacao) {
		this.motivoSituacao = motivoSituacao;
	}

	public String getSituacaoEspecial() {
		return situacaoEspecial;
	}

	public void setSituacaoEspecial(String situacaoEspecial) {
		this.situacaoEspecial = situacaoEspecial;
	}

	public String getDataSituacaoEspecial() {
		return dataSituacaoEspecial;
	}

	public void setDataSituacaoEspecial(String dataSituacaoEspecial) {
		this.dataSituacaoEspecial = dataSituacaoEspecial;
	}

	public String getCapitalSocial() {
		return capitalSocial;
	}

	public void setCapitalSocial(String capitalSocial) {
		this.capitalSocial = capitalSocial;
	}

	public RevendedorDto(String nome, String telefone, String fantasia, String status, String cnpj, String abertura,
			String municipio, String uf, String logradouro, String bairro, String natureza_juridica) {
		super();
		this.nome = nome;
		this.telefone = telefone;
		this.fantasia = fantasia;
		this.status = status;
		this.cnpj = cnpj;
		this.abertura = abertura;
		this.municipio = municipio;
		this.uf = uf;
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.naturezaJuridica = natureza_juridica;
	}

	public RevendedorDto() {
		// TODO Auto-generated constructor stub
	}

}
