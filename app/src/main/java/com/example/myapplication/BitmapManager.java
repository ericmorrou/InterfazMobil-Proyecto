package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class BitmapManager {

    private static Bitmap spriteSheet = null;
    private static Rect rectHome, rectCart, rectMenu, rectCamion;

    public static void inicializar(Context context) {
        if (spriteSheet != null) {
            return;
        }

        Bitmap bmpHome = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_home);
        Bitmap bmpCart = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_cart);
        Bitmap bmpMenu = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_menu);
        Bitmap bmpCamion = BitmapFactory.decodeResource(context.getResources(), R.drawable.camion);

        int anchoTotal = bmpHome.getWidth() + bmpCart.getWidth() + bmpMenu.getWidth() + bmpCamion.getWidth();
        int altoMaximo = Math.max(Math.max(bmpHome.getHeight(), bmpCart.getHeight()), Math.max(bmpMenu.getHeight(), bmpCamion.getHeight()));

        spriteSheet = Bitmap.createBitmap(anchoTotal, altoMaximo, Bitmap.Config.ARGB_8888);

        Canvas lienzo = new Canvas(spriteSheet);

        int xActual = 0;

        lienzo.drawBitmap(bmpHome, xActual, 0, null);
        rectHome = new Rect(xActual, 0, xActual + bmpHome.getWidth(), bmpHome.getHeight());
        xActual += bmpHome.getWidth();

        lienzo.drawBitmap(bmpCart, xActual, 0, null);
        rectCart = new Rect(xActual, 0, xActual + bmpCart.getWidth(), bmpCart.getHeight());
        xActual += bmpCart.getWidth();

        lienzo.drawBitmap(bmpMenu, xActual, 0, null);
        rectMenu = new Rect(xActual, 0, xActual + bmpMenu.getWidth(), bmpMenu.getHeight());
        xActual += bmpMenu.getWidth();

        lienzo.drawBitmap(bmpCamion, xActual, 0, null);
        rectCamion = new Rect(xActual, 0, xActual + bmpCamion.getWidth(), bmpCamion.getHeight());
    }

    public static Drawable getIcono(Context context, String nombre) {
        if (spriteSheet == null) {
            inicializar(context);
        }

        Rect rectanguloFuente;

        switch (nombre.toLowerCase()) {
            case "home":
                rectanguloFuente = rectHome;
                break;
            case "cart":
                rectanguloFuente = rectCart;
                break;
            case "menu":
                rectanguloFuente = rectMenu;
                break;
            case "camion":
                rectanguloFuente = rectCamion;
                break;
            default:
                return context.getDrawable(R.drawable.ic_launcher_foreground);
        }

        if (rectanguloFuente == null) {
            return context.getDrawable(R.drawable.ic_launcher_foreground);
        }

        Bitmap spriteRecortado = Bitmap.createBitmap(
                spriteSheet,
                rectanguloFuente.left,
                rectanguloFuente.top,
                rectanguloFuente.width(),
                rectanguloFuente.height()
        );

        return new BitmapDrawable(context.getResources(), spriteRecortado);
    }
}
