package roles.gerente;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.gestioncombustible.R;
import com.example.gestioncombustible.data.database.AppDatabase;
import com.example.gestioncombustible.data.entity.CargaCombustible;
import com.example.gestioncombustible.data.entity.Estacion;

import java.util.List;

public class ReporteConsumoHU07 extends AppCompatActivity {

    // BOTON
    private Button btnGenerarReporte;

    // RESULTADO
    private TextView tvReporte;

    // BASE DATOS
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // CARGAR XML
        setContentView(R.layout.activity_reporte_consumo_hu07);

        // COMPONENTES
        btnGenerarReporte =
                findViewById(R.id.btnGenerarReporte);

        tvReporte =
                findViewById(R.id.tvReporte);

        // BASE DATOS
        db = Room.databaseBuilder(
                        getApplicationContext(),
                        AppDatabase.class,
                        "combustible_db"
                )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        // BOTON GENERAR REPORTE
        btnGenerarReporte.setOnClickListener(
                v -> generarReporte()
        );
    }

    // GENERAR REPORTE
    private void generarReporte() {

        // Obtener todas las cargas
        List<CargaCombustible> lista =
                db.cargaDao().obtenerTodas();

        StringBuilder reporte =
                new StringBuilder();

        double totalGastado = 0;

        double totalCantidad = 0;

        // RECORRER CARGAS
        for (CargaCombustible c : lista) {

            reporte.append("-------------------------------\n");

            reporte.append("PLACA: ")
                    .append(c.placa)
                    .append("\n\n");

            reporte.append("ESTACION: ")
                    .append(c.estacionNombre)
                    .append("\n\n");

            reporte.append("CANTIDAD: ")
                    .append(c.cantidad)
                    .append("\n\n");

            reporte.append("PRECIO: ")
                    .append(c.precio)
                    .append("\n\n");

            reporte.append("COSTO TOTAL: ")
                    .append(c.costoTotal)
                    .append("\n\n");

            // Mostrar subsidio
            reporte.append("SUBSIDIO: ");

            if (c.tieneSubsidio) {

                reporte.append("SI\n\n");

            } else {

                reporte.append("NO\n\n");
            }

            // Mostrar descuento
            reporte.append("DESCUENTO: ")
                    .append(c.descuento)
                    .append("\n\n");

            // Mostrar total final
            reporte.append("TOTAL FINAL: ")
                    .append(c.totalFinal)
                    .append("\n\n");

            // Buscar estación
            Estacion estacion =
                    db.estacionDao()
                            .buscarPorNombre(
                                    c.estacionNombre
                            );

            // Mostrar inventario restante
            if (estacion != null) {

                reporte.append("INVENTARIO RESTANTE: ")
                        .append(estacion.getInventarioCorriente())
                        .append("\n\n");
            }

            reporte.append("FECHA: ")
                    .append(c.fecha)
                    .append("\n");

            reporte.append("-------------------------------\n\n");

            // Totales generales
            totalGastado += c.totalFinal;

            totalCantidad += c.cantidad;
        }

        // TOTALES GENERALES
        reporte.append("\nTOTALES\n\n");

        reporte.append("TOTAL COMBUSTIBLE VENDIDO: ")
                .append(totalCantidad)
                .append("\n\n");

        reporte.append("TOTAL RECAUDADO: ")
                .append(totalGastado)
                .append("\n\n");

        // INVENTARIO POR ESTACION
        reporte.append("INVENTARIO ESTACIONES \n\n");

        List<Estacion> estaciones =
                db.estacionDao().obtenerTodas();

        for (Estacion e : estaciones) {

            reporte.append("ESTACION: ")
                    .append(e.getNombre())
                    .append("\n");

            reporte.append("ZONA: ")
                    .append(e.getZona())
                    .append("\n");

            reporte.append("INVENTARIO CORRIENTE: ")
                    .append(e.getInventarioCorriente())
                    .append("\n");

            reporte.append("INVENTARIO ACPM: ")
                    .append(e.getInventarioAcpm())
                    .append("\n\n");
        }

        // MOSTRAR REPORTE
        tvReporte.setText(reporte.toString());
    }
}