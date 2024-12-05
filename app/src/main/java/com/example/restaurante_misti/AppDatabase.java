package com.example.restaurante_misti;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(
        entities = {
            PlatilloEntity.class,
            MesaEntity.class,
            MeseroEntity.class
        },
        version = 3,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PlatilloDao platilloDao();

    public abstract MesaDao mesaDao();

    public abstract MeseroDao meseroDao();

}
