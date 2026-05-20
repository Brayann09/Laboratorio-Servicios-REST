package com.example.gestioncombustible;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import roles.adminflota.AdminFlotaMenuActivity;
import roles.comprador.CompradorMenuActivity;
import roles.gerente.ReporteConsumoHU07;
import roles.estacionservicio.HistoriaGestionInventarioHU10;

import com.example.gestioncombustible.rest.ConsumoRestActivity;
import com.example.gestioncombustible.roles.adminsistema.HistoriaConfiguracionHU06;
import com.example.gestioncombustible.roles.almacen.HistoriaControlInventarioHU02;
import com.example.gestioncombustible.roles.analista.HistoriaMonitoreoHU08;
import com.example.gestioncombustible.roles.distribuidor.RegistroEntregaActivityHU03;

public class MainActivity extends AppCompatActivity {

    private LinearLayout btnAdminFlota;
    private LinearLayout btnAlmacen;
    private LinearLayout btnDistribuidor;
    private LinearLayout btnComprador;
    private LinearLayout btnAdminSistema;
    private LinearLayout btnAnalista;
    private LinearLayout btnGerente;
    private LinearLayout btnEstacionServicio;

    // BOTON REST
    private LinearLayout btnRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Carga interfaz principal
        setContentView(R.layout.activity_main);

        // Conecta botones XML
        btnAdminFlota = findViewById(R.id.btnAdminFlota);
        btnAlmacen = findViewById(R.id.btnAlmacen);
        btnDistribuidor = findViewById(R.id.btnDistribuidor);
        btnComprador = findViewById(R.id.btnComprador);
        btnAdminSistema = findViewById(R.id.btnAdminSistema);
        btnAnalista = findViewById(R.id.btnAnalista);
        btnGerente = findViewById(R.id.btnGerente);
        btnEstacionServicio = findViewById(R.id.btnEstacionServicio);

        // NUEVO
        btnRest = findViewById(R.id.btnRest);

        // Abre menu admin flota
        btnAdminFlota.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    AdminFlotaMenuActivity.class
            );

            startActivity(intent);
        });

        // Abre HU02 almacen
        btnAlmacen.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                MainActivity.this,
                                HistoriaControlInventarioHU02.class
                        )
                )
        );

        // Abre HU03 distribuidor
        btnDistribuidor.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                MainActivity.this,
                                RegistroEntregaActivityHU03.class
                        )
                )
        );

        // Abre menu comprador
        btnComprador.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                this,
                                CompradorMenuActivity.class
                        )
                )
        );

        // Abre HU06 admin sistema
        btnAdminSistema.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                MainActivity.this,
                                HistoriaConfiguracionHU06.class
                        )
                )
        );

        // Abre HU08 analista
        btnAnalista.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                MainActivity.this,
                                HistoriaMonitoreoHU08.class
                        )
                )
        );

        // Abre HU07 gerente
        btnGerente.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    ReporteConsumoHU07.class
            );

            startActivity(intent);

        });

        // Abre HU10 estacion servicio
        btnEstacionServicio.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    HistoriaGestionInventarioHU10.class
            );

            startActivity(intent);

        });

        // ABRE LABORATORIO REST
        btnRest.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    ConsumoRestActivity.class
            );

            startActivity(intent);

        });
    }
}