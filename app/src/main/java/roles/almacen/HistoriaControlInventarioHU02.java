package com.example.gestioncombustible.roles.almacen;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestioncombustible.R;

import java.util.ArrayList;

public class HistoriaControlInventarioHU02 extends AppCompatActivity {

    private Button btnHistoriaHU02;
    private Button btnConsultarExistencias;
    private Button btnActualizarInventario;
    private Button btnGenerarAlertas;
    private Button btnRegistrarIngresoCompra;
    private Button btnConsultarHistorico;

    private Button btnVerExistencias;
    private Button btnActualizar;
    private Button btnVerAlerta;
    private Button btnRegistrarCompra;
    private Button btnVerHistorico;

    private Spinner spinnerTipoCombustible;
    private Spinner spinnerMovimiento;

    private EditText etCantidadMovimiento;
    private EditText etCantidadCompra;
    private EditText etNivelMinimo;

    private TextView tvResultadoHU02;

    private View layoutMenuHU02;
    private View layoutExistencias;
    private View layoutActualizarInventario;
    private View layoutAlertas;
    private View layoutIngresosCompra;
    private View layoutHistorico;

    private final String[] tiposCombustible = {
            "Gasolina Corriente",
            "Gasolina Extra",
            "Diésel"
    };

    private final String[] tiposMovimiento = {
            "Ingreso",
            "Salida"
    };

    private double existenciaCorriente = 5000;
    private double existenciaExtra = 3000;
    private double existenciaDiesel = 4000;

    private final ArrayList<String> historicoMovimientos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia_control_inventario_hu02);

        btnHistoriaHU02 = findViewById(R.id.btnHistoriaHU02);
        btnConsultarExistencias = findViewById(R.id.btnConsultarExistencias);
        btnActualizarInventario = findViewById(R.id.btnActualizarInventario);
        btnGenerarAlertas = findViewById(R.id.btnGenerarAlertas);
        btnRegistrarIngresoCompra = findViewById(R.id.btnRegistrarIngresoCompra);
        btnConsultarHistorico = findViewById(R.id.btnConsultarHistorico);

        btnVerExistencias = findViewById(R.id.btnVerExistencias);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnVerAlerta = findViewById(R.id.btnVerAlerta);
        btnRegistrarCompra = findViewById(R.id.btnRegistrarCompra);
        btnVerHistorico = findViewById(R.id.btnVerHistorico);

        spinnerTipoCombustible = findViewById(R.id.spinnerTipoCombustible);
        spinnerMovimiento = findViewById(R.id.spinnerMovimiento);

        etCantidadMovimiento = findViewById(R.id.etCantidadMovimiento);
        etCantidadCompra = findViewById(R.id.etCantidadCompra);
        etNivelMinimo = findViewById(R.id.etNivelMinimo);

        tvResultadoHU02 = findViewById(R.id.tvResultadoHU02);

        layoutMenuHU02 = findViewById(R.id.layoutMenuHU02);
        layoutExistencias = findViewById(R.id.layoutExistencias);
        layoutActualizarInventario = findViewById(R.id.layoutActualizarInventario);
        layoutAlertas = findViewById(R.id.layoutAlertas);
        layoutIngresosCompra = findViewById(R.id.layoutIngresosCompra);
        layoutHistorico = findViewById(R.id.layoutHistorico);

        ArrayAdapter<String> adapterCombustible = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                tiposCombustible
        );
        adapterCombustible.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTipoCombustible.setAdapter(adapterCombustible);

        ArrayAdapter<String> adapterMovimiento = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                tiposMovimiento
        );
        adapterMovimiento.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMovimiento.setAdapter(adapterMovimiento);

        layoutMenuHU02.setVisibility(View.GONE);
        layoutExistencias.setVisibility(View.GONE);
        layoutActualizarInventario.setVisibility(View.GONE);
        layoutAlertas.setVisibility(View.GONE);
        layoutIngresosCompra.setVisibility(View.GONE);
        layoutHistorico.setVisibility(View.GONE);

        btnHistoriaHU02.setOnClickListener(v -> {
            btnHistoriaHU02.setVisibility(View.GONE);
            layoutMenuHU02.setVisibility(View.VISIBLE);
            tvResultadoHU02.setText("Seleccione una opción de la historia HU02.");
        });

        btnConsultarExistencias.setOnClickListener(v -> mostrarSolo(layoutExistencias));
        btnActualizarInventario.setOnClickListener(v -> mostrarSolo(layoutActualizarInventario));
        btnGenerarAlertas.setOnClickListener(v -> mostrarSolo(layoutAlertas));
        btnRegistrarIngresoCompra.setOnClickListener(v -> mostrarSolo(layoutIngresosCompra));
        btnConsultarHistorico.setOnClickListener(v -> mostrarSolo(layoutHistorico));

        btnVerExistencias.setOnClickListener(v -> consultarExistenciasActuales());
        btnActualizar.setOnClickListener(v -> actualizarInventario());
        btnVerAlerta.setOnClickListener(v -> generarAlertasPorNivelBajo());
        btnRegistrarCompra.setOnClickListener(v -> registrarIngresoPorCompra());
        btnVerHistorico.setOnClickListener(v -> consultarHistoricoMovimientos());
    }

    private void mostrarSolo(View layoutVisible) {
        layoutExistencias.setVisibility(View.GONE);
        layoutActualizarInventario.setVisibility(View.GONE);
        layoutAlertas.setVisibility(View.GONE);
        layoutIngresosCompra.setVisibility(View.GONE);
        layoutHistorico.setVisibility(View.GONE);

        layoutVisible.setVisibility(View.VISIBLE);
    }

    private void consultarExistenciasActuales() {
        String texto = "Existencias actuales:\n\n" +
                "Gasolina Corriente: " + existenciaCorriente + " litros\n" +
                "Gasolina Extra: " + existenciaExtra + " litros\n" +
                "Diésel: " + existenciaDiesel + " litros";

        tvResultadoHU02.setText(texto);
        mostrarMensaje("Ya se consultaron las existencias actuales");
    }

    private void actualizarInventario() {
        String combustible = spinnerTipoCombustible.getSelectedItem().toString();
        String movimiento = spinnerMovimiento.getSelectedItem().toString();
        String cantidadTexto = etCantidadMovimiento.getText().toString().trim();

        if (cantidadTexto.isEmpty()) {
            mostrarMensaje("Ingrese la cantidad para actualizar el inventario");
            return;
        }

        double cantidad = Double.parseDouble(cantidadTexto);

        if (cantidad <= 0) {
            mostrarMensaje("La cantidad debe ser mayor a cero");
            return;
        }

        if (combustible.equals("Gasolina Corriente")) {
            if (movimiento.equals("Ingreso")) {
                existenciaCorriente += cantidad;
            } else {
                if (cantidad > existenciaCorriente) {
                    mostrarMensaje("No hay suficiente inventario de gasolina corriente");
                    return;
                }
                existenciaCorriente -= cantidad;
            }
        } else if (combustible.equals("Gasolina Extra")) {
            if (movimiento.equals("Ingreso")) {
                existenciaExtra += cantidad;
            } else {
                if (cantidad > existenciaExtra) {
                    mostrarMensaje("No hay suficiente inventario de gasolina extra");
                    return;
                }
                existenciaExtra -= cantidad;
            }
        } else if (combustible.equals("Diésel")) {
            if (movimiento.equals("Ingreso")) {
                existenciaDiesel += cantidad;
            } else {
                if (cantidad > existenciaDiesel) {
                    mostrarMensaje("No hay suficiente inventario de diésel");
                    return;
                }
                existenciaDiesel -= cantidad;
            }
        }

        historicoMovimientos.add("Movimiento: " + movimiento + " | Combustible: " + combustible + " | Cantidad: " + cantidad + " litros");

        tvResultadoHU02.setText(
                "Inventario actualizado correctamente:\n\n" +
                        "Tipo de combustible: " + combustible + "\n" +
                        "Tipo de movimiento: " + movimiento + "\n" +
                        "Cantidad: " + cantidad + " litros"
        );

        etCantidadMovimiento.setText("");
        mostrarMensaje("Ya se actualizó el inventario");
    }

    private void generarAlertasPorNivelBajo() {
        String nivelTexto = etNivelMinimo.getText().toString().trim();

        if (nivelTexto.isEmpty()) {
            mostrarMensaje("Ingrese el nivel mínimo");
            return;
        }

        double nivelMinimo = Double.parseDouble(nivelTexto);

        if (nivelMinimo <= 0) {
            mostrarMensaje("El nivel mínimo debe ser mayor a cero");
            return;
        }

        StringBuilder alerta = new StringBuilder();
        alerta.append("Alertas por nivel bajo:\n\n");

        boolean hayAlertas = false;

        if (existenciaCorriente < nivelMinimo) {
            alerta.append("Gasolina Corriente en nivel bajo: ").append(existenciaCorriente).append(" litros\n");
            hayAlertas = true;
        }

        if (existenciaExtra < nivelMinimo) {
            alerta.append("Gasolina Extra en nivel bajo: ").append(existenciaExtra).append(" litros\n");
            hayAlertas = true;
        }

        if (existenciaDiesel < nivelMinimo) {
            alerta.append("Diésel en nivel bajo: ").append(existenciaDiesel).append(" litros\n");
            hayAlertas = true;
        }

        if (!hayAlertas) {
            alerta.append("No hay combustibles en nivel bajo.");
        }

        tvResultadoHU02.setText(alerta.toString());
        mostrarMensaje("Ya se generaron las alertas por nivel bajo");
    }

    private void registrarIngresoPorCompra() {
        String combustible = spinnerTipoCombustible.getSelectedItem().toString();
        String cantidadTexto = etCantidadCompra.getText().toString().trim();

        if (cantidadTexto.isEmpty()) {
            mostrarMensaje("Ingrese la cantidad de compra");
            return;
        }

        double cantidad = Double.parseDouble(cantidadTexto);

        if (cantidad <= 0) {
            mostrarMensaje("La cantidad de compra debe ser mayor a cero");
            return;
        }

        if (combustible.equals("Gasolina Corriente")) {
            existenciaCorriente += cantidad;
        } else if (combustible.equals("Gasolina Extra")) {
            existenciaExtra += cantidad;
        } else if (combustible.equals("Diésel")) {
            existenciaDiesel += cantidad;
        }

        historicoMovimientos.add("Ingreso por compra | Combustible: " + combustible + " | Cantidad: " + cantidad + " litros");

        tvResultadoHU02.setText(
                "Ingreso por compra registrado correctamente:\n\n" +
                        "Combustible: " + combustible + "\n" +
                        "Cantidad comprada: " + cantidad + " litros"
        );

        etCantidadCompra.setText("");
        mostrarMensaje("Ya se registró el ingreso por compra");
    }

    private void consultarHistoricoMovimientos() {
        if (historicoMovimientos.isEmpty()) {
            tvResultadoHU02.setText("No hay movimientos registrados en el histórico.");
            mostrarMensaje("No hay histórico de movimientos");
            return;
        }

        StringBuilder texto = new StringBuilder();
        texto.append("Histórico de movimientos:\n\n");

        for (String movimiento : historicoMovimientos) {
            texto.append("- ").append(movimiento).append("\n");
        }

        tvResultadoHU02.setText(texto.toString());
        mostrarMensaje("Ya se consultó el histórico de movimientos");
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}
