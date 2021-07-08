package com.pecaja.app.pecajamobile.models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Serializable {

    private final static long serialVersionUID = -6973918734565894885L;

    @SerializedName("id")
    @Expose
    public Long id;
    @SerializedName("nome")
    @Expose
    public String nome;
    @SerializedName("cnpj")
    @Expose
    public String cnpj;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("telefone")
    @Expose
    public String telefone;
    @SerializedName("fantasia")
    @Expose
    public String fantasia;
    @SerializedName("mensagem")
    @Expose
    public String mensagem;
    @SerializedName("imagem")
    @Expose
    public String imagem;
    @SerializedName("accountNonExpired")
    @Expose
    public Boolean accountNonExpired;
    @SerializedName("accountNonLocked")
    @Expose
    public Boolean accountNonLocked;
    @SerializedName("credentialsNonExpired")
    @Expose
    public Boolean credentialsNonExpired;
    @SerializedName("enabled")
    @Expose
    public Boolean enabled;

    public User() {
    }

    public User(Long id, String nome, String cnpj, String username, String email, String telefone, String fantasia, String mensagem, String imagem, Boolean accountNonExpired, Boolean accountNonLocked, Boolean credentialsNonExpired, Boolean enabled) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.username = username;
        this.email = email;
        this.telefone = telefone;
        this.fantasia = fantasia;
        this.mensagem = mensagem;
        this.imagem = imagem;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", fantasia='" + fantasia + '\'' +
                ", mensagem='" + mensagem + '\'' +
                ", imagem='" + imagem + '\'' +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", enabled=" + enabled +
                '}';
    }
}