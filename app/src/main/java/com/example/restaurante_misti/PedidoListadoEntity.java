package com.example.restaurante_misti;

import androidx.room.Entity;

@Entity
public class PedidoListadoEntity {
    private int id_pedido;
    private String fecha;
    private float total;
    private String mesa;
    private String mesero;

    public PedidoListadoEntity(int id_pedido, String fecha, float total, String mesa, String mesero) {
        this.id_pedido = id_pedido;
        this.fecha = fecha;
        this.total = total;
        this.mesa = mesa;
        this.mesero = mesero;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public String getMesero() {
        return mesero;
    }

    public void setMesero(String mesero) {
        this.mesero = mesero;
    }

    @Override
    public String toString() {
        return this.mesa + " | Total: S/" + this.total + "\n" +
                "Mesero: " + this.mesero + " | Fecha: " + this.fecha;
    }
}
