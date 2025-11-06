package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class CarritoManager {

    private static final List<Producto> productosEnCarrito = new ArrayList<>();

    public static void agregarProducto(Producto producto) {
        productosEnCarrito.add(producto);
    }

    public static List<Producto> getProductos() {
        return productosEnCarrito;
    }

    public static void quitarProducto(Producto producto) {
        productosEnCarrito.remove(producto);
    }

    public static void quitarProductoPorPosicion(int posicion) {
        if (posicion >= 0 && posicion < productosEnCarrito.size()) {
            productosEnCarrito.remove(posicion);
        }
    }

    public static void limpiarCarrito() {
        productosEnCarrito.clear();
    }
}
