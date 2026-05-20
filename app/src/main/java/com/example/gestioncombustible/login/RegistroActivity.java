package com.example.gestioncombustible.login;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.gestioncombustible.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.Calendar;

public class RegistroActivity extends AppCompatActivity {

    EditText etFecha;
    Spinner spRoles;
    Button btnRegistrar, btnUbicacion;

    TextView tvLatitud, tvLongitud;

    int añoSeleccionado;

    FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etFecha = findViewById(R.id.etFecha);
        spRoles = findViewById(R.id.spRoles);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnUbicacion = findViewById(R.id.btnUbicacion);

        tvLatitud = findViewById(R.id.tvLatitud);
        tvLongitud = findViewById(R.id.tvLongitud);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Spinner Roles
        String[] roles = {"Comprador", "Estación de servicio", "Distribuidor","Admin Flota","Admin Sistema","Almacen","Analista","Gerente"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                roles
        );

        spRoles.setAdapter(adapter);

        // Fecha nacimiento
        etFecha.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, year1, month1, dayOfMonth) -> {

                        etFecha.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1);
                        añoSeleccionado = year1;

                    },
                    year,
                    month,
                    day
            );

            datePickerDialog.show();
        });

        // Obtener ubicación
        btnUbicacion.setOnClickListener(v -> obtenerUbicacion());

        // Botón registrar
        btnRegistrar.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();
            int añoActual = calendar.get(Calendar.YEAR);

            int edad = añoActual - añoSeleccionado;

            if (edad < 18) {

                Toast.makeText(this, "Debe ser mayor de 18 años", Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(this, "Usuario Registrado", Toast.LENGTH_SHORT).show();

            }

        });

    }

    private void obtenerUbicacion() {

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {

                    if (location != null) {

                        double lat = location.getLatitude();
                        double lon = location.getLongitude();

                        tvLatitud.setText("Latitud: " + lat);
                        tvLongitud.setText("Longitud: " + lon);

                    } else {

                        Toast.makeText(this,
                                "No se pudo obtener ubicación",
                                Toast.LENGTH_SHORT).show();

                    }

                });

    }

}