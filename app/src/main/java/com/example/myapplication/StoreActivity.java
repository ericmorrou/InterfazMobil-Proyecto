package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class StoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        Button botonAddJudas = findViewById(R.id.boton_add_1);
        Button botonAddPecado = findViewById(R.id.boton_add_2);

        botonAddJudas.setOnClickListener(v -> {
            Producto producto = new Producto("Brazo de Judas", "8.99", R.drawable.brazodejudas);
            CarritoManager.agregarProducto(producto);
            Toast.makeText(StoreActivity.this, "Brazo de Judas añadido al carrito", Toast.LENGTH_SHORT).show();
        });

        botonAddPecado.setOnClickListener(v -> {
            Producto producto = new Producto("Pecado Original", "4.50", R.drawable.croissant);
            CarritoManager.agregarProducto(producto);
            Toast.makeText(StoreActivity.this, "Pecado Original añadido al carrito", Toast.LENGTH_SHORT).show();
        });

        configurarNavegacion();
    }

    private void configurarNavegacion() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                return true;
            }

            Intent proximaActividad = null;
            if (itemId == R.id.nav_menu) {
                proximaActividad = new Intent(this, MenuActivity.class);
            } else if (itemId == R.id.nav_cart) {
                proximaActividad = new Intent(this, CartActivity.class);
            } else if (itemId == R.id.nav_orders) {
                proximaActividad = new Intent(this, OrdersActivity.class);
            } else if (itemId == R.id.nav_profile) {
                proximaActividad = new Intent(this, ProfileActivity.class);
            }

            if (proximaActividad != null) {
                Bundle userData = UserManager.getInstance().getUserData();
                if (userData != null) {
                    proximaActividad.putExtras(userData);
                }

                startActivity(proximaActividad);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
            return true;
        });
    }
}
