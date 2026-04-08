package com.example.gestioncombustible.roles.analista;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestioncombustible.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HistoriaMonitoreoHU08 extends AppCompatActivity {

    private Button btnHistoriaHU08;
    private Button btnCalcularConsumo;
    private Button btnMostrarRanking;
    private Button btnCalcular;
    private Button btnVerRanking;

    private EditText etPlaca;
    private EditText etKilometros;
    private EditText etCombustible;

    private TextView tvResultado;

    private View layoutMenuHU08;
    private View layoutCalculoConsumo;
    private View layoutRankingVehiculos;

    private final ArrayList<Vehiculo> listaVehiculos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia_monitoreo_hu08);

        btnHistoriaHU08 = findViewById(R.id.btnHistoriaHU08);
        btnCalcularConsumo = findViewById(R.id.btnCalcularConsumo);
        btnMostrarRanking = findViewById(R.id.btnMostrarRanking);
        btnCalcular = findViewById(R.id.btnCalcular);
        btnVerRanking = findViewById(R.id.btnVerRanking);

        etPlaca = findViewById(R.id.etPlaca);
        etKilometros = findViewById(R.id.etKilometros);
        etCombustible = findViewById(R.id.etCombustible);

        tvResultado = findViewById(R.id.tvResultadoHU08);

        layoutMenuHU08 = findViewById(R.id.layoutMenuHU08);
        layoutCalculoConsumo = findViewById(R.id.layoutCalculoConsumo);
        layoutRankingVehiculos = findViewById(R.id.layoutRankingVehiculos);

        layoutMenuHU08.setVisibility(View.GONE);
        layoutCalculoConsumo.setVisibility(View.GONE);
        layoutRankingVehiculos.setVisibility(View.GONE);

        btnHistoriaHU08.setOnClickListener(v -> {
            btnHistoriaHU08.setVisibility(View.GONE);
            layoutMenuHU08.setVisibility(View.VISIBLE);
            tvResultado.setText("Seleccione una opción de la historia HU08.");
        });

        btnCalcularConsumo.setOnClickListener(v -> {
            layoutCalculoConsumo.setVisibility(View.VISIBLE);
            layoutRankingVehiculos.setVisibility(View.GONE);
            tvResultado.setText("Módulo de cálculo de consumo activo.");
        });

        btnMostrarRanking.setOnClickListener(v -> {
            layoutRankingVehiculos.setVisibility(View.VISIBLE);
            layoutCalculoConsumo.setVisibility(View.GONE);
            tvResultado.setText("Módulo de ranking de vehículos activo.");
        });

        btnCalcular.setOnClickListener(v -> calcularConsumoPorKilometro());
        btnVerRanking.setOnClickListener(v -> mostrarRankingVehiculos());
    }

    private void calcularConsumoPorKilometro() {
        String placa = etPlaca.getText().toString().trim();
        String kilometrosTexto = etKilometros.getText().toString().trim();
        String combustibleTexto = etCombustible.getText().toString().trim();

        if (placa.isEmpty() || kilometrosTexto.isEmpty() || combustibleTexto.isEmpty()) {
            mostrarMensaje("Complete todos los campos para calcular el consumo");
            return;
        }

        double kilometros = Double.parseDouble(kilometrosTexto);
        double combustible = Double.parseDouble(combustibleTexto);

        if (kilometros <= 0 || combustible <= 0) {
            mostrarMensaje("Los kilómetros y el combustible deben ser mayores a cero");
            return;
        }

        double consumoPorKm = combustible / kilometros;

        Vehiculo existente = buscarVehiculoPorPlaca(placa);

        if (existente != null) {
            existente.setKilometros(kilometros);
            existente.setCombustible(combustible);
            existente.setConsumoPorKm(consumoPorKm);
        } else {
            Vehiculo vehiculo = new Vehiculo(placa, kilometros, combustible, consumoPorKm);
            listaVehiculos.add(vehiculo);
        }

        tvResultado.setText(
                "Consumo calculado correctamente:\n\n" +
                        "Placa: " + placa + "\n" +
                        "Kilómetros recorridos: " + kilometros + "\n" +
                        "Combustible consumido: " + combustible + " galones/litros\n" +
                        "Consumo por kilómetro: " + consumoPorKm
        );

        limpiarCampos();
        mostrarMensaje("Ya se calculó el consumo por kilómetro");
    }

    private void mostrarRankingVehiculos() {
        if (listaVehiculos.isEmpty()) {
            tvResultado.setText("No hay vehículos registrados para mostrar ranking.");
            mostrarMensaje("No hay vehículos en el ranking");
            return;
        }

        ArrayList<Vehiculo> ranking = new ArrayList<>(listaVehiculos);

        Collections.sort(ranking, Comparator.comparingDouble(Vehiculo::getConsumoPorKm));

        StringBuilder texto = new StringBuilder();
        texto.append("Ranking de vehículos (mejor rendimiento primero):\n\n");

        for (int i = 0; i < ranking.size(); i++) {
            Vehiculo vehiculo = ranking.get(i);
            texto.append(i + 1).append(". ");
            texto.append("Placa: ").append(vehiculo.getPlaca()).append("\n");
            texto.append("Kilómetros: ").append(vehiculo.getKilometros()).append("\n");
            texto.append("Combustible: ").append(vehiculo.getCombustible()).append("\n");
            texto.append("Consumo por km: ").append(vehiculo.getConsumoPorKm()).append("\n");
            texto.append("--------------------------\n");
        }

        tvResultado.setText(texto.toString());
        mostrarMensaje("Ya se mostró el ranking de vehículos");
    }

    private Vehiculo buscarVehiculoPorPlaca(String placa) {
        for (Vehiculo vehiculo : listaVehiculos) {
            if (vehiculo.getPlaca().equalsIgnoreCase(placa)) {
                return vehiculo;
            }
        }
        return null;
    }

    private void limpiarCampos() {
        etPlaca.setText("");
        etKilometros.setText("");
        etCombustible.setText("");
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private static class Vehiculo {
        private String placa;
        private double kilometros;
        private double combustible;
        private double consumoPorKm;

        public Vehiculo(String placa, double kilometros, double combustible, double consumoPorKm) {
            this.placa = placa;
            this.kilometros = kilometros;
            this.combustible = combustible;
            this.consumoPorKm = consumoPorKm;
        }

        public String getPlaca() {
            return placa;
        }

        public double getKilometros() {
            return kilometros;
        }

        public double getCombustible() {
            return combustible;
        }

        public double getConsumoPorKm() {
            return consumoPorKm;
        }

        public void setKilometros(double kilometros) {
            this.kilometros = kilometros;
        }

        public void setCombustible(double combustible) {
            this.combustible = combustible;
        }

        public void setConsumoPorKm(double consumoPorKm) {
            this.consumoPorKm = consumoPorKm;
        }
    }
}
