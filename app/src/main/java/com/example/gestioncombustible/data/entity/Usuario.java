package com.example.gestioncombustible.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// HU06 - Gestión de usuarios y roles
@Entity
public class Usuario {

    @PrimaryKey
    public int id;

    public String nombre;
    public String correo;
    public String rol;
}
