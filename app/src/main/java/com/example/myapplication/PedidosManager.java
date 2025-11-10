package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class PedidosManager {

    private static final String PREFS_NAME = "PedidosPrefs";
    private static final String PEDIDOS_KEY = "listaDePedidos";

    private static void guardarPedidos(Context context, List<Pedido> pedidos) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(pedidos);
            oos.close();
            String encodedString = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            editor.putString(PEDIDOS_KEY, encodedString);
            editor.apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Pedido> getListaPedidos(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String encodedString = prefs.getString(PEDIDOS_KEY, null);

        if (encodedString == null) {
            return new ArrayList<>();
        }

        try {
            byte[] bytes = Base64.decode(encodedString, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            List<Pedido> pedidos = (List<Pedido>) ois.readObject();
            return pedidos;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void agregarPedido(Context context, Pedido nuevoPedido) {
        List<Pedido> pedidosActuales = getListaPedidos(context);
        pedidosActuales.add(0, nuevoPedido);
        guardarPedidos(context, pedidosActuales);
    }
}
