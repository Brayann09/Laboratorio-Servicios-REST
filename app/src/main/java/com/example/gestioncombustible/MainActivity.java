package com.example.gestioncombustible;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import roles.adminflota.AdminFlotaMenuActivity;
import com.example.gestioncombustible.roles.almacen.HistoriaControlInventarioHU02;
import com.example.gestioncombustible.roles.distribuidor.RegistroEntregaActivityHU03;
import com.example.gestioncombustible.roles.comprador.AplicacionPreciosSubsidiosActivityHU04;
import com.example.gestioncombustible.roles.adminsistema.HistoriaConfiguracionHU06;
import com.example.gestioncombustible.roles.analista.HistoriaMonitoreoHU08;

public class MainActivity extends AppCompatActivity {

    private LinearLayout btnAdminFlota;
    private LinearLayout btnAlmacen;
    private LinearLayout btnDistribuidor;
    private LinearLayout btnComprador;
    private LinearLayout btnAdminSistema;
    private LinearLayout btnAnalista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdminFlota = findViewById(R.id.btnAdminFlota);
        btnAlmacen = findViewById(R.id.btnAlmacen);
        btnDistribuidor = findViewById(R.id.btnDistribuidor);
        btnComprador = findViewById(R.id.btnComprador);
        btnAdminSistema = findViewById(R.id.btnAdminSistema);
        btnAnalista = findViewById(R.id.btnAnalista);

        btnAdminFlota.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AdminFlotaMenuActivity.class);
            startActivity(intent);
        });

        btnAlmacen.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, HistoriaControlInventarioHU02.class)));

        btnDistribuidor.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, RegistroEntregaActivityHU03.class)));

        btnComprador.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AplicacionPreciosSubsidiosActivityHU04.class)));

        btnAdminSistema.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, HistoriaConfiguracionHU06.class)));

        btnAnalista.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, HistoriaMonitoreoHU08.class)));
    }
}