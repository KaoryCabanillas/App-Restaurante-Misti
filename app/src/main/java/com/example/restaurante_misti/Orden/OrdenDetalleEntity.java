package com.example.restaurante_misti.Orden;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.restaurante_misti.Pedido.PedidoDetalleEntity;
import com.example.restaurante_misti.Pedido.PedidoEntity;

import java.util.List;

public class OrdenDetalleEntity {

    @Embedded
    public PedidoEntity pedido;

    @Relation(
            parentColumn = "id",
            entityColumn = "pedido_id"
    )
    public List<PedidoDetalleEntity> detalle_pedido;

}
