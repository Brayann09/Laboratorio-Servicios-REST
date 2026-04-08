package com.example.gestioncombustible.roles.comprador;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;


import com.example.gestioncombustible.R;

import java.util.ArrayList;

public class HistorialComprasActivityHU09 extends AppCompatActivity {

    private ListView lvHistorialCompras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_compras_hu09);

        lvHistorialCompras = findViewById(R.id.lvHistorialCompras);

        ArrayList<String> compras = new ArrayList<>();
        compras.add("Fecha: 10/03/2026 - Estación Centro - 10 galones - $120000");
        compras.add("Fecha: 11/03/2026 - Estación Norte - 8 galones - $95000");
        compras.add("Fecha: 12/03/2026 - Estación Sur - 12 galones - $140000");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                compras
        );

        lvHistorialCompras.setAdapter(adapter);
    }
}