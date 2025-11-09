package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText etNombreCompleto, etNombreUsuario, etContrasena;
    private Button btnConfirmarRegistro;
    private ImageButton botonVolver; // El botón para volver

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // --- Búsqueda de Vistas (Esto ya lo tenías bien) ---
        etNombreCompleto = findViewById(R.id.editTextNombreCompleto);
        etNombreUsuario = findViewById(R.id.editTextUsuarioRegister);
        etContrasena = findViewById(R.id.editTextContrasenaRegister);
        btnConfirmarRegistro = findViewById(R.id.buttonConfirmarRegistro);
        botonVolver = findViewById(R.id.boton_volver_register);

        // ==================================================================
        // SOLUCIÓN: AÑADIR ESTE BLOQUE PARA DARLE VIDA AL BOTÓN DE VOLVER
        // ==================================================================
        botonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // El método finish() cierra la actividad actual (RegisterActivity)
                // y Android automáticamente muestra la actividad anterior en la pila,
                // que en este caso es MainActivity (la de inicio de sesión).
                finish();
            }
        });
        // ==================================================================


        // --- Lógica del botón de registro (Esto ya lo tenías bien) ---
        btnConfirmarRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nombreCompleto = etNombreCompleto.getText().toString().trim();
                final String nombreUsuario = etNombreUsuario.getText().toString().trim();
                final String contrasena = etContrasena.getText().toString().trim();

                if (nombreCompleto.isEmpty() || nombreUsuario.isEmpty() || contrasena.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    registrarUsuario(nombreCompleto, nombreUsuario, contrasena);
                }
            }
        });
    }

    private void registrarUsuario(final String nombreCompleto, final String nombreUsuario, final String contrasena) {
        // Tu método de registro con Volley está perfecto, no se necesita cambiar nada aquí.
        String url = "http://10.0.2.2/jesuscrust/registro.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String status = jsonResponse.getString("status");
                            String message = jsonResponse.getString("message");

                            if ("success".equals(status)) {
                                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish(); // Cierra la pantalla de registro tras un éxito
                            } else {
                                Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(RegisterActivity.this, "Error al procesar la respuesta del servidor", Toast.LENGTH_LONG).show();
                            Log.e("JSONError", "Error al parsear JSON: " + response, e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String serverResponse = "Sin respuesta del servidor o error de red.";

                        if (error.networkResponse != null && error.networkResponse.data != null) {
                            try {
                                serverResponse = new String(error.networkResponse.data, "UTF-8");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        Toast.makeText(RegisterActivity.this, "ERROR: " + serverResponse, Toast.LENGTH_LONG).show();

                        Log.e("DEBUG_REGISTRO", "Código de estado: " + (error.networkResponse != null ? error.networkResponse.statusCode : "N/A"));
                        Log.e("DEBUG_REGISTRO", "Respuesta de error del servidor: " + serverResponse);
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nombre_completo", nombreCompleto);
                params.put("nombre_usuario", nombreUsuario);
                params.put("password", contrasena);
                return params;
            }
        };

        queue.add(stringRequest);
    }
}
