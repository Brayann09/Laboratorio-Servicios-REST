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
import androidx.room.Room;

import com.example.gestioncombustible.R;
import com.example.gestioncombustible.data.database.AppDatabase;
import com.example.gestioncombustible.data.entity.Inventario;
import com.example.gestioncombustible.data.entity.MovimientoInventario;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoriaControlInventarioHU02 extends AppCompatActivity {

    // BOTONES MENU
    private Button btnHistoriaHU02;
    private Button btnConsultarExistencias;
    private Button btnActualizarInventario;
    private Button btnGenerarAlertas;
    private Button btnRegistrarIngresoCompra;
    private Button btnConsultarHistorico;

    // BOTONES ACCIONES
    private Button btnVerExistencias;
    private Button btnActualizar;
    private Button btnVerAlerta;
    private Button btnRegistrarCompra;
    private Button btnVerHistorico;

    // SPINNERS
    private Spinner spinnerTipoCombustible;
    private Spinner spinnerMovimiento;

    // CAMPOS TEXTO
    private EditText etCantidadMovimiento;
    private EditText etCantidadCompra;
    private EditText etNivelMinimo;

    // RESULTADO
    private TextView tvResultadoHU02;

    // LAYOUTS
    private View layoutMenuHU02;
    private View layoutExistencias;
    private View layoutActualizarInventario;
    private View layoutAlertas;
    private View layoutIngresosCompra;
    private View layoutHistorico;

    // BASE DATOS
    private AppDatabase db;

    // TIPOS COMBUSTIBLE
    private final String[] tiposCombustible = {
            "Gasolina Corriente",
            "Gasolina Extra",
            "Diesel"
    };

    // TIPOS MOVIMIENTO
    private final String[] tiposMovimiento = {
            "Ingreso",
            "Salida"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // CARGAR XML
        setContentView(R.layout.activity_historia_control_inventario_hu02);

        // BASE DATOS
        db = Room.databaseBuilder(
                        getApplicationContext(),
                        AppDatabase.class,
                        "combustible_db"
                )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        // BOTONES MENU
        btnHistoriaHU02 =
                findViewById(R.id.btnHistoriaHU02);

        btnConsultarExistencias =
                findViewById(R.id.btnConsultarExistencias);

        btnActualizarInventario =
                findViewById(R.id.btnActualizarInventario);

        btnGenerarAlertas =
                findViewById(R.id.btnGenerarAlertas);

        btnRegistrarIngresoCompra =
                findViewById(R.id.btnRegistrarIngresoCompra);

        btnConsultarHistorico =
                findViewById(R.id.btnConsultarHistorico);

        // BOTONES ACCIONES
        btnVerExistencias =
                findViewById(R.id.btnVerExistencias);

        btnActualizar =
                findViewById(R.id.btnActualizar);

        btnVerAlerta =
                findViewById(R.id.btnVerAlerta);

        btnRegistrarCompra =
                findViewById(R.id.btnRegistrarCompra);

        btnVerHistorico =
                findViewById(R.id.btnVerHistorico);

        // SPINNERS
        spinnerTipoCombustible =
                findViewById(R.id.spinnerTipoCombustible);

        spinnerMovimiento =
                findViewById(R.id.spinnerMovimiento);

        // CAMPOS TEXTO
        etCantidadMovimiento =
                findViewById(R.id.etCantidadMovimiento);

        etCantidadCompra =
                findViewById(R.id.etCantidadCompra);

        etNivelMinimo =
                findViewById(R.id.etNivelMinimo);

        // RESULTADO
        tvResultadoHU02 =
                findViewById(R.id.tvResultadoHU02);

        // LAYOUTS
        layoutMenuHU02 =
                findViewById(R.id.layoutMenuHU02);

        layoutExistencias =
                findViewById(R.id.layoutExistencias);

        layoutActualizarInventario =
                findViewById(R.id.layoutActualizarInventario);

        layoutAlertas =
                findViewById(R.id.layoutAlertas);

        layoutIngresosCompra =
                findViewById(R.id.layoutIngresosCompra);

        layoutHistorico =
                findViewById(R.id.layoutHistorico);

        // SPINNERS
        spinnerTipoCombustible.setAdapter(

                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        tiposCombustible
                )
        );

        spinnerMovimiento.setAdapter(

                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        tiposMovimiento
                )
        );

        // OCULTAR TODO
        layoutMenuHU02.setVisibility(View.GONE);

        ocultarTodos();

        // MENU PRINCIPAL
        btnHistoriaHU02.setOnClickListener(v -> {

            btnHistoriaHU02.setVisibility(View.GONE);

            layoutMenuHU02.setVisibility(View.VISIBLE);
        });

        // NAVEGACION
        btnConsultarExistencias.setOnClickListener(
                v -> mostrarSolo(layoutExistencias)
        );

        btnActualizarInventario.setOnClickListener(
                v -> mostrarSolo(layoutActualizarInventario)
        );

        btnGenerarAlertas.setOnClickListener(
                v -> mostrarSolo(layoutAlertas)
        );

        btnRegistrarIngresoCompra.setOnClickListener(
                v -> mostrarSolo(layoutIngresosCompra)
        );

        btnConsultarHistorico.setOnClickListener(
                v -> mostrarSolo(layoutHistorico)
        );

        // ACCIONES
        btnVerExistencias.setOnClickListener(
                v -> consultarExistencias()
        );

        btnActualizar.setOnClickListener(
                v -> actualizarInventario()
        );

        btnRegistrarCompra.setOnClickListener(
                v -> registrarCompra()
        );

        btnVerHistorico.setOnClickListener(
                v -> verHistorico()
        );

        btnVerAlerta.setOnClickListener(
                v -> generarAlertas()
        );
    }

    // OCULTAR LAYOUTS
    private void ocultarTodos() {

        layoutExistencias.setVisibility(View.GONE);

        layoutActualizarInventario.setVisibility(View.GONE);

        layoutAlertas.setVisibility(View.GONE);

        layoutIngresosCompra.setVisibility(View.GONE);

        layoutHistorico.setVisibility(View.GONE);
    }

    // MOSTRAR SOLO UN LAYOUT
    private void mostrarSolo(View layoutVisible) {

        ocultarTodos();

        layoutVisible.setVisibility(View.VISIBLE);
    }

    // FECHA ACTUAL
    private String fechaActual() {

        return new SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
        ).format(new Date());
    }

    // CONSULTAR EXISTENCIAS
    private void consultarExistencias() {

        List<Inventario> lista =
                db.inventarioDao().obtenerTodos();

        String texto =
                "INVENTARIO ACTUAL\n\n";

        for (Inventario i : lista) {

            texto += "COMBUSTIBLE: "
                    + i.tipoCombustible + "\n";

            texto += "CANTIDAD: "
                    + i.cantidadDisponible + "\n";

            texto += "FECHA: "
                    + i.fecha + "\n\n";
        }

        tvResultadoHU02.setText(texto);
    }

    // ACTUALIZAR INVENTARIO
    private void actualizarInventario() {

        String tipo =
                spinnerTipoCombustible
                        .getSelectedItem()
                        .toString();

        String movimiento =
                spinnerMovimiento
                        .getSelectedItem()
                        .toString();

        String cantidadTexto =
                etCantidadMovimiento
                        .getText()
                        .toString();

        if (cantidadTexto.isEmpty()) {

            mostrarMensaje(
                    "Ingrese cantidad"
            );

            return;
        }

        double cantidad =
                Double.parseDouble(cantidadTexto);

        // BUSCAR INVENTARIO
        Inventario inv =
                db.inventarioDao()
                        .obtenerPorTipo(tipo);

        if (inv == null) {

            inv = new Inventario();

            inv.tipoCombustible = tipo;

            inv.cantidadDisponible = 0;
        }

        // INGRESO
        if (movimiento.equals("Ingreso")) {

            inv.cantidadDisponible += cantidad;

        } else {

            // VALIDAR INVENTARIO
            if (cantidad > inv.cantidadDisponible) {

                mostrarMensaje(
                        "No hay suficiente inventario"
                );

                return;
            }

            inv.cantidadDisponible -= cantidad;
        }

        inv.fecha = fechaActual();

        // INSERTAR O ACTUALIZAR
        if (inv.id == 0) {

            db.inventarioDao().insertar(inv);

        } else {

            db.inventarioDao().actualizar(inv);
        }

        // GUARDAR MOVIMIENTO
        MovimientoInventario m =
                new MovimientoInventario();

        m.tipoCombustible = tipo;

        m.tipoMovimiento = movimiento;

        m.cantidad = cantidad;

        m.fecha = fechaActual();

        // NUEVA ESTACION
        m.estacionNombre = "Estacion Principal";

        db.movimientoInventarioDao().insertar(m);

        mostrarMensaje(
                "Inventario actualizado"
        );

        etCantidadMovimiento.setText("");
    }

    // REGISTRAR COMPRA
    private void registrarCompra() {

        String tipo =
                spinnerTipoCombustible
                        .getSelectedItem()
                        .toString();

        String cantidadTexto =
                etCantidadCompra
                        .getText()
                        .toString();

        if (cantidadTexto.isEmpty()) {

            mostrarMensaje(
                    "Ingrese cantidad"
            );

            return;
        }

        double cantidad =
                Double.parseDouble(cantidadTexto);

        Inventario inv =
                db.inventarioDao()
                        .obtenerPorTipo(tipo);

        if (inv == null) {

            inv = new Inventario();

            inv.tipoCombustible = tipo;

            inv.cantidadDisponible = 0;
        }

        inv.cantidadDisponible += cantidad;

        inv.fecha = fechaActual();

        if (inv.id == 0) {

            db.inventarioDao().insertar(inv);

        } else {

            db.inventarioDao().actualizar(inv);
        }

        mostrarMensaje(
                "Compra registrada"
        );

        etCantidadCompra.setText("");
    }

    // VER HISTORICO
    private void verHistorico() {

        List<MovimientoInventario> lista =
                db.movimientoInventarioDao().obtenerTodos();

        String texto =
                "HISTORICO MOVIMIENTOS\n\n";

        for (MovimientoInventario m : lista) {

            texto += "------------------------\n";

            texto += "TIPO MOVIMIENTO: "
                    + m.tipoMovimiento + "\n\n";

            texto += "COMBUSTIBLE: "
                    + m.tipoCombustible + "\n\n";

            texto += "CANTIDAD: "
                    + m.cantidad + "\n\n";

            texto += "ESTACION: "
                    + m.estacionNombre + "\n\n";

            texto += "FECHA: "
                    + m.fecha + "\n";

            texto += "------------------------\n\n";
        }

        tvResultadoHU02.setText(texto);
    }

    // GENERAR ALERTAS
    private void generarAlertas() {

        String nivelTexto =
                etNivelMinimo
                        .getText()
                        .toString();

        if (nivelTexto.isEmpty()) {

            mostrarMensaje(
                    "Ingrese nivel mínimo"
            );

            return;
        }

        double nivel =
                Double.parseDouble(nivelTexto);

        List<Inventario> lista =
                db.inventarioDao().obtenerTodos();

        String texto =
                "ALERTAS INVENTARIO\n\n";

        for (Inventario i : lista) {

            if (i.cantidadDisponible < nivel) {

                texto += i.tipoCombustible
                        + " bajo: "
                        + i.cantidadDisponible
                        + "\n\n";
            }
        }

        tvResultadoHU02.setText(texto);
    }

    // MENSAJES
    private void mostrarMensaje(String msg) {

        Toast.makeText(
                this,
                msg,
                Toast.LENGTH_SHORT
        ).show();
    }
}