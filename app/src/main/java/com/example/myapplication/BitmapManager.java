package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import java.util.HashMap;
import java.util.Map;

public class BitmapManager {

    private static final Map<String, Bitmap> cacheDeIconos = new HashMap<>();

    private static final String[] nombresDeIconos = {"home", "menu", "cart", "camion", "profile"};

    public static void inicializar(Context context) {
        if (!cacheDeIconos.isEmpty()) {
            return;
        }

        for (String nombreIcono : nombresDeIconos) {
            int resourceId = context.getResources().getIdentifier(nombreIcono, "drawable", context.getPackageName());

            if (resourceId != 0) {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
                cacheDeIconos.put(nombreIcono, bitmap);
            }
        }
    }
    public static BitmapDrawable getIcono(Context context, String nombreIcono) {
        Bitmap bitmap = cacheDeIconos.get(nombreIcono);
        if (bitmap != null) {
            return new BitmapDrawable(context.getResources(), bitmap);
        }
        return null;
    }
}
