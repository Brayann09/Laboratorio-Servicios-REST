package com.example.gestioncombustible.roles.comprador;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestioncombustible.R;

public class AplicacionPreciosSubsidiosActivityHU04 extends AppCompatActivity {

    private Spinner spinnerTipoVehiculo;
    private Spinner spinnerZona;
    private EditText etCantidadCombustible;
    private Button btnCalcularPrecio;
    private TextView tvResultadoHU04;

    private final String[] tiposVehiculo = {
            "Particular",
            "Servicio público",
            "Oficial",
            "Diplomático"
    };

    private final String[] zonas = {
            "Zona urbana",
            "Zona rural",
            "Zona fronteriza"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplicacion_precios_subsidios_hu04);

        spinnerTipoVehiculo = findViewById(R.id.spinnerTipoVehiculo);
        spinnerZona = findViewById(R.id.spinnerZona);
        etCantidadCombustible = findViewById(R.id.etCantidadCombustible);
        btnCalcularPrecio = findViewById(R.id.btnCalcularPrecio);
        tvResultadoHU04 = findViewById(R.id.tvResultadoHU04);

        ArrayAdapter<String> adapterVehiculos = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                tiposVehiculo
        );
        adapterVehiculos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoVehiculo.setAdapter(adapterVehiculos);

        ArrayAdapter<String> adapterZonas = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                zonas
        );
        adapterZonas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerZona.setAdapter(adapterZonas);

        btnCalcularPrecio.setOnClickListener(v -> calcularPrecio());
    }

    private void calcularPrecio() {
        String tipoVehiculo = spinnerTipoVehiculo.getSelectedItem().toString();
        String zona = spinnerZona.getSelectedItem().toString();
        String cantidadTexto = etCantidadCombustible.getText().toString().trim();

        if (cantidadTexto.isEmpty()) {
            Toast.makeText(this, "Ingrese la cantidad de combustible", Toast.LENGTH_SHORT).show();
            return;
        }

        double cantidad = Double.parseDouble(cantidadTexto);

        if (cantidad <= 0) {
            Toast.makeText(this, "La cantidad debe ser mayor a cero", Toast.LENGTH_SHORT).show();
            return;
        }

        double precioBase = 15000.0;
        double precioFinal = precioBase;

        if (tipoVehiculo.equals("Servicio público")) {
            precioFinal -= 2000;
        } else if (tipoVehiculo.equals("Oficial")) {
            precioFinal += 1000;
        } else if (tipoVehiculo.equals("Diplomático")) {
            precioFinal += 1500;
        }

        if (zona.equals("Zona rural")) {
            precioFinal += 500;
        } else if (zona.equals("Zona fronteriza")) {
            precioFinal -= 1000;
        }

        double total = precioFinal * cantidad;

        String mensaje = "Resultado del cálculo:\n\n" +
                "Tipo de vehículo: " + tipoVehiculo + "\n" +
                "Zona: " + zona + "\n" +
                "Cantidad: " + cantidad + " galones/litros\n" +
                "Precio por unidad: $" + precioFinal + "\n" +
                "Total a pagar: $" + total;

        tvResultadoHU04.setText(mensaje);
        Toast.makeText(this, "Precio calculado correctamente", Toast.LENGTH_SHORT).show();

        etCantidadCombustible.setText("");
    }
}
