package com.example.gestioncombustible.network.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    // URL base de la API
    private static final String BASE_URL =
            "https://jsonplaceholder.typicode.com/";

    private static Retrofit retrofit;

    // Método para crear Retrofit
    public static Retrofit getClient() {

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(
                            GsonConverterFactory.create()
                    )
                    .build();
        }

        return retrofit;
    }
}
