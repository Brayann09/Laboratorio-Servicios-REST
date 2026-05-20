package com.example.gestioncombustible.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.gestioncombustible.data.dao.*;
import com.example.gestioncombustible.data.entity.*;

@Database(entities = {

        CargaCombustible.class,
        AsignacionCombustible.class,
        Usuario.class,
        Inventario.class,
        MovimientoInventario.class,


        Estacion.class

}, version = 7)

public abstract class AppDatabase extends RoomDatabase {

    public abstract CargaCombustibleDao cargaDao();

    public abstract AsignacionCombustibleDao asignacionDao();

    public abstract UsuarioDao usuarioDao();

    public abstract InventarioDao inventarioDao();

    public abstract MovimientoInventarioDao movimientoInventarioDao();


    public abstract EstacionDao estacionDao();
}