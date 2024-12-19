package com.example.restaurante_misti;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.restaurante_misti.Mesa.MesaDao;
import com.example.restaurante_misti.Mesa.MesaEntity;
import com.example.restaurante_misti.Mesero.MeseroDao;
import com.example.restaurante_misti.Mesero.MeseroEntity;
import com.example.restaurante_misti.Pedido.PedidoDao;
import com.example.restaurante_misti.Pedido.PedidoDetalleEntity;
import com.example.restaurante_misti.Pedido.PedidoEntity;
import com.example.restaurante_misti.Platillo.PlatilloDao;
import com.example.restaurante_misti.Platillo.PlatilloEntity;

@Database(
        entities = {
            PlatilloEntity.class,
            MesaEntity.class,
            MeseroEntity.class,
            PedidoEntity.class,
            PedidoDetalleEntity.class
        },
        version = 5,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PlatilloDao platilloDao();

    public abstract MesaDao mesaDao();

    public abstract MeseroDao meseroDao();
    
    public abstract PedidoDao pedidoDao();

}
