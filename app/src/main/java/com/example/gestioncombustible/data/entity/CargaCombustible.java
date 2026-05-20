package com.example.gestioncombustible.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// HU01 - Registro de cargas de combustible
@Entity
public class CargaCombustible {

    @PrimaryKey(autoGenerate = true)
    public int id;

    // Placa del vehículo
    public String placa;

    // Cantidad de combustible
    public double cantidad;

    // Precio por litro/galón
    public double precio;

    // Costo antes del descuento
    public double costoTotal;

    // Fecha del registro
    public String fecha;

    // =========================
    // NUEVOS CAMPOS
    // =========================

    // Nombre de la estación
    public String estacionNombre;

    // Indica si tiene subsidio
    public boolean tieneSubsidio;

    // Valor descontado
    public double descuento;

    // Total después del subsidio
    public double totalFinal;
}