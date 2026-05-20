package roles.adminflota;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.gestioncombustible.R;
import com.example.gestioncombustible.data.database.AppDatabase;
import com.example.gestioncombustible.data.entity.CargaCombustible;
import com.example.gestioncombustible.data.entity.Estacion;
import com.example.gestioncombustible.data.entity.MovimientoInventario;

public class HistoriaRegistroHU01 extends AppCompatActivity {

    // BOTONES
    private Button btnHistoriaHU01;
    private Button btnRegistrarCarga;
    private Button btnCalcularCosto;
    private Button btnGuardarCarga;
    private Button btnCalcularTotal;

    // CAMPOS TEXTO
    private EditText etPlacaHU01;
    private EditText etCantidadCombustible;
    private EditText etPrecioPorLitro;

    // COMPONENTES NUEVOS
    private Spinner spinnerEstacionesHU01;
    private CheckBox checkSubsidioHU01;

    // RESULTADO
    private TextView tvResultadoHU01;

    // LAYOUTS
    private View layoutMenuHU01;
    private View layoutRegistrarCarga;
    private View layoutCalcularCosto;

    // BASE DATOS
    private AppDatabase db;

    // LISTA ESTACIONES
    private List<Estacion> listaEstaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // CARGAR XML
        setContentView(R.layout.activity_historia_registro_hu01);


        // BASE DATOS
        db = Room.databaseBuilder(
                        getApplicationContext(),
                        AppDatabase.class,
                        "combustible_db"
                )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();


        // BOTONES
        btnHistoriaHU01 = findViewById(R.id.btnHistoriaHU01);

        btnRegistrarCarga = findViewById(R.id.btnRegistrarCarga);

        btnCalcularCosto = findViewById(R.id.btnCalcularCosto);

        btnGuardarCarga = findViewById(R.id.btnGuardarCarga);

        btnCalcularTotal = findViewById(R.id.btnCalcularTotal);


        // CAMPOS TEXTO
        etPlacaHU01 = findViewById(R.id.etPlacaHU01);

        etCantidadCombustible =
                findViewById(R.id.etCantidadCombustible);

        etPrecioPorLitro =
                findViewById(R.id.etPrecioPorLitro);


        // COMPONENTES NUEVOS
        spinnerEstacionesHU01 =
                findViewById(R.id.spinnerEstacionesHU01);

        checkSubsidioHU01 =
                findViewById(R.id.checkSubsidioHU01);


        // RESULTADO
        tvResultadoHU01 =
                findViewById(R.id.tvResultadoHU01);


        // LAYOUTS
        layoutMenuHU01 =
                findViewById(R.id.layoutMenuHU01);

        layoutRegistrarCarga =
                findViewById(R.id.layoutRegistrarCarga);

        layoutCalcularCosto =
                findViewById(R.id.layoutCalcularCosto);


        // OCULTAR LAYOUTS
        layoutMenuHU01.setVisibility(View.GONE);

        layoutRegistrarCarga.setVisibility(View.GONE);

        layoutCalcularCosto.setVisibility(View.GONE);

        // CARGAR ESTACIONES
        cargarEstaciones();

        // MOSTRAR MENU
        btnHistoriaHU01.setOnClickListener(v -> {

            btnHistoriaHU01.setVisibility(View.GONE);

            layoutMenuHU01.setVisibility(View.VISIBLE);

            tvResultadoHU01.setText(
                    "Seleccione una opción de la HU01"
            );
        });

        // MOSTRAR REGISTRO
        btnRegistrarCarga.setOnClickListener(v -> {

            layoutRegistrarCarga.setVisibility(View.VISIBLE);

            layoutCalcularCosto.setVisibility(View.GONE);

            tvResultadoHU01.setText(
                    "Registro de carga activo"
            );
        });

        // MOSTRAR CALCULO
        btnCalcularCosto.setOnClickListener(v -> {

            layoutCalcularCosto.setVisibility(View.VISIBLE);

            layoutRegistrarCarga.setVisibility(View.GONE);

            tvResultadoHU01.setText(
                    "Cálculo de costo activo"
            );
        });

        // BOTONES
        btnGuardarCarga.setOnClickListener(
                v -> registrarCargaCombustible()
        );

        btnCalcularTotal.setOnClickListener(
                v -> calcularCostoTotal()
        );
    }

    // CARGAR ESTACIONES
    private void cargarEstaciones() {

        listaEstaciones =
                db.estacionDao().obtenerTodas();

        List<String> nombres =
                new ArrayList<>();

        for (Estacion e : listaEstaciones) {

            nombres.add(e.getNombre());
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        nombres
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerEstacionesHU01.setAdapter(adapter);
    }

    // REGISTRAR CARGA
    private void registrarCargaCombustible() {

        // DATOS
        String placa =
                etPlacaHU01.getText().toString().trim();

        String cantidadTexto =
                etCantidadCombustible
                        .getText()
                        .toString()
                        .trim();

        String precioTexto =
                etPrecioPorLitro
                        .getText()
                        .toString()
                        .trim();

        // VALIDAR CAMPOS
        if (placa.isEmpty()
                || cantidadTexto.isEmpty()
                || precioTexto.isEmpty()) {

            mostrarMensaje(
                    "Complete todos los campos"
            );

            return;
        }

        double cantidad =
                Double.parseDouble(cantidadTexto);

        double precio =
                Double.parseDouble(precioTexto);

        // VALIDAR VALORES
        if (cantidad <= 0 || precio <= 0) {

            mostrarMensaje(
                    "Valores inválidos"
            );

            return;
        }

        // ESTACION
        String nombreEstacion =
                spinnerEstacionesHU01
                        .getSelectedItem()
                        .toString();

        Estacion estacion =
                db.estacionDao()
                        .buscarPorNombre(nombreEstacion);

        // VALIDAR INVENTARIO
        if (estacion.getInventarioCorriente()
                < cantidad) {

            mostrarMensaje(
                    "No hay suficiente combustible"
            );

            return;
        }

        // COSTO TOTAL
        double costoTotal =
                cantidad * precio;

        // SUBSIDIO
        boolean tieneSubsidio =
                checkSubsidioHU01.isChecked();

        double descuento = 0;

        if (tieneSubsidio) {

            descuento = costoTotal * 0.10;
        }

        double totalFinal =
                costoTotal - descuento;

        // ACTUALIZAR INVENTARIO
        double nuevoInventario =
                estacion.getInventarioCorriente()
                        - cantidad;

        estacion.setInventarioCorriente(
                nuevoInventario
        );

        db.estacionDao().actualizar(estacion);

        // FECHA ACTUAL
        String fechaActual =
                new SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault()
                ).format(new Date());

        // GUARDAR CARGA
        CargaCombustible carga =
                new CargaCombustible();

        carga.placa = placa;

        carga.cantidad = cantidad;

        carga.precio = precio;

        carga.costoTotal = costoTotal;

        carga.fecha = fechaActual;

        carga.estacionNombre = nombreEstacion;

        carga.tieneSubsidio = tieneSubsidio;

        carga.descuento = descuento;

        carga.totalFinal = totalFinal;

        db.cargaDao().insertar(carga);

        // GUARDAR MOVIMIENTO
        MovimientoInventario movimiento =
                new MovimientoInventario();

        movimiento.tipoCombustible = "Corriente";

        movimiento.tipoMovimiento = "SALIDA";

        movimiento.cantidad = cantidad;

        movimiento.fecha = fechaActual;

        movimiento.estacionNombre =
                nombreEstacion;

        db.movimientoInventarioDao()
                .insertar(movimiento);

        // MOSTRAR RESULTADO
        tvResultadoHU01.setText(

                "CARGA REGISTRADA\n\n" +

                        "PLACA: " + placa + "\n\n" +

                        "ESTACION: " +
                        nombreEstacion + "\n\n" +

                        "CANTIDAD: " +
                        cantidad + "\n\n" +

                        "PRECIO: " +
                        precio + "\n\n" +

                        "COSTO TOTAL: " +
                        costoTotal + "\n\n" +

                        "SUBSIDIO: " +
                        (tieneSubsidio ? "SI" : "NO")
                        + "\n\n" +

                        "DESCUENTO: " +
                        descuento + "\n\n" +

                        "TOTAL FINAL: " +
                        totalFinal + "\n\n" +

                        "INVENTARIO RESTANTE: " +
                        nuevoInventario
        );

        mostrarMensaje(
                "Carga registrada correctamente"
        );

        limpiarCampos();
    }

    // CALCULAR COSTO
    private void calcularCostoTotal() {

        String cantidadTexto =
                etCantidadCombustible
                        .getText()
                        .toString()
                        .trim();

        String precioTexto =
                etPrecioPorLitro
                        .getText()
                        .toString()
                        .trim();

        if (cantidadTexto.isEmpty()
                || precioTexto.isEmpty()) {

            mostrarMensaje(
                    "Complete los campos"
            );

            return;
        }

        double cantidad =
                Double.parseDouble(cantidadTexto);

        double precio =
                Double.parseDouble(precioTexto);

        double total =
                cantidad * precio;

        tvResultadoHU01.setText(
                "Costo total calculado: " + total
        );
    }

    // LIMPIAR CAMPOS
    private void limpiarCampos() {

        etPlacaHU01.setText("");

        etCantidadCombustible.setText("");

        etPrecioPorLitro.setText("");

        checkSubsidioHU01.setChecked(false);
    }

    // MENSAJES
    private void mostrarMensaje(String mensaje) {

        Toast.makeText(
                this,
                mensaje,
                Toast.LENGTH_SHORT
        ).show();
    }
}