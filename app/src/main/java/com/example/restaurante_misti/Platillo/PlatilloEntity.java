package com.example.restaurante_misti.Platillo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PlatilloEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;
    private float precio;

    public PlatilloEntity(String nombre, float precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public PlatilloEntity() {    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Platillo: " + nombre + " - S/" + precio;
    }
}
