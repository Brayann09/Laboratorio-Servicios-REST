package com.example.gestioncombustible.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.gestioncombustible.data.entity.MovimientoInventario;

import java.util.List;

@Dao
public interface MovimientoInventarioDao {

    @Insert
    void insertar(MovimientoInventario movimiento);

    @Query("SELECT * FROM MovimientoInventario")
    List<MovimientoInventario> obtenerTodos();
}
