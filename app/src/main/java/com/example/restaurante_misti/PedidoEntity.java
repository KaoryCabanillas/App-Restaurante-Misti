package com.example.restaurante_misti;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = MesaEntity.class,
                        parentColumns = "id",
                        childColumns = "mesa_id"
                ),
                @ForeignKey(
                        entity = MesaEntity.class,
                        parentColumns = "id",
                        childColumns = "mesero_id"
                )
        }
)
public class PedidoEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int mesa_id;
    private int mesero_id;
    private String fecha;
    private float total;

    public PedidoEntity(int mesa_id, int mesero_id, String fecha) {
        this.mesa_id = mesa_id;
        this.mesero_id = mesero_id;
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMesa_id() {
        return mesa_id;
    }

    public void setMesa_id(int mesa_id) {
        this.mesa_id = mesa_id;
    }

    public int getMesero_id() {
        return mesero_id;
    }

    public void setMesero_id(int mesero_id) {
        this.mesero_id = mesero_id;
    }
}
