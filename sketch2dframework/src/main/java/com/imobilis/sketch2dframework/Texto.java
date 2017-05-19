package com.imobilis.sketch2dframework;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.TypedValue;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * Created by alexs on 07/03/2017.
 */

public class Texto extends Figura {

    private float tamTexto;
    private String string;
    private int cor;
    private int dimensoes[] = new int[]{0,0};
    private Paint paint;
    public Texto(Activity activity,ArrayList<Point> pontos,String string, float tamTexto) {
        super(activity,pontos,false);
        this.tamTexto = tamTexto;
        this.string = string;
        this.cor = Color.BLACK;
        calculaDimensoes();
    }
    public Texto(Activity activity,ArrayList<Point> pontos,String string, float tamTexto,int cor) {
        super(activity,pontos,false);
        this.tamTexto = tamTexto;
        this.string = string;
        this.cor = cor;
        calculaDimensoes();
    }
    public void calculaDimensoes()
    {
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        textPaint.setColor(getCor());
        textPaint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,getTamTexto(), getActivity().getResources().getDisplayMetrics()));
        textPaint.setTextAlign(Paint.Align.LEFT);
        String text = getString();
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        Point tamText = new Point(Math.abs(bounds.left-bounds.right),Math.abs(bounds.top-bounds.bottom));
        setDimensoes(new int[]{tamText.x,tamText.y});
        setPaint(textPaint);
    }
    public void updateCanvas()
    {
        if(getView()!=null)
        {
            double dif = 0.1*getTamTexto();
            getView().setLayoutParams(new FrameLayout.LayoutParams((int)(getDimensoes()[0]+dif),(int)(getDimensoes()[1]+dif)));
        }
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
        calculaDimensoes();
        updateCanvas();
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
        calculaDimensoes();
        updateCanvas();
    }

    public int getCor() {
        return cor;
    }

    public void setCor(int cor) {
        this.cor = cor;
        calculaDimensoes();
    }

    public int[] getDimensoes() {
        return dimensoes;
    }

    public void setDimensoes(int[] dimensoes) {
        this.dimensoes = dimensoes;
    }

    @Override
    public Paint getPaint() {
        return paint;
    }

    @Override
    public void setPaint(Paint paint) {
        this.paint = paint;
    }
}

