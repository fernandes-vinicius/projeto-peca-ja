package com.pecaja.app.pecajamobile.models;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Revendedor implements Serializable {

    private final static long serialVersionUID = -5497603702031522857L;

    @SerializedName("id")
    @Expose
    public Long id;
    @SerializedName("nome")
    @Expose
    public String nome;
    @SerializedName("telefone")
    @Expose
    public String telefone;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("fantasia")
    @Expose
    public String fantasia;
    @SerializedName("cnpj")
    @Expose
    public String cnpj;
    @SerializedName("estimativa_entrega")
    @Expose
    public Integer estimativaEntrega;
    @SerializedName("produtos")
    @Expose
    public List<Produto> produtos = null;

    public Revendedor() {
    }

    public Revendedor(Long id, String nome, String telefone, String email, String fantasia, String cnpj, Integer estimativaEntrega, List<Produto> produtos) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.fantasia = fantasia;
        this.cnpj = cnpj;
        this.estimativaEntrega = estimativaEntrega;
        this.produtos = produtos;
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

    public Integer getEstimativaEntrega() {
        return estimativaEntrega;
    }

    public void setEstimativaEntrega(Integer estimativaEntrega) {
        this.estimativaEntrega = estimativaEntrega;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public String toString() {
        return "Revendedor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", fantasia='" + fantasia + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", estimativaEntrega=" + estimativaEntrega +
                ", produtos=" + produtos.toString() +
                '}';
    }
}