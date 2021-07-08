package com.pecaja.app.models;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pecaja.app.enums.Situacao;
import com.pecaja.app.enums.Status;

@Entity
@Table(name = "user")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Size(min = 4, message = "O nome deve ter pelo menos 4 letras")
	@Column(name = "nome")
	private String nome;

	@Column(unique = true, name = "cpf")
	@JsonIgnore
	private String cpf;

	@Column(unique = true, name = "cnpj")
	private String cnpj;

	@Size(min = 4, message = "O usuário deve ter pelo menos 4 caracteres")
	@Column(unique = true, name = "username")
	private String username;

	@Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres.")
	@Column(unique = true, name = "password")
	@JsonIgnore
	private String password;

	@Email(message = "O email informado não é válido")
	@Column(unique = true, name = "email")
	private String email;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dataCriacao")
	@JsonIgnore
	private Date dataCriacao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dataUpdate")
	@JsonIgnore
	private Date dataUpdate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ultimoAcesso")
	@JsonIgnore
	private Date ultimoAcesso;

	@Column(name = "isAccountNonExpired")
	@JsonIgnore
	private boolean isAccountNonExpired;

	@Column(name = "isAccountNonLocked")
	@JsonIgnore
	private boolean isAccountNonLocked;

	@Column(name = "isCredentialsNonExpired")
	@JsonIgnore
	private boolean isCredentialsNonExpired;

	@Column(name = "isEnabled")
	@JsonIgnore
	private boolean isEnabled;

	@JsonIgnore
	private String atividade_principal;

	@Enumerated(EnumType.STRING)
	@JsonIgnore
	private Situacao situacao;

	@Column(name = "code", length = 45)
	@JsonIgnore
	private String code;

	@OneToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "endereco_id")
	@JsonIgnore
	private Endereco endereco;

	@Column(name = "abertura", length = 45)
	@JsonIgnore
	private String abertura;

	@Column(name = "telefone", length = 45)
	private String telefone;

	@Column(name = "natureza_juridica", length = 200)
	@JsonIgnore
	private String natureza_juridica;

	@Column(name = "fantasia", length = 100)
	private String fantasia;

	@Column(name = "mensagem", length = 1000)
	private String mensagem;
	
	@ManyToMany
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "email"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "nome"))
	@JsonIgnore
	private List<Role> roles;

	@Enumerated(EnumType.STRING)
	@JsonIgnore
	private Status status;
	
	@Lob
    @Column(name="imagem")
    private byte[] imagem;

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setImagem(byte[] imagem) {
		this.imagem = imagem;
	}
	
	public byte[] getImagem() {
		return imagem;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return (Collection<? extends GrantedAuthority>) this.roles;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.isEnabled;
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAtividade_principal() {
		return atividade_principal;
	}

	public void setAtividade_principal(String atividade_principal) {
		this.atividade_principal = atividade_principal;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public String getAbertura() {
		return abertura;
	}

	public void setAbertura(String abertura) {
		this.abertura = abertura;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getNatureza_juridica() {
		return natureza_juridica;
	}

	public void setNatureza_juridica(String natureza_juridica) {
		this.natureza_juridica = natureza_juridica;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataUpdate() {
		return dataUpdate;
	}

	public void setDataUpdate(Date dataUpdate) {
		this.dataUpdate = dataUpdate;
	}

	public Date getUltimoAcesso() {
		return ultimoAcesso;
	}

	public void setUltimoAcesso(Date ultimoAcesso) {
		this.ultimoAcesso = ultimoAcesso;
	}

	public void setAccountNonExpired(boolean isAccountNonExpired) {
		this.isAccountNonExpired = isAccountNonExpired;
	}

	public void setAccountNonLocked(boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}

	public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

}
