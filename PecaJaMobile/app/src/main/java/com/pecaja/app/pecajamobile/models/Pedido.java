package com.pecaja.app.pecajamobile.models;

import java.io.Serializable;
import java.util.Date;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pedido implements Serializable {

    private final static long serialVersionUID = 2123190731851017773L;

    @SerializedName("id")
    @Expose
    public Long id;
    @SerializedName("data")
    @Expose
    public Date data;
    @SerializedName("valor")
    @Expose
    public Double valor;
    @SerializedName("dicaEntregador")
    @Expose
    public String dicaEntregador;
    @SerializedName("cliente")
    @Expose
    public Cliente cliente;
    @SerializedName("estimativa_entrega")
    @Expose
    public Integer estimativaEntrega;
    @SerializedName("statusPedido")
    @Expose
    public String statusPedido;
    @SerializedName("quantidade")
    @Expose
    public Integer quantidade;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("revendedor")
    @Expose
    public Revendedor revendedor;
    @SerializedName("historico")
    @Expose
    public Object historico;
    @SerializedName("produto")
    @Expose
    public Produto produto;

    public Pedido() {
    }

    public Pedido(Long id, Date data, Double valor, String dicaEntregador, Cliente cliente, Integer estimativaEntrega, String statusPedido, Integer quantidade, String status, Revendedor revendedor, Object historico, Produto produto) {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.dicaEntregador = dicaEntregador;
        this.cliente = cliente;
        this.estimativaEntrega = estimativaEntrega;
        this.statusPedido = statusPedido;
        this.quantidade = quantidade;
        this.status = status;
        this.revendedor = revendedor;
        this.historico = historico;
        this.produto = produto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDicaEntregador() {
        return dicaEntregador;
    }

    public void setDicaEntregador(String dicaEntregador) {
        this.dicaEntregador = dicaEntregador;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Integer getEstimativaEntrega() {
        return estimativaEntrega;
    }

    public void setEstimativaEntrega(Integer estimativaEntrega) {
        this.estimativaEntrega = estimativaEntrega;
    }

    public String getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(String statusPedido) {
        this.statusPedido = statusPedido;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Revendedor getRevendedor() {
        return revendedor;
    }

    public void setRevendedor(Revendedor revendedor) {
        this.revendedor = revendedor;
    }

    public Object getHistorico() {
        return historico;
    }

    public void setHistorico(Object historico) {
        this.historico = historico;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", data=" + data.toString() +
                ", valor=" + valor +
                ", dicaEntregador='" + dicaEntregador + '\'' +
                ", cliente=" + cliente +
                ", estimativaEntrega=" + estimativaEntrega +
                ", statusPedido='" + statusPedido + '\'' +
                ", quantidade=" + quantidade +
                ", status='" + status + '\'' +
                ", revendedor=" + revendedor.getFantasia() +
                ", historico=" + historico +
                ", produto=" + produto.getId() +
                '}';
    }
}