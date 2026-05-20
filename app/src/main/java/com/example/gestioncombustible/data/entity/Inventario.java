package com.example.gestioncombustible.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// HU02 - Inventario actual
@Entity
public class Inventario {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String tipoCombustible;
    public double cantidadDisponible;
    public String fecha;
}
