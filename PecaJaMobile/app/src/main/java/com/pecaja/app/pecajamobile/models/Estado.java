package com.pecaja.app.pecajamobile.models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Estado implements Serializable {

    private final static long serialVersionUID = -7276334585344270081L;

    @SerializedName("id")
    @Expose
    public Long id;
    @SerializedName("uf")
    @Expose
    public String uf;

    public Estado() {
    }

    public Estado(Long id, String uf) {
        this.id = id;
        this.uf = uf;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public String toString() {
        return uf;
    }
}