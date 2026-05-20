package roles.estacionservicio;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.gestioncombustible.R;
import com.example.gestioncombustible.data.database.AppDatabase;
import com.example.gestioncombustible.data.entity.MovimientoInventario;

import java.time.LocalDate;
import java.util.List;

public class HistoriaGestionInventarioHU10 extends AppCompatActivity {

    // CAMPOS TEXTO
    private EditText editCombustible;
    private EditText editCantidad;
    private EditText editMovimiento;

    // BOTON
    private Button btnRegistrar;

    // HISTORIAL
    private TextView textHistorial;

    // BASE DATOS
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // CARGAR XML
        setContentView(
                R.layout.activity_historia_gestion_inventario_hu10
        );

        // COMPONENTES XML
        editCombustible =
                findViewById(R.id.editCombustible);

        editCantidad =
                findViewById(R.id.editCantidad);

        editMovimiento =
                findViewById(R.id.editMovimiento);

        btnRegistrar =
                findViewById(R.id.btnRegistrar);

        textHistorial =
                findViewById(R.id.textHistorial);

        // BASE DATOS
        db = Room.databaseBuilder(
                        getApplicationContext(),
                        AppDatabase.class,
                        "combustible_db"
                )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        // MOSTRAR HISTORIAL
        mostrarHistorial();

        // BOTON REGISTRAR
        btnRegistrar.setOnClickListener(
                v -> registrarMovimiento()
        );
    }

    // REGISTRAR MOVIMIENTO
    private void registrarMovimiento() {

        String combustible =
                editCombustible.getText().toString();

        String cantidadTexto =
                editCantidad.getText().toString();

        String movimiento =
                editMovimiento.getText().toString();

        // VALIDAR CAMPOS
        if (combustible.isEmpty()
                || cantidadTexto.isEmpty()
                || movimiento.isEmpty()) {

            Toast.makeText(
                    this,
                    "Complete todos los campos",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        double cantidad =
                Double.parseDouble(cantidadTexto);

        // CREAR MOVIMIENTO
        MovimientoInventario nuevo =
                new MovimientoInventario();

        nuevo.tipoCombustible =
                combustible;

        nuevo.cantidad =
                cantidad;

        nuevo.tipoMovimiento =
                movimiento;

        nuevo.fecha =
                LocalDate.now().toString();

        // NUEVA ESTACION
        nuevo.estacionNombre =
                "Estacion Principal";

        // GUARDAR SQLITE
        db.movimientoInventarioDao()
                .insertar(nuevo);

        Toast.makeText(
                this,
                "Movimiento guardado",
                Toast.LENGTH_SHORT
        ).show();

        // LIMPIAR CAMPOS
        editCombustible.setText("");

        editCantidad.setText("");

        editMovimiento.setText("");

        // ACTUALIZAR HISTORIAL
        mostrarHistorial();
    }

    // MOSTRAR HISTORIAL
    private void mostrarHistorial() {

        List<MovimientoInventario> lista =
                db.movimientoInventarioDao().obtenerTodos();

        StringBuilder historial =
                new StringBuilder();

        historial.append(
                "HISTORIAL INVENTARIO\n\n"
        );

        for (MovimientoInventario m : lista) {

            historial.append(
                    "--------------------\n"
            );

            historial.append("TIPO: ")
                    .append(m.tipoMovimiento)
                    .append("\n\n");

            historial.append("COMBUSTIBLE: ")
                    .append(m.tipoCombustible)
                    .append("\n\n");

            historial.append("CANTIDAD: ")
                    .append(m.cantidad)
                    .append("\n\n");

            historial.append("ESTACION: ")
                    .append(m.estacionNombre)
                    .append("\n\n");

            historial.append("FECHA: ")
                    .append(m.fecha)
                    .append("\n");

            historial.append(
                    "--------------------\n\n"
            );
        }

        textHistorial.setText(
                historial.toString()
        );
    }
}