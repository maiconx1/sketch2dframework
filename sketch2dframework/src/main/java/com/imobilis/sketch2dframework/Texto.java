package com.imobilis.sketch2dframework;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import java.util.ArrayList;

/**
 * Created by alexs on 07/03/2017.
 */

public class Texto extends Figura {

    private float tamTexto;
    private String string;
    private int cor;
    private int dimensoes[] = new int[]{0,0};
    public Texto(Activity activity,ArrayList<Point> pontos,String string, float tamTexto) {
        super(activity,pontos,false);
        this.tamTexto = tamTexto;
        this.string = string;
        this.cor = Color.BLACK;
    }
    public Texto(Activity activity,ArrayList<Point> pontos,String string, float tamTexto,int cor) {
        super(activity,pontos,false);
        this.tamTexto = tamTexto;
        this.string = string;
        this.cor = cor;
    }
    @Override
    public boolean isDentro(Point ponto) {
        return false;
    }

    @Override
    public ArrayList<Point[]> pontoMaisProximo(Figura f, float offsetX, float offsetY) {
        return null;
    }

    public float getTamTexto() {
        return tamTexto;
    }

    public void setTamTexto(float tamTexto) {
        this.tamTexto = tamTexto;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public int getCor() {
        return cor;
    }

    public void setCor(int cor) {
        this.cor = cor;
    }

    public int[] getDimensoes() {
        return dimensoes;
    }

    public void setDimensoes(int[] dimensoes) {
        this.dimensoes = dimensoes;
    }
}

