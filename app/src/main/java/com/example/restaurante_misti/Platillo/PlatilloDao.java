package com.example.restaurante_misti.Platillo;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlatilloDao {

    @Query("SELECT * FROM PlatilloEntity")
    List<PlatilloEntity> listadoPlatillo();

    @Insert
    void nuevoPlatillo(PlatilloEntity platillo);

}
