package roles.comprador;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestioncombustible.R;
import com.example.gestioncombustible.roles.comprador.AplicacionPreciosSubsidiosActivityHU04;
import com.example.gestioncombustible.roles.comprador.HistorialComprasActivityHU09;

public class CompradorMenuActivity extends AppCompatActivity {

    Button btnHU04, btnHU09;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comprador_menu);

        btnHU04 = findViewById(R.id.btnHU04);
        btnHU09 = findViewById(R.id.btnHU09);

        // Ir a HU04
        btnHU04.setOnClickListener(v -> {
            Intent intent = new Intent(this, AplicacionPreciosSubsidiosActivityHU04.class);
            startActivity(intent);
        });

        // Ir a HU09
        btnHU09.setOnClickListener(v -> {
            Intent intent = new Intent(this, HistorialComprasActivityHU09.class);
            startActivity(intent);
        });
    }
}