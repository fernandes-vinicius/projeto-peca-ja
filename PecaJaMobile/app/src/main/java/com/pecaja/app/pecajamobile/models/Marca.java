package com.pecaja.app.pecajamobile.models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Marca implements Serializable {

    private final static long serialVersionUID = 5754067356055295611L;

    @SerializedName("id")
    @Expose
    public Long id;
    @SerializedName("nome")
    @Expose
    public String nome;
    @SerializedName("categoria_id")
    @Expose
    public String categoriaId;

    public Marca () {
    }

    public Marca(Long id, String nome, String categoriaId) {
        this.id = id;
        this.nome = nome;
        this.categoriaId = categoriaId;
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

    public String getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(String categoriaId) {
        this.categoriaId = categoriaId;
    }

    @Override
    public String toString() {
        return "Marca{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", categoriaId='" + categoriaId + '\'' +
                '}';
    }
}