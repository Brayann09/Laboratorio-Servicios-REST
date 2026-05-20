package com.example.gestioncombustible.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gestioncombustible.data.entity.Estacion;

import java.util.List;

@Dao
public interface EstacionDao {

    // Insertar nueva estación
    @Insert
    void insertar(Estacion estacion);

    // Obtener todas las estaciones
    @Query("SELECT * FROM Estacion")
    List<Estacion> obtenerTodas();

    // Buscar estación por nombre
    @Query("SELECT * FROM Estacion WHERE nombre = :nombre LIMIT 1")
    Estacion buscarPorNombre(String nombre);

    // Actualizar estación
    @Update
    void actualizar(Estacion estacion);
}