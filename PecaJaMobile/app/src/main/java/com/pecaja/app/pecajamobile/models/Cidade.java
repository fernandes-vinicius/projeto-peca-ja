package com.pecaja.app.pecajamobile.models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cidade implements Serializable {

    private final static long serialVersionUID = -5665272718005745794L;

    @SerializedName("id")
    @Expose
    public Long id;
    @SerializedName("nome")
    @Expose
    public String nome;

    public Cidade () {
    }

    public Cidade(Long id, String nome) {
        this.id = id;
        this.nome = nome;
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

    @Override
    public String toString() {
        return nome;
    }
}