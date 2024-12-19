package com.example.restaurante_misti.Mesero;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity()
public class MeseroEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;
    private String apellido;

    public MeseroEntity(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
