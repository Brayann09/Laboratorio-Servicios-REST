package com.example.gestioncombustible.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// HU02 - Histórico movimientos inventario
@Entity
public class MovimientoInventario {

    @PrimaryKey(autoGenerate = true)
    public int id;

    // Tipo combustible
    public String tipoCombustible;

    // Entrada o salida
    public String tipoMovimiento;

    // Cantidad movimiento
    public double cantidad;

    // Fecha movimiento
    public String fecha;


    // Estación donde ocurrió
    public String estacionNombre;
}