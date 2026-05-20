package roles.adminflota;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.gestioncombustible.R;
import com.example.gestioncombustible.data.database.AppDatabase;
import com.example.gestioncombustible.data.entity.AsignacionCombustible;

public class AsignacionCombustibleActivityHU05 extends AppCompatActivity {

    private EditText etPlacaVehiculo;
    private EditText etCupoCombustible;
    private Button btnAsignarCupo;
    private TextView tvResultadoHU05;

    //BASE DE DATOS
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignacion_combustible_hu05);

        //INICIALIZAR BD
        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "combustible_db"
                ).fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        etPlacaVehiculo = findViewById(R.id.etPlacaVehiculo);
        etCupoCombustible = findViewById(R.id.etCupoCombustible);
        btnAsignarCupo = findViewById(R.id.btnAsignarCupo);
        tvResultadoHU05 = findViewById(R.id.tvResultadoHU05);

        btnAsignarCupo.setOnClickListener(v -> asignarCupo());
    }

    private void asignarCupo() {
        String placa = etPlacaVehiculo.getText().toString().trim();
        String cupoTexto = etCupoCombustible.getText().toString().trim();

        if (placa.isEmpty() || cupoTexto.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double cupo = Double.parseDouble(cupoTexto);

        if (cupo <= 0) {
            Toast.makeText(this, "El cupo debe ser mayor a cero", Toast.LENGTH_SHORT).show();
            return;
        }

        //GUARDAR EN BD
        AsignacionCombustible asignacion = new AsignacionCombustible();
        asignacion.placa = placa;
        asignacion.cupo = cupo;
        String fechaActual = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        asignacion.fecha = fechaActual;

        db.asignacionDao().insertar(asignacion);

        String mensaje;
        if (cupo > 100) {
            mensaje = "Asignación registrada:\n\n" +
                    "Vehículo: " + placa + "\n" +
                    "Cupo asignado: " + cupo + "\n\n" +
                    "Alerta: supera el límite recomendado.";
        } else {
            mensaje = "Asignación registrada:\n\n" +
                    "Vehículo: " + placa + "\n" +
                    "Cupo asignado: " + cupo + "\n\n" +
                    "Estado: dentro del rango permitido.";
        }

        tvResultadoHU05.setText(mensaje);
        Toast.makeText(this, "Cupo guardado en la Base De Datos", Toast.LENGTH_SHORT).show();

        etPlacaVehiculo.setText("");
        etCupoCombustible.setText("");
    }
}