package com.example.gestioncombustible.roles.distribuidor;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestioncombustible.R;

public class RegistroEntregaActivityHU03 extends AppCompatActivity {

    private EditText etVehiculo;
    private EditText etVolumen;
    private EditText etEstacionDestino;
    private Button btnGuardarEntrega;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_entrega_hu03);

        etVehiculo = findViewById(R.id.etVehiculo);
        etVolumen = findViewById(R.id.etVolumen);
        etEstacionDestino = findViewById(R.id.etEstacionDestino);
        btnGuardarEntrega = findViewById(R.id.btnGuardarEntrega);

        btnGuardarEntrega.setOnClickListener(v -> {
            String vehiculo = etVehiculo.getText().toString().trim();
            String volumen = etVolumen.getText().toString().trim();
            String estacion = etEstacionDestino.getText().toString().trim();

            if (vehiculo.isEmpty() || volumen.isEmpty() || estacion.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Entrega registrada correctamente", Toast.LENGTH_SHORT).show();

                etVehiculo.setText("");
                etVolumen.setText("");
                etEstacionDestino.setText("");
            }
        });
    }
}