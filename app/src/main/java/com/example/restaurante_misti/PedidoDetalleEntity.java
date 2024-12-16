package com.example.restaurante_misti;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = PedidoEntity.class,
                        parentColumns = "id",
                        childColumns = "pedido_id"
                ),
                @ForeignKey(
                        entity = PlatilloEntity.class,
                        parentColumns = "id",
                        childColumns = "platillo_id"
                )
        }
)
public class PedidoDetalleEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int cantidad;
    private float subtotal;
    private int platillo_id;
    private int pedido_id;

    public PedidoDetalleEntity(int cantidad, float subtotal, int platillo_id) {
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.platillo_id = platillo_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public int getPlatillo_id() {
        return platillo_id;
    }

    public void setPlatillo_id(int platillo_id) {
        this.platillo_id = platillo_id;
    }

    public int getPedido_id() {
        return pedido_id;
    }

    public void setPedido_id(int pedido_id) {
        this.pedido_id = pedido_id;
    }
}
