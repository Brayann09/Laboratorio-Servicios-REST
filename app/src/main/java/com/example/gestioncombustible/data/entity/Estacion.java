package com.example.gestioncombustible.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Estacion {

    @PrimaryKey(autoGenerate = true)
    private int id;

    // Nombre estación
    private String nombre;

    // Ciudad o zona
    private String zona;

    // Inventario disponible
    private double inventarioCorriente;

    private double inventarioAcpm;

    // Precio por galón
    private double precioCorriente;

    private double precioAcpm;

    // =========================
    // GETTERS Y SETTERS
    // =========================

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

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public double getInventarioCorriente() {
        return inventarioCorriente;
    }

    public void setInventarioCorriente(double inventarioCorriente) {
        this.inventarioCorriente = inventarioCorriente;
    }

    public double getInventarioAcpm() {
        return inventarioAcpm;
    }

    public void setInventarioAcpm(double inventarioAcpm) {
        this.inventarioAcpm = inventarioAcpm;
    }

    public double getPrecioCorriente() {
        return precioCorriente;
    }

    public void setPrecioCorriente(double precioCorriente) {
        this.precioCorriente = precioCorriente;
    }

    public double getPrecioAcpm() {
        return precioAcpm;
    }

    public void setPrecioAcpm(double precioAcpm) {
        this.precioAcpm = precioAcpm;
    }
}
