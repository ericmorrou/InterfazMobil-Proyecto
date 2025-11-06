package com.example.myapplication;

import java.io.Serializable;

public class Pedido implements Serializable {
    private String estado;
    private String fecha;
    private String precioTotal;
    private int imagenResId;

    public Pedido(String estado, String fecha, String precioTotal, int imagenResId) {
        this.estado = estado;
        this.fecha = fecha;
        this.precioTotal = precioTotal;
        this.imagenResId = imagenResId;
    }

    public String getEstado() {
        return estado;
    }

    public String getFecha() {
        return fecha;
    }

    public String getPrecioTotal() {
        return precioTotal;
    }

    public int getImagenResId() {
        return imagenResId;
    }
}
