package com.example.gestioncombustible.network.api;

import com.example.gestioncombustible.network.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceholderService {

    // Servicio GET para obtener posts
    @GET("posts")
    Call<List<Post>> obtenerPosts();

    // Servicio POST para crear post
    @POST("posts")
    Call<Post> crearPost(@Body Post post);
}
