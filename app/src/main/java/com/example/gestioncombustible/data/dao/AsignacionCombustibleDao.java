package com.example.gestioncombustible.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.gestioncombustible.data.entity.AsignacionCombustible;

import java.util.List;

@Dao
public interface AsignacionCombustibleDao {

    @Insert
    void insertar(AsignacionCombustible asignacion);

    @Query("SELECT * FROM AsignacionCombustible")
    List<AsignacionCombustible> obtenerTodas();
}
