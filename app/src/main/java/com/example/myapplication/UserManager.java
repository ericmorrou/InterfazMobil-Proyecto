package com.example.myapplication;

import android.os.Bundle;

public class UserManager {

    private static UserManager instance;
    private Bundle userData = null;private UserManager() {
    }

    public static synchronized UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }
    public void setUserData(String nombreCompleto, String urlImagen) {
        if (userData == null) {
            userData = new Bundle();
        }
        userData.putString("NOMBRE_COMPLETO", nombreCompleto);
        userData.putString("URL_IMAGEN", urlImagen);
    }
    public Bundle getUserData() {
        return this.userData;
    }
    public void clearData() {
        this.userData = null;
    }
}
