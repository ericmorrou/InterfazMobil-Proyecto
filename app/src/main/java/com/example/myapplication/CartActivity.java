package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {
    private LinearLayout contenedorProductos;
    private TextView textoSubtotal, textoDelivery, textoTotal;
    private Button buttonOrderNow;
    private TextView textoCarritoVacio;
    private Group groupContenidoCarrito;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // AÑADIDO: Inicializamos el BitmapManager
        BitmapManager.inicializar(this);

        contenedorProductos = findViewById(R.id.contenedor_productos_carrito);
        textoSubtotal = findViewById(R.id.texto_subtotal);
        textoDelivery = findViewById(R.id.texto_delivery);
        textoTotal = findViewById(R.id.texto_total);
        buttonOrderNow = findViewById(R.id.button_order_now);
        textoCarritoVacio = findViewById(R.id.texto_carrito_vacio);
        groupContenidoCarrito = findViewById(R.id.group_contenido_carrito);

        buttonOrderNow.setOnClickListener(v -> {
            List<Producto> productosEnCarrito = CarritoManager.getProductos();
            if (productosEnCarrito == null || productosEnCarrito.isEmpty()) {
                Toast.makeText(this, "El carrito está vacío.", Toast.LENGTH_SHORT).show();
                return;
            }
            String totalDelPedido = textoTotal.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String fechaActual = sdf.format(new Date());
            int imagenDelPedido = productosEnCarrito.get(0).getImagenResId();
            Pedido nuevoPedido = new Pedido("Delivering", fechaActual, totalDelPedido, imagenDelPedido);
            PedidosManager.agregarPedido(nuevoPedido);
            CarritoManager.limpiarCarrito();
            Toast.makeText(this, "¡Pedido realizado con éxito!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(CartActivity.this, OrdersActivity.class);
            startActivity(intent);
            finish();
        });

        actualizarCarritoUI();
        // AÑADIDO: Llamamos a la nueva función de navegación
        configurarNavegacion();
    }

    // AÑADIDO: La función de navegación copiada y pegada
    private void configurarNavegacion() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_cart);

        // Lógica para poner los iconos del BitmapManager
        Menu menu = bottomNavigationView.getMenu();
        MenuItem itemHome = menu.findItem(R.id.nav_home);
        MenuItem itemMenu = menu.findItem(R.id.nav_menu);
        MenuItem itemCart = menu.findItem(R.id.nav_cart);
        MenuItem itemOrders = menu.findItem(R.id.nav_orders);

        if (itemHome != null) itemHome.setIcon(BitmapManager.getIcono(this, "home"));
        if (itemMenu != null) itemMenu.setIcon(BitmapManager.getIcono(this, "menu"));
        if (itemCart != null) itemCart.setIcon(BitmapManager.getIcono(this, "cart"));
        if (itemOrders != null) itemOrders.setIcon(BitmapManager.getIcono(this, "camion"));

        // Lógica para los clicks
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_cart) return true;

            Intent proximaActividad = null;
            if (itemId == R.id.nav_home) {
                proximaActividad = new Intent(this, StoreActivity.class);
            } else if (itemId == R.id.nav_menu) {
                proximaActividad = new Intent(this, MenuActivity.class);
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

    private void actualizarCarritoUI() {
        List<Producto> productos = CarritoManager.getProductos();
        contenedorProductos.removeAllViews();

        if (productos == null || productos.isEmpty()) {
            textoCarritoVacio.setVisibility(View.VISIBLE);
            groupContenidoCarrito.setVisibility(View.GONE);
        } else {
            textoCarritoVacio.setVisibility(View.GONE);
            groupContenidoCarrito.setVisibility(View.VISIBLE);
            LayoutInflater inflater = LayoutInflater.from(this);
            double subtotal = 0;
            for (int i = 0; i < productos.size(); i++) {
                Producto producto = productos.get(i);
                View vistaProducto = inflater.inflate(R.layout.item_carrito, contenedorProductos, false);
                TextView nombreProducto = vistaProducto.findViewById(R.id.nombre_producto_item);
                TextView precioProducto = vistaProducto.findViewById(R.id.precio_producto_item);
                ImageView imagenProducto = vistaProducto.findViewById(R.id.imagen_producto_item);
                Button botonQuitar = vistaProducto.findViewById(R.id.boton_quit_item);
                nombreProducto.setText(producto.getNombre());
                precioProducto.setText("$" + producto.getPrecio());
                Glide.with(this).load(producto.getImagenResId()).into(imagenProducto);

                final int posicionActual = i;
                botonQuitar.setOnClickListener(v -> {
                    CarritoManager.quitarProductoPorPosicion(posicionActual);
                    actualizarCarritoUI();
                });
                contenedorProductos.addView(vistaProducto);
                try {
                    subtotal += Double.parseDouble(producto.getPrecio());
                } catch (NumberFormatException e) { /* Ignorar */ }
            }
            double delivery = 3.50;
            double total = subtotal + delivery;
            textoSubtotal.setText(String.format(Locale.US, "$%.2f", subtotal));
            textoDelivery.setText(String.format(Locale.US, "$%.2f", delivery));
            textoTotal.setText(String.format(Locale.US, "$%.2f", total));
        }
    }
}
