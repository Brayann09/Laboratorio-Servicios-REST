package roles.adminflota;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestioncombustible.R;

public class AsignacionCombustibleActivityHU05 extends AppCompatActivity {

    private EditText etPlacaVehiculo;
    private EditText etCupoCombustible;
    private Button btnAsignarCupo;
    private TextView tvResultadoHU05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignacion_combustible_hu05);

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

        String mensaje;
        if (cupo > 100) {
            mensaje = "Asignación registrada:\n\n" +
                    "Vehículo: " + placa + "\n" +
                    "Cupo asignado: " + cupo + " galones/litros\n\n" +
                    "Alerta: el cupo asignado supera el límite recomendado.";
        } else {
            mensaje = "Asignación registrada:\n\n" +
                    "Vehículo: " + placa + "\n" +
                    "Cupo asignado: " + cupo + " galones/litros\n\n" +
                    "Estado: cupo dentro del rango permitido.";
        }

        tvResultadoHU05.setText(mensaje);
        Toast.makeText(this, "Cupo asignado correctamente", Toast.LENGTH_SHORT).show();

        etPlacaVehiculo.setText("");
        etCupoCombustible.setText("");
    }
}