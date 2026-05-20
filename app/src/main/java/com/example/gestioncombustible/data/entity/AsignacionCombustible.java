package com.example.gestioncombustible.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// HU05 - Asignación de combustible
@Entity
public class AsignacionCombustible {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String placa;
    public double cupo;
    public String fecha;
}
