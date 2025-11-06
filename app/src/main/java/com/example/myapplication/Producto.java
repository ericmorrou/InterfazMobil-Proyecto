package com.example.myapplication;

public class Producto {

    private String nombre;
    private String precio;
    private int imagenResId; // Guardaremos el ID de la imagen (ej: R.drawable.brazodejudas)    // El constructor para crear un nuevo producto
    public Producto(String nombre, String precio, int imagenResId) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagenResId = imagenResId;
    } // <--- La llave que faltaba por cerrar aquí

    // Métodos para poder coger los datos del producto desde fuera
    public String getNombre() {
        return nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public int getImagenResId() {
        return imagenResId;
    }
}
