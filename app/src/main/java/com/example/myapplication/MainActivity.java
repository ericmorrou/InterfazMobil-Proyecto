package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText etUsuario, etContrasena;
    private Button btnLogin;
    private TextView tvCrearCuenta;
    private RequestQueue queue;
    private static final String URL_LOGIN = "http://10.0.2.2/jesuscrust/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsuario = findViewById(R.id.editTextUser);
        etContrasena = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.buttonLogin);
        tvCrearCuenta = findViewById(R.id.textViewCreateAccount);

        queue = Volley.newRequestQueue(this);

        btnLogin.setOnClickListener(v -> intentarLogin());

        tvCrearCuenta.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void intentarLogin() {
        final String usuario = etUsuario.getText().toString().trim();
        final String contrasena = etContrasena.getText().toString().trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, URL_LOGIN,
                response -> {
                    Log.d("RespuestaServidor", "Response: " + response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String estado = jsonObject.getString("estado");
                        String mensaje = jsonObject.getString("mensaje");

                        Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_LONG).show();

                        if (estado.equals("ok")) {
                            JSONObject datosUsuario = jsonObject.getJSONObject("datos_usuario");
                            String nombreCompleto = datosUsuario.getString("nombre_completo");
                            String urlImagen = datosUsuario.getString("url_imagen");

                            UserManager.getInstance().setUserData(nombreCompleto, urlImagen);

                            Intent intent = new Intent(MainActivity.this, StoreActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(MainActivity.this, "Error: La respuesta del servidor no es válida.", Toast.LENGTH_SHORT).show();
                        Log.e("ErrorJSON", "Fallo al procesar JSON: " + e.getMessage());
                    }
                },
                error -> {
                    Log.e("ErrorVolley", "Error en la conexión: " + error.toString());
                    Toast.makeText(MainActivity.this, "Error de red. Asegúrate de que XAMPP está activo y la IP es correcta.", Toast.LENGTH_LONG).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("usuario", usuario);
                params.put("contrasena", contrasena);
                return params;
            }
        };
        queue.add(stringRequest);
    }
}
