package com.example.restaurante_misti;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MeseroDao {

    @Query("SELECT * FROM MeseroEntity")
    List<MeseroEntity> listadoMeseros();

    @Insert
    void nuevoMesero(MeseroEntity mesero);

}
