package com.example.myapplication;

public class Pedido {

    private String estado;
    private String fecha;
    private String total;
    private int imagenResId; // <--- ¡EL CAMPO QUE NECESITAMOS!

    // Constructor modificado para aceptar la imagen
    public Pedido(String estado, String fecha, String total, int imagenResId) {
        this.estado = estado;
        this.fecha = fecha;
        this.total = total;
        this.imagenResId = imagenResId; // Guardamos el ID de la imagen
    }

    // --- GETTERS para cada campo ---

    public String getEstado() {
        return estado;
    }

    public String getFecha() {
        return fecha;
    }

    public String getTotal() {
        return total;
    }

    public int getImagenResId() {
        return imagenResId; // <--- Getter para la imagen
    }
}
