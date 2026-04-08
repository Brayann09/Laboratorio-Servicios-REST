package roles.adminflota;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestioncombustible.R;

public class HistoriaRegistroHU01 extends AppCompatActivity {

    private Button btnHistoriaHU01;
    private Button btnRegistrarCarga;
    private Button btnCalcularCosto;
    private Button btnGuardarCarga;
    private Button btnCalcularTotal;

    private EditText etPlacaHU01;
    private EditText etCantidadCombustible;
    private EditText etPrecioPorLitro;

    private TextView tvResultadoHU01;

    private View layoutMenuHU01;
    private View layoutRegistrarCarga;
    private View layoutCalcularCosto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia_registro_hu01);

        btnHistoriaHU01 = findViewById(R.id.btnHistoriaHU01);
        btnRegistrarCarga = findViewById(R.id.btnRegistrarCarga);
        btnCalcularCosto = findViewById(R.id.btnCalcularCosto);
        btnGuardarCarga = findViewById(R.id.btnGuardarCarga);
        btnCalcularTotal = findViewById(R.id.btnCalcularTotal);

        etPlacaHU01 = findViewById(R.id.etPlacaHU01);
        etCantidadCombustible = findViewById(R.id.etCantidadCombustible);
        etPrecioPorLitro = findViewById(R.id.etPrecioPorLitro);

        tvResultadoHU01 = findViewById(R.id.tvResultadoHU01);

        layoutMenuHU01 = findViewById(R.id.layoutMenuHU01);
        layoutRegistrarCarga = findViewById(R.id.layoutRegistrarCarga);
        layoutCalcularCosto = findViewById(R.id.layoutCalcularCosto);

        layoutMenuHU01.setVisibility(View.GONE);
        layoutRegistrarCarga.setVisibility(View.GONE);
        layoutCalcularCosto.setVisibility(View.GONE);

        btnHistoriaHU01.setOnClickListener(v -> {
            btnHistoriaHU01.setVisibility(View.GONE);
            layoutMenuHU01.setVisibility(View.VISIBLE);
            tvResultadoHU01.setText("Seleccione una opción de la historia HU01.");
        });

        btnRegistrarCarga.setOnClickListener(v -> {
            layoutRegistrarCarga.setVisibility(View.VISIBLE);
            layoutCalcularCosto.setVisibility(View.GONE);
            tvResultadoHU01.setText("Módulo de registro de carga activo.");
        });

        btnCalcularCosto.setOnClickListener(v -> {
            layoutCalcularCosto.setVisibility(View.VISIBLE);
            layoutRegistrarCarga.setVisibility(View.GONE);
            tvResultadoHU01.setText("Módulo de cálculo de costo total activo.");
        });

        btnGuardarCarga.setOnClickListener(v -> registrarCargaCombustible());
        btnCalcularTotal.setOnClickListener(v -> calcularCostoTotal());
    }

    private void registrarCargaCombustible() {
        String placa = etPlacaHU01.getText().toString().trim();
        String cantidadTexto = etCantidadCombustible.getText().toString().trim();
        String precioTexto = etPrecioPorLitro.getText().toString().trim();

        if (placa.isEmpty() || cantidadTexto.isEmpty() || precioTexto.isEmpty()) {
            mostrarMensaje("Complete todos los campos para registrar la carga");
            return;
        }

        double cantidad = Double.parseDouble(cantidadTexto);
        double precio = Double.parseDouble(precioTexto);

        if (cantidad <= 0 || precio <= 0) {
            mostrarMensaje("La cantidad y el precio deben ser mayores a cero");
            return;
        }

        tvResultadoHU01.setText(
                "Carga registrada correctamente:\n\n" +
                        "Placa: " + placa + "\n" +
                        "Cantidad de combustible: " + cantidad + "\n" +
                        "Precio por litro/galón: " + precio
        );

        mostrarMensaje("Ya se registró la carga de combustible");
    }

    private void calcularCostoTotal() {
        String placa = etPlacaHU01.getText().toString().trim();
        String cantidadTexto = etCantidadCombustible.getText().toString().trim();
        String precioTexto = etPrecioPorLitro.getText().toString().trim();

        if (placa.isEmpty() || cantidadTexto.isEmpty() || precioTexto.isEmpty()) {
            mostrarMensaje("Complete todos los campos para calcular el costo total");
            return;
        }

        double cantidad = Double.parseDouble(cantidadTexto);
        double precio = Double.parseDouble(precioTexto);

        if (cantidad <= 0 || precio <= 0) {
            mostrarMensaje("La cantidad y el precio deben ser mayores a cero");
            return;
        }

        double costoTotal = cantidad * precio;

        tvResultadoHU01.setText(
                "Costo total calculado correctamente:\n\n" +
                        "Placa: " + placa + "\n" +
                        "Cantidad de combustible: " + cantidad + "\n" +
                        "Precio por litro/galón: " + precio + "\n" +
                        "Costo total: " + costoTotal
        );

        mostrarMensaje("Ya se calculó el costo total");
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
