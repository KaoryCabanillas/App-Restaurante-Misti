package com.example.restaurante_misti;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MesaEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String n_mesa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getN_mesa() {
        return n_mesa;
    }

    public void setN_mesa(String n_mesa) {
        this.n_mesa = n_mesa;
    }

    public MesaEntity(String n_mesa) {
        this.n_mesa = n_mesa;
    }
}
