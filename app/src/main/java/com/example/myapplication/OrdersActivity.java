package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {
    private RecyclerView recyclerViewPedidos;
    private PedidosAdapter pedidosAdapter;
    private TextView textoSinPedidos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        recyclerViewPedidos = findViewById(R.id.recycler_view_pedidos);
        textoSinPedidos = findViewById(R.id.texto_sin_pedidos);

        configurarRecyclerView();
        configurarNavegacion();
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarVistaPedidos();
    }

    private void configurarNavegacion() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_orders);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_orders) return true;

            Intent proximaActividad = null;
            if (itemId == R.id.nav_home) {
                proximaActividad = new Intent(this, StoreActivity.class);
            } else if (itemId == R.id.nav_menu) {
                proximaActividad = new Intent(this, MenuActivity.class);
            } else if (itemId == R.id.nav_cart) {
                proximaActividad = new Intent(this, CartActivity.class);
            } else if (itemId == R.id.nav_profile) {
                proximaActividad = new Intent(this, ProfileActivity.class);
            }

            if (proximaActividad != null) {
                proximaActividad.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                Bundle userData = UserManager.getInstance().getUserData();
                if (userData != null) {
                    proximaActividad.putExtras(userData);
                }
                startActivity(proximaActividad);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            return true;
        });
    }

    private void configurarRecyclerView() {
        recyclerViewPedidos.setLayoutManager(new LinearLayoutManager(this));
    }

    private void actualizarVistaPedidos() {
        List<Pedido> listaDePedidos = PedidosManager.getListaPedidos(OrdersActivity.this);

        if (listaDePedidos == null || listaDePedidos.isEmpty()) {
            recyclerViewPedidos.setVisibility(View.GONE);
            textoSinPedidos.setVisibility(View.VISIBLE);
        } else {
            recyclerViewPedidos.setVisibility(View.VISIBLE);
            textoSinPedidos.setVisibility(View.GONE);
            if (pedidosAdapter == null) {
                pedidosAdapter = new PedidosAdapter(listaDePedidos, this);
                recyclerViewPedidos.setAdapter(pedidosAdapter);
            } else {
                pedidosAdapter.actualizarPedidos(listaDePedidos);
            }
        }
    }
}
