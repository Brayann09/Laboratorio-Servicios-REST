package com.example.gestioncombustible.roles.distribuidor;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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
import com.google.android.gms.location.Priority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RegistroEntregaActivityHU03 extends AppCompatActivity {

    // Campos del formulario
    private EditText etVehiculo;
    private EditText etVolumen;

    // Spinner estaciones
    private Spinner spinnerEstaciones;

    // Botones
    private Button btnGuardarEntrega;
    private Button btnUbicacion;

    // TextView GPS
    private TextView txtLatitud;
    private TextView txtLongitud;
    private TextView txtDireccion;
    private TextView txtRecorrido;
    private TextView txtDestinoCoords;
    private TextView txtDistancia;
    private TextView txtEstado;

    // Cliente GPS
    private FusedLocationProviderClient fusedLocationClient;

    // Historial del recorrido
    private ArrayList<String> recorrido = new ArrayList<>();

    // Estaciones del sistema
    private final String[] estaciones = {
            "Estacion Norte - Bogotá",
            "Estacion Sur - Cali",
            "Estacion Centro - Medellín"
    };

    // Coordenadas destino
    private final double[] latitudes = {
            4.7110,
            3.4516,
            6.2442
    };

    private final double[] longitudes = {
            -74.0721,
            -76.5320,
            -75.5812
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Conectar XML
        setContentView(R.layout.activity_registro_entrega_hu03);

        // Inicializar campos
        etVehiculo = findViewById(R.id.etVehiculo);
        etVolumen = findViewById(R.id.etVolumen);

        // Inicializar spinner
        spinnerEstaciones =
                findViewById(R.id.spinnerEstaciones);

        // Inicializar botones
        btnGuardarEntrega =
                findViewById(R.id.btnGuardarEntrega);

        btnUbicacion =
                findViewById(R.id.btnUbicacion);

        // Inicializar TextView
        txtLatitud =
                findViewById(R.id.txtLatitud);

        txtLongitud =
                findViewById(R.id.txtLongitud);

        txtDireccion =
                findViewById(R.id.txtDireccion);

        txtRecorrido =
                findViewById(R.id.txtRecorrido);

        txtDestinoCoords =
                findViewById(R.id.txtDestinoCoords);

        txtDistancia =
                findViewById(R.id.txtDistancia);

        txtEstado =
                findViewById(R.id.txtEstado);

        // Inicializar FusedLocationProviderClient
        fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(this);

        // Configuración estaciones
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        estaciones);

        spinnerEstaciones.setAdapter(adapter);

        // Mostrar coordenadas destino
        spinnerEstaciones.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            android.view.View view,
                            int position,
                            long id) {

                        txtDestinoCoords.setText(
                                "Destino:\nLat: "
                                        + latitudes[position]
                                        + "\nLng: "
                                        + longitudes[position]);
                    }

                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {
                    }
                });

        // Guardar entrega
        btnGuardarEntrega.setOnClickListener(v -> {

            String vehiculo =
                    etVehiculo.getText().toString().trim();

            String volumen =
                    etVolumen.getText().toString().trim();

            String estacion =
                    spinnerEstaciones.getSelectedItem().toString();

            if (vehiculo.isEmpty() || volumen.isEmpty()) {

                Toast.makeText(
                        this,
                        "Complete todos los campos",
                        Toast.LENGTH_SHORT).show();

            } else {

                // Estado inicial transporte
                txtEstado.setText(
                        "Estado del transporte: EN TRÁNSITO");

                Toast.makeText(
                        this,
                        "Entrega enviada hacia "
                                + estacion,
                        Toast.LENGTH_LONG).show();
            }
        });

        // Obtener ubicación GPS
        btnUbicacion.setOnClickListener(
                v -> obtenerUbicacion());
    }

    // Obtener ubicación GPS actual
    private void obtenerUbicacion() {

        // Verificar permisos GPS
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    100);

            return;
        }

        // Obtener ubicación actual en tiempo real
        fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                null
        ).addOnSuccessListener(location -> {

            if (location != null) {

                double latitudActual =
                        location.getLatitude();

                double longitudActual =
                        location.getLongitude();

                // Mostrar coordenadas actuales
                txtLatitud.setText(
                        "Latitud actual: "
                                + latitudActual);

                txtLongitud.setText(
                        "Longitud actual: "
                                + longitudActual);

                // Uso de Geocoder
                Geocoder geocoder =
                        new Geocoder(
                                this,
                                Locale.getDefault());

                try {

                    List<Address> direcciones =
                            geocoder.getFromLocation(
                                    latitudActual,
                                    longitudActual,
                                    1);

                    if (direcciones != null
                            && !direcciones.isEmpty()) {

                        String direccion =
                                direcciones.get(0)
                                        .getAddressLine(0);

                        // Mostrar dirección
                        txtDireccion.setText(
                                "Dirección actual:\n"
                                        + direccion);

                        // Guardar recorrido
                        recorrido.add(direccion);

                        // Mostrar historial recorrido
                        StringBuilder historial =
                                new StringBuilder();

                        historial.append(
                                "Recorrido del transporte:\n\n");

                        for (String punto : recorrido) {

                            historial.append("• ")
                                    .append(punto)
                                    .append("\n");
                        }

                        txtRecorrido.setText(
                                historial.toString());
                    }

                } catch (IOException e) {

                    e.printStackTrace();
                }

                // Calcular distancia al destino
                int posicion =
                        spinnerEstaciones.getSelectedItemPosition();

                double latDestino =
                        latitudes[posicion];

                double lngDestino =
                        longitudes[posicion];

                float[] resultados = new float[1];

                Location.distanceBetween(
                        latitudActual,
                        longitudActual,
                        latDestino,
                        lngDestino,
                        resultados);

                // Distancia en kilómetros
                float distanciaKm =
                        resultados[0] / 1000;

                txtDistancia.setText(
                        "Distancia restante: "
                                + String.format(
                                Locale.getDefault(),
                                "%.2f km",
                                distanciaKm));

                // Estado del transporte
                if (distanciaKm > 300) {

                    txtEstado.setText(
                            "Estado del transporte: EN TRÁNSITO");

                } else if (distanciaKm > 50) {

                    txtEstado.setText(
                            "Estado del transporte: PRÓXIMO A DESTINO");

                } else {

                    txtEstado.setText(
                            "Estado del transporte: ENTREGA COMPLETADA");
                }

            } else {

                Toast.makeText(
                        this,
                        "No se pudo obtener ubicación",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}