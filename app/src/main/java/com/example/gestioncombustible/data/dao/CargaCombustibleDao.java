package com.example.gestioncombustible.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.gestioncombustible.data.entity.CargaCombustible;

import java.util.List;

@Dao
public interface CargaCombustibleDao {

    @Insert
    void insertar(CargaCombustible carga);

    @Query("SELECT * FROM CargaCombustible")
    List<CargaCombustible> obtenerTodas();
}