package com.example.gestioncombustible.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gestioncombustible.data.entity.Usuario;

import java.util.List;

@Dao
public interface UsuarioDao {

    @Insert
    void insertar(Usuario usuario);

    @Update
    void actualizar(Usuario usuario);

    @Delete
    void eliminar(Usuario usuario);

    @Query("SELECT * FROM Usuario")
    List<Usuario> obtenerTodos();

    @Query("SELECT * FROM Usuario WHERE id = :id LIMIT 1")
    Usuario buscarPorId(int id);
}
