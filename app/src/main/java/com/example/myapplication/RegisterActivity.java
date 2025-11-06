package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton; // <-- 1. ¡IMPORTANTE! Asegúrate de importar ImageButton
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNombreCompleto, etUsuario, etContrasena;
    private Button btnConfirmarRegistro;
    private ImageButton botonVolver; // <-- 2. Declara la variable para el botón de volver

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etNombreCompleto = findViewById(R.id.editTextNombreCompleto);
        etUsuario = findViewById(R.id.editTextUsuarioRegister);
        etContrasena = findViewById(R.id.editTextContrasenaRegister);
        btnConfirmarRegistro = findViewById(R.id.buttonConfirmarRegistro);

        botonVolver = findViewById(R.id.boton_volver_register);
        botonVolver.setOnClickListener(v -> {
            finish(); // Cierra esta actividad y regresa a la anterior
        });

        btnConfirmarRegistro.setOnClickListener(v -> {
            String nombreCompleto = etNombreCompleto.getText().toString();
            String usuario = etUsuario.getText().toString();
            String contrasena = etContrasena.getText().toString();

            if (!nombreCompleto.isEmpty() && !usuario.isEmpty() && !contrasena.isEmpty()) {
                registrarUsuario(nombreCompleto, usuario, contrasena);
            } else {
                Toast.makeText(RegisterActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registrarUsuario(final String nombreCompleto, final String usuario, final String contrasena) {
        String url = "http://192.168.1.35/login/register.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    if (response.trim().equals("success")) {
                        Toast.makeText(RegisterActivity.this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                        finish(); // Opcional: También puedes volver al login tras un registro exitoso
                    } else {
                        Toast.makeText(RegisterActivity.this, "Error: " + response, Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(RegisterActivity.this, "Error de conexión: " + error.toString(), Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombre_completo", nombreCompleto);
                params.put("usuario", usuario);
                params.put("contrasena", contrasena);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
