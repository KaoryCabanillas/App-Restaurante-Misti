package com.example.restaurante_misti.Pedido;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.restaurante_misti.Orden.OrdenDetalleEntity;

import java.util.List;

@Dao
public interface PedidoDao {

    @Insert
    long insertarPedido(PedidoEntity pedido);

    @Insert
    void insertarDetallePedido(PedidoDetalleEntity detalle);

    // Insertar un pedido con sus detalles (función que inserta un pedido y sus detalles asociados)
    @Transaction
    default void insertarPedidoConDetalles(PedidoEntity pedido, List<PedidoDetalleEntity> detalles) {

        float total = 0;
        // calcular el total del pedido
        for(PedidoDetalleEntity detalle : detalles) {
            total+= detalle.getSubtotal();
        }

        pedido.setTotal(total);
        long pedidoId = insertarPedido(pedido);  // Inserta el pedido y obtiene su ID

        for (PedidoDetalleEntity detalle : detalles) {

            detalle.setPedido_id((int) pedidoId);  // Asigna el ID del pedido a cada detalle
            insertarDetallePedido(detalle);  // Inserta el detalle
        }
    }

    @Query("SELECT p.id AS id_pedido, " +
            "p.fecha, " +
            "p.total, " +
            "m.n_mesa AS mesa, " +
            "me.nombre || ' ' || me.apellido AS mesero " +
            "FROM PedidoEntity AS p " +
            "JOIN MesaEntity AS m ON p.mesa_id = m.id " +
            "JOIN MeseroEntity AS me ON p.mesero_id = me.id")
    List<PedidoListadoEntity> ListadoPedidosHechos();

    @Transaction
    @Query("SELECT * FROM PedidoEntity WHERE id = :pedido_id")
    OrdenDetalleEntity ordenDetalles(int pedido_id);
}
