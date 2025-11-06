package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imagenPerfil;
    private TextView nombreUsuarioPerfil;
    private Spinner spinnerTiendas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BitmapManager.inicializar(this);

        imagenPerfil = findViewById(R.id.imagen_perfil);
        nombreUsuarioPerfil = findViewById(R.id.nombre_usuario_perfil);
        spinnerTiendas = findViewById(R.id.spinner_tiendas);

        cargarDatosDeUsuario();
        configurarNavegacion();
        cargarTiendasEnSpinner();
    }

    private void cargarDatosDeUsuario() {
        Bundle userData = UserManager.getInstance().getUserData();
        if (userData != null) {
            nombreUsuarioPerfil.setText(userData.getString("NOMBRE_COMPLETO"));
            Glide.with(this)
                    .load(R.drawable.default_user)
                    .circleCrop()
                    .into(imagenPerfil);
        } else {
            Toast.makeText(this, "No se pudieron cargar los datos del usuario.", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarTiendasEnSpinner() {
        String url = "http://10.0.2.2/get_tiendas.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        List<String> nombresLocalidades = new ArrayList<>();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresLocalidades);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTiendas.setAdapter(spinnerAdapter);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        if ("ok".equals(response.getString("estado"))) {
                            JSONArray tiendasArray = response.getJSONArray("tiendas");
                            nombresLocalidades.clear();
                            for (int i = 0; i < tiendasArray.length(); i++) {
                                JSONObject tienda = tiendasArray.getJSONObject(i);
                                nombresLocalidades.add(tienda.getString("localidad"));
                            }
                            spinnerAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(this, "Error: " + response.getString("mensaje"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Log.e("SpinnerError", "Error al parsear el JSON de tiendas", e);
                        Toast.makeText(this, "Error de formato en la respuesta del servidor.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("SpinnerError", "Error de red al cargar tiendas", error);
                    Toast.makeText(this, "No se pudo conectar para cargar las tiendas.", Toast.LENGTH_LONG).show();
                }
        );
        queue.add(jsonObjectRequest);
    }

    private void configurarNavegacion() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_profile);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem itemHome = menu.findItem(R.id.nav_home);
        MenuItem itemMenu = menu.findItem(R.id.nav_menu);
        MenuItem itemCart = menu.findItem(R.id.nav_cart);
        MenuItem itemOrders = menu.findItem(R.id.nav_orders);

        if (itemHome != null) itemHome.setIcon(BitmapManager.getIcono(this, "home"));
        if (itemMenu != null) itemMenu.setIcon(BitmapManager.getIcono(this, "menu"));
        if (itemCart != null) itemCart.setIcon(BitmapManager.getIcono(this, "cart"));
        if (itemOrders != null) itemOrders.setIcon(BitmapManager.getIcono(this, "camion"));

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_profile) return true;

            Intent proximaActividad = null;
            if (itemId == R.id.nav_home) {
                proximaActividad = new Intent(this, StoreActivity.class);
            } else if (itemId == R.id.nav_menu) {
                proximaActividad = new Intent(this, MenuActivity.class);
            } else if (itemId == R.id.nav_cart) {
                proximaActividad = new Intent(this, CartActivity.class);
            } else if (itemId == R.id.nav_orders) {
                proximaActividad = new Intent(this, OrdersActivity.class);
            }

            if (proximaActividad != null) {
                proximaActividad.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                Bundle userData = UserManager.getInstance().getUserData();
                if (userData != null) proximaActividad.putExtras(userData);
                startActivity(proximaActividad);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            return true;
        });
    }
}
