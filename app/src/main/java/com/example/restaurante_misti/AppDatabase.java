package com.example.restaurante_misti;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(
        entities = {PlatilloEntity.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract PlatilloDao platilloDao();

}
