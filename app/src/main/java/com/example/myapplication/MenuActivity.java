package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        MaterialButton botonAddProducto1 = findViewById(R.id.boton_add_1);
        MaterialButton botonAddProducto2 = findViewById(R.id.boton_add_2);
        Button botonOrderNowHeader = findViewById(R.id.button_order_now);

        botonAddProducto1.setOnClickListener(v -> {
            Producto producto = new Producto("Brazo de Judas", "8.99", R.drawable.brazodejudas);
            CarritoManager.agregarProducto(producto);
            Toast.makeText(this, "Brazo de Judas añadido al carrito", Toast.LENGTH_SHORT).show();
        });

        botonAddProducto2.setOnClickListener(v -> {
            Producto producto = new Producto("Pecado Original", "4.50", R.drawable.croissant);
            CarritoManager.agregarProducto(producto);
            Toast.makeText(this, "Pecado Original añadido al carrito", Toast.LENGTH_SHORT).show();
        });

        botonOrderNowHeader.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, CartActivity.class);
            startActivity(intent);
        });

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
