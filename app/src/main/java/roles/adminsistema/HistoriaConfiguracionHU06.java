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

import com.example.gestioncombustible.R;

import java.util.ArrayList;

public class HistoriaConfiguracionHU06 extends AppCompatActivity {

    private Button btnHistoriaHU06;
    private Button btnGestionUsuarios;
    private Button btnAsignarRoles;

    private Button btnCrearUsuario;
    private Button btnEditarUsuario;
    private Button btnEliminarUsuario;
    private Button btnListarUsuarios;

    private Button btnAsignarRol;
    private Button btnConsultarPermisos;
    private Button btnValidarAcceso;

    private EditText etIdUsuario;
    private EditText etNombreUsuario;
    private EditText etCorreoUsuario;
    private EditText etIdRol;

    private Spinner spinnerRoles;
    private Spinner spinnerPermisos;

    private TextView tvResultado;

    private View layoutMenuPrincipal;
    private View layoutGestionUsuarios;
    private View layoutRolesPermisos;

    private final ArrayList<Usuario> listaUsuarios = new ArrayList<>();

    private final String[] roles = {
            "Administrador",
            "Cajero",
            "Supervisor",
            "Operario"
    };

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

        btnHistoriaHU06 = findViewById(R.id.btnHistoriaHU06);
        btnGestionUsuarios = findViewById(R.id.btnGestionUsuarios);
        btnAsignarRoles = findViewById(R.id.btnAsignarRoles);

        btnCrearUsuario = findViewById(R.id.btnCrearUsuario);
        btnEditarUsuario = findViewById(R.id.btnEditarUsuario);
        btnEliminarUsuario = findViewById(R.id.btnEliminarUsuario);
        btnListarUsuarios = findViewById(R.id.btnListarUsuarios);

        btnAsignarRol = findViewById(R.id.btnAsignarRol);
        btnConsultarPermisos = findViewById(R.id.btnConsultarPermisos);
        btnValidarAcceso = findViewById(R.id.btnValidarAcceso);

        etIdUsuario = findViewById(R.id.etIdUsuario);
        etNombreUsuario = findViewById(R.id.etNombreUsuario);
        etCorreoUsuario = findViewById(R.id.etCorreoUsuario);
        etIdRol = findViewById(R.id.etIdRol);

        spinnerRoles = findViewById(R.id.spinnerRoles);
        spinnerPermisos = findViewById(R.id.spinnerPermisos);

        tvResultado = findViewById(R.id.tvResultado);

        layoutMenuPrincipal = findViewById(R.id.layoutMenuPrincipal);
        layoutGestionUsuarios = findViewById(R.id.layoutGestionUsuarios);
        layoutRolesPermisos = findViewById(R.id.layoutRolesPermisos);

        ArrayAdapter<String> adapterRoles = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                roles
        );
        adapterRoles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoles.setAdapter(adapterRoles);

        ArrayAdapter<String> adapterPermisos = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                permisos
        );
        adapterPermisos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPermisos.setAdapter(adapterPermisos);

        layoutMenuPrincipal.setVisibility(View.GONE);
        layoutGestionUsuarios.setVisibility(View.GONE);
        layoutRolesPermisos.setVisibility(View.GONE);

        btnHistoriaHU06.setOnClickListener(v -> {
            btnHistoriaHU06.setVisibility(View.GONE);
            layoutMenuPrincipal.setVisibility(View.VISIBLE);
            tvResultado.setText("Seleccione una opción de la historia.");
        });

        btnGestionUsuarios.setOnClickListener(v -> {
            layoutGestionUsuarios.setVisibility(View.VISIBLE);
            layoutRolesPermisos.setVisibility(View.GONE);
            tvResultado.setText("Módulo de gestión de usuarios activo.");
        });

        btnAsignarRoles.setOnClickListener(v -> {
            layoutRolesPermisos.setVisibility(View.VISIBLE);
            layoutGestionUsuarios.setVisibility(View.GONE);
            tvResultado.setText("Módulo de roles y permisos activo.");
        });

        btnCrearUsuario.setOnClickListener(v -> crearUsuario());
        btnEditarUsuario.setOnClickListener(v -> editarUsuario());
        btnEliminarUsuario.setOnClickListener(v -> eliminarUsuario());
        btnListarUsuarios.setOnClickListener(v -> listarUsuarios());

        btnAsignarRol.setOnClickListener(v -> asignarRol());
        btnConsultarPermisos.setOnClickListener(v -> consultarPermisos());
        btnValidarAcceso.setOnClickListener(v -> validarAcceso());
    }

    private void crearUsuario() {
        String idTexto = etIdUsuario.getText().toString().trim();
        String nombre = etNombreUsuario.getText().toString().trim();
        String correo = etCorreoUsuario.getText().toString().trim();

        if (idTexto.isEmpty() || nombre.isEmpty() || correo.isEmpty()) {
            mostrarMensaje("Complete ID, nombre y correo para crear el usuario");
            return;
        }

        int id = Integer.parseInt(idTexto);

        if (buscarUsuarioPorId(id) != null) {
            mostrarMensaje("Ya existe un usuario con ese ID");
            return;
        }

        Usuario usuario = new Usuario(id, nombre, correo, "Sin rol");
        listaUsuarios.add(usuario);

        limpiarCamposUsuario();
        tvResultado.setText("Ya se creó el usuario:\n\nID: " + id + "\nNombre: " + nombre + "\nCorreo: " + correo + "\nRol: Sin rol");
        mostrarMensaje("Ya se creó el usuario");
    }

    private void editarUsuario() {
        String idTexto = etIdUsuario.getText().toString().trim();
        String nombre = etNombreUsuario.getText().toString().trim();
        String correo = etCorreoUsuario.getText().toString().trim();

        if (idTexto.isEmpty() || nombre.isEmpty() || correo.isEmpty()) {
            mostrarMensaje("Complete ID, nombre y correo para editar el usuario");
            return;
        }

        int id = Integer.parseInt(idTexto);
        Usuario usuario = buscarUsuarioPorId(id);

        if (usuario == null) {
            mostrarMensaje("No existe un usuario con ese ID");
            return;
        }

        usuario.setNombre(nombre);
        usuario.setCorreo(correo);

        limpiarCamposUsuario();
        tvResultado.setText("Ya se editó el usuario:\n\nID: " + usuario.getId() + "\nNombre: " + usuario.getNombre() + "\nCorreo: " + usuario.getCorreo() + "\nRol: " + usuario.getRol());
        mostrarMensaje("Ya se editó el usuario");
    }

    private void eliminarUsuario() {
        String idTexto = etIdUsuario.getText().toString().trim();

        if (idTexto.isEmpty()) {
            mostrarMensaje("Ingrese el ID para eliminar el usuario");
            return;
        }

        int id = Integer.parseInt(idTexto);
        Usuario usuario = buscarUsuarioPorId(id);

        if (usuario == null) {
            mostrarMensaje("No existe un usuario con ese ID");
            return;
        }

        listaUsuarios.remove(usuario);

        limpiarCamposUsuario();
        tvResultado.setText("Ya se eliminó el usuario con ID: " + id);
        mostrarMensaje("Ya se eliminó el usuario");
    }

    private void listarUsuarios() {
        if (listaUsuarios.isEmpty()) {
            tvResultado.setText("No hay usuarios registrados.");
            mostrarMensaje("No hay usuarios para listar");
            return;
        }

        StringBuilder texto = new StringBuilder();
        texto.append("Lista de usuarios:\n\n");

        for (Usuario usuario : listaUsuarios) {
            texto.append("ID: ").append(usuario.getId()).append("\n");
            texto.append("Nombre: ").append(usuario.getNombre()).append("\n");
            texto.append("Correo: ").append(usuario.getCorreo()).append("\n");
            texto.append("Rol: ").append(usuario.getRol()).append("\n");
            texto.append("-------------------------\n");
        }

        tvResultado.setText(texto.toString());
        mostrarMensaje("Ya se listaron los usuarios");
    }

    private void asignarRol() {
        String idTexto = etIdRol.getText().toString().trim();

        if (idTexto.isEmpty()) {
            mostrarMensaje("Ingrese el ID del usuario para asignar rol");
            return;
        }

        int id = Integer.parseInt(idTexto);
        Usuario usuario = buscarUsuarioPorId(id);

        if (usuario == null) {
            mostrarMensaje("No existe un usuario con ese ID");
            return;
        }

        String rolSeleccionado = spinnerRoles.getSelectedItem().toString();
        usuario.setRol(rolSeleccionado);

        tvResultado.setText("Rol asignado correctamente:\n\nUsuario: " + usuario.getNombre() + "\nID: " + usuario.getId() + "\nRol: " + usuario.getRol());
        mostrarMensaje("Rol asignado correctamente");
    }

    private void consultarPermisos() {
        String idTexto = etIdRol.getText().toString().trim();

        if (idTexto.isEmpty()) {
            mostrarMensaje("Ingrese el ID del usuario para consultar permisos");
            return;
        }

        int id = Integer.parseInt(idTexto);
        Usuario usuario = buscarUsuarioPorId(id);

        if (usuario == null) {
            mostrarMensaje("No existe un usuario con ese ID");
            return;
        }

        String permisosRol = obtenerPermisosPorRol(usuario.getRol());

        tvResultado.setText("Consulta de permisos:\n\nUsuario: " + usuario.getNombre() + "\nRol: " + usuario.getRol() + "\n\nPermisos:\n" + permisosRol);
        mostrarMensaje("Permisos consultados correctamente");
    }

    private void validarAcceso() {
        String idTexto = etIdRol.getText().toString().trim();

        if (idTexto.isEmpty()) {
            mostrarMensaje("Ingrese el ID del usuario para validar acceso");
            return;
        }

        int id = Integer.parseInt(idTexto);
        Usuario usuario = buscarUsuarioPorId(id);

        if (usuario == null) {
            mostrarMensaje("No existe un usuario con ese ID");
            return;
        }

        String permisoSeleccionado = spinnerPermisos.getSelectedItem().toString();
        boolean tieneAcceso = tienePermiso(usuario.getRol(), permisoSeleccionado);

        if (tieneAcceso) {
            tvResultado.setText("Validación de acceso:\n\nUsuario: " + usuario.getNombre() + "\nRol: " + usuario.getRol() + "\nPermiso consultado: " + permisoSeleccionado + "\n\nResultado: ACCESO PERMITIDO");
            mostrarMensaje("Acceso permitido según rol");
        } else {
            tvResultado.setText("Validación de acceso:\n\nUsuario: " + usuario.getNombre() + "\nRol: " + usuario.getRol() + "\nPermiso consultado: " + permisoSeleccionado + "\n\nResultado: ACCESO DENEGADO");
            mostrarMensaje("Acceso denegado según rol");
        }
    }

    private Usuario buscarUsuarioPorId(int id) {
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }

    private String obtenerPermisosPorRol(String rol) {
        switch (rol) {
            case "Administrador":
                return "- Crear usuarios\n- Editar usuarios\n- Eliminar usuarios\n- Listar usuarios\n- Asignar rol\n- Consultar permisos\n- Validar acceso\n- Ver reportes";
            case "Cajero":
                return "- Listar usuarios\n- Registrar ventas";
            case "Supervisor":
                return "- Listar usuarios\n- Consultar permisos\n- Ver reportes";
            case "Operario":
                return "- Despachar combustible";
            default:
                return "- Este usuario no tiene rol asignado";
        }
    }

    private boolean tienePermiso(String rol, String permiso) {
        switch (rol) {
            case "Administrador":
                return permiso.equals("Crear usuarios")
                        || permiso.equals("Editar usuarios")
                        || permiso.equals("Eliminar usuarios")
                        || permiso.equals("Listar usuarios")
                        || permiso.equals("Asignar rol")
                        || permiso.equals("Consultar permisos")
                        || permiso.equals("Validar acceso")
                        || permiso.equals("Ver reportes");

            case "Cajero":
                return permiso.equals("Listar usuarios")
                        || permiso.equals("Registrar ventas");

            case "Supervisor":
                return permiso.equals("Listar usuarios")
                        || permiso.equals("Consultar permisos")
                        || permiso.equals("Ver reportes");

            case "Operario":
                return permiso.equals("Despachar combustible");

            default:
                return false;
        }
    }

    private void limpiarCamposUsuario() {
        etIdUsuario.setText("");
        etNombreUsuario.setText("");
        etCorreoUsuario.setText("");
    }

    private void mostrarMensaje(String mensaje) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }

    private static class Usuario {
        private int id;
        private String nombre;
        private String correo;
        private String rol;

        public Usuario(int id, String nombre, String correo, String rol) {
            this.id = id;
            this.nombre = nombre;
            this.correo = correo;
            this.rol = rol;
        }

        public int getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        public String getCorreo() {
            return correo;
        }

        public String getRol() {
            return rol;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public void setRol(String rol) {
            this.rol = rol;
        }
    }
}
