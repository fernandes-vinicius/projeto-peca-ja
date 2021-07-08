package com.pecaja.app.pecajamobile.models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Endereco implements Serializable {

    private final static long serialVersionUID = -3209196049679793673L;

    @SerializedName("id")
    @Expose
    public Long id;
    @SerializedName("rua")
    @Expose
    public String rua;
    @SerializedName("bairro")
    @Expose
    public String bairro;
    @SerializedName("numero")
    @Expose
    public Integer numero;
    @SerializedName("latitude")
    @Expose
    public Integer latitude;
    @SerializedName("longitude")
    @Expose
    public Integer longitude;
    @SerializedName("estado")
    @Expose
    public Estado estado;
    @SerializedName("cidade")
    @Expose
    public Cidade cidade;
    @SerializedName("shortEndereco")
    @Expose
    public String shortEndereco;

    public Endereco () {
    }

    public Endereco(String rua, String bairro, Integer numero, Estado estado, Cidade cidade) {
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
        this.estado = estado;
        this.cidade = cidade;
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

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getLatitude() {
        return latitude;
    }

    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

    public Integer getLongitude() {
        return longitude;
    }

    public void setLongitude(Integer longitude) {
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
        return numero + " " + rua + "," + bairro + "," + cidade.getNome() + ","
                + estado.getUf();
    }

    public void setShortEndereco(String shortEndereco) {
        this.shortEndereco = shortEndereco;
    }

    @Override
    public String toString() {
        return shortEndereco;
    }
}