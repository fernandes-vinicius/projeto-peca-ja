package com.pecaja.app.pecajamobile.models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Produto implements Serializable {

    private final static long serialVersionUID = -5575774130302786277L;

    @SerializedName("id")
    @Expose
    public Long id;
    @SerializedName("peso")
    @Expose
    public Double peso;
    @SerializedName("preco")
    @Expose
    public Double preco;
    @SerializedName("quantidade")
    @Expose
    public Integer quantidade;
    @SerializedName("marca")
    @Expose
    public Marca marca;
    @SerializedName("marca_id")
    @Expose
    public String marcaId;
    @SerializedName("categoria_id")
    @Expose
    public String categoriaId;
    @SerializedName("categoria")
    @Expose
    public Categoria categoria;
    @SerializedName("user")
    @Expose
    public User user;

    private Integer estimativa_entrega;

    public Produto() {
    }

    public Produto(Long id, Double peso, Double preco, Integer quantidade, Marca marca, String marcaId, String categoriaId, Categoria categoria, User user) {
        this.id = id;
        this.peso = peso;
        this.preco = preco;
        this.quantidade = quantidade;
        this.marca = marca;
        this.marcaId = marcaId;
        this.categoriaId = categoriaId;
        this.categoria = categoria;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(String marcaId) {
        this.marcaId = marcaId;
    }

    public String getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(String categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getEstimativa_entrega() {
        return estimativa_entrega;
    }

    public void setEstimativa_entrega(Integer estimativa_entrega) {
        this.estimativa_entrega = estimativa_entrega;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", peso=" + peso +
                ", preco=" + preco +
                ", quantidade=" + quantidade +
                ", marca=" + marca.getNome() +
                ", marcaId='" + marcaId + '\'' +
                ", categoriaId='" + categoriaId + '\'' +
                ", categoria=" + categoria.getNome() +
                ", user=" + user.getFantasia() +
                ", estimativa_entrega=" + estimativa_entrega +
                '}';
    }
}