package com.example.gestioncombustible.roles.adminsistema;

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
import com.example.gestioncombustible.data.entity.Usuario;
import com.example.gestioncombustible.data.entity.Estacion;

import java.util.List;

public class HistoriaConfiguracionHU06 extends AppCompatActivity {

    // BOTONES PRINCIPALES
    private Button btnHistoriaHU06, btnGestionUsuarios, btnAsignarRoles;

    // BOTONES GESTION USUARIOS
    private Button btnCrearUsuario, btnEditarUsuario,
            btnEliminarUsuario, btnListarUsuarios;

    // BOTONES ROLES
    private Button btnAsignarRol, btnConsultarPermisos,
            btnValidarAcceso;

    // CAMPOS TEXTO
    private EditText etIdUsuario, etNombreUsuario,
            etCorreoUsuario, etIdRol;

    // SPINNERS
    private Spinner spinnerRoles, spinnerPermisos;

    // RESULTADOS
    private TextView tvResultado;

    // LAYOUTS
    private View layoutMenuPrincipal,
            layoutGestionUsuarios,
            layoutRolesPermisos;

    // BASE DE DATOS
    private AppDatabase db;

    // ROLES DISPONIBLES
    private final String[] roles = {
            "Administrador",
            "Cajero",
            "Supervisor",
            "Operario"
    };

    // PERMISOS DISPONIBLES
    private final String[] permisos = {
            "Crear usuarios",
            "Editar usuarios",
            "Eliminar usuarios",
            "Listar usuarios",
            "Asignar rol",
            "Consultar permisos",
            "Validar acceso",
            "Registrar ventas",
            "Ver reportes",
            "Despachar combustible"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_historia_configuracion_hu06);

        // INICIALIZAR BASE DATOS
        db = Room.databaseBuilder(
                        getApplicationContext(),
                        AppDatabase.class,
                        "combustible_db"
                )
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        // CREAR ESTACIONES INICIALES
        crearEstacionesIniciales();

        // BOTONES PRINCIPALES
        btnHistoriaHU06 = findViewById(R.id.btnHistoriaHU06);
        btnGestionUsuarios = findViewById(R.id.btnGestionUsuarios);
        btnAsignarRoles = findViewById(R.id.btnAsignarRoles);

        // BOTONES GESTION USUARIOS
        btnCrearUsuario = findViewById(R.id.btnCrearUsuario);
        btnEditarUsuario = findViewById(R.id.btnEditarUsuario);
        btnEliminarUsuario = findViewById(R.id.btnEliminarUsuario);
        btnListarUsuarios = findViewById(R.id.btnListarUsuarios);

        // BOTONES ROLES
        btnAsignarRol = findViewById(R.id.btnAsignarRol);
        btnConsultarPermisos = findViewById(R.id.btnConsultarPermisos);
        btnValidarAcceso = findViewById(R.id.btnValidarAcceso);

        // CAMPOS TEXTO
        etIdUsuario = findViewById(R.id.etIdUsuario);
        etNombreUsuario = findViewById(R.id.etNombreUsuario);
        etCorreoUsuario = findViewById(R.id.etCorreoUsuario);
        etIdRol = findViewById(R.id.etIdRol);

        // SPINNERS
        spinnerRoles = findViewById(R.id.spinnerRoles);
        spinnerPermisos = findViewById(R.id.spinnerPermisos);

        // TEXTVIEW RESULTADO
        tvResultado = findViewById(R.id.tvResultado);

        // LAYOUTS
        layoutMenuPrincipal = findViewById(R.id.layoutMenuPrincipal);
        layoutGestionUsuarios = findViewById(R.id.layoutGestionUsuarios);
        layoutRolesPermisos = findViewById(R.id.layoutRolesPermisos);

        // CONFIGURAR SPINNERS
        spinnerRoles.setAdapter(
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        roles
                )
        );

        spinnerPermisos.setAdapter(
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        permisos
                )
        );

        // OCULTAR LAYOUTS
        layoutMenuPrincipal.setVisibility(View.GONE);
        layoutGestionUsuarios.setVisibility(View.GONE);
        layoutRolesPermisos.setVisibility(View.GONE);

        // MOSTRAR MENU PRINCIPAL
        btnHistoriaHU06.setOnClickListener(v -> {

            btnHistoriaHU06.setVisibility(View.GONE);

            layoutMenuPrincipal.setVisibility(View.VISIBLE);
        });

        // MOSTRAR GESTION USUARIOS
        btnGestionUsuarios.setOnClickListener(v -> {

            layoutGestionUsuarios.setVisibility(View.VISIBLE);

            layoutRolesPermisos.setVisibility(View.GONE);
        });

        // MOSTRAR ROLES Y PERMISOS
        btnAsignarRoles.setOnClickListener(v -> {

            layoutRolesPermisos.setVisibility(View.VISIBLE);

            layoutGestionUsuarios.setVisibility(View.GONE);
        });

        // BOTONES CRUD USUARIOS
        btnCrearUsuario.setOnClickListener(v -> crearUsuario());

        btnEditarUsuario.setOnClickListener(v -> editarUsuario());

        btnEliminarUsuario.setOnClickListener(v -> eliminarUsuario());

        btnListarUsuarios.setOnClickListener(v -> listarUsuarios());

        // BOTONES ROLES
        btnAsignarRol.setOnClickListener(v -> asignarRol());

        btnConsultarPermisos.setOnClickListener(v -> consultarPermisos());

        btnValidarAcceso.setOnClickListener(v -> validarAcceso());
    }

    // CREAR ESTACIONES INICIALES
    private void crearEstacionesIniciales() {

        // Verificar si ya existen estaciones
        if (db.estacionDao().obtenerTodas().isEmpty()) {

            // ESTACION NORTE
            Estacion norte = new Estacion();

            norte.setNombre("Estacion Norte");
            norte.setZona("Bogota");

            norte.setInventarioCorriente(5000);
            norte.setInventarioAcpm(3000);

            norte.setPrecioCorriente(15000);
            norte.setPrecioAcpm(17000);

            db.estacionDao().insertar(norte);

            // ESTACION SUR
            Estacion sur = new Estacion();

            sur.setNombre("Estacion Sur");
            sur.setZona("Cali");

            sur.setInventarioCorriente(4000);
            sur.setInventarioAcpm(2500);

            sur.setPrecioCorriente(14800);
            sur.setPrecioAcpm(16800);

            db.estacionDao().insertar(sur);

            // ESTACION CENTRO
            Estacion centro = new Estacion();

            centro.setNombre("Estacion Centro");
            centro.setZona("Medellin");

            centro.setInventarioCorriente(7000);
            centro.setInventarioAcpm(5000);

            centro.setPrecioCorriente(15200);
            centro.setPrecioAcpm(17200);

            db.estacionDao().insertar(centro);

            mostrarMensaje("Estaciones creadas");
        }
    }

    // CREAR USUARIO
    private void crearUsuario() {

        int id = Integer.parseInt(etIdUsuario.getText().toString());

        String nombre = etNombreUsuario.getText().toString();

        String correo = etCorreoUsuario.getText().toString();

        // Verificar si existe
        if (db.usuarioDao().buscarPorId(id) != null) {

            mostrarMensaje("Ya existe usuario");

            return;
        }

        Usuario u = new Usuario();

        u.id = id;
        u.nombre = nombre;
        u.correo = correo;
        u.rol = "Sin rol";

        db.usuarioDao().insertar(u);

        mostrarMensaje("Usuario creado");
    }

    // EDITAR USUARIO
    private void editarUsuario() {

        int id = Integer.parseInt(etIdUsuario.getText().toString());

        Usuario u = db.usuarioDao().buscarPorId(id);

        if (u == null) {

            mostrarMensaje("No existe");

            return;
        }

        u.nombre = etNombreUsuario.getText().toString();

        u.correo = etCorreoUsuario.getText().toString();

        db.usuarioDao().actualizar(u);

        mostrarMensaje("Usuario actualizado");
    }

    // ELIMINAR USUARIO
    private void eliminarUsuario() {

        int id = Integer.parseInt(etIdUsuario.getText().toString());

        Usuario u = db.usuarioDao().buscarPorId(id);

        if (u == null) {

            mostrarMensaje("No existe");

            return;
        }

        db.usuarioDao().eliminar(u);

        mostrarMensaje("Usuario eliminado");
    }

    // LISTAR USUARIOS
    private void listarUsuarios() {

        List<Usuario> lista = db.usuarioDao().obtenerTodos();

        String texto = "Usuarios:\n\n";

        for (Usuario u : lista) {

            texto += "ID: " + u.id +
                    "\nNombre: " + u.nombre +
                    "\nCorreo: " + u.correo +
                    "\nRol: " + u.rol +
                    "\n-----------------\n";
        }

        tvResultado.setText(texto);
    }

    // ASIGNAR ROL
    private void asignarRol() {

        int id = Integer.parseInt(etIdRol.getText().toString());

        Usuario u = db.usuarioDao().buscarPorId(id);

        if (u == null) {

            mostrarMensaje("No existe");

            return;
        }

        u.rol = spinnerRoles.getSelectedItem().toString();

        db.usuarioDao().actualizar(u);

        mostrarMensaje("Rol asignado");
    }

    // CONSULTAR PERMISOS
    private void consultarPermisos() {

        int id = Integer.parseInt(etIdRol.getText().toString());

        Usuario u = db.usuarioDao().buscarPorId(id);

        if (u == null) {

            mostrarMensaje("No existe");

            return;
        }

        tvResultado.setText(
                "Usuario: " + u.nombre +
                        "\nRol: " + u.rol
        );
    }

    // VALIDAR ACCESO
    private void validarAcceso() {

        mostrarMensaje("Funcionalidad simplificada");
    }

    // MOSTRAR MENSAJES
    private void mostrarMensaje(String mensaje) {

        Toast.makeText(
                this,
                mensaje,
                Toast.LENGTH_SHORT
        ).show();
    }
}