package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class PedidosManager {

    private static final List<Pedido> listaPedidos = new ArrayList<>();

    public static void agregarPedido(Pedido pedido) {
        listaPedidos.add(0, pedido);
    }

    public static List<Pedido> getListaPedidos() {
        return listaPedidos;
    }

    public static void limpiarPedidos() {
        listaPedidos.clear();
    }
}
