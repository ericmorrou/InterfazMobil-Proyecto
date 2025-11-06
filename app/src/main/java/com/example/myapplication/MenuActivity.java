package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton; // Importante para tu tipo de botón

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // --- ENLAZAMOS LOS BOTONES USANDO LOS IDS REALES DE TU XML ---
        MaterialButton botonAddProducto1 = findViewById(R.id.boton_add_1);
        MaterialButton botonAddProducto2 = findViewById(R.id.boton_add_2);
        Button botonOrderNowHeader = findViewById(R.id.button_order_now);

        // --- LÓGICA PARA EL PRIMER PRODUCTO: Brazo de Judas ---
        botonAddProducto1.setOnClickListener(v -> {
            // Creamos el producto con sus datos exactos
            Producto producto = new Producto("Brazo de Judas", "8.99", R.drawable.brazodejudas);

            // Lo añadimos al carrito
            CarritoManager.agregarProducto(producto);

            // Informamos al usuario
            Toast.makeText(this, "Brazo de Judas añadido al carrito", Toast.LENGTH_SHORT).show();
        });

        // --- LÓGICA PARA EL SEGUNDO PRODUCTO: Pecado Original ---
        botonAddProducto2.setOnClickListener(v -> {
            Producto producto = new Producto("Pecado Original", "4.50", R.drawable.croissant);
            CarritoManager.agregarProducto(producto);
            Toast.makeText(this, "Pecado Original añadido al carrito", Toast.LENGTH_SHORT).show();
        });

        // --- LÓGICA PARA EL BOTÓN 'ORDER NOW' DE LA CABECERA ---
        // Este botón debería llevar directamente al carrito.
        botonOrderNowHeader.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, CartActivity.class);
            startActivity(intent);
            // No usamos finish() aquí para que el usuario pueda volver atrás si quiere
        });

        // --- CONFIGURAMOS LA NAVEGACIÓN INFERIOR (esto ya estaba bien) ---
        configurarNavegacion();
    }

    private void configurarNavegacion() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_menu);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_menu) return true;

            Intent proximaActividad = null;
            if (itemId == R.id.nav_home) {
                proximaActividad = new Intent(this, StoreActivity.class);
            } else if (itemId == R.id.nav_cart) {
                proximaActividad = new Intent(this, CartActivity.class);
            } else if (itemId == R.id.nav_orders) {
                proximaActividad = new Intent(this, OrdersActivity.class);
            } else if (itemId == R.id.nav_profile) {
                proximaActividad = new Intent(this, ProfileActivity.class);
            }

            if (proximaActividad != null) {
                startActivity(proximaActividad);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
            return true;
        });
    }
}
