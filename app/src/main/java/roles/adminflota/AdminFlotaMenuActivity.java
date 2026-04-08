package roles.adminflota;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestioncombustible.R;

import roles.adminflota.HistoriaRegistroHU01;
import roles.adminflota.AsignacionCombustibleActivityHU05;

public class AdminFlotaMenuActivity extends AppCompatActivity {

    Button btnHU01, btnHU05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_flota_menu);

        btnHU01 = findViewById(R.id.btnHU01);
        btnHU05 = findViewById(R.id.btnHU05);

        btnHU01.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistoriaRegistroHU01.class);
            startActivity(intent);
        });

        btnHU05.setOnClickListener(v -> {
            Intent intent = new Intent(this, AsignacionCombustibleActivityHU05.class);
            startActivity(intent);
        });
    }
}