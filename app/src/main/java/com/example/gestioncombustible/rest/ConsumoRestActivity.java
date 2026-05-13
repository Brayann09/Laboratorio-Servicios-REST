package com.example.gestioncombustible.rest;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestioncombustible.R;
import com.example.gestioncombustible.network.api.JsonPlaceholderService;
import com.example.gestioncombustible.network.model.Post;
import com.example.gestioncombustible.network.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsumoRestActivity extends AppCompatActivity {

    // Botones
    private Button btnGet;
    private Button btnPost;

    // TextView resultado
    private TextView txtResultado;

    // Servicio Retrofit
    private JsonPlaceholderService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumo_rest);

        // Relacionar componentes XML
        btnGet = findViewById(R.id.btnGet);
        btnPost = findViewById(R.id.btnPost);
        txtResultado = findViewById(R.id.txtResultado);

        // Crear servicio Retrofit
        apiService = RetrofitClient
                .getClient()
                .create(JsonPlaceholderService.class);

        // Evento botón GET
        btnGet.setOnClickListener(v -> obtenerPosts());

        // Evento botón POST
        btnPost.setOnClickListener(v -> crearPost());
    }

    // Método GET
    private void obtenerPosts() {

        // Crear llamada GET
        Call<List<Post>> call =
                apiService.obtenerPosts();

        // Ejecutar llamada
        call.enqueue(new Callback<List<Post>>() {

            @Override
            public void onResponse(
                    Call<List<Post>> call,
                    Response<List<Post>> response) {

                // Validar respuesta
                if (response.isSuccessful()
                        && response.body() != null) {

                    // StringBuilder para mostrar texto
                    StringBuilder resultado =
                            new StringBuilder();

                    // Recorrer lista de posts
                    for (Post post : response.body()) {

                        resultado.append("ID: ")
                                .append(post.getId())
                                .append("\n");

                        resultado.append("Titulo: ")
                                .append(post.getTitle())
                                .append("\n");

                        resultado.append("Contenido: ")
                                .append(post.getBody())
                                .append("\n\n");
                    }

                    // Mostrar resultado
                    txtResultado.setText(
                            resultado.toString());

                } else {

                    Toast.makeText(
                            ConsumoRestActivity.this,
                            "Error en respuesta",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(
                    Call<List<Post>> call,
                    Throwable t) {

                Toast.makeText(
                        ConsumoRestActivity.this,
                        "Error: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    // Método POST
    private void crearPost() {

        // Crear objeto Post
        Post nuevoPost = new Post(
                1,
                0,
                "Registro Combustible",
                "POST realizado desde Android"
        );

        // Crear llamada POST
        Call<Post> call =
                apiService.crearPost(nuevoPost);

        // Ejecutar POST
        call.enqueue(new Callback<Post>() {

            @Override
            public void onResponse(
                    Call<Post> call,
                    Response<Post> response) {

                if (response.isSuccessful()
                        && response.body() != null) {

                    Post postRespuesta =
                            response.body();

                    txtResultado.setText(

                            "POST EXITOSO\n\n" +

                                    "ID: "
                                    + postRespuesta.getId()

                                    + "\n\nTitulo: "

                                    + postRespuesta.getTitle()

                                    + "\n\nContenido: "

                                    + postRespuesta.getBody()
                    );

                } else {

                    txtResultado.setText(
                            "Error en POST"
                    );
                }
            }

            @Override
            public void onFailure(
                    Call<Post> call,
                    Throwable t) {

                txtResultado.setText(
                        "Error: " + t.getMessage()
                );
            }
        });
    }
}
