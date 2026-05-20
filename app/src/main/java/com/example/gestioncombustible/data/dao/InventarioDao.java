package com.example.gestioncombustible.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gestioncombustible.data.entity.Inventario;

import java.util.List;

@Dao
public interface InventarioDao {

    @Insert
    void insertar(Inventario inventario);

    @Update
    void actualizar(Inventario inventario);

    @Query("SELECT * FROM Inventario WHERE tipoCombustible = :tipo LIMIT 1")
    Inventario obtenerPorTipo(String tipo);

    @Query("SELECT * FROM Inventario")
    List<Inventario> obtenerTodos();
}