package com.example.restaurante_misti;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MesaDao {

    @Query(" SELECT * FROM MesaEntity ")
    List<MesaEntity> listadoMesas();

    @Insert
    void nuevaMesa(MesaEntity mesa);

}
