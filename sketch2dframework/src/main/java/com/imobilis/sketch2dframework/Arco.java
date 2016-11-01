package com.imobilis.sketch2dframework;

import android.app.Activity;
import android.graphics.Point;

import java.util.ArrayList;

/**
 * Created by Alexsander on 27/10/2016.
 */
public class Arco extends Figura {

    private float raio;
    private float angleInit, degrees;
    public Arco(Activity activity, ArrayList<Point> pontos, float raio, float angleInit, float degrees, boolean editavel) {
        super(activity, pontos,editavel);
        this.raio = raio;
        this.degrees = degrees;
        this.angleInit = angleInit;
    }

    public float getRaio() {
        return raio;
    }

    public void setRaio(float raio) {
        this.raio = raio;
    }

    public float getAngleInit() {
        return angleInit;
    }

    public void setAngleInit(float angleInit) {
        this.angleInit = angleInit;
    }

    public float getDegrees() {
        return degrees;
    }

    public void setDegrees(float degrees) {
        this.degrees = degrees;
    }

    @Override
    public boolean isDentro(Point ponto) {
        return false;
    }

    @Override
    public ArrayList<Point[]> pontoMaisProximo(Figura f, float offsetX, float offsetY) {
        return null;
    }
}
